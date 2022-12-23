package xin.yukino.blockchain.component;

import lombok.Builder;
import lombok.Getter;
import okhttp3.OkHttpClient;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import sun.net.SocksProxy;

import java.net.InetSocketAddress;

@Builder
@Getter
public class BlockChainClient {

    private Web3j web3j;

    private String netVersion;

    private long chainId;

    private static OkHttpClient OK_HTTP = HttpService.getOkHttpClientBuilder().proxy(SocksProxy.create(new InetSocketAddress("127.0.0.1", 10010), 5)).build();

    public static final BlockChainClient OKC_MAIN_NET = BlockChainClient.builder().web3j(Web3j.build(new HttpService("https://exchainrpc.okex.org", OK_HTTP))).netVersion("66").chainId(66L).build();

    public static final BlockChainClient OKC_TEST_NET = BlockChainClient.builder().web3j(Web3j.build(new HttpService("https://exchaintestrpc.okex.org", OK_HTTP))).netVersion("65").chainId(65L).build();

    public static final BlockChainClient ETH_MAIN_NET = BlockChainClient.builder().web3j(Web3j.build(new HttpService("https://mainnet.infura.io/v3/099fc58e0de9451d80b18d7c74caa7c1", OK_HTTP))).netVersion("1").chainId(1L).build();
}
