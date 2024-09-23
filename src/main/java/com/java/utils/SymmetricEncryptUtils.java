package com.java.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * The term Cipher is standard term for an encryption algorithm in the world of cryptography.
 * That is why the Java class is called Cipher
 * <p>
 * Some encryption algorithms can work in different modes. Those are cipher modes, some of the most
 * well-known cipher modes: ECB, CBC, CFB, OFB, CTR
 * CBC mode requires an IV(Initialization vector) for secure operation. The IV is used to randomize the encryption
 * and ensure that identical plaintext blocks don't produce identical ciphertext blocks.
 * <p>
 * If the data to be encrypted doesn’t meet the block size requirement of 128 bits, it must be padded.
 * Padding is the process of filling up the last block to 128 bits.
 * In Java, PKCS5Padding is treated as a general padding scheme for any block cipher
 * <p>
 * DES use 56-bits key and encrypts data in block 64-bits => use PKCS5Padding
 */
public class SymmetricEncryptUtils {
    private SymmetricEncryptUtils() {
    }

    /**
     * Method for create an Initialization vector parameter(IV)
     */
    public static IvParameterSpec ivSpec(int byteSize) {
        byte[] iv = new byte[byteSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        return new IvParameterSpec(iv);
    }


    /**
     * In Java, PKCS5Padding is treated as a general padding scheme for any block cipher. Thus, it is actually
     * performing PKCS7Padding for AES and PKCS5Padding for DES.
     */
    public static String encryptAesInCBC(String input, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decryptAesInCBC(String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    public static void main(String[] args) throws Exception {
        SecretKey aesSecretKey = KeyUtils.generateSymmetricKey("AES", 256);
        IvParameterSpec ivSpec = ivSpec(16);

        String plaintext = "Hello, AES with CBC!";
        String encryptedText = encryptAesInCBC(plaintext, aesSecretKey, ivSpec);

        System.out.println("Encrypted text: " + encryptedText);
        System.out.println("Plaintext: " + decryptAesInCBC(encryptedText, aesSecretKey, ivSpec));
    }
}
