package com.kai;

import com.kai.model.AES128;
import com.kai.model.RijndaelSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Kai Tinkess
 * @version Sep 23, 2021
 */
public class Aes128Test {

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

}
