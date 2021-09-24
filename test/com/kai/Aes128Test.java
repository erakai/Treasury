package com.kai;

import com.kai.model.AES128;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Kai Tinkess
 * @version Sep 23, 2021
 */
public class Aes128Test {

    @Test
    public void Aes128EncryptionTest() {
        String plaintext = "testtesttesttest";
        String key = "testtesttesttest";
        String expectedEncrypted = "BQLAkm7JRmnXYHSpxhWfWqgWzpb238QZn80sC0hHruw=";
        String actualEncrypted = AES128.encrypt(plaintext, key);

        Assertions.assertEquals(expectedEncrypted, actualEncrypted);
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
                0x00, 0x01, 0x02, 0x03,
                0x05, 0x06, 0x07, 0x04,
                0x0a, 0x0b, 0x08, 0x09,
                0x0f, 0x0c, 0x0d, 0x0e
        };

        AES128.shiftRows(actualBytes);
        Assertions.assertArrayEquals(expectedBytes, actualBytes);
    }

    @Test
    public void mixColumnsTests() {
        byte[] actualBytes = {
                (byte) 0xdb, (byte) 0xf2, 0x01, 0x2d,
                       0x13,        0x0a, 0x01, 0x26,
                       0x53,        0x22, 0x01, 0x31,
                       0x45,        0x5c, 0x01, 0x4c
        };
        byte[] expectedBytes = {
                (byte) 0x8e, (byte) 0x9f, 0x01,        0x4d,
                       0x4d, (byte) 0xdc, 0x01,        0x7e,
                (byte) 0xa1,        0x58, 0x01, (byte) 0xbd,
                (byte) 0xbc, (byte) 0x9d, 0x01, (byte) 0xf8
        };

        AES128.mixColumns(actualBytes);
        Assertions.assertArrayEquals(expectedBytes, actualBytes);
    }

}
