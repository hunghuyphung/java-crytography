package com.java.utils;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Message digest: is a string of fixed length (hash string), generated using a hash function from original string
 * MAC (Message Authentication Code): A MAC is similar to a message digest, but uses an ADDITIONAL KEY
   to encrypt the message digest => mac string = hash(key + original string)
 */
public class HashMacUtils {
    private HashMacUtils() {}

    public static byte[] hashing(String data, String alg) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(alg);
        return messageDigest.digest(data.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String data = "123";

        System.out.printf("MD5 hash: %s", CharsetUtils.byteArrayToHexString(hashing(data, "MD5")));
        System.out.printf("SHA-256 hash: %s", CharsetUtils.byteArrayToHexString(hashing(data, "SHA-256")));
        System.out.printf("SHA-512 hash: %s", CharsetUtils.byteArrayToHexString(hashing(data, "SHA-512")));
    }
}
