package com.kai;

import com.kai.model.BaseConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Kai Tinkess
 * @version Sep 22, 2021
 */
public class BaseConverterTest {

    @Test
    public void testDecToHex() {
        int[] initDec = {5, 32, 43, 100, 163, 343, 483993};
        String[] expectedHex = {"5", "20", "2b", "64", "a3", "157", "76299"};
        String[] convertedDec = new String[initDec.length];

        for (int i = 0; i < initDec.length; i++) convertedDec[i] = BaseConverter.decToHex(initDec[i]);
        Assertions.assertArrayEquals(expectedHex, convertedDec);
    }

    @Test
    public void testHexToDec() {
        String[] initHex = {"5", "20", "2B", "64", "A3", "157", "76299"};
        int[] expectedDec = {5, 32, 43, 100, 163, 343, 483993};
        int[] convertedHex = new int[initHex.length];

        for (int i = 0; i < initHex.length; i++) convertedHex[i] = BaseConverter.hexToDec(initHex[i]);
        Assertions.assertArrayEquals(expectedDec, convertedHex);
    }
}
