package nl.labela.dietethereumandroid.solidity;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Diet extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506107e9806100206000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c806365c06f701161005b57806365c06f70146100bd57806371b88037146100d05780639746c057146100d8578063bfb238e2146100ed57600080fd5b806328455e4b1461008257806330184b5f146100a05780633940454a146100b5575b600080fd5b61008a6100f5565b6040516100979190610665565b60405180910390f35b6100b36100ae3660046105ec565b610199565b005b61008a61021c565b6100b36100cb36600461051a565b61023d565b61008a6102f8565b6100e0610316565b604051610097919061060e565b6100b3610395565b33600090815260208190526040902060040180546060919061011690610762565b80601f016020809104026020016040519081016040528092919081815260200182805461014290610762565b801561018f5780601f106101645761010080835404028352916020019161018f565b820191906000526020600020905b81548152906001019060200180831161017257829003601f168201915b5050505050905090565b60408051808201825283815260208082018481523360009081528083528481206005018054600180820183559183529184902085516002909302018281558351910155845190815290519181019190915290917f0a82e570f8e51654123b92d724ec657d34eb53fe5b4dc2150afee412b0776b95910160405180910390a1505050565b33600090815260208190526040902060030180546060919061011690610762565b336000908152602081815260409091208651909161025f9183918901906103b5565b50845161027590600183019060208801906103b5565b50835161028b90600283019060208701906103b5565b5082516102a190600383019060208601906103b5565b5081516102b790600483019060208501906103b5565b506040517f56b0a2dda7952cd07f438433182565929ba3aba4c3404a7d29a0840ac6284da4906102e89083906106ba565b60405180910390a1505050505050565b33600090815260208190526040902080546060919061011690610762565b33600090815260208181526040808320600501805482518185028101850190935280835260609492939192909184015b8282101561038c57838290600052602060002090600202016040518060400160405290816000820154815260200160018201548152505081526020019060010190610346565b50505050905090565b3360009081526020819052604081206103b391600590910190610439565b565b8280546103c190610762565b90600052602060002090601f0160209004810192826103e35760008555610429565b82601f106103fc57805160ff1916838001178555610429565b82800160010185558215610429579182015b8281111561042957825182559160200191906001019061040e565b5061043592915061045d565b5090565b508054600082556002029060005260206000209081019061045a9190610472565b50565b5b80821115610435576000815560010161045e565b5b808211156104355760008082556001820155600201610473565b600082601f83011261049e57600080fd5b813567ffffffffffffffff808211156104b9576104b961079d565b604051601f8301601f19908116603f011681019082821181831017156104e1576104e161079d565b816040528381528660208588010111156104fa57600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080600080600060a0868803121561053257600080fd5b853567ffffffffffffffff8082111561054a57600080fd5b61055689838a0161048d565b9650602088013591508082111561056c57600080fd5b61057889838a0161048d565b9550604088013591508082111561058e57600080fd5b61059a89838a0161048d565b945060608801359150808211156105b057600080fd5b6105bc89838a0161048d565b935060808801359150808211156105d257600080fd5b506105df8882890161048d565b9150509295509295909350565b600080604083850312156105ff57600080fd5b50508035926020909101359150565b602080825282518282018190526000919060409081850190868401855b828110156106585761064884835180518252602090810151910152565b928401929085019060010161062b565b5091979650505050505050565b600060208083528351808285015260005b8181101561069257858101830151858201604001528201610676565b818111156106a4576000604083870101525b50601f01601f1916929092016040019392505050565b600060208083526000845481600182811c9150808316806106dc57607f831692505b8583108114156106fa57634e487b7160e01b85526022600452602485fd5b878601838152602001818015610717576001811461072857610753565b60ff19861682528782019650610753565b60008b81526020902060005b8681101561074d57815484820152908501908901610734565b83019750505b50949998505050505050505050565b600181811c9082168061077657607f821691505b6020821081141561079757634e487b7160e01b600052602260045260246000fd5b50919050565b634e487b7160e01b600052604160045260246000fdfea2646970667358221220e0144a8a3a320436b3c2652f6e22d09fa40f0579cd761606ff9d97f37b0cecf264736f6c63430008050033";

    public static final String FUNC_ADDWEIGHTENTRY = "addWeightEntry";

    public static final String FUNC_CLEARWEIGHTENTRIES = "clearWeightEntries";

    public static final String FUNC_CREATEUSER = "createUser";

    public static final String FUNC_GETUSEREMAIL = "getUserEmail";

    public static final String FUNC_GETUSERGOALWEIGHT = "getUserGoalWeight";

    public static final String FUNC_GETUSERSTARTWEIGHT = "getUserStartWeight";

    public static final String FUNC_GETWEIGHTENTRIES = "getWeightEntries";

    public static final Event USERCREATED_EVENT = new Event("UserCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event WEIGHTENTRYADDED_EVENT = new Event("WeightEntryAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<WeightEntry>() {}));
    ;

    @Deprecated
    protected Diet(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Diet(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Diet(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Diet(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<UserCreatedEventResponse> getUserCreatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(USERCREATED_EVENT, transactionReceipt);
        ArrayList<UserCreatedEventResponse> responses = new ArrayList<UserCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserCreatedEventResponse typedResponse = new UserCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.email = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UserCreatedEventResponse> userCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, UserCreatedEventResponse>() {
            @Override
            public UserCreatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(USERCREATED_EVENT, log);
                UserCreatedEventResponse typedResponse = new UserCreatedEventResponse();
                typedResponse.log = log;
                typedResponse.email = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UserCreatedEventResponse> userCreatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(USERCREATED_EVENT));
        return userCreatedEventFlowable(filter);
    }

    public List<WeightEntryAddedEventResponse> getWeightEntryAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WEIGHTENTRYADDED_EVENT, transactionReceipt);
        ArrayList<WeightEntryAddedEventResponse> responses = new ArrayList<WeightEntryAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WeightEntryAddedEventResponse typedResponse = new WeightEntryAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.weightEntry = (WeightEntry) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WeightEntryAddedEventResponse> weightEntryAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, WeightEntryAddedEventResponse>() {
            @Override
            public WeightEntryAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WEIGHTENTRYADDED_EVENT, log);
                WeightEntryAddedEventResponse typedResponse = new WeightEntryAddedEventResponse();
                typedResponse.log = log;
                typedResponse.weightEntry = (WeightEntry) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<WeightEntryAddedEventResponse> weightEntryAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WEIGHTENTRYADDED_EVENT));
        return weightEntryAddedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addWeightEntry(BigInteger _grams, BigInteger _timestamp) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDWEIGHTENTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_grams), 
                new org.web3j.abi.datatypes.generated.Uint256(_timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> clearWeightEntries() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLEARWEIGHTENTRIES, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createUser(String _email, String _firstName, String _lastName, String _startWeight, String _goalWeight) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CREATEUSER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_email), 
                new org.web3j.abi.datatypes.Utf8String(_firstName), 
                new org.web3j.abi.datatypes.Utf8String(_lastName), 
                new org.web3j.abi.datatypes.Utf8String(_startWeight), 
                new org.web3j.abi.datatypes.Utf8String(_goalWeight)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getUserEmail() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUSEREMAIL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getUserGoalWeight() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUSERGOALWEIGHT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getUserStartWeight() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETUSERSTARTWEIGHT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getWeightEntries() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETWEIGHTENTRIES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<WeightEntry>>() {}));
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

    @Deprecated
    public static Diet load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Diet(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Diet load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Diet(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Diet load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Diet(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Diet load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Diet(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Diet> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Diet.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Diet> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Diet.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Diet> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Diet.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Diet> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Diet.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class WeightEntry extends StaticStruct {
        public BigInteger grams;

        public BigInteger timestamp;

        public WeightEntry(BigInteger grams, BigInteger timestamp) {
            super(new org.web3j.abi.datatypes.generated.Uint256(grams),new org.web3j.abi.datatypes.generated.Uint256(timestamp));
            this.grams = grams;
            this.timestamp = timestamp;
        }

        public WeightEntry(Uint256 grams, Uint256 timestamp) {
            super(grams,timestamp);
            this.grams = grams.getValue();
            this.timestamp = timestamp.getValue();
        }
    }

    public static class UserCreatedEventResponse extends BaseEventResponse {
        public String email;
    }

    public static class WeightEntryAddedEventResponse extends BaseEventResponse {
        public WeightEntry weightEntry;
    }
}
