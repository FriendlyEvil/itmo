import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CubeHash {
    public static void main(String[] args) {
        CubeHash cubeHash = new CubeHash(16, 32, 512, 32);
        int b = cubeHash.b;
    }

    int r; //rounds count
    int b; //block size in bytes
    int h; //hash size in bits
    int f; //final rounds count


}
