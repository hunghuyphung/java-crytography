package com.java.utils;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class KeyUtils {
    private KeyUtils() {
    }

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

    public static ECPublicKey getPublicKeyFromBytes(byte[] publicKeyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
        // Create key specification from the bytes
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        // Get KeyFactory for EC algorithm
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        // Generate PublicKey
        return (ECPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static ECPrivateKey getPrivateKeyFromBytes(byte[] privateKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Create key specification from the bytes
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        // Get KeyFactory for EC algorithm
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        // Generate PrivateKey
        return (ECPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public static void main(String[] args) throws Exception {
        //DES uses a 56-bit key for encryption and decryption
        var desKey = generateSymmetricKey("DES", 56);
        // 168-bit key (56 bits * 3). Generate a key. Per block key size is 56 bits
        var tdesKey = generateSymmetricKey("DESede", 168);
        //AES keys can be 128, 192 or 256 bits
        var aes256Key = generateSymmetricKey("AES", 256);

        //encrypted data bytes are commonly encode to hex or base64 string
        System.out.printf("DES key: %s\n", CharsetUtils.byteArrayToHexString(desKey.getEncoded()));
        System.out.printf("3DES key: %s\n", CharsetUtils.byteArrayToHexString(tdesKey.getEncoded()));
        System.out.printf("AES key: %s\n", CharsetUtils.byteArrayToHexString(aes256Key.getEncoded()));

        var rsaKeyPair = generateKeyPair("RSA", 2048);

        System.out.printf("RSA public key: %s\n", Base64.getEncoder().encodeToString(rsaKeyPair.getPublic().getEncoded()));
        System.out.printf("RSA private key: %s\n", Base64.getEncoder().encodeToString(rsaKeyPair.getPrivate().getEncoded()));

        var ecKeyPair = generateKeyPair("EC", 256);
        var base64EcPublicKey = Base64.getEncoder().encodeToString(ecKeyPair.getPublic().getEncoded());
        var base64EcPrivateKey = Base64.getEncoder().encodeToString(ecKeyPair.getPrivate().getEncoded());

        System.out.printf("EC public key: %s\n", base64EcPublicKey);
        System.out.printf("EC private key: %s\n", base64EcPrivateKey);

        var ecPublicKey = getPublicKeyFromBytes(Base64.getDecoder().decode(base64EcPublicKey));
        var ecPrivateKey = getPrivateKeyFromBytes(Base64.getDecoder().decode(base64EcPrivateKey));
    }
}
