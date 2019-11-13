import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class A38Test {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    Random random = new Random();

    public static void main(String[] args) {

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

    @Test
    public void test() {
        byte[] bytes = new byte[16];
        Arrays.fill(bytes, (byte) 0);
        System.out.println(bytesToHex(A38.bitsToBytes(A38.encode(bytes, bytes).getSRES())));
    }

    private byte[] randombytes(int len) {
        byte[] bytes = new byte[len];
        random.nextBytes(bytes);
        return bytes;
    }
}
