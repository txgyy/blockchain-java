package xin.yukino.blockchain.contract.erc20;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthCall;
import xin.yukino.blockchain.component.BlockChainClient;
import xin.yukino.blockchain.constant.MyConstant;
import xin.yukino.blockchain.util.Web3jUtil;

import java.util.List;

public class Erc20 {

    public static EthCall symbol(String contract) {
        List<Type> inputParameters = Lists.newArrayList();
        List<TypeReference<?>> outputParameters = Lists.newArrayList(TypeReference.create(Utf8String.class));
        Function function = new Function("symbol", inputParameters, outputParameters);
        String from = MyConstant.DEFAULT_SENDER.getAddress();
        Web3j web3j = BlockChainClient.OKC_MAIN_NET.getWeb3j();
        return Web3jUtil.call(contract, function, from, web3j);
    }

    public static void main(String[] args) {
        String contract = "0xc946daf81b08146b1c7a8da2a851ddf2b3eaaf85";
        EthCall ethCall = symbol(contract);

        System.out.println(JSON.toJSONString(ethCall));
        System.out.println(ethCall.getResult());
    }
}