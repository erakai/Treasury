package com.kai;

import com.kai.model.AES128;
import com.kai.model.AesUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Kai Tinkess
 * @version Sep 23, 2021
 */
public class Aes128Test {

    @Test
    public void Aes128StringEncryptionTest() {
        String plaintext = "testtesttesttest";
        String key = "testtesttesttest";
        String expectedEncrypted = "BQLAkm7JRmnXYHSpxhWfWqgWzpb238QZn80sC0hHruw=";
        String actualEncrypted = AES128.encrypt(plaintext, key);

        Assertions.assertEquals(expectedEncrypted, actualEncrypted);
    }

    @Test
    public void Aes128ByteEncryptionTest() {
        //Test vectors given by https://csrc.nist.gov/csrc/media/publications/fips/197/final/documents/fips-197.pdf
        byte[][] blocks = {
                {0x32, 0x43, (byte) 0xf6, (byte) 0xa8, (byte) 0x88, 0x5a, 0x30, (byte) 0x8d, 0x31,
                        0x31, (byte) 0x98, (byte) 0xa2, (byte) 0xe0, 0x37, 0x07, 0x34},
                {0x32, 0x43, (byte) 0xf6, (byte) 0xa8, (byte) 0x88, 0x5a, 0x30, (byte) 0x8d, 0x31,
                        0x31, (byte) 0x98, (byte) 0xa2, (byte) 0xe0, 0x37, 0x07, 0x34}
        };
        byte[] cipherKey = {0x2b, 0x7e, 0x15, 0x16, 0x28, (byte) 0xae, (byte) 0xd2, (byte) 0xa6,
                (byte) 0xab, (byte) 0xf7, 0x15, (byte) 0x88, 0x09, (byte) 0xcf, 0x4f, 0x3c};
        byte[][] expectedOutput = {
                {0x39, 0x25, (byte) 0x84, 0x1d, 0x02, (byte) 0xdc, 0x09, (byte) 0xfb, (byte) 0xdc, 0x11,
                        (byte) 0x85, (byte) 0x97, 0x19, 0x6a, 0x0b, 0x32},
                {0x39, 0x25, (byte) 0x84, 0x1d, 0x02, (byte) 0xdc, 0x09, (byte) 0xfb, (byte) 0xdc, 0x11,
                        (byte) 0x85, (byte) 0x97, 0x19, 0x6a, 0x0b, 0x32}
        };

        AES128.encrypt(blocks, cipherKey);
        Assertions.assertArrayEquals(expectedOutput, blocks);
    }

    @Test
    public void Aes128ByteDecryptionTest() {
        byte[][] blocks = {
                {0x39, 0x25, (byte) 0x84, 0x1d, 0x02, (byte) 0xdc, 0x09, (byte) 0xfb, (byte) 0xdc, 0x11,
                        (byte) 0x85, (byte) 0x97, 0x19, 0x6a, 0x0b, 0x32},
                {0x39, 0x25, (byte) 0x84, 0x1d, 0x02, (byte) 0xdc, 0x09, (byte) 0xfb, (byte) 0xdc, 0x11,
                        (byte) 0x85, (byte) 0x97, 0x19, 0x6a, 0x0b, 0x32}
        };
        byte[] cipherKey = {0x2b, 0x7e, 0x15, 0x16, 0x28, (byte) 0xae, (byte) 0xd2, (byte) 0xa6,
                (byte) 0xab, (byte) 0xf7, 0x15, (byte) 0x88, 0x09, (byte) 0xcf, 0x4f, 0x3c};
        byte[][] expectedOutput = {
                {0x32, 0x43, (byte) 0xf6, (byte) 0xa8, (byte) 0x88, 0x5a, 0x30, (byte) 0x8d, 0x31,
                        0x31, (byte) 0x98, (byte) 0xa2, (byte) 0xe0, 0x37, 0x07, 0x34},
                {0x32, 0x43, (byte) 0xf6, (byte) 0xa8, (byte) 0x88, 0x5a, 0x30, (byte) 0x8d, 0x31,
                        0x31, (byte) 0x98, (byte) 0xa2, (byte) 0xe0, 0x37, 0x07, 0x34}
        };

        AES128.decrypt(blocks, cipherKey);
        Assertions.assertArrayEquals(expectedOutput, blocks);
    }

    @Test
    public void subBytesTest() {
        byte[] actualBytes = {0x3f, 0x02, (byte) 0xff, 0x5e};
        byte[] expectedBytes = {0x75, 0x77, 0x16, 0x58};

        AES128.subBytes(actualBytes);
        Assertions.assertArrayEquals(expectedBytes, actualBytes);
    }

    @Test
    public void shiftRowsTest() {
        byte[] actualBytes = {
                0x00, 0x01, 0x02, 0x03,
                0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b,
                0x0c, 0x0d, 0x0e, 0x0f
        };
        byte[] expectedBytes = {
                0x00, 0x05, 0x0a, 0x0f,
                0x04, 0x09, 0x0e, 0x03,
                0x08, 0x0d, 0x02, 0x07,
                0x0c, 0x01, 0x06, 0x0b
        };

        AES128.shiftRows(actualBytes);
        System.out.println("EXPECTED: ");
        AesUtil.printByteArray(expectedBytes);
        System.out.println("ACTUAL: ");
        AesUtil.printByteArray(actualBytes);
        Assertions.assertArrayEquals(expectedBytes, actualBytes);
    }

    @Test
    public void mixColumnsTests() {
        byte[] actualBytes = {
                (byte) 0xd4, (byte) 0xbf,        0x5d,        0x30,
                (byte) 0xe0, (byte) 0xb4,        0x52, (byte) 0xae,
                (byte) 0xb8,        0x41,        0x11, (byte) 0xf1,
                       0x1e,        0x27, (byte) 0x98, (byte) 0xe5
        };
        byte[] expectedBytes = {
                (byte) 0x04, (byte) 0x66, (byte) 0x81, (byte) 0xe5,
                (byte) 0xe0, (byte) 0xcb,        0x19, (byte) 0x9a,
                (byte) 0x48, (byte) 0xf8, (byte) 0xd3, (byte) 0x7a,
                (byte) 0x28, (byte) 0x06,        0x26, (byte) 0x4c
        };

        AES128.mixColumns(actualBytes);
        Assertions.assertArrayEquals(expectedBytes, actualBytes);
    }

}
