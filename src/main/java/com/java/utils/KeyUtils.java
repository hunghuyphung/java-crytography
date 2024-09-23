package com.java.utils;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class KeyUtils {
    private KeyUtils() {}

    /**
     * The Java KeyGenerator class (javax.crypto.KeyGenerator) is used to generate symmetric encryption keys
     */
    public static SecretKey generateSymmetricKey(String alg, int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(alg);

        //The reason for using SecureRandom is to ensure that the key generation process uses a cryptographically
        // strong source of randomness.
        keyGenerator.init(keySize, new SecureRandom());

        return keyGenerator.generateKey();
    }

    /**
     * The Java KeyPairGenerator class (java.security.KeyPairGenerator) is used to generate asymmetric encryption / decryption key pairs.
     */
    public static KeyPair generateKeyPair(String alg, int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(alg);
        keyPairGenerator.initialize(keySize, new SecureRandom());

        return keyPairGenerator.generateKeyPair();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //DES uses a 56-bit key for encryption and decryption
        var desKey = generateSymmetricKey("DES", 56);
        // 168-bit key (56 bits * 3). Generate a key. Per block key size is 56 bits
        var tdesKey = generateSymmetricKey("DESede", 168);
        //AES keys can be 128, 192 or 256 bits
        var aes256Key = generateSymmetricKey("AES", 256);

        //encrypted data bytes are commonly encode to hex or base64 string
        System.out.printf("DES key: %s", CharsetUtils.byteArrayToHexString(desKey.getEncoded()));
        System.out.printf("3DES key: %s", CharsetUtils.byteArrayToHexString(tdesKey.getEncoded()));
        System.out.printf("AES key: %s", CharsetUtils.byteArrayToHexString(aes256Key.getEncoded()));

        var rsaKeyPair = generateKeyPair("RSA", 2048);

        System.out.printf("RSA public key: %s", Base64.getEncoder().encodeToString(rsaKeyPair.getPublic().getEncoded()));
        System.out.printf("RSA private key: %s", Base64.getEncoder().encodeToString(rsaKeyPair.getPrivate().getEncoded()));

        var ecKeyPair = generateKeyPair("EC", 256);
        System.out.printf("EC public key: %s", Base64.getEncoder().encodeToString(ecKeyPair.getPublic().getEncoded()));
        System.out.printf("EC private key: %s", Base64.getEncoder().encodeToString(ecKeyPair.getPrivate().getEncoded()));
    }
}
