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
        for (int i = 0; i < array.length; i++) {
            ans[i] = array[newPosition[i]];
        }
        return ans;
    }
}
