package xin.yukino.blockchain.contract.erc20

import com.google.common.collect.Lists
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.protocol.core.methods.response.EthCall
import spock.lang.Specification
import xin.yukino.blockchain.component.BlockChainClient

class Erc20Test extends Specification {
    def "Symbol"() {
        given:
        String contract = "0xe9e171d3d613328090733f67028e66863c044b96";
        TypeReference utf8String = TypeReference.makeTypeReference("string", false, true);
        when:
        EthCall ethCall = Erc20.symbol(contract, BlockChainClient.ETH_MAIN_NET);
        then:
        System.out.println(FunctionReturnDecoder.decode(ethCall.getResult(), Lists.newArrayList(utf8String)));
    }
}
