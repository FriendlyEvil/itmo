import lombok.SneakyThrows;
import lombok.Value;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    @SneakyThrows
    public static List<BigInteger> encode(BigInteger m, Pair openKey) {
        List<BigInteger> ans = new ArrayList<>();

        BigInteger n = openKey.getSecond();
        BigInteger pow = getPowForPartMessage(openKey);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        MontgomeryMultiplication multiplicator = new MontgomeryMultiplication(openKey.second);
        BigInteger ones = ONE.shiftLeft(pow.bitLength() - 1).subtract(ONE);
        while (m.compareTo(n) > 0) {
            BigInteger nextMessagePart = m.and(ones);
            ans.add(nextMessagePart);
            m = m.shiftRight(pow.bitLength() - 1);
        }
        for (int i = 0; i < ans.size(); i++) {
            int j = i;
            executorService.submit(() -> {
                ans.set(j, multiplicator.modPow(ans.get(j), openKey.first, openKey.second));
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(10000, TimeUnit.DAYS);
        ans.add(multiplicator.modPow(m, openKey.first, openKey.second));
        return ans;
    }

    @SneakyThrows
    public BigInteger decode(List<BigInteger> m) {
        BigInteger pow = getPowForPartMessage(openKey);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        BigInteger ans = BigInteger.ZERO;
        for (int i = m.size() - 1; i >= 0; i--) {
            int j = i;
            executorService.submit(() -> {
                m.set(j, multiplicator.modPow(m.get(j), privateKey.first, privateKey.second));
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(10000, TimeUnit.DAYS);
        for (int i = m.size() - 1; i >= 0; i--) {
            ans = ans.shiftLeft(pow.bitLength() - 1).add(m.get(i));
        }

        return ans;
    }

    private static BigInteger getPowForPartMessage(Pair pair) {
        BigInteger n = pair.getSecond();
        int len = n.bitLength() - 5;
        return ONE.shiftLeft(len);
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
        return BigInteger.probablePrime(BITS, random);
    }

    public Pair getOpenKey() {
        return openKey;
    }

    @Value
    public static class Pair {
        BigInteger first;
        BigInteger second;
    }
}