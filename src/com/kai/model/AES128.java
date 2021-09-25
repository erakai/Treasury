package com.kai.model;

import com.kai.main.Runner;

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
     * Convert given plaintext and password into bytes in order to be encrypted.
     *
     * @see AES128#encrypt(byte[][], byte[])
     *
     * @param plaintext The message to be encrypted
     * @param initKey The key in plaintext
     * @return The encrypted text
     */
    public static String encrypt(String plaintext, String initKey) {
        byte[] textBlocks = Base64.getDecoder().decode(plaintext);
        byte[][] blocks = new byte[textBlocks.length / 16 + ((textBlocks.length) % 16 == 0 ? 1 : 2)][16];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < 16; j++) {
                blocks[i][j] = (16 * i + j < textBlocks.length ? textBlocks[16 * i + j] : 0x00);
            }
        }

        encrypt(blocks, RijndaelSchedule.convertKeyToBytes(initKey));

        StringBuilder encryptedString = new StringBuilder();
        for (byte[] word: blocks) encryptedString.append(Base64.getEncoder().encodeToString(word));
        return encryptedString.toString();
    }

    /**
     * Perform the AES-128 cipher on a given array of blocks given a cipher key.
     *
     * @param toEncrypt The array of 16-byte blocks that is encrypted.
     * @param cipherKey A 16-byte word that is expanded into a key schedule.
     */
    public static void encrypt(byte[][] toEncrypt, byte[] cipherKey) {
        byte[] fullRoundKey = RijndaelSchedule.genKeySchedule128(cipherKey);

        for (int round = 0; round < 11; round++) { // 10 rounds for AES-128
            for (byte[] state: toEncrypt) {
                if (round != 0) {
                    subBytes(state);
                    shiftRows(state);
                    if (round != 10) mixColumns(state);
                }
                addRoundKey(state, fullRoundKey, round);
            }
        }
    }

    /**
     * For each byte in the state, substitute it with the given value in Rijndael's sbox.
     *
     * @param state A 16-byte word.
     */
    public static void subBytes(byte[] state) {
        for (int i = 0; i < state.length; i++) state[i] = (byte) GaloisField.sbox[state[i] & 0xff];
    }

    /**
     * Shift the second row of bytes in the state by 1 place to the left, the third by 2, and the fourth by 3.
     * Row means the corresponding column because who knows
     *
     * @param state A 16-byte word.
     */
    public static void shiftRows(byte[] state) {
        byte[] stateCopy = new byte[state.length];
        System.arraycopy(state, 0, stateCopy, 0, state.length);

        state[0] = stateCopy[0];
        state[1] = stateCopy[5];
        state[2] = stateCopy[10];
        state[3] = stateCopy[15];
        state[4] = stateCopy[4];
        state[5] = stateCopy[9];
        state[6] = stateCopy[14];
        state[7] = stateCopy[3];
        state[8] = stateCopy[8];
        state[9] = stateCopy[13];
        state[10] = stateCopy[2];
        state[11] = stateCopy[7];
        state[12] = stateCopy[12];
        state[13] = stateCopy[1];
        state[14] = stateCopy[6];
        state[15] = stateCopy[11];
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
        byte[] colRep = {
                state[0], state[4], state[8], state[12],
                state[1], state[5], state[9], state[13],
                state[2], state[6], state[10], state[14],
                state[3], state[7], state[11], state[15]
        };

        byte[] stateCopy = new byte[colRep.length];
        System.arraycopy(colRep, 0, stateCopy, 0, 16);
        for (int i = 0; i < 4; i++)
            colRep[i] = (byte) (GaloisField.gMul(stateCopy[i], 2) ^ GaloisField.gMul(stateCopy[4 + i], 3)
                    ^ stateCopy[8 + i] ^ stateCopy[12 + i]);
        for (int i = 4; i < 8; i++)
            colRep[i] = (byte) (stateCopy[i - 4] ^ GaloisField.gMul(stateCopy[i], 2)
                    ^ GaloisField.gMul(stateCopy[4 + i], 3) ^ stateCopy[8 + i]);
        for (int i = 8; i < 12; i++)
            colRep[i] = (byte) (stateCopy[i - 8] ^ stateCopy[i - 4] ^ GaloisField.gMul(stateCopy[i], 2)
                    ^ GaloisField.gMul(stateCopy[4 + i], 3));
        for (int i = 12; i < 16; i++)
            colRep[i] = (byte) (GaloisField.gMul(stateCopy[i - 12], 3) ^ stateCopy[i - 8]
                    ^ stateCopy[i - 4] ^ GaloisField.gMul(stateCopy[i], 2));

        byte[] rowRep = {
                colRep[0], colRep[4], colRep[8], colRep[12],
                colRep[1], colRep[5], colRep[9], colRep[13],
                colRep[2], colRep[6], colRep[10], colRep[14],
                colRep[3], colRep[7], colRep[11], colRep[15]
        };

        System.arraycopy(rowRep, 0, state, 0, 16);
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
