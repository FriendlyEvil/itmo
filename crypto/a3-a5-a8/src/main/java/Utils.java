public class Utils {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static byte[] bytesToBits(byte[] bytes) {
        byte[] bits = new byte[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                bits[i * 8 + j] = (byte) ((bytes[i] >>> j) & 1);
            }
        }
        return bits;
    }

    public static byte[] bitsToBytes(byte[] bits) {
        byte[] bytes = new byte[bits.length / 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 7; j >= 0; j--) {
                bytes[i] <<= 1;
                bytes[i] |= bits[i * 8 + j] & 1;
            }
            System.out.println();
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
