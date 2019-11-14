import java.util.Arrays;

public class A5 {
    private static int first = (1 << 18) + (1 << 17) + (1 << 16) + (1 << 13);
    private static int second = (1 << 21) + (1 << 20);
    private static int third = (1 << 22) + (1 << 21) + (1 << 20) + (1 << 7);

    private static int firstLen = 19;
    private static int secondLen = 22;
    private static int thirdLen = 23;


    public static void main(String[] args) {
        byte[] key = new byte[]{0x12, 0x23, 0x45, 0x67, -0x9, -0x2B, -0x4D, -0x6F};
        key = A38.bytesToBits(key);
        byte[] frame = new byte[]{0x00, 0x01, 0x34};
        frame = A38.bytesToBits(frame);
        byte[] ans1 = new byte[114];
        byte[] ans2 = new byte[114];
        a5(key, frame, ans1, ans2);

        System.out.println(Arrays.toString(A38.bitsToBytes(ans1)));
        System.out.println(Arrays.toString(A38.bitsToBytes(ans2)));
    }

    public static void a5(byte[] key, byte[] frame, byte[] dec, byte[] enc) {
        int[] r = new int[]{0, 0, 0};

        for (int i = 0; i < 64; i++) {
            int keyBit = key[i];
            clockAll(r);
            r[0] ^= keyBit;
            r[1] ^= keyBit;
            r[2] ^= keyBit;
        }

        for (int i = 0; i < 22; i++) {
            int bt = frame[i];
            clockAll(r);
            r[1] ^= bt;
            r[0] ^= bt;
            r[2] ^= bt;
        }

        for (int i = 0; i < 100; i++) {
            clockFunction(r);
        }


        for (int i = 0; i < 114; i++) {
            clockFunction(r);
            dec[i] = getOutput(r);
        }
        for (int i = 0; i < 114; i++) {
            clockFunction(r);
            enc[i] = getOutput(r);
        }
    }

    private static byte getOutput(int[] r) {
        return (byte) ((r[0] >> (firstLen - 1)) ^ (r[1] >> (secondLen - 1)) ^ (r[2] >> (thirdLen - 1)));
    }

    private static int getFristBit(int r, int mask) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            if (((mask >> i) & 1) == 1) {
                res ^= (r >> i);
            }
        }
        return res & 1;
    }

    private static int rotate(int r, int len, int polynom) {
        return ((r << 1) & ((1 << len) - 1)) | getFristBit(r, polynom);
    }

    private static void clockFunction(int[] r) {
        int[] clBits = getClockingBits(r);
        int majority = (clBits[0] & clBits[1]) | (clBits[0] & clBits[2]) | (clBits[1] & clBits[2]);

        if (majority == clBits[0]) {
            r[0] = rotate(r[0], firstLen, first);
        }
        if (majority == clBits[1]) {
            r[1] = rotate(r[1], secondLen, second);
        }
        if (majority == clBits[2]) {
            r[2] = rotate(r[2], thirdLen, third);
        }
    }

    private static void clockAll(int[] r) {
        r[0] = rotate(r[0], firstLen, first);
        r[1] = rotate(r[1], secondLen, second);
        r[2] = rotate(r[2], thirdLen, third);
    }

    private static int[] getClockingBits(int[] r) {
        return new int[]{getBit(r[0], 8), getBit(r[1], 10), getBit(r[2], 10)};
    }

    private static int getBit(int value, int pos) {
        return (value >> pos) & 1;
    }
}
