package xin.yukino.blockchain.contract.okc.gasback;

import com.google.common.collect.Lists;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthCall;
import xin.yukino.blockchain.component.BlockChainClient;
import xin.yukino.blockchain.constant.MyConstant;
import xin.yukino.blockchain.util.CodecUtil;
import xin.yukino.blockchain.util.Web3jUtil;

import java.util.List;
import java.util.stream.Collectors;

public class GasBackMSGHelper {

    public static List<Type> genRegisterMsg(String contract, String withdraw, List<Integer> nonceList, String contractAddress, Credentials credentials, BlockChainClient blockChainClient) {
        List<Type> inputParameters = Lists.newArrayList(new Address(contract), new Address(withdraw), new DynamicArray<>(Uint256.class, nonceList.stream().map(Uint256::new).collect(Collectors.toList())));
        List<TypeReference<?>> outputParameters = Lists.newArrayList(new TypeReference<Utf8String>() {
        });
        Function function = new Function("genRegisterMsg", inputParameters, outputParameters);

        EthCall ethCall = Web3jUtil.call(contractAddress, function, credentials.getAddress(), blockChainClient.getWeb3j());
        return FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
    }

    public static void main(String[] args) {
        String contract = "0x359d7fEb2809F19b20e986D67Fa137fbD54C5ffB";
        String withdraw = "0x3ea82868125fdc75e7eccb812d55cf3f57e8c19a";
        List<Integer> nonceList = Lists.newArrayList();
        nonceList.add(2);
        String contractAddress = "0x0dd08b74c111d148751f38f02ab0c3408ead7d18";
        Utf8String genRegisterMsg = (Utf8String) genRegisterMsg(contract, withdraw, nonceList, contractAddress, MyConstant.DEFAULT_SENDER, BlockChainClient.OKC_MAIN_NET).get(0);
        System.out.println(genRegisterMsg);
        System.out.println(CodecUtil.decodeHex(genRegisterMsg.getValue()));
    }
}
