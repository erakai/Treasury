package com.kai.model;

public class BaseConverter {
    private BaseConverter() {}

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


}
