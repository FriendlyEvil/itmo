public class MARS {
    public static int[] code(int[] mas, int[] key) {
        int countOfAddElements = mas.length % 4 == 0 ? 0 : 4 - mas.length % 4;
        int len = mas.length + countOfAddElements + 1;
        int subLen = mas.length / 4;
        int[] res = new int[len];

        int[] tempInput = new int[4];

        for (int i = 0; i < subLen; i++) {
            System.arraycopy(mas, 4 * i, tempInput, 0, 4);
            partCode(tempInput, key, res, i);
        }

        if (countOfAddElements > 0) {
            System.arraycopy(mas, 4 * subLen, tempInput, 0, countOfAddElements);
            partCode(tempInput, key, res, subLen);
        }

        res[len - 1] = countOfAddElements;
        return res;
    }

    private static void partCode(int[] input, int[] key, int[] output, int position) {
        int[] code = MARSEncrypter.code(input, key);
        System.arraycopy(code, 0, output, 4 * position, 4);
    }

    public static int[] decode(int[] mas, int[] key) {
        int len = mas.length - 1;
        int[] res = new int[len - mas[len]];
        int[] input = new int[4];

        for (int i = 0; i < len / 4 - 1; i++) {
            System.arraycopy(mas, 4 * i, input, 0, 4);
            partDecode(input, key, res, i);
        }

        System.arraycopy(mas, len - 4, input, 0, 4);
        partDecode(input, key, input, 0);
        System.arraycopy(input, 0, res, len -4, 4 - mas[len]);
        return res;

    }

    private static void partDecode(int[] input, int[] key, int[] output, int position) {
        int[] code = MARSEncrypter.decode(input, key);
        System.arraycopy(code, 0, output, 4 * position, 4);
    }
}
