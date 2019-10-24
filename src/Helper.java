public class Helper {
    public static int leftCyclicShift(int number, int shift) {
        return (number << shift) | (number >>> (32 - shift));
    }

    public static int rightCyclicShift(int number, int shift) {
        return (number >>> shift) | (number << (32 - shift));
    }

    public static int getByte(int number, int byteNumber) {
        return (number << (8 * byteNumber)) & ((1 << 8) - 1);
    }

    public static int getSomeBits(int value, int count) {
        return (value) & ((1 << count) - 1);
    }

    public static int[] rotateArray(int[] array, int[] newPosition) {
        int[] ans = new int[array.length];
        for (int i = 0; i < newPosition.length; i++) {
            ans[i] = array[newPosition[i]];
        }
        return ans;
    }

    public static int mask(int w) {
        int M = 0;
        int ones = (1 << 10) - 1;
        int d = 1;
        while (d < 31 - 10) {
            int zerosOrOnes = (ones << d) & w;
            if (zerosOrOnes == 0 || zerosOrOnes == (ones << d)) {
                ++d;
                boolean dd = zerosOrOnes > 0;
                while (d < 32 && ((1 << d) > 0) == dd) {
                    M |= 1 << d;
                    ++d;
                }
                --d;
                M ^= 1 << d;
            }
            ++d;
        }
        return M;
    }
}
