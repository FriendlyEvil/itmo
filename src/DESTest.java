import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

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

    @Test
    public void test() {
        long key = 0x0e2468d92324L;
        for (long i = 0; i < 1000; i++) {
            Assert.assertEquals(DESEncryptor.decrypt(DESEncryptor.encrypt(i, key), key), i);
        }
    }
}
