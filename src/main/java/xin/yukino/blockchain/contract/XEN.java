package xin.yukino.blockchain.contract;

import com.google.common.collect.Lists;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import xin.yukino.blockchain.component.BlockChainClient;
import xin.yukino.blockchain.constant.MyConstant;
import xin.yukino.blockchain.util.WalletUtil;
import xin.yukino.blockchain.util.Web3jUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static xin.yukino.blockchain.constant.MyConstant.DEFAULT_GAS_LIMIT;
import static xin.yukino.blockchain.constant.MyConstant.DEFAULT_SENDER;

public class XEN {

    public static EthSendTransaction claimRank(int term, String contractAddress, BigInteger gasLimit, Credentials credentials, BlockChainClient blockChainClient) {
        List<Type> inputParameters = Lists.newArrayList(new Uint256(term));
        List<TypeReference<?>> outputParameters = Lists.newArrayList();
        Function function = new Function("claimRank", inputParameters, outputParameters);
        return Web3jUtil.execute(contractAddress, FunctionEncoder.encode(function), gasLimit, credentials, blockChainClient);
    }

    public static EthSendTransaction claimMintReward(String contractAddress, BigInteger gasLimit, Credentials credentials, BlockChainClient blockChainClient) {
        List<Type> inputParameters = Lists.newArrayList();
        List<TypeReference<?>> outputParameters = Lists.newArrayList();
        Function function = new Function("claimMintReward", inputParameters, outputParameters);
        return Web3jUtil.execute(contractAddress, FunctionEncoder.encode(function), gasLimit, credentials, blockChainClient);
    }


    public static void bulkClaimRank(String contractAddress, int accountIndex, int num, BigDecimal gasFee) {
        for (int i = accountIndex; i < accountIndex + num; i++) {
            System.out.println("===================================================================");
            // 根据助记词生成账号，逻辑和web3钱包一致
            Credentials contract = WalletUtil.generateBip44Credentials(MyConstant.DEFAULT_MNEMONIC, i);
            System.out.println("privateKey: " + contract.getEcKeyPair().getPrivateKey());
            System.out.println("address: " + contract.getAddress());
            // 先往账号里转gas费
            String tsfHash = Web3jUtil.transfer(contract.getAddress(), gasFee, MyConstant.DEFAULT_SENDER, BlockChainClient.OKC_MAIN_NET).getTransactionHash();
            System.out.println("tsfHash: " + tsfHash);
            // 设置天数
            int term = 1;
            // 执行claimRank
            String claimRankHash = claimRank(term, contractAddress, DEFAULT_GAS_LIMIT, DEFAULT_SENDER, BlockChainClient.OKC_MAIN_NET).getTransactionHash();
            System.out.println("claimRankHash: " + claimRankHash);
        }
    }

    public static void bulkClaimMintReward(String contractAddress, int accountIndex, int num) {
        for (int i = accountIndex; i < accountIndex + num; i++) {
            System.out.println("===================================================================");
            // 根据助记词生成账号，逻辑和web3钱包一致
            Credentials contract = WalletUtil.generateBip44Credentials(MyConstant.DEFAULT_MNEMONIC, i);
            System.out.println("privateKey: " + contract.getEcKeyPair().getPrivateKey());
            System.out.println("address: " + contract.getAddress());
            // claimMintReward
            String claimMintRewardHash = claimMintReward(contractAddress, DEFAULT_GAS_LIMIT, DEFAULT_SENDER, BlockChainClient.OKC_MAIN_NET).getTransactionHash();
            System.out.println("claimMintRewardHash: " + claimMintRewardHash);
        }
    }

    public static void main(String[] args) {
        // xen 在 okc 上的合约地址
        String contractAddress = "0x1cC4D981e897A3D2E7785093A648c0a75fAd0453";
        // bip44公式中的Account Index, >=0 即可
        int accountIndex = 10;
        // 循环生成账号数量
        int num = 1;
        // gas fee
        BigDecimal gasFee = new BigDecimal("0.0004");

        bulkClaimRank(contractAddress, accountIndex, num, gasFee);
    }
}
