import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigInteger;
import java.security.SecureRandom;

import static java.math.BigInteger.ONE;


public class RSA {
    private static final SecureRandom random = new SecureRandom();
    private static final int BITS = 512;

    private static final String TAB = "    ";

    private final Pair openKey; //open key (e, n)
    private final Pair privateKey; //closed key (d, n)

    public RSA() {
        BigInteger p = genRandomPrimeNum();
        BigInteger q = genRandomPrimeNum();
        BigInteger n = p.multiply(q);
        BigInteger phi_n = p.subtract(ONE).multiply(q.subtract(ONE));
        BigInteger e = chooseE(phi_n);
        BigInteger d = e.modInverse(phi_n);

        System.out.println("First prime number = " + p);
        System.out.println("Second prime number = " + q);

        openKey = new Pair(e, n);
        privateKey = new Pair(d, n);

        System.out.println("Open key = {\n" + TAB + "e = "+ openKey.first + "\n" + TAB + "n = " + openKey.getSecond() +  "\n}");
        System.out.println("Private key = {\n" + TAB + "d = "+ privateKey.first + "\n" + TAB + "n = " + privateKey.getSecond() +  "\n}");
    }

    public static BigInteger encode(BigInteger m, Pair openKey) {
        return m.modPow(openKey.first, openKey.second);
    }

    public BigInteger decode(BigInteger m) {
        return m.modPow(privateKey.first, privateKey.second);
    }

    private static BigInteger chooseE(BigInteger phi_n) {
        //returns x : 0.25 * phi_n <= x < 0.75 * phi_n
        BigInteger lowerBound = phi_n.divide(BigInteger.valueOf(4));
        BigInteger upperBound = phi_n.divide(BigInteger.TWO);
        int len = upperBound.bitLength();
        BigInteger res;
        while (true) {
            res = new BigInteger(len, random);
            if (res.compareTo(upperBound) >= 0) {
                continue;
            }
            res = res.add(lowerBound);
            if (phi_n.gcd(res).equals(ONE)) {
                break;
            }
        }
        return res;
    }

    private static BigInteger genRandomPrimeNum() {
        //TODO
        /** returns {@link BITS}-bits prime number*/
        return BigInteger.probablePrime(BITS, random);
    }

    public Pair getOpenKey() {
        return openKey;
    }

    @Value
    public class Pair {
        BigInteger first;
        BigInteger second;
    }
}