package xin.yukino.blockchain.util;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.util.List;
import java.util.stream.Collectors;

public class CodecUtil {

    @SneakyThrows
    public static String decodeHex(String hex) {
        return new String(Hex.decodeHex(hex));
    }


    public static String buildMethodSignature(
            String methodName, List<Type> parameters) {

        final StringBuilder result = new StringBuilder();
        result.append(methodName);
        result.append("(");
        final String params =
                parameters.stream().map(Type::getTypeAsString).collect(Collectors.joining(","));
        result.append(params);
        result.append(")");
        return result.toString();
    }

    public static String buildMethodId(String methodSignature) {
        final byte[] input = methodSignature.getBytes();
        final byte[] hash = Hash.sha3(input);
        return Numeric.toHexString(hash).substring(0, 10);
    }

    public static void main(String[] args) {
        String methodName = "test1";
        List<Type> parameters = Lists.newArrayList();
        System.out.println(buildMethodId(buildMethodSignature(methodName, parameters)));
    }


}
