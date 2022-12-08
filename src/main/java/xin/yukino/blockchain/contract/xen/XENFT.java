package xin.yukino.blockchain.contract.xen;

import com.google.common.collect.Lists;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import xin.yukino.blockchain.component.BlockChainClient;
import xin.yukino.blockchain.util.Web3jUtil;

import java.math.BigInteger;
import java.util.List;

import static xin.yukino.blockchain.constant.MyConstant.DEFAULT_GAS_LIMIT;
import static xin.yukino.blockchain.constant.MyConstant.DEFAULT_SENDER;

public class XENFT {

    public static EthSendTransaction bulkClaimRank(int count, int term, String contractAddress, BigInteger gasLimit, Credentials credentials, BlockChainClient blockChainClient) {
        List<Type> inputParameters = Lists.newArrayList(new Uint256(count), new Uint256(term));
        List<TypeReference<?>> outputParameters = Lists.newArrayList(new TypeReference<Uint256>() {
        });
        Function function = new Function("bulkClaimRank", inputParameters, outputParameters);

        return Web3jUtil.execute(contractAddress, FunctionEncoder.encode(function), gasLimit, credentials, blockChainClient);
    }

    public static EthSendTransaction bulkClaimMintReward(int tokenId, String contractAddress, BigInteger gasLimit, Credentials credentials, BlockChainClient blockChainClient) {
        List<Type> inputParameters = Lists.newArrayList(new Uint256(tokenId), new Address(credentials.getAddress()));
        List<TypeReference<?>> outputParameters = Lists.newArrayList();
        Function function = new Function("bulkClaimMintReward", inputParameters, outputParameters);

        return Web3jUtil.execute(contractAddress, FunctionEncoder.encode(function), gasLimit, credentials, blockChainClient);
    }

    public static EthCall tokenURI(int tokenId, String contractAddress, Credentials credentials, BlockChainClient blockChainClient) {
        List<Type> inputParameters = Lists.newArrayList(new Uint256(tokenId));
        List<TypeReference<?>> outputParameters = Lists.newArrayList(new TypeReference<Utf8String>() {
        });
        Function function = new Function("tokenURI", inputParameters, outputParameters);

        return Web3jUtil.call(contractAddress, function, credentials.getAddress(), blockChainClient.getWeb3j());
    }

    public static void main(String[] args) {
        String contractAddress = "0x359d7fEb2809F19b20e986D67Fa137fbD54C5ffB";
        System.out.println(bulkClaimRank(1, 1, contractAddress, DEFAULT_GAS_LIMIT, DEFAULT_SENDER, BlockChainClient.OKC_MAIN_NET).getTransactionHash());
//        EthSendTransaction transaction = bulkClaimMintReward(10003, contractAddress, DEFAULT_GAS_LIMIT, DEFAULT_SENDER, BlockChainClient.OKC_MAIN_NET);
//        EthCall transaction = tokenURI(10001, contractAddress, DEFAULT_SENDER, BlockChainClient.OKC_MAIN_NET);
    }
}
