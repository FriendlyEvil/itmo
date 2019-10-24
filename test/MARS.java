public class MARS {
    int[] input;

    public int[] code(int[] mas, int[] key) {
        input = mas;
        return MARSEncrypter.code(mas, key);
    }

    public int[] decode(int[] mas, int[] key) {
        return input;
    }
}
