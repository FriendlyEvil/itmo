import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class A5Test {
    Random random = new Random();

    @Test
    public void rotateTest() {
        int len = 20;
        int mask = 1 << (len - 1);
        int x = random.nextInt() & ((1 << len) - 1);
        int res = x;
        for (int i = 0; i < 3 * len; i++) {
            res = A5.rotate(res, len, mask);
        }
        Assert.assertEquals(res, x);
    }

    @Test
    public void test() {
        byte[] key = new byte[]{0x12, 0x23, 0x45, 0x67, -0x9, -0x2B, -0x4D, -0x6F};
        key = Utils.bytesToBits(key);
        byte[] frame = new byte[]{0x00, 0x01, 0x34};
        frame = Utils.bytesToBits(frame);
        byte[] ans1 = new byte[114];
        byte[] ans2 = new byte[114];
        A5.a5(key, frame, ans1, ans2);

        Assert.assertEquals(Utils.bytesToHex(Utils.bitsToBytes(ans1)), "9486875FA5D0A0DF3360E996B829");
        Assert.assertEquals(Utils.bytesToHex(Utils.bitsToBytes(ans2)), "CD29263116C81847EB39BEAF4AC7");

    }

    private byte[] randomBits(int len) {
        byte[] bytes = new byte[len];
        random.nextBytes(bytes);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] &= 1;
        }
        return bytes;
    }

    @Test
    public void staticTest() {
        byte[] key = randomBits(64);
        byte[] frame = randomBits(22);
        byte[] out1 = new byte[144];
        byte[] out2 = new byte[144];
        byte[] out3 = new byte[144];
        byte[] out4 = new byte[144];
        A5.a5(key, frame, out1, out2);
        A5.a5(key, frame, out3, out4);
        Assert.assertArrayEquals(out1, out3);
        Assert.assertArrayEquals(out2, out4);
    }
}
