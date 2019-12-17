public final class Utils {
    public static int leftCyclicShift(int number, int shift) {
        return (number << shift) | (number >>> (32 - shift));
    }

    public static int rightCyclicShift(int number, int shift) {
        return (number >>> shift) | (number << (32 - shift));
    }

    public static void swap(int[] arr, int first, int second) {
        int temp = arr[first];
        arr[first] = arr[second];
        arr[second] = temp;
    }

    public static void add(int[] arr, int first, int second) {
//        arr[first] += arr[second]; //module 2^32
        arr[second] += arr[first]; //module 2^32
    }

    public static void xor(int[] arr, int first, int second) {
//        arr[first] ^= arr[second];
        arr[second] ^= arr[first];
    }
}
