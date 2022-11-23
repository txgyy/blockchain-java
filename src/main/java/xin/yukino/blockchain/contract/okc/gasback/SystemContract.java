package xin.yukino.blockchain.contract.okc.gasback;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import xin.yukino.blockchain.component.BlockChainClient;
import xin.yukino.blockchain.util.Web3jUtil;

import java.math.BigInteger;
import java.util.List;

import static xin.yukino.blockchain.constant.MyConstant.DEFAULT_GAS_LIMIT;
import static xin.yukino.blockchain.constant.MyConstant.DEFAULT_SENDER;

public class SystemContract {

    public static EthSendTransaction invoke(String hex, String contractAddress, BigInteger gasLimit, Credentials credentials, BlockChainClient blockChainClient) {
        List<Type> inputParameters = Lists.newArrayList(new Utf8String(hex));
        List<TypeReference<?>> outputParameters = Lists.newArrayList();
        Function function = new Function("invoke", inputParameters, outputParameters);

        return Web3jUtil.execute(contractAddress, FunctionEncoder.encode(function), gasLimit, credentials, blockChainClient);
    }

    public static void main(String[] args) {
        String hex = "7b2274797065223a20226f6b6578636861696e2f4d7367526567697374657246656553706c6974222c2276616c7565223a207b22636f6e74726163745f61646472657373223a2022307833353964376665623238303966313962323065393836643637666131333766626435346335666662222c226465706c6f7965725f61646472657373223a2022307862303432653538383239353630396163633733356137636662333365323865376430376636653065222c22776974686472617765725f61646472657373223a2022307833656138323836383132356664633735653765636362383132643535636633663537653863313961222c226e6f6e636573223a205b325d7d7d";
        String contractAddress = "0xd6bce454316b8ddFb76bB7bb1B57B8942B09Acd5";
        EthSendTransaction transaction = invoke(hex, contractAddress, DEFAULT_GAS_LIMIT, DEFAULT_SENDER, BlockChainClient.OKC_MAIN_NET);
        System.out.println(JSON.toJSONString(transaction));
    }
}
