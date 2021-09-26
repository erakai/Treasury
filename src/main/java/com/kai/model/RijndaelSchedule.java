package com.kai.model;

import java.util.Base64;

/**
 * @author Kai Tinkess
 * @version Sep 22, 2021
 */
public class RijndaelSchedule {
    private RijndaelSchedule() {}

    /**
     * @see RijndaelSchedule#genKeySchedule128(byte[])
     *
     * @param initKey The initial key given in plaintext
     * @return A 176-length byte array composed of 4 words for 11 rounds.
     */
    public static byte[] getKeySchedule128(String initKey) {
        return genKeySchedule128(convertKeyToBytes(initKey));
    }

    /**
     * Generates an expanded array of 176 bytes given a 16 byte key. The first 16 bytes are the encryption key, and
     * after that the first word of each round has scheduleCore() ran on it. Then each word is XOR with the word
     * in the previous round that had its location.
     *
     * @see RijndaelSchedule#scheduleCore(byte[], int)
     *
     * @param initKey The initial 16 byte encryption key.
     * @return A 176-length byte array composed of 4 words for 11 rounds.
     */
    public static byte[] genKeySchedule128(byte[] initKey) {
        int byteNumber = 4 * 4 * 11, rconValue = 1;
        byte[] keyWords = new byte[byteNumber];
        byte[] tempWord = new byte[4];

        if (initKey.length != 16) return null;
        for (int k = 0; k < 16; k++) {
            keyWords[k] = (byte) initKey[k];
        }

        for (int i = 16; i < byteNumber;) {
            System.arraycopy(keyWords, i - 4, tempWord, 0, 4);
            if (i % 16 == 0) {
                scheduleCore(tempWord, rconValue);
                rconValue++;
            }

            for (int j = 0; j < 4; j++) {
                keyWords[i] = (byte) (keyWords[i - 16] ^ tempWord[j]);
                i++;
            }
        }

        return keyWords;
    }

    /**
     * Takes in the initial given key and puts the first 16 digits into a byte array according to UTF-8.
     * //TODO: Actually make it do the thing https://security.stackexchange.com/questions/38828/how-can-i-securely-convert-a-string-password-to-a-key-used-in-aes
     *
     *
     * @param initKey The given key in plaintext
     * @return An array of 16 bytes
     */
    public static byte[] convertKeyToBytes(String initKey) {
        byte[] bytes = new byte[16];
        byte[] givenBytes = Base64.getDecoder().decode(initKey.strip());
        for (int i = bytes.length-1; i >= 0; i--) {
            if (i < givenBytes.length) {
                bytes[i] = givenBytes[i];
            } else {
                bytes[i] = 0x00;
            }
        }

        return bytes;
    }

    /**
     * Core method that scrambles the bytes during key expansion. Takes an array of 4 bytes, rotates it, and then
     * substitutes each byte using sbox. Then the rcon value for the round is added to the first byte.
     *
     * @param byteArray An array of 4 bytes to be scrambled.
     * @param round The round number the method is called in.
     */
    public static void scheduleCore(byte[] byteArray, int round) {
        rotate(byteArray);
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) GaloisField.sbox[byteArray[i] & 0xff];
        }
        byteArray[0] ^= (rcon(round)[0] & 0xff);
    }

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
     * @param rc The round rc that the constant is generated for
     * @return An array of 4 bytes with the rcon as the first value and 0 for the rest
     */
    public static byte[] rcon(int rc) {
        //TODO: Correct rcon generation so rc=10 isn't hardcoded
        byte[] byteArray = {0, 0, 0, 0};
        if (rc == 0) return byteArray;
        byteArray[0] = (byte) Math.pow(2, rc-1);

        if (rc == 10) {
            byteArray[0] = 0x36;
        } else if (Math.pow(2, rc - 1) >= 256) {
            byteArray[0] = (byte) ((2 * (rcon(rc-1)[0] & 0xff)) ^ (0x11b));
        }

        return byteArray;
    }

}
