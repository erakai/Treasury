package com.kai.model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Kai Tinkess
 * @version Sep 22, 2021
 */
public class AesUtil {
    private AesUtil() {}

    public static String byteToBinary(byte dec) {
        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(dec & 0xff));
        while (binaryString.length() != 8) binaryString.insert(0, "0");
        return binaryString.toString();
    }

    public static String blocksToString(byte[][] blocks) {
        StringBuilder encryptedString = new StringBuilder();
        for (byte[] word: blocks) encryptedString.append(Base64.getEncoder().encodeToString(word));
        return encryptedString.toString();
    }

    public static byte[][] stringToBlocks(String s) {
        return singleByteArrayToMatrix(Base64.getDecoder().decode(s));
    }

    //TODO: Write test for convertToWords()
    public static byte[][] singleByteArrayToMatrix(byte[] array) {
        byte[][] words = new byte[array.length / 16  + 1][16];
        for (int i = 0; i < array.length; i++) {
            words[i / 16][i % 16] = array[i];
        }
        return words;
    }

    public static byte[] matrixByteArrayToSingle(byte[][] blocks) {
        List<Byte> bytes = new ArrayList<>();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                bytes.add(blocks[i][j]);
            }
        }

        byte[] array = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) array[i] = bytes.get(i);
        return array;
    }

    public static String decToHex(int dec) {
        if (dec == 0) return "00";
        StringBuilder reverseHex = new StringBuilder();
        while (dec > 0) {
            int rem = dec % 16;
            dec /= 16;

            switch (rem) {
                case 10:
                    reverseHex.append("A");
                    break;
                case 11:
                    reverseHex.append("B");
                    break;
                case 12:
                    reverseHex.append("C");
                    break;
                case 13:
                    reverseHex.append("D");
                    break;
                case 14:
                    reverseHex.append("E");
                    break;
                case 15:
                    reverseHex.append("F");
                    break;
                default:
                    reverseHex.append(rem);
            }
        }

        return reverseHex.reverse().toString().toLowerCase();
    }

    public static int hexToDec(String hex) {
        String[] hexValues = hex.toLowerCase().split("");
        int dec = 0;

        for (int i = hexValues.length-1; i >= 0; i--) {
            int intValue;

            switch (hexValues[i]) {
                case "a":
                    intValue = 10;
                    break;
                case "b":
                    intValue = 11;
                    break;
                case "c":
                    intValue = 12;
                    break;
                case "d":
                    intValue = 13;
                    break;
                case "e":
                    intValue = 14;
                    break;
                case "f":
                    intValue = 15;
                    break;
                default:
                    intValue = Integer.parseInt(hexValues[i]);
            }

            dec += (intValue * ((int) Math.pow(16, hexValues.length - i - 1)));
        }

        return dec;
    }

    public static void printByteArray(byte[] array, boolean isWord) {
        byte[] printArray = array;
        if (isWord) {
            if (array.length != 16) {
                System.out.println("Byte array isn't a word.");
                return;
            }

            printArray = flipByteArray(array);
        }

        int i = 0;
        for (byte b: printArray) {
            String s = decToHex(b & 0xff);
            if (s.length() == 1) s = "0" + s;
            System.out.print(s + "\t");
            if (++i % 4 == 0) System.out.println();
        }
        System.out.println("\n");
    }

    public static byte[] flipByteArray(byte[] array) {
        return new byte[] {
                array[0], array[4], array[8], array[12],
                array[1], array[5], array[9], array[13],
                array[2], array[6], array[10], array[14],
                array[3], array[7], array[11], array[15]
        };
    }

}
