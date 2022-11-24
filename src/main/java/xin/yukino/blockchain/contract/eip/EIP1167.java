package xin.yukino.blockchain.contract.eip;

import org.apache.commons.lang3.StringUtils;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import xin.yukino.blockchain.component.BlockChainClient;
import xin.yukino.blockchain.constant.MyConstant;
import xin.yukino.blockchain.util.Web3jUtil;

import java.math.BigInteger;

public class EIP1167 {

    public static EthSendTransaction createMinProxy(String contractAddress, BigInteger gasLimit, Credentials credentials, BlockChainClient blockChainClient) {
        if (StringUtils.startsWithIgnoreCase(contractAddress, "0x")) {
            contractAddress = contractAddress.substring(2);
        }
        String init = "0x3d602d80600a3d3981f3" +
                "363d3d373d3d3d363d73" +
                contractAddress +
                "5af43d82803e903d91602b57fd5bf3";
        return Web3jUtil.execute(null, init, gasLimit, credentials, blockChainClient);
    }

    public static void main(String[] args) {
        String contractAddress = "0x1cC4D981e897A3D2E7785093A648c0a75fAd0453";
        System.out.println(createMinProxy(contractAddress, BigInteger.valueOf(4000000), MyConstant.DEFAULT_SENDER, BlockChainClient.OKC_TEST_NET).getTransactionHash());
    }
}
