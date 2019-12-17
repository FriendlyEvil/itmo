import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CubeHash {
    public static void main(String[] args) {
        CubeHash cubeHash = new CubeHash(16, 32, 512, 32, new int[32]);
        int b = cubeHash.b;
    }

    private int r; //rounds count
    private int b; //block size in bytes
    private int h; //hash size in bits
    private int f; //final rounds count
    private int[] hash;

    public int[] hash(int[] input) {
        for (int i = 0; i < 16; i++) {
            int first = i;
            int second = i | 16;
            Utils.add(hash, first, second);
        }

        for (int i = 0; i < 16; i++) {
            Utils.leftCyclicShift(hash, i, 7);
        }

        for (int i = 0; i < 8; i++) {
            int first = i;
            int second = i | 8;
            Utils.swap(hash, first, second);
        }

        for (int i = 0; i < 16; i++) {
            int first = i | 16;
            int second = i;
            Utils.xor(hash, first, second);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                int first = (i << 2) | 16 | j;
                int second = (i << 2) | 16 | 2 | j;
                Utils.swap(hash, first, second);
            }
        }

        for (int i = 0; i < 16; i++) {
            int first = i;
            int second = i | 16;
            Utils.add(hash, first, second);
        }

        for (int i = 0; i < 16; i++) {
            Utils.leftCyclicShift(hash, i, 11);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                int first = (j << 3) | j;
                int second = (j << 3) | 8 | j;
                Utils.swap(hash, first, second);
            }
        }

        for (int i = 0; i < 16; i++) {
            int first = i | 16;
            int second = i;
            Utils.xor(hash, first, second);
        }

        for (int i = 0; i < 8; i++) {
            int first = (i << 1) | 16;
            int second = (i << 1) | 16 | 1;
            Utils.swap(hash, first, second);
        }

        return input;
    }

}
