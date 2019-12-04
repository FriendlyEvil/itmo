import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

public class RSATest {
    private static final SecureRandom random = new SecureRandom();

    private RSA server;
    private RSA.Pair openKey;

    @Before
    public void before() {
        server = new RSA();
        openKey = server.getOpenKey();
    }

    private void textTest(String message) {
        System.out.println("Test with message = \"" + message + '"');
        BigInteger mess = new BigInteger(message.getBytes());

        List<BigInteger> encodeMessage = RSA.encode(mess, openKey);
        BigInteger decodeMessage = server.decode(encodeMessage);

        Assert.assertEquals(new String(decodeMessage.toByteArray()), message);
    }

    private void integerTest(BigInteger message) {
        System.out.println("Test with integer = \"" + message + '"');
        List<BigInteger> encodeMessage = RSA.encode(message, openKey);
        BigInteger decodeMessage = server.decode(encodeMessage);

        Assert.assertEquals(decodeMessage, message);
    }

    @Test
    public void simpleTest() {
        textTest("simple test");
    }

    @Test
    public void helloTest() {
        textTest("Hello, world!");
    }

    @Test
    public void smallIntegerTest() {
        integerTest(new BigInteger("42"));
    }

    @Test
    public void primeIntegerTest() {
        integerTest(BigInteger.probablePrime(512, random));
    }

    @Test
    public void bigPrimeIntegerTest() {
        integerTest(BigInteger.probablePrime(2048, random));
    }

    @Test
    public void testRandomInteger() {
        for (int i = 0; i < 1000; i++) {
            integerTest(new BigInteger(512, random));
        }
    }

    @Test
    public void testSmallRandomInteger() {
        for (int i = 0; i < 1000; i++) {
            integerTest(new BigInteger(64, random));
        }
    }

    @Test
    public void testMiddleRandomInteger() {
        for (int i = 0; i < 1000; i++) {
            integerTest(new BigInteger(1024, random));
        }
    }
}