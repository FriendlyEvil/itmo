import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Tests {
    Random random = new Random();
    private static final int SIZE = 100;

    public static String printInt(int a) {
        String s = Integer.toBinaryString(a);
        String white = "00000000000000000000000000000000"; //32 of '0'
        return (white.substring(0, 32 - s.length()) + s);
    }

    private static void printArray(int[] ar) {
        for (int i = 0; i < ar.length; i++) {
            System.out.print(printInt(ar[i]));
        }
        System.out.println();
    }

    private void test(int[] mas, int[] key) {
        int[] code = MARSEncrypter.code(mas, key);
        int[] decode = MARSEncrypter.decode(code, key);
        Assert.assertArrayEquals(decode, mas);
    }

    private int[] generateArray() {
        return new int[]{random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt()};
    }

    private int[] generateKey() {
        return new int[]{random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt()};
    }

    private byte[] getFile(String name) throws IOException {
        return Files.readAllBytes(Paths.get(name));
    }

    private static byte getByte(byte[] ar, int ind) {
        return ind >= ar.length ? 0 : ar[ind];
    }

    public static int[] byteToInt(byte[] bytes, boolean addCountOfNewBytes) {
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
        int n = longs.length * 2;
        int[] ints = new int[n];
        for (int i = 0; i < longs.length; i++) {
            ints[2 * i] = (int) (longs[i] >>> 32);
            ints[2 * i] = (int) (longs[i] & ((1L << 32) - 1));
        }
        return ints;
    }

    private void testFile(String filename) {
        try {
            int[] key = generateKey();
            int[] text = byteToInt(getFile(filename), false);
            System.out.println(filename + " " + text.length);
            test(text, key);
        } catch (IOException e) {
            throw new AssertionError("File \"" + filename + "\" not found");
        }
    }

    @Test
    public void firstTest() {
        int[] mas = new int[]{1, 1, 1, 1};
        int[] key = new int[]{1, 2, 3, 4};
        test(mas, key);
    }

    @Test
    public void randomArray() {
        int[] mas = generateArray();
        int[] key = new int[]{1, 2, 3, 4};
        test(mas, key);
    }

    @Test
    public void someRandomArray() {
        int[] key = new int[]{1, 2, 3, 4};
        for (int i = 0; i < SIZE; i++) {
            int[] mas = generateArray();
            test(mas, key);
        }
    }

    @Test
    public void randomKey() {
        int[] mas = new int[]{1, 2, 3, 4};
        int[] key = generateKey();
        test(mas, key);
    }

    @Test
    public void someRandomKey() {
        int[] mas = new int[]{1, 2, 3, 4};
        for (int i = 0; i < SIZE; i++) {
            int[] key = generateKey();
            test(mas, key);
        }
    }

    @Test
    public void randomArrayAndKey() {
        for (int i = 0; i < 100000; i++) {
            int[] mas = generateArray();
            int[] key = generateKey();
            test(mas, key);
        }
    }

    @Test
    public void someRandomArrayAndKey() {
        for (int i = 0; i < SIZE; i++) {
            int[] mas = generateArray();
            int[] key = generateKey();
            test(mas, key);
        }
    }

    @Test
    public void smallEnglishText() {
        testFile("test/SmallEnglishText.txt");
    }

    @Test
    public void middleEnglishText() {
        testFile("test/MiddleEnglishText.txt");
    }

//    @Test
//    public void bigEnglishText() {
//        testFile("test/BigEnglishText.txt");
//    }

    @Test
    public void smallRussianText() {
        testFile("test/SmallRussianText.txt");
    }

    @Test
    public void middleRussianText() {
        testFile("test/MiddleRussianText.txt");
    }

//    @Test
//    public void bigRussianText() {
//        testFile("test/BigRussianText.txt");
//    }

    @Test
    public void chineseText() {
        testFile("test/ChineseText.txt");
    }

    @Test
    public void testPdfFormat() {
        testFile("test/crypto_1_lecture.pdf");
    }
}
