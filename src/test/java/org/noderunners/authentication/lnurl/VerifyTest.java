package org.noderunners.authentication.lnurl;

import fr.acinq.secp256k1.Secp256k1;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;
import org.lightningj.util.ZBase32;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class VerifyTest {

    Secp256k1 secp256k1 = Secp256k1.get();

    String PREFIX = "Lightning Signed Message:";
    @Test
    public void verifyMsgTest() throws DecoderException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest((PREFIX + "test").getBytes(StandardCharsets.UTF_8));
        hash = md.digest(hash); //double SHA-256 ( Hex: 954d5a49fd70d9b8bcdb35d252267829957f7ef7fa6c74f88419bdc5e82209f4 )

        byte[] signature = ZBase32.decode("dh4pwm6rqz7dfpsfdurbocsyqafgs176qi7nftf33a4rxjxfi5w9a7hfoyugapzdxs4obkca65kqdpuq7eb7q7k7bez44x8ep38py1yo");
        //remove the first byte (rec_id), result is byte[64],
        //otherwise 'secp256k1_ecdsa_signature_parse_der failed' exception, which tells me converting to DER succeeds in verify()
        signature = Arrays.copyOfRange(signature, 1, signature.length);

        byte[] pubkey = Hex.decodeHex("03b3438ad95c08db73cc5a9d2e434f8589fede975b4306a9fb48225704c0a9f1a7".toCharArray()); //node-id

        //public abstract fun verify(signature: kotlin.ByteArray, message: kotlin.ByteArray, pubkey: kotlin.ByteArray): kotlin.Boolean
        boolean verify = secp256k1.verify(signature, hash, pubkey); //returns false :-(
        Assert.assertTrue(verify);
    }
}
