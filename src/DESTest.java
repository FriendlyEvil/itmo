import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class DESTest {
    Random random = new Random();

    @Test
    public void testPermutation() {
        long[] per = {64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        for (int j = 0; j < 100; j++) {

            long l = random.nextLong();
            long r = DESEncryptor.permutation(l, per);
            for (int i = 0; i < 64; i++) {
                Assert.assertEquals(DESEncryptor.getBit(l, i + 1), DESEncryptor.getBit(r, 64 - i));
            }
        }
    }

    @Test
    public void keyExpansion() {
        for (long k = 0; k < 10000; k++) {
            long bigKey = 0;
            for (int i = 0; i < 8; i++) {
                long s = ((k >>> (7 * i)) & 127);
//                s <<= 1;
                s |= (DESEncryptor.count1(s) << 7);
                bigKey |= (s << (8 * i));
            }
            System.out.println(Long.toBinaryString(k) + " " + Long.toBinaryString(bigKey));
        }
    }

    public long generateKey() {
        long key = random.nextLong();
        return key & ((1L << 56) - 1);
    }

    @Test
    public void test() {
        long key = generateKey();
        for (long i = 0; i < 1_000_000; i++) {
            long message = random.nextLong();
            Assert.assertEquals(DESEncryptor.decrypt(DESEncryptor.encrypt(message, key), key), message);
        }
    }

    public byte[] getFile(String name) throws IOException {
        return Files.readAllBytes(Paths.get(name));
    }


    @Test
    public void realText() {
        try {
            byte[] encoded = getFile("simple_test.txt");
            String str = new String(encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
