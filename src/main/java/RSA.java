import lombok.Value;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;


public class RSA {
    private static final SecureRandom random = new SecureRandom();
    private static final int BITS = 512;

    private static final String TAB = "    ";

    private final Pair openKey; //open key (e, n)
    private final Pair privateKey; //closed key (d, n)
    private final MontgomeryMultiplication multiplicator;

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
        multiplicator = new MontgomeryMultiplication(n);

        System.out.println("Open key = {\n" + TAB + "e = " + openKey.first + "\n" + TAB + "n = " + openKey.getSecond() + "\n}");
        System.out.println("Private key = {\n" + TAB + "d = " + privateKey.first + "\n" + TAB + "n = " + privateKey.getSecond() + "\n}");
    }

    public static List<BigInteger> encode(BigInteger m, Pair openKey) {
        List<BigInteger> ans = new ArrayList<>();

        BigInteger n = openKey.getSecond();
        BigInteger pow = getPowForPartMessage(openKey);

        MontgomeryMultiplication multiplicator = new MontgomeryMultiplication(openKey.second);
        while (m.compareTo(n) > 0) {
            BigInteger nextMessagePart = m.mod(pow);
//            ans.add(nextMessagePart.modPow(openKey.first, openKey.second));
            ans.add(multiplicator.modPow(nextMessagePart, openKey.first, openKey.second));
            m = m.divide(pow);
        }
//        ans.add(m.modPow(openKey.first, openKey.second));
        ans.add(multiplicator.modPow(m, openKey.first, openKey.second));
        return ans;
    }

    public BigInteger decode(List<BigInteger> m) {
        BigInteger pow = getPowForPartMessage(openKey);

        BigInteger ans = BigInteger.ZERO;
        for (int i = m.size() - 1; i >= 0; i--) {
//            BigInteger tempPart = m.get(i).modPow(privateKey.first, privateKey.second);
            BigInteger tempPart = multiplicator.modPow(m.get(i), privateKey.first, privateKey.second);
            ans = ans.multiply(pow).add(tempPart);
        }

        return ans;
    }

    private static BigInteger getPowForPartMessage(Pair pair) {
        BigInteger n = pair.getSecond();
        int len = n.bitLength() - 5;
        return TWO.pow(len);
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