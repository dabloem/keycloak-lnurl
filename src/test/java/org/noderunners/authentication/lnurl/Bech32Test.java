package org.noderunners.authentication.lnurl;

import fr.acinq.secp256k1.Secp256k1;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import fr.acinq.bitcoin.Bech32;

public class Bech32Test {

    @Test
    public void checkSignature() throws DecoderException {
        byte[] k1 = Hex.decodeHex("9aba6152b43231c8e6021846472e1870802e930af43d078370f332cbfac99e60".toCharArray());
        byte[] key = Hex.decodeHex("037c4ec87637caaca6c52a56543a88a1896b22557909f5b1fecc8b709423df60e9".toCharArray());
        byte[] sig = Hex.decodeHex("3045022100f9983ec84ee361d89559d89f3413d6590e2e80f3f5ff2eb6cc276b16a7caa12a0220178bbc8718f7b223bea4ae0e74f40691f1db5fcba13d52144708da61d36ab56d".toCharArray());

        Secp256k1 secpk256k1 = Secp256k1.get();
        Assert.assertTrue(secpk256k1.verify(sig, k1, key));

    }

    @Test
    public void bech32Encode() throws EncoderException {
        String lnurl = toBech32(URI.create("https://service.com/api?q=3f"));
        System.out.println(lnurl);
    }

    private static String toBech32(URI uri) {
        byte[] data = uri.toString().getBytes(StandardCharsets.UTF_8);
        return Bech32.encode("lnurl", toPrimitives(Bech32.eight2five(data)), Bech32.Encoding.Bech32);
    }

    private static byte[] toPrimitives(Byte[] oBytes) {
        byte[] bytes = new byte[oBytes.length];
        for (int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }
        return bytes;
    }

}