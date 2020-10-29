package com.alphawallet.app.ui;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alphawallet.app.C;
import com.alphawallet.app.R;
import com.alphawallet.app.entity.QRResult;
import com.alphawallet.app.entity.Wallet;
import com.alphawallet.app.entity.tokens.Token;
import com.alphawallet.app.entity.tokens.TokenInfo;
import com.alphawallet.app.repository.TokenRepositoryType;
import com.alphawallet.app.ui.widget.OnQRCodeScannedListener;
import com.alphawallet.app.ui.widget.entity.AmountEntryItem;
import com.alphawallet.app.ui.widget.entity.ENSHandler;
import com.alphawallet.app.ui.zxing.FullScannerFragment;
import com.alphawallet.app.util.BalanceUtils;
import com.alphawallet.app.util.KeyboardUtils;
import com.alphawallet.app.util.Utils;
import com.alphawallet.app.viewmodel.SendViewModel;
import com.alphawallet.app.widget.AWalletAlertDialog;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import org.web3j.utils.Convert;

import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.alphawallet.app.C.Key.WALLET;
import static com.alphawallet.token.tools.Convert.getEthString;

public class BuyUpcActivity extends BaseActivity implements OnQRCodeScannedListener {
    private static final int BARCODE_READER_REQUEST_CODE = 1;
    @Inject
    SendViewModel viewModel;



    private static final int RC_HANDLE_CAMERA_PERM = 2;
    public static final int RC_HANDLE_IMAGE_PICKUP = 3;

    public static final int DENY_PERMISSION = 1;
    protected TokenRepositoryType tokenRepository;

    private FullScannerFragment fullScannerFragment;

    private TextView flashButton;
    private TextView myAddressButton;
    private TextView browseButton;
    private Disposable disposable;
    private AWalletAlertDialog dialog;
    private ImageButton scanQrImageView;
    private TextView tokenBalanceText;
    private TextView tokenSymbolText;

    private TextView upcRaw;
    private TextView totalBalance;
    private TextView currentStaker;
    private TextView amountStaked;

    private TextView pasteText;
    private Button nextBtn;
    private String currentAmount;
    private QRResult currentResult;

    private String myAddress;
    private int decimals;
    private String symbol;
    private Wallet wallet;
    private Token token;
    private String contractAddress;
    private ENSHandler ensHandler;
    private Handler handler;
    private TextView chainName;
    private int currentChain;
    private AmountEntryItem amountInput;

    @Override
    public void onCreate(Bundle state)
    {
        super.onCreate(state);
        setContentView(R.layout.activity_buy_upc);
        initView();
/*
        //see how the send page accepts these parameters from wherever it is called
        handler = new Handler();

        contractAddress = getIntent().getStringExtra(C.EXTRA_CONTRACT_ADDRESS);

        decimals = getIntent().getIntExtra(C.EXTRA_DECIMALS, C.ETHER_DECIMALS);
        symbol = getIntent().getStringExtra(C.EXTRA_SYMBOL);
        symbol = symbol == null ? C.ETH_SYMBOL : symbol;
        wallet = getIntent().getParcelableExtra(WALLET);
        token = getIntent().getParcelableExtra(C.EXTRA_TOKEN_ID);
        QRResult result = getIntent().getParcelableExtra(C.EXTRA_AMOUNT);
        currentChain = getIntent().getIntExtra(C.EXTRA_NETWORKID, 1);
        myAddress = wallet.address;

        setupTokenContent();

        if (token != null)
        {
            amountInput = new AmountEntryItem(this, tokenRepository, token); //ticker is used automatically now
        }
*/

    }

