package Leb128;

import java.io.ByteArrayOutputStream;

public class UnsignedLeb128 {
    public byte[] writeUnsignedLeb128(int value) {
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        int remaining = value >>> 7;
        while (remaining != 0) {
            o.write((byte) ((value & 0x7f) | 0x80));
            value = remaining;
            remaining >>>= 7;
        }
        o.write((byte) (value & 0x7f));
        return o.toByteArray();
    }
}
