import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class TestRC4 {
    private final Random random = new Random();

    //40 - 2048
    private byte[] generateRandomKey() {
        int lenKey = Math.abs(random.nextInt()) % 2008 + 40;
        byte[] key = new byte[lenKey];
        random.nextBytes(key);
        return key;
    }

    @Test
    public void firstTest() {
        byte[] b = new byte[]{1, 125, 7, 15};
        byte[] key = new byte[]{1, 125, 7, 15};
        byte[] ans = RC4.encode(b, key);
        ans = RC4.decode(ans, key);

        Assert.assertArrayEquals(ans, b);
    }
}
