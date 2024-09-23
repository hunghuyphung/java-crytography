package com.java.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

public class AsymmetricEncryptUtils {
    private static final String RSA_OAEP_PADDING = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";


    private AsymmetricEncryptUtils() {}

    public static String encryptRSA(String input, PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(RSA_OAEP_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decryptRSA(String cipherText, PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(RSA_OAEP_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    public static void main(String[] args) {
        try {
            String data = "data";
            KeyPair rsaKeyPair = KeyUtils.generateKeyPair("RSA", 2048);

            String encryptedData = encryptRSA(data, rsaKeyPair.getPublic());
            System.out.println("Encrypted data: " + encryptedData);
            System.out.println(decryptRSA(encryptedData, rsaKeyPair.getPrivate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