    private void initView() {
        String upcString;
        String totalBalanceString;
        String currentStakerString;
        String amountStakedString;

        upcRaw = findViewById(R.id.upc_raw);
        currentStaker = findViewById(R.id.current_staker);
        amountStaked = findViewById(R.id.amount_staked);


        if (getIntent() != null) {
            upcString = getIntent().getStringExtra("upc_raw");
            //totalBalanceString = getIntent().getStringExtra("total_balance");
            currentStakerString = getIntent().getStringExtra("current_staker");
            amountStakedString  = getIntent().getStringExtra("amount_staked");

            upcRaw.setText(upcString);
            //totalBalance.setText(totalBalanceString);
            String addyBegin = currentStakerString.substring(0,10);
            String addyEnd =   currentStakerString.substring(currentStakerString.length()-5);
            String addyFinal = addyBegin + "..." + addyEnd;
            String amountString = Convert.fromWei(amountStakedString,Convert.Unit.ETHER).toString() + "(xDAI)";

            if(addyFinal.equals("0x00000000...00000") ){
                addyFinal = "Vacant";
            }

            currentStaker.setText(addyFinal);
            amountStaked.setText(amountString);

        }

        nextBtn = findViewById(R.id.button_next);
        nextBtn.setOnClickListener(v -> {
            onNext();
        });
    }


    private boolean isBalanceZero(String balance)
    {
        try
        {
            /*
            While checking 0.00 value which is passed while using Fiat currency,
            BigDecimal.ZERO fails to send accurate value.
            Using .doubleValue(), converts to actual amount and compare without scale.
             */
            BigDecimal amount = new BigDecimal(balance);
            return BigDecimal.ZERO.doubleValue() == amount.doubleValue();
        }
        catch (Exception e)
        {
            return true;
        }
    }

    private boolean isBalanceEnough(String eth)
    {
        try
        {
            //Needs to take into account decimal of token
            int decimals = (token != null && token.tokenInfo != null) ? token.tokenInfo.decimals : 18;
            BigDecimal amount = new BigDecimal(BalanceUtils.baseToSubunit(eth, decimals));
            return (token.balance.subtract(amount).compareTo(BigDecimal.ZERO) >= 0);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private void setupTokenContent() {
        tokenBalanceText = findViewById(R.id.balance_eth);
        tokenSymbolText = findViewById(R.id.symbol);
        chainName = findViewById(R.id.text_chain_name);

        String symbol = token.getSymbol();

        tokenSymbolText.setText(TextUtils.isEmpty(token.tokenInfo.name)
                ? symbol
                : symbol.length() > 0 ? getString(R.string.token_name, token.tokenInfo.name, symbol)
                : token.tokenInfo.name);

        TokenInfo tokenInfo = token.tokenInfo;
        BigDecimal decimalDivisor = new BigDecimal(Math.pow(10, tokenInfo.decimals));
        BigDecimal ethBalance = tokenInfo.decimals > 0
                ? token.balance.divide(decimalDivisor, Token.TOKEN_BALANCE_PRECISION, RoundingMode.DOWN).stripTrailingZeros() : token.balance;
        String value = getEthString(ethBalance.doubleValue());
        tokenBalanceText.setText(value);

        tokenBalanceText.setVisibility(View.VISIBLE);
        if (token != null)
        {
            Utils.setChainColour(chainName, token.tokenInfo.chainId);
            chainName.setText(viewModel.getChainName(token.tokenInfo.chainId));
            viewModel.setChainId(token.tokenInfo.chainId);
        }
    }


    private void onNext() {
        KeyboardUtils.hideKeyboard(getCurrentFocus());
        boolean isValid = amountInput.checkValidAmount();

        if (isBalanceZero(currentAmount)) {
            amountInput.setError(R.string.error_zero_balance);
            isValid = false;
        }
        if (!isBalanceEnough(currentAmount)) {
            amountInput.setError(R.string.error_insufficient_funds);
            isValid = false;
        }

        String to = ensHandler.getAddressFromEditView();
        if (to == null) return;

        if (isValid) {
            BigInteger amountInSubunits = BalanceUtils.baseToSubunit(currentAmount, decimals);
            boolean sendingTokens = !token.isEthereum();
            viewModel.openConfirmation(this, to, amountInSubunits, token.getAddress(), token.tokenInfo.decimals, token.getSymbol(), sendingTokens, ensHandler.getEnsName(), currentChain);
        }
    }

    private void pickImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RC_HANDLE_IMAGE_PICKUP);
    }

