package xin.yukino.blockchain.util

import spock.lang.Specification
import xin.yukino.blockchain.component.BlockChainClient

class Web3jBaseUtilTest extends Specification {
    def "GetNetVersion"() {
        expect:
        println Web3jBaseUtil.getNetVersion(BlockChainClient.PLG_MAIN_NET.web3j)
    }
}
