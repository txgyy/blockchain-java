package xin.yukino.blockchain.constant;

import lombok.SneakyThrows;
import org.web3j.crypto.Credentials;
import xin.yukino.blockchain.util.WalletUtil;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public interface MyConstant {

    Properties PROPERTIES = initEnv();

    String DEFAULT_MNEMONIC = PROPERTIES.getProperty("DEFAULT_MNEMONIC");

    Credentials DEFAULT_SENDER = WalletUtil.generateBip44Credentials(DEFAULT_MNEMONIC, 0);

    BigInteger DEFAULT_GAS_LIMIT = BigInteger.valueOf(4000000);


    @SneakyThrows
    static Properties initEnv() {
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Paths.get(".env")));
        return properties;
    }
}
