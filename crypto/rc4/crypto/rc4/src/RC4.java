public class RC4 {
    private static int getMod(int n) {
        return (((n % 256) + 256) % 256);
    }

    private static byte[] initializeSBlock(byte[] key) {
        byte[] s = new byte[256];
        for (int i = 0; i < 256; i++) {
            s[i] = (byte) i;
        }

        int j = 0;
        int keyLength = key.length;
        for (int i = 0; i < 256; i++) {
            j = getMod(j + s[i] + key[i % keyLength]);

            byte swap = s[i];
            s[i] = s[j];
            s[j] = swap;
        }
        return s;
    }

    public static byte[] encode(byte[] message, byte[] key) {
        byte[] s = initializeSBlock(key);
        byte[] ans = new byte[message.length];

        int x = 0;
        int y = 0;
        int swap;
        for (int i = 0; i < message.length; i++) {
            x = getMod(x + 1);
            y = getMod((y + s[x]));

            swap = x;
            x = y;
            y = swap;

            ans[i] = (byte) (message[i] ^ s[getMod(s[x] + s[y])]);
        }

        return ans;
    }

    public static byte[] decode(byte[] message, byte[] key) {
        return encode(message, key);
    }
}
