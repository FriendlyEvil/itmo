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
        initState();
    }

    public void initState() {
        rounds(initRounds);
    }

    public int[] getHashDebug() {
        return hash;
    }

    public int[] getHash() {
        for (int i = 0; i < 32; i++) {
            int[] hhh = Arrays.copyOf(hash, hash.length);
            Utils.xor(hhh, 31, 1);
            rounds(f);
            System.out.println();
            System.out.println(Utils.intToHex(Arrays.copyOfRange(hhh, 0, h / 8 / 4)));
        }
        return Arrays.copyOfRange(hash, 0, h / 8 / 4);
    }

    public void updateHash(int[] input) {
//        input = Arrays.copyOf(input, 8);
//        input[1] = 1 << 31;
        for (int i = 0; i < b; i++) {
            hash[i] ^= input[i];
        }
        rounds(r);
    }

    public void updateHash(byte[] input) {
//        input = Arrays.copyOf(input, 8);
//        input[1] = 1 << 31;
        for (int i = 0; i < b * 8; i++) {
            hash[1] ^= input[i];
        }
        rounds(r);
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
