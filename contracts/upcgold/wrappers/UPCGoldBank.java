package upcgold;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.7.0.
 */
@SuppressWarnings("rawtypes")
public class UPCGoldBank extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ACTIONPOT = "actionPot";

    public static final String FUNC_ADDRESSTOLEASE = "addressToLease";

    public static final String FUNC_BALANCERECEIVED = "balanceReceived";

    public static final String FUNC_CALCULATEFEE = "calculateFee";

    public static final String FUNC_DEPOSITMONEY = "depositMoney";

    public static final String FUNC_GETADDRESSBALANCE = "getAddressBalance";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_GETCOSTTOEVICT = "getCostToEvict";

    public static final String FUNC_GETMYSCANNABLES = "getMyScannables";

    public static final String FUNC_GETSCANNABLE = "getScannable";

    public static final String FUNC_HARVESTREWARDFORSCANNABLE = "harvestRewardForScannable";

    public static final String FUNC_INTERNALTRANSFER = "internalTransfer";

    public static final String FUNC_SCANNABLES = "scannables";

    public static final String FUNC_SETREWARDGRANTER = "setRewardGranter";

    public static final String FUNC_WITHDRAW = "withdraw";

    @Deprecated
    protected UPCGoldBank(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected UPCGoldBank(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected UPCGoldBank(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected UPCGoldBank(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> actionPot() {
        final Function function = new Function(FUNC_ACTIONPOT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple8<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, byte[], String>> addressToLease(String param0, BigInteger param1) {
        final Function function = new Function(FUNC_ADDRESSTOLEASE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple8<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, byte[], String>>(function,
                new Callable<Tuple8<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, byte[], String>>() {
                    @Override
                    public Tuple8<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, byte[], String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, byte[], String>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (byte[]) results.get(6).getValue(), 
                                (String) results.get(7).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<BigInteger, BigInteger>> balanceReceived(String param0) {
        final Function function = new Function(FUNC_BALANCERECEIVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<BigInteger, BigInteger>>(function,
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> calculateFee(BigInteger amount) {
        final Function function = new Function(FUNC_CALCULATEFEE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> depositMoney(String upcId) {
        final Function function = new Function(
                FUNC_DEPOSITMONEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(upcId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getAddressBalance() {
        final Function function = new Function(FUNC_GETADDRESSBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getBalance() {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple4<String, BigInteger, Boolean, byte[]>> getCostToEvict(String upcId) {
        final Function function = new Function(FUNC_GETCOSTTOEVICT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(upcId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteFunctionCall<Tuple4<String, BigInteger, Boolean, byte[]>>(function,
                new Callable<Tuple4<String, BigInteger, Boolean, byte[]>>() {
                    @Override
                    public Tuple4<String, BigInteger, Boolean, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, BigInteger, Boolean, byte[]>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (byte[]) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<List> getMyScannables() {
        final Function function = new Function(FUNC_GETMYSCANNABLES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Tuple7<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, String>> getScannable(byte[] upcHash) {
        final Function function = new Function(FUNC_GETSCANNABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(upcHash)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple7<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, String>>(function,
                new Callable<Tuple7<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple7<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, String>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (String) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> harvestRewardForScannable(BigInteger amount) {
        final Function function = new Function(FUNC_HARVESTREWARDFORSCANNABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> internalTransfer(String _to, BigInteger _amount) {
        final Function function = new Function(
                FUNC_INTERNALTRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple8<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, byte[], String>> scannables(byte[] param0) {
        final Function function = new Function(FUNC_SCANNABLES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple8<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, byte[], String>>(function,
                new Callable<Tuple8<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, byte[], String>>() {
                    @Override
                    public Tuple8<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, byte[], String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, BigInteger, Boolean, BigInteger, BigInteger, BigInteger, byte[], String>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (byte[]) results.get(6).getValue(), 
                                (String) results.get(7).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setRewardGranter(String addy) {
        final Function function = new Function(
                FUNC_SETREWARDGRANTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addy)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String upcId) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(upcId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static UPCGoldBank load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new UPCGoldBank(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static UPCGoldBank load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new UPCGoldBank(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static UPCGoldBank load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new UPCGoldBank(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static UPCGoldBank load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new UPCGoldBank(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
