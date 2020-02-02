import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class DESTest {
    private Random random = new Random();

    private long generateRandomKey() {
        long key = random.nextLong();
        return key & ((1L << 56) - 1);
    }

    private byte[] getFile(String name) throws IOException {
        return Files.readAllBytes(Paths.get(name));
    }

    private void testFile(String filename) {
        try {
            long key = generateRandomKey();
            byte[] text = getFile(filename);
            byte[] encodedText = DES.encryptBytes(text, key);
            byte[] decodedText = DES.decryptBytes(encodedText, key);
            Assert.assertArrayEquals(text, decodedText);
        } catch (IOException e) {
            throw new AssertionError("File \"" + filename + "\" not found");
        }
    }

    @Test
    public void testDESAlgorithmOnRandomData() {
        long key = generateRandomKey();
        for (long i = 0; i < 100_000; i++) {
            long message = random.nextLong();
            Assert.assertEquals(DESAlgorithm.decrypt(DESAlgorithm.encrypt(message, key), key), message);
        }
    }

    @Test
    public void smallEnglishText() {
        testFile("text/SmallEnglishText.txt");
    }

    @Test
    public void middleEnglishText() {
        testFile("text/MiddleEnglishText.txt");
    }

    @Test
    public void bigEnglishText() {
        testFile("text/BigEnglishText.txt");
    }

    @Test
    public void smallRussianText() {
        testFile("text/SmallRussianText.txt");
    }

    @Test
    public void middleRussianText() {
        testFile("text/MiddleRussianText.txt");
    }

    @Test
    public void bigRussianText() {
        testFile("text/BigRussianText.txt");
    }

    @Test
    public void chineseText() {
        testFile("text/ChineseText.txt");
    }

    @Test
    public void testPdfFormat() {
        testFile("text/crypto_1_lecture.pdf");
    }

    @Test
    public void testJavaCode() {
        testFile("src/DESAlgorithm.java");
    }
}
