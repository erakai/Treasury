package com.kai.model;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Kai Tinkess
 * @version Sep 22, 2021
 */
public class AES128 {
    private AES128() {}

    /**
     * Given some plaintext and a key, encrypts the plaintext using AES-128.
     *
     * @param plaintext The message to be encrypted
     * @param initKey The key in plaintext
     * @return The encrypted text
     */
    public static String encrypt(String plaintext, String initKey) {
        byte[] textBlocks = plaintext.getBytes(StandardCharsets.UTF_8);
        byte[] roundKeys = RijndaelSchedule.convertKeyToBytes(initKey);
        byte[][] blocks = new byte[16][textBlocks.length / 16 + (textBlocks.length) % 16 == 0 ? 0 : 1];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < 16; j++) {
                blocks[i][j] = (16 * i + j < textBlocks.length ? textBlocks[16 * i + j] : 0x00);
            }
        }

        return null;
    }

    /**
     * For each byte in the state, substitute it with the given value in Rijndael's sbox.
     *
     * @param state A 16-byte word.
     */
    public static void subBytes(byte[] state) { //TODO: Write test
        for (int i = 0; i < state.length; i++) state[i] = (byte) RijndaelSchedule.sbox[state[i] & 0xff];
    }

    /**
     * Shift the second row of bytes in the state by 1 place to the left, the third by 2, and the fourth by 3.
     *
     * @param state A 16-byte word.
     */
    public static void shiftRows(byte[] state) { //TODO: Write test
        for (int i = 1; i < 4; i++) {
            byte[] b = new byte[4];
            System.arraycopy(state, i * 4, b, 0, 4);

            for (int j = 0; j < 4; j++) {
                int posIncrease = j + i;
                if (posIncrease >= 4) posIncrease -= 4;
                state[(i * 4) + j] = b[posIncrease];
            }
        }
    }

    public static void mixColumns(byte[] state) {

    }

    public static void addRoundKey(byte[] state, byte[] roundKey) {

    }



}
