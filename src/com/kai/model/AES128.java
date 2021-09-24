package com.kai.model;

import java.util.Base64;

/**
 * ECB with Base64
 *
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
        byte[] textBlocks = Base64.getDecoder().decode(plaintext);
        byte[] fullRoundKey = RijndaelSchedule.genKeySchedule128(RijndaelSchedule.convertKeyToBytes(initKey));
        byte[][] blocks = new byte[textBlocks.length / 16 + ((textBlocks.length) % 16 == 0 ? 1 : 2)][16];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < 16; j++) {
                blocks[i][j] = (16 * i + j < textBlocks.length ? textBlocks[16 * i + j] : 0x00);
            }
            addRoundKey(blocks[i], fullRoundKey, 0);
        }

        for (int round = 1; round < 11; round++) { // 11 rounds for AES-128
            for (byte[] state: blocks) {
                subBytes(state);
                shiftRows(state);
                if (round != 10) mixColumns(state);
                addRoundKey(state, fullRoundKey, round);
            }
        }

        StringBuilder encryptedString = new StringBuilder();
        for (byte[] word: blocks) encryptedString.append(Base64.getEncoder().encodeToString(word));
        return encryptedString.toString();
    }

    /**
     * For each byte in the state, substitute it with the given value in Rijndael's sbox.
     *
     * @param state A 16-byte word.
     */
    public static void subBytes(byte[] state) { //TODO: Write test
        for (int i = 0; i < state.length; i++) state[i] = (byte) GaloisField.sbox[state[i] & 0xff];
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

    /**
     * Mix the columns by manually multiplying a matrix (in galois)
     * 02 03 01 01
     * 01 02 03 01
     * 01 01 02 03
     * 03 01 01 02
     * by a 16-word byte.
     *
     * @param state A 16-byte word / a 4x4 matrix
     */
    public static void mixColumns(byte[] state) {
        byte[] stateCopy = new byte[state.length];
        System.arraycopy(state, 0, stateCopy, 0, 16);
        for (int i = 0; i < 4; i++)
            state[i] = (byte) ((GaloisField.mul2Lookup[stateCopy[i] & 0xff]) ^ (GaloisField.mul3Lookup[stateCopy[4 + i]
                    & 0xff]) ^ stateCopy[8 + i] ^ stateCopy[12 + i]);
        for (int i = 4; i < 8; i++)
            state[i] = (byte) (stateCopy[i - 4] ^ (GaloisField.mul2Lookup[stateCopy[i] & 0xff])
                    ^ (GaloisField.mul3Lookup[stateCopy[4 + i] & 0xff]) ^ stateCopy[8 + i]);
        for (int i = 8; i < 12; i++)
            state[i] = (byte) (stateCopy[i - 8] ^ stateCopy[i - 4] ^ (GaloisField.mul2Lookup[stateCopy[i] & 0xff])
                    ^ (GaloisField.mul3Lookup[stateCopy[4 + i] & 0xff]));
        for (int i = 12; i < 16; i++)
            state[i] = (byte) ((GaloisField.mul3Lookup[stateCopy[i - 12] & 0xff]) ^ stateCopy[i - 8]
                    ^ stateCopy[i - 4] ^ (GaloisField.mul2Lookup[stateCopy[i] & 0xff]));
    }

    /**
     * Adds the current state to the corresponding block of the round key.
     *
     * @param state A 16-byte word
     * @param fullRoundKey The full 176-byte round key.
     * @param round The current round the method is called in.
     */
    public static void addRoundKey(byte[] state, byte[] fullRoundKey, int round) {
        byte[] roundKey = new byte[state.length];
        System.arraycopy(fullRoundKey, round * 16, roundKey, 0, 16);

        for (int i = 0; i < state.length; i++) state[i] ^= roundKey[i];
    }



}
