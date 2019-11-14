import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class A38Test {
    Random random = new Random();

    @Test
    public void test() {
        byte[] key = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        byte[] rand = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

        A38.A3Res encode = A38.encode(key, rand);
        byte[] sres = encode.getSRES();
        byte[] kc = Utils.bitsToBytes(encode.getKc());
        Assert.assertArrayEquals(new byte[]{-107, -39, -14, 9}, sres);
        Assert.assertArrayEquals(new byte[]{-118, -116, -10, 126, 98, 45, 28, 0}, kc);
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
}
