import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CubeHash {
    public static void main(String[] args) {
        CubeHash cubeHash = new CubeHash(16, 32, 512, 32);
        int b = cubeHash.b;
    }

    private int r; //rounds count
    private int b; //block size in bytes
    private int h; //hash size in bits
    private int f; //final rounds count
    private int[] hash;

    public int[] hash(int[] input) {
        for (int i = 0; i < 15; i++) {
            int first = i;
            int second = i | 16;
            Utils.add(hash, first, second);
        }

        for (int i = 0; i < 15; i++) {
            int num = hash[i];
            Utils.leftCyclicShift(num, 7);
        }

        return input;
    }

}
