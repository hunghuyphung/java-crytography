package com.java.utils;

import java.nio.charset.StandardCharsets;

/**
 * Need to differentiate: Hex string, ASCII string, byte, bit
 * Relationship between ASCII and UTF-8 characters
 */
public final class CharsetUtils {
    private CharsetUtils() {}

    /**
     * In Java, a byte is an 8-bit signed integer. This means it can hold values in the range of -128 to 127.
     * Use ascii table for search per integer to ascii character and vice versa
     */
    public static char byteToChar(byte b) {
        return (char) b;
    }

    public static byte charToByte(char c) {
        return (byte) c;
    }


    /**
     * Method for convert hexadecimal string to bytes
     * 1byte = 8bit, 1hex = 4bit => bytes size = 2 length of hex string
     * Take a hex char modula by 2 => 4bit
     * Ex: 72bc (string) => 7 2 b c (char) => 0111 0010 1011 1100 (bit) => 2 (byte)
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }


    /**
     * Method for convert bytes to ASCII String
     */
    public static String byteArrayToString(byte[] bytes) {
        try {
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    public static byte[] stringToByteArray(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }


    /**
     * A number is in decimal. Per number is corresponding to an ascii character
     * For details, use an ascii table to check for
     */
    public static String numberArrayToString(int[] asciiValues) {
        StringBuilder result = new StringBuilder();
        for (int value : asciiValues) {
            result.append((char) value);
        }

        return result.toString();
    }
}
