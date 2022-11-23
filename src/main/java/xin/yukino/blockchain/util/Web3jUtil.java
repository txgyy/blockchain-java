package xin.yukino.blockchain.util;

import lombok.SneakyThrows;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.RawTransactionManager;
import xin.yukino.blockchain.component.BlockChainClient;

import java.math.BigDecimal;
import java.math.BigInteger;

import static xin.yukino.blockchain.constant.OkcConstant.OKT_DECIMAL;
import static xin.yukino.blockchain.constant.OkcConstant.OKT_TRANSFER_GAS;
import static xin.yukino.blockchain.util.Web3jBaseUtil.getNetworkGasPrice;
import static xin.yukino.blockchain.util.Web3jBaseUtil.getNonce;

public class Web3jUtil {

    @SneakyThrows
    public static EthSendTransaction signAndSend(RawTransaction rawTransaction, Credentials credentials, BlockChainClient blockChainClient) {
        //EIP155签名
        RawTransactionManager transactionManager = new RawTransactionManager(blockChainClient.getWeb3j(), credentials, blockChainClient.getChainId());
        return transactionManager.signAndSend(rawTransaction);
    }

    // ********************************** okt transfer ********************************** //
    @SneakyThrows
    public static EthSendTransaction transfer(BigInteger nonce, BigInteger gasPrice, String to, BigDecimal value, Credentials credentials, BlockChainClient blockChainClient) {
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, OKT_TRANSFER_GAS, to, value.multiply(BigDecimal.TEN.pow(OKT_DECIMAL)).toBigInteger());
        return signAndSend(rawTransaction, credentials, blockChainClient);
    }

    public static EthSendTransaction transfer(String to, BigDecimal value, Credentials credentials, BlockChainClient blockChainClient) {
        Web3j web3j = blockChainClient.getWeb3j();
        //获取nonce，交易笔数
        BigInteger nonce = getNonce(credentials.getAddress(), web3j);
        //创建RawTransaction交易对象
        BigInteger gasPrice = getNetworkGasPrice(web3j);
        return transfer(nonce, gasPrice, to, value, credentials, blockChainClient);
    }


    // ********************************** contract execute ********************************** //
    @SneakyThrows
    public static EthSendTransaction execute(BigInteger nonce, BigInteger gasPrice, String contractAddress, String encodedFunction, BigInteger gasLimit, Credentials credentials, BlockChainClient blockChainClient) {
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, contractAddress, encodedFunction);
        return signAndSend(rawTransaction, credentials, blockChainClient);
    }

    public static EthSendTransaction execute(String contractAddress, String encodedFunction, BigInteger gasLimit, Credentials credentials, BlockChainClient blockChainClient) {
        Web3j web3j = blockChainClient.getWeb3j();
        BigInteger nonce = getNonce(credentials.getAddress(), web3j);
        BigInteger gasPrice = getNetworkGasPrice(web3j);

        return execute(nonce, gasPrice, contractAddress, encodedFunction, gasLimit, credentials, blockChainClient);
    }

    // ********************************** contract call ********************************** //
    @SneakyThrows
    public static EthCall call(String contractAddress, Function function, String from, Web3j web3j) {
        String encodedFunction = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(from, contractAddress, encodedFunction);
        return web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
    }

}
