package com.java.utils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Signature;

public class SignatureUtils {
    private SignatureUtils() {}

    /**
     * How SHA256WithDSA works:
     * Hashing: First, the message or data to be signed is passed through the SHA-256 hashing algorithm, resulting in a fixed-size 256-bit hash (digest).
     * Signing: The hash is then signed using the DSA algorithm with a private key, producing a digital signature. The signature is typically sent along with the original message.
     */
    public static void main(String[] args) {
        try {
            SecureRandom sr = new SecureRandom();
            var dsaKeyPair = KeyUtils.generateKeyPair("DSA", 1024);
            var signature = Signature.getInstance("SHA256WithDSA");

            signature.initSign(dsaKeyPair.getPrivate(), sr);
            //Get data bytes be needed to signing
            byte[] data = "data".getBytes(StandardCharsets.UTF_8);

            signature.update(data);
            byte[] digitalSignature = signature.sign();

            signature.initVerify(dsaKeyPair.getPublic());
            signature.update(data);

            System.out.println(signature.verify(digitalSignature));
        } catch (Exception e) {

        }

    }
}
