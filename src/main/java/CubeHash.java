import lombok.Data;

import java.util.Arrays;

@Data
public class CubeHash {
    private int initRounds; //initial rounds
    private int r; //rounds per message block
    private int b; //bytes per message block
    private int h; //hash size in bits
    private int f; //finalization rounds
    private int[] hash;

    public CubeHash(int r, int b, int h) {
        this.r = r;
        this.b = b;
        this.h = h;
        f = r * 10;
        initRounds = f;
        hash = new int[32];
        hash[0] = h / 8;
        hash[1] = b;
        hash[2] = r;
        rounds(initRounds);
    }

    public CubeHash(int initRounds, int r, int b, int h, int f) {
        this.initRounds = initRounds;
        this.r = r;
        this.b = b;
        this.h = h;
        this.f = f;
        hash = new int[32];
        hash[0] = h / 8;
        hash[1] = b;
        hash[2] = r;
        rounds(initRounds);
    }

    public int[] getHashDebug() {
        return hash;
    }

    public int[] getHash() {
        Utils.xor(hash, 31, 1);
        rounds(f);
        return Arrays.copyOfRange(hash, 0, h / 8 / 4);
    }

    public void updateHash(byte[] input) {
        int ext_len = b * (input.length / b + 1);
        input = Arrays.copyOf(input, ext_len);
        input[input.length - 1] = (byte) 1;
        for (int k = 0; k < input.length; k += b) {
            for (int i = 0; i < b; i++) {
                hash[i / 4] ^= input[k + i] << (i % 4 * 8);
            }
            rounds(r);
        }
    }

    private void rounds(int it) {
        for (int k = 0; k < it; k++) {
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
                Utils.xorItems(hash, first, second);
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
                    int first = (j << 3) | i;
                    int second = (j << 3) | 4 | i;
                    Utils.swap(hash, first, second);
                }
            }

            for (int i = 0; i < 16; i++) {
                int first = i | 16;
                int second = i;
                Utils.xorItems(hash, first, second);
            }

            for (int i = 0; i < 8; i++) {
                int first = (i << 1) | 16;
                int second = (i << 1) | 16 | 1;
                Utils.swap(hash, first, second);
            }
        }
    }

}
