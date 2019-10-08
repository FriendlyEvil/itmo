public class Helper {
    public static long[] byteToLongs(byte[] bytes, boolean addCountOfNewBytes) {
        int size = (bytes.length + 7) / 8;
        if (addCountOfNewBytes)
            size += 1;
        long[] longs = new long[size];
        if (addCountOfNewBytes)
            longs[longs.length - 1] = ((bytes.length + 7) / 8) * 8 - bytes.length;
        for (int i = 0; i < bytes.length; i += 8) {
            long value = 0;
            for (int j = 0; j < 8; j++) {
                value <<= 8;
                value ^= (0xFF & getByte(bytes, i + j));
            }
            longs[i / 8] = value;
        }
        return longs;
    }

    public static byte[] longToBytes(long[] longs, boolean removeExtraBytes) {
        int end = longs.length * 8;
        if (removeExtraBytes) {
            end = end - 8 - (int) longs[longs.length - 1];
        }
        byte[] bytes = new byte[end];
        for (int i = 0; i < longs.length; i++) {
            for (int j = 7; j >= 0 && (i * 8 + 7 - j < end); j--) {
                bytes[i * 8 + 7 - j] = (byte) ((longs[i] >> (8 * j)) & 0xFF);
            }
        }
        return bytes;
    }

    public static long permutation(long value, long[] per) {
        long res = 0;
        for (int i = 0; i < per.length; i++) {
            res |= (getBit(value, per[i]) << i);
        }
        return res;
    }

    private static byte getByte(byte[] ar, int ind) {
        return ind >= ar.length ? 0 : ar[ind];
    }

    private static long getBit(long value, long ind) {
        return 1L & (value >> (ind - 1));
    }

    public static long getSixBitFromPosition(long num, long pos) {
        return (int) ((num >> (pos)) & 63);
    }

    public static long countOfNonZeroBits(long k) {
        int res = 0;
        for (int i = 0; i < 64; i++) {
            res += ((k >>> i) & 1);
        }
        if (res % 2 == 0) {
            return 1;
        }
        return 0;
    }
}
