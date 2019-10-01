public class DESEncryptor {
    byte[] encrypt(String text, long key) {
        byte[] tmp = text.getBytes();
        byte[] bytes = new byte[(tmp.length + 7) / 8 * 8 + 1];
        byte appendix = (byte) (bytes.length - tmp.length);
        bytes[0] = appendix;
        System.arraycopy(tmp, 0, bytes, 1, tmp.length);
        initialPermutation(bytes);





        return bytes;
    }

    void initialPermutation(byte[] bytes) {
        for (int i = 1; i < bytes.length; i += 8) {
            long res = 0;
            long value = getFromBytes(bytes, i);
            for (int j = 0; j < 64; j++) {
                res ^= (getBit(value, initialPermutation[i]) << i);
            }
        }
    }

    void cypherCycle(byte[] bytes, int ind) {
        int L = (int) getFromBytes(bytes, ind);
        int R = (int) (getFromBytes(bytes, ind) >>> 32);
        long key = 0;//TODO
        for (int i = 0; i < 16; i++) {
            int tmp = R
            R = L ^ feistelFunction(R, key);
            L = tmp;
        }

    }


    int feistelFunction(int R, long k) {

        return R; //TODO
    }






    long getFromBytes(byte[] bytes, int from) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value <<= 8;
            value += bytes[from + i];
        }
        return value;
    }

    long getBit(long value, int ind) {
        return 1l & (value >> (ind - 1));
    }


    private static int[] initialPermutation = {58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};
}
