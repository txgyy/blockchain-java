package xin.yukino.blockchain.component;

import lombok.Builder;
import lombok.Getter;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Builder
@Getter
public class BlockChainClient {

    private Web3j web3j;

    private String netVersion;

    private long chainId;

    public static final BlockChainClient OKC_MAIN_NET = BlockChainClient.builder().web3j(Web3j.build(new HttpService("https://exchainrpc.okex.org"))).netVersion("66").chainId(66L).build();

    public static final BlockChainClient OKC_TEST_NET = BlockChainClient.builder().web3j(Web3j.build(new HttpService("https://exchaintestrpc.okex.org"))).netVersion("65").chainId(65L).build();

}
