package xin.yukino.blockchain.contract.erc20;

import com.google.common.collect.Lists;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.core.methods.response.EthCall;
import xin.yukino.blockchain.component.BlockChainClient;
import xin.yukino.blockchain.constant.MyConstant;
import xin.yukino.blockchain.util.CodecUtil;
import xin.yukino.blockchain.util.Web3jUtil;

import java.util.List;

public class Erc20 {

    public static EthCall symbol(String contract, BlockChainClient blockChainClient) {
        List<Type> inputParameters = Lists.newArrayList();
        List<TypeReference<?>> outputParameters = Lists.newArrayList(TypeReference.create(Utf8String.class));
        Function function = new Function("symbol", inputParameters, outputParameters);
        String from = MyConstant.DEFAULT_SENDER.getAddress();
        return Web3jUtil.call(contract, function, from, blockChainClient.getWeb3j());
    }

    public static void main(String[] args) {
        String contract = "0xe9e171d3d613328090733f67028e66863c044b96";
        EthCall ethCall = symbol(contract, BlockChainClient.ETH_MAIN_NET);

        System.out.println(ethCall.getResult());
        System.out.println(CodecUtil.decodeHex("200455534443"));
    }
}
