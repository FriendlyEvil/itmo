import lombok.Value;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class MontgomeryMultiplication {
    static Tuple gcd(BigInteger a, BigInteger b, BigInteger[] x, BigInteger[] y) {
        if (a.equals(ZERO)) {
            x[0] = ZERO;
            y[0] = ONE;
            return new Tuple(x, y, b);
        }
        BigInteger[] x1 = new BigInteger[1], y1 = new BigInteger[1];
        BigInteger d = gcd(b.mod(a), a, x1, y1).gcd;
        x[0] = y1[0].subtract((b.divide(a)).multiply(x1[0]));
        y[0] = x1[0];
        return new Tuple(x, y, d);
    }

    @Value
    public static class Tuple {
        BigInteger[] x;
        BigInteger[] y;
        BigInteger gcd;
    }

    static BigInteger monPro(BigInteger a, BigInteger b, BigInteger n) {
//        BigInteger[] r_inv = new BigInteger[1];
//        BigInteger[] n_sh = new BigInteger[1];
//        BigIntEuclidean calculate = BigIntEuclidean.calculate(r, n);

//        Tuple gcd = gcd(r, n, r_inv, n_sh);
//        r_inv[0] = calculate.x;
//        n_sh[0] = calculate.y;
//        n_sh[0] = n_sh[0].multiply(BigInteger.valueOf(-1));
        BigInteger t = a.multiply(b);
        BigInteger u = (t.add(t.multiply(n_sh).and(ONE.shiftLeft(n.bitLength() + 1).subtract(ONE)).multiply(n))).shiftRight(n.bitLength() + 1);
        while (u.compareTo(n) >= 0) {
            u = u.mod(n);
        }
        return u;
//        return a.multiply(b).multiply(r_).mod(n);
    }

    static BigInteger r_ = null;
    static BigInteger n_sh = null;

    static BigInteger modPow(BigInteger a, BigInteger e, BigInteger n) {
        if (r_ == null) {
            r_ = BigInteger.valueOf(1).shiftLeft(n.bitLength() + 1).modInverse(n);
        }
        BigInteger r = BigInteger.valueOf(1).shiftLeft(n.bitLength() + 1);
        if (n_sh == null) {
            n_sh = r.multiply(r_).subtract(ONE).divide(n);
        }
        a = a.multiply(r).mod(n);
        BigInteger x = r.mod(n);
        for (int i = e.bitLength() - 1; i >= 0; i--) {
            x = monPro(x, x, n);
            if (e.testBit(i)) {
                x = monPro(a, x, n);
            }
        }
        x = monPro(x, ONE, n);
        return x;
    }

    public static void main(String[] args) {
        BigInteger[] x = new BigInteger[1];
        BigInteger[] y = new BigInteger[1];
        Tuple gcd = gcd(BigInteger.valueOf(3), BigInteger.valueOf(5), x, y);
    }

    public static class BigIntEuclidean
    {
        public BigInteger x, y, gcd;

        private BigIntEuclidean()
        {
        }

        public static BigIntEuclidean calculate(BigInteger a, BigInteger b)
        {
            BigInteger x = BigInteger.ZERO;
            BigInteger lastx = BigInteger.ONE;
            BigInteger y = BigInteger.ONE;
            BigInteger lasty = BigInteger.ZERO;
            while (!b.equals(BigInteger.ZERO))
            {
                BigInteger[] quotientAndRemainder = a.divideAndRemainder(b);
                BigInteger quotient = quotientAndRemainder[0];

                BigInteger temp = a;
                a = b;
                b = quotientAndRemainder[1];

                temp = x;
                x = lastx.subtract(quotient.multiply(x));
                lastx = temp;

                temp = y;
                y = lasty.subtract(quotient.multiply(y));
                lasty = temp;
            }

            BigIntEuclidean result = new BigIntEuclidean();
            result.x = lastx;
            result.y = lasty;
            result.gcd = a;
            return result;
        }
    }
}
