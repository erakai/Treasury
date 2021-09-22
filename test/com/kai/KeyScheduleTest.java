package com.kai;

import com.kai.model.BaseConverter;
import com.kai.model.RijndaelSchedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KeyScheduleTest {

    @Test
    public void rotateTest() {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte) BaseConverter.hexToDec("1d");
        byteArray[1] = (byte) BaseConverter.hexToDec("2c");
        byteArray[2] = (byte) BaseConverter.hexToDec("3a");
        byteArray[3] = (byte) BaseConverter.hexToDec("4f");

        RijndaelSchedule.rotate(byteArray);
        String hex = "";
        for (byte b: byteArray) {
            hex += (BaseConverter.decToHex(b));
        }

        Assertions.assertEquals("2c3a4f1d", hex);
    }
}
