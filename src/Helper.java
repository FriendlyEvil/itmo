import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static int leftCyclicShift(int number, int shift) {
        return (number << shift) | (number >>> (32 - shift));
    }

    public static int rightCyclicShift(int number, int shift) {
        return (number >>> shift) | (number << (32 - shift));
    }

    public static int getByte(int number, int byteNumber) {
        return (number >> (8 * byteNumber)) & ((1 << 8) - 1);
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
        List<Integer> bit_reset = new ArrayList<>();
        int last_bit = (w >> 31) & 1;
        int num_last_bit = 1;
        for (int pos = 30; pos >= 0; --pos) {
            int c = (w >> pos) & 1;
            if (c != last_bit && num_last_bit >= 10) {
                bit_reset.add(pos + num_last_bit);
                bit_reset.add(pos + 1);
            }
            if (c != last_bit) {
                last_bit = c;
                num_last_bit = 1;
            } else {
                ++num_last_bit;
            }
        }
        int mask = 0;
        for (int i = 0; i < bit_reset.size(); i += 2) {
            int r = bit_reset.get(i);
            int l = bit_reset.get(i + 1);
            for (int pos = l + 1; pos < r; ++pos) {
                mask |= 1 << pos;
            }
        }
        return mask;
    }
}
