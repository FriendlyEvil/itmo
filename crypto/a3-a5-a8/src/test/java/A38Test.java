import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class A38Test {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static Random random = new Random(0);

//    public static void main(String[] args) {
//        System.out.println("Type Ki: ");
//        byte[] ki = read16ByteArray();
//        System.out.println("SRES" + Arrays.toString(ki));
//        byte[] rand = randombytes(16);
//        A38.A38Res res = A38.encode(ki, rand);
//
//        System.out.print("SRES" + Arrays.toString(res.getSRES()));
//    }

    private static void printByteArray(byte[] ar) {
        for (byte b : ar) {
            System.out.print(b + " ");
        }
        System.out.println();
    }

    private static byte[] read16ByteArray() {
        byte[] bytes = new byte[16];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 16; i++) {
            System.out.print("byte " + i + ": ");
            bytes[i] = scanner.nextByte();
//            System.out.println();
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

    private static byte[] randombytes(int len) {
        byte[] bytes = new byte[len];
        random.nextBytes(bytes);
        return bytes;
    }

    @Test
    public void testZeros() {
        byte[] bytes = new byte[16];
        Arrays.fill(bytes, (byte) 0);
        byte[] sres = A38.encode(bytes, bytes).getSRES();
        byte[] kc = Utils.bitsToBytes(A38.encode(bytes, bytes).getKc());
        Assert.assertArrayEquals(new byte[]{9, -27, 93, -92}, sres);
        Assert.assertArrayEquals(new byte[]{23, 71, 87, 120, 61, -60, 4, 0}, kc);
    }

    @Test
    public void test() {
        byte[] key = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        byte[] rand = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        A38.A38Res encode = A38.encode(key, rand);
        byte[] sres = encode.getSRES();
        byte[] kc = Utils.bitsToBytes(encode.getKc());
        Assert.assertArrayEquals(new byte[]{-107, -39, -14, 9}, sres);
        Assert.assertArrayEquals(new byte[]{-118, -116, -10, 126, 98, 45, 28, 0}, kc);
    }
}
