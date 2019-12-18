import java.util.Arrays;

public final class Utils {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toLowerCase().toCharArray();

    public static void leftCyclicShift(int[] arr, int pos, int shift) {
        int number = arr[pos];
        number = (number << shift) | (number >>> (32 - shift));
        arr[pos] = number;
    }

    public static void rightCyclicShift(int[] arr, int pos, int shift) {
        int number = arr[pos];
        number = (number >>> shift) | (number << (32 - shift));
        arr[pos] = number;
    }

    public static void swap(int[] arr, int first, int second) {
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    public static void add(int[] arr, int first, int second) {
//        arr[first] += arr[second]; //module 2^32
        arr[second] += arr[first]; //module 2^32
    }

    public static void xorItems(int[] arr, int first, int second) {
//        arr[first] ^= arr[second];
        arr[second] ^= arr[first];
    }

    public static void xor(int[] arr, int ind, int value) {
//        arr[first] ^= arr[second];
        arr[ind] ^= value;
    }

    public static String intToHex(int[] arr) {
        return bytesToHex(intToBytes(arr));
    }

    public static int[] byteToInt(byte[] bytes) {
        int size = (bytes.length + 3) / 4;
        int[] ints = new int[size];

        for (int i = 0; i < bytes.length; i += 4) {
            int value = 0;
            for (int j = 0; j < 4; j++) {
                value <<= 8;
                value ^= (0xFFFF & getByte(bytes, i + j));
            }
            ints[i / 4] = value;
        }
        return ints;
    }


    private static int getByte(byte[] ar, int ind) {
        if (ind >= ar.length) {
            return 0;
        }
        int a = ar[ind];
        if (a < 0) {
            return (a & Byte.MAX_VALUE) | 0x80;
        }
        return a;
    }

    public static byte[] intToBytes(int[] longs) {
        int end = longs.length * 4;
        byte[] bytes = new byte[end];
        for (int i = 0; i < longs.length; i++) {
            for (int j = 3; j >= 0 && (i * 4 + 3 - j < end); j--) {
                bytes[i * 4 + 3 - j] = (byte) ((longs[i] >> (8 * j)) & 0xFF);
            }
        }
        return bytes;
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static int[] apeendToLenght(byte[] arr, int len) {
        return Arrays.copyOf(byteToInt(arr), len);
    }
}
