import java.math.BigInteger;
import java.security.SecureRandom;

import static java.math.BigInteger.ONE;


public class RSA {
    private static final SecureRandom random = new SecureRandom();
    private static final int BITS = 512;

    public static void main(String[] args) {
        BigInteger p = genRandomPrimeNum();
        BigInteger q = genRandomPrimeNum();
        BigInteger n = p.multiply(q);
        BigInteger phi_n = p.subtract(ONE).multiply(q.subtract(ONE));
        BigInteger e = chooseE(phi_n);
        BigInteger d = e.modInverse(phi_n);

        //open key (e, n)
        //closed key (d, n)
    }


    public static BigInteger encode(BigInteger m) {
        //TODO
        return null;
    }


    public static BigInteger decode(BigInteger m) {
        //TODO
        return null;
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
}