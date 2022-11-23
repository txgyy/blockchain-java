package xin.yukino.blockchain.contract.okc.create;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import xin.yukino.blockchain.component.BlockChainClient;
import xin.yukino.blockchain.constant.MyConstant;
import xin.yukino.blockchain.util.Web3jUtil;

import java.math.BigInteger;

public class Create {

    public static EthSendTransaction create(String init, BigInteger gasLimit, Credentials credentials, BlockChainClient blockChainClient) {
        return Web3jUtil.execute(null, init, gasLimit, credentials, blockChainClient);
    }

    public static void main(String[] args) {
        String init = "0x3d602d80600a3d3981f3363d3d373d3d3d363d731cC4D981e897A3D2E7785093A648c0a75fAd04535af43d82803e903d91602b57fd5bf3";
        System.out.println(create(init, BigInteger.valueOf(4000000), MyConstant.DEFAULT_SENDER, BlockChainClient.OKC_TEST_NET).getTransactionHash());
    }
}