    // Handles the requesting of the camera permission.
    private void requestCameraPermission()
    {
        Log.w("QR SCanner", "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM); //always ask for permission to scan
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean handled = false;

        if (requestCode == RC_HANDLE_CAMERA_PERM)
        {
            for (int i = 0; i < permissions.length; i++)
            {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.CAMERA))
                {
                    if (grantResult == PackageManager.PERMISSION_GRANTED)
                    {
                        setContentView(R.layout.activity_full_screen_scanner_fragment);
                        initView();
                        handled = true;
                    }
                }
            }
        }
        else if (requestCode == RC_HANDLE_IMAGE_PICKUP)
        {
            pickImage();
            handled = true;
        }

        // Handle deny permission
        if (!handled)
        {
            Intent intent = new Intent();
            setResult(DENY_PERMISSION, intent);
            finish();
        }
    }

    @Override
    public void onReceive(String result)
    {
        handleQRCode(result);
    }

    public void handleQRCode(String qrCode)
    {
        if (qrCode.startsWith("wc:")) {
            startWalletConnect(qrCode);
        } else {

            //SplashActivity sa = new SplashActivity();
            //Intent intent = new Intent(sa, SplashActivity.class);
            //sa.startActivityForResult(intent, HomeActivity.DAPP_BARCODE_READER_REQUEST_CODE);


            //Intent intent = new Intent();
            //intent.putExtra(C.EXTRA_UNIVERSAL_SCAN, qrCode);
            //setResult(Activity.RESULT_OK, intent);
            //finish();
        }
    }

    private void startWalletConnect(String qrCode) {
        Intent intent = new Intent(this, WalletConnectActivity.class);
        intent.putExtra("qrCode", qrCode);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_HANDLE_IMAGE_PICKUP && resultCode == Activity.RESULT_OK)
        {
            if (data != null) {
                Uri selectedImage = data.getData();

                disposable = concertAndHandle(selectedImage)
                        .observeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onSuccess, this::onError);
            }
        }
    }

    private void onError(Throwable throwable)
    {
        displayErrorDialog(getString(R.string.title_dialog_error), getString(R.string.error_browse_selection));
    }

    private void onSuccess(Result result)
    {
        if (result == null)
        {
            displayErrorDialog(getString(R.string.title_dialog_error), getString(R.string.error_browse_selection));
        }
        else
        {
            handleQRCode(result.getText());
        }
    }

    private Single<Result> concertAndHandle(Uri selectedImage)
    {
        return Single.fromCallable(() -> {

            SoftReference<Bitmap> softReferenceBitmap;
            softReferenceBitmap = new SoftReference<>(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage));

            if (softReferenceBitmap.get() != null)
            {
                int width = softReferenceBitmap.get().getWidth(), height = softReferenceBitmap.get().getHeight();
                int[] pixels = new int[width * height];
                softReferenceBitmap.get().getPixels(pixels, 0, width, 0, 0, width, height);
                softReferenceBitmap.get().recycle();
                softReferenceBitmap.clear();
                RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
                MultiFormatReader reader = new MultiFormatReader();
                return reader.decodeWithState(bBitmap);
            }

            return null;
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop()
    {
        if (disposable != null && !disposable.isDisposed())
        {
            disposable.dispose();
        }
    }

    private void displayErrorDialog(String title, String errorMessage)
    {
        AWalletAlertDialog aDialog = new AWalletAlertDialog(this);
        aDialog.setTitle(title);
        aDialog.setMessage(errorMessage);
        aDialog.setIcon(AWalletAlertDialog.ERROR);
        aDialog.setButtonText(R.string.button_ok);
        aDialog.setButtonListener(v -> {
            aDialog.dismiss();
        });
        dialog = aDialog;
        dialog.show();
    }
}