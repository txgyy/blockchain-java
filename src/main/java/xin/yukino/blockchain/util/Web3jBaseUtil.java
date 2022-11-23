package xin.yukino.blockchain.util;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.*;
import xin.yukino.blockchain.component.BlockChainClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static xin.yukino.blockchain.constant.OkcConstant.OKT_DECIMAL;

public class Web3jBaseUtil {

    public static List<Log> getLogs(long blockNumber, String address, Web3j web3j, String... topics) {
        return getLogs(blockNumber, blockNumber, address, web3j, topics);
    }

    @SneakyThrows
    public static List<Log> getLogs(long blockNumberStart, long blockNumberEnd, String address, Web3j web3j, String... topics) {
        EthFilter ethFilter = new EthFilter(DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumberStart)),
                DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumberEnd)), address);
        ethFilter.addOptionalTopics(topics);
        EthLog ethLog = web3j.ethGetLogs(ethFilter).send();
        if (CollectionUtils.isEmpty(ethLog.getLogs())) {
            return Lists.newArrayList();
        }
        return ethLog.getLogs().stream().map(l -> ((EthLog.LogObject) l.get()).get()).collect(Collectors.toList());
    }

    @SneakyThrows
    public static EthBlock.Block getBlock(long blockNumberStart, Web3j web3j) {
        return web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(blockNumberStart)), true).send().getBlock();
    }

    @SneakyThrows
    public static BigInteger getCurrentBlockNumber(Web3j web3j) {
        EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();
        return ethBlockNumber.getBlockNumber();
    }

    @SneakyThrows
    public static BigInteger getNonce(String address, Web3j web3j) {
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).send();
        return ethGetTransactionCount.getTransactionCount();
    }

    @SneakyThrows
    public static BigInteger getNetworkGasPrice(Web3j web3j) {
        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
        return ethGasPrice.getGasPrice();
    }

    @SneakyThrows
    public static String getNetVersion(Web3j web3j) {
        return web3j.netVersion().send().getNetVersion();
    }


    @SneakyThrows
    public static BigDecimal getBalance(String address, Web3j web3j) {
        EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
        BigInteger balance = ethGetBalance.getBalance();
        return new BigDecimal(balance.toString()).divide(BigDecimal.TEN.pow(OKT_DECIMAL), OKT_DECIMAL, RoundingMode.HALF_DOWN);

    }

    public static void main(String[] args) {
        EthBlock.Block block = getBlock(15461965L, BlockChainClient.OKC_MAIN_NET.getWeb3j());
        System.out.println(JSON.toJSONString(block));
    }
}
