package com.kai.model;

public class RijndaelSchedule {
    private RijndaelSchedule() {}

    /**
     * Rotates a 32-bit word 8 bits to the left.
     *
     * @param byteArray An array of 4 bytes.
     */
    public static void rotate(byte[] byteArray) {
        if (byteArray.length != 4) return;

        byte init = byteArray[0];
        for (int i = 0; i < 3; i++ ) {
            byte b = byteArray[i + 1];
            byteArray[i] = b;
        }
        byteArray[3] = init;
    }

    /**
     * Creates the round constant rcon, an 8 bit value equivalent to 2 raised to a given power.
     *
     * @param rc the round rc that the constant is generated for
     * @return an array of 4 bytes with the rcon as the first value and 0 for the rest
     */
    public static byte[] rcon(int rc) {
        byte[] byteArray = {0, 0, 0, 0};
        if (rc == 0) return byteArray;
        byteArray[0] = (byte) Math.pow(2, rc);

        if (Math.pow(2, rc - 1) >= 128) {
            byteArray[0] = (byte) (byteArray[0] ^ ((byte) BaseConverter.hexToDec("11B")));
        }

        return byteArray;
    }
}
