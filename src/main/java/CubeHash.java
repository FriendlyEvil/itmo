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

    public int[] hash(int[] input) {
        return input;
    }

}
