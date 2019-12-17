public final class Utils {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static int leftCyclicShift(int number, int shift) {
        return (number << shift) | (number >>> (32 - shift));
    }

    public static int rightCyclicShift(int number, int shift) {
        return (number >>> shift) | (number << (32 - shift));
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

    public static void xor(int[] arr, int first, int second) {
//        arr[first] ^= arr[second];
        arr[second] ^= arr[first];
    }

    public static String intToHex(int[] arr) {
        return bytesToHex(intToBytes(arr));
    }

    public static byte[] intToBytes(int[] longs) {
        int end = longs.length * 4;
        byte[] bytes = new byte[end];
        for (int i = 0; i < longs.length; i++) {
            for (int j = 3; j >= 0 && (i * 4 + 3 - j < end); j--) {
                bytes[i * 4 + 3 - j] = (byte) ((longs[i] >> (4 * j)) & 0xFF);
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
}
