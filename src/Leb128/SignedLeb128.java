package Leb128;

import java.io.ByteArrayOutputStream;

public class SignedLeb128 {
    public static byte[] writeSignedLeb128(int value) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int remaining = value >> 7;
        boolean hasMore = true;
        int end = ((value & Integer.MIN_VALUE) == 0) ? 0 : -1;
        while (hasMore) {
            hasMore = (remaining != end) || ((remaining & 1) != ((value >> 6) & 1));
            out.write((byte) ((value & 0x7f) | (hasMore ? 0x80 : 0)));
            value = remaining;
            remaining >>= 7;
        }
        return out.toByteArray();
    }
}
