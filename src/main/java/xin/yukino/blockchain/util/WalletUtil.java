package xin.yukino.blockchain.util;

import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Bip44WalletUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import xin.yukino.blockchain.constant.MyConstant;

import java.security.SecureRandom;

import static org.web3j.crypto.Bip32ECKeyPair.HARDENED_BIT;

public class WalletUtil {

    /**
     * generate a random group of mnemonics
     * 生成一组随机的助记词
     */
    public static String generateMnemonics() {
        byte[] initialEntropy = new byte[16];
        new SecureRandom().nextBytes(initialEntropy);

        return MnemonicUtils.generateMnemonic(initialEntropy);
    }

    public static byte[] generateSeed(String mnemonic) {
        return MnemonicUtils.generateSeed(mnemonic, "");
    }

    public static Bip32ECKeyPair createBip32ECKeyPair(byte[] seed) {
        return Bip32ECKeyPair.generateKeyPair(seed);
    }

    public static Bip32ECKeyPair createBip44ECKeyPair(byte[] seed) {
        Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
        return Bip44WalletUtils.generateBip44KeyPair(masterKeypair, false);

    }

    public static Credentials generateBip44Credentials(String mnemonic, int index) {
        byte[] seed = generateSeed(mnemonic);
        Bip32ECKeyPair master = createBip32ECKeyPair(seed);
        int[] path = {44 | HARDENED_BIT, 60 | HARDENED_BIT, 0 | HARDENED_BIT, 0, index};
        Bip32ECKeyPair bip44ECKeyPair = Bip32ECKeyPair.deriveKeyPair(master, path);
        return Credentials.create(bip44ECKeyPair);
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 50; i++) {
            System.out.printf("index: %d; address: %s\n", i, generateBip44Credentials(MyConstant.DEFAULT_MNEMONIC, i).getAddress());
        }
    }
}
