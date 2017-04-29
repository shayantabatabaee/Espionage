package com.gravity.andorid.util;

/**
 * Created by shayantabatabee on 1/27/17.
 */

public class ShortToByteConvertor {

    public static byte[] shortToByte(short[] shorts) {
        byte[] bytes = new byte[shorts.length * 2];
        for (int i = 0; i < shorts.length; i++) {
            bytes[i * 2] = (byte) (shorts[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (shorts[i] >> 8);
            shorts[i] = 0;
        }

        return bytes;
    }
}
