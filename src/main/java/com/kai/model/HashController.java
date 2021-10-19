package com.kai.model;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Controller class for SHA-256 hashing using Java's inbuilt MessageDigest
 *
 * @author Kai Tinkess
 * @version Oct 18, 2021
 */
public class HashController {
    private HashController() {}

    //TODO: Implement salting and probably switch to some random library
    public static byte[] hash(String plain) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(plain.getBytes(StandardCharsets.UTF_8));

            plain = "";
            return bytes;
        } catch (NoSuchAlgorithmException e) {e.printStackTrace();}
        return new byte[] {};
    }

    public static String byteToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            if (Integer.toHexString(b & 0xff).length() == 1) sb.append("0");
            sb.append(Integer.toHexString(b & 0xff));
        }
        Arrays.fill(bytes, (byte) 0);

        return sb.toString();
    }

    public static byte[] hexToByte(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i+=2) {
            byte b = (byte) (Integer.parseInt(String.valueOf((hex.charAt(i))) + (hex.charAt(i+1)), 16) & 0xff);
            bytes[i / 2] = b;
        }
        return bytes;
    }
}
