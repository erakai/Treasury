package com.kai.model;

class RijndaelSchedule {

    /**
     * Rotates a 32-bit word 8 bits to the left.
     *
     * @param byteArray An array of 4 bytes.
     * @return the rotated array
     */
    void rotate(byte[] byteArray) {
        if (byteArray.length != 4) return;

        byteArray[3] = byteArray[0];
        for (int i = 0; i < 3; i++ ) {
            byte b = byteArray[i + 1];
            byteArray[i] = b;
        }
    }
}
