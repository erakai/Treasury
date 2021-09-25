package com.kai.model;

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

    public static String decToHex(int dec) {
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

    public static void printByteArray(byte[] array) {
        if (array.length != 16) {
            System.out.println("Byte array isn't a word.");
            return;
        }

        byte[] printArray = { // why is this how words are blocks are represented someone help
                array[0], array[4], array[8], array[12],
                array[1], array[5], array[9], array[13],
                array[2], array[6], array[10], array[14],
                array[3], array[7], array[11], array[15]
        };

        int i = 0;
        for (byte b: printArray) {
            String s = AesUtil.decToHex(b & 0xff);
            if (s.length() == 1) s = "0" + s;
            System.out.print(s + "\t");
            if (++i % 4 == 0) System.out.println();
        }
        System.out.println("\n");
    }

}
