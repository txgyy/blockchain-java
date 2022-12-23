package xin.yukino.blockchain.contract.erc20;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.web3j.abi.FunctionReturnDecoder;
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

    @SneakyThrows
    public static void main(String[] args) {
        String contract = "0xdf54b6c6195ea4d948d03bfd818d365cf175cfc2";
        EthCall ethCall = symbol(contract);

        TypeReference utf8String = TypeReference.makeTypeReference("string", false, true);
        System.out.println(FunctionReturnDecoder.decode(ethCall.getResult(), Lists.newArrayList(utf8String)));
    }
}
