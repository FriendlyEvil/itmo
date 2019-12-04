import java.math.BigInteger;

import static java.math.BigInteger.ONE;

public class MontgomeryMultiplication {
    BigInteger r;
    BigInteger r_;
    BigInteger n_sh;

    MontgomeryMultiplication(BigInteger n) {
        r = BigInteger.valueOf(1).shiftLeft(n.bitLength() + 1);
        r_ = r.modInverse(n);
        n_sh = r.multiply(r_).subtract(ONE).divide(n);
    }

    private BigInteger monPro(BigInteger a, BigInteger b, BigInteger n) {
        BigInteger t = a.multiply(b);
        BigInteger u = (t.add(t.multiply(n_sh).and(ONE.shiftLeft(n.bitLength() + 1).subtract(ONE)).multiply(n))).shiftRight(n.bitLength() + 1);
        while (u.compareTo(n) >= 0) {
            u = u.mod(n);
        }
        return u;
    }


    public BigInteger modPow(BigInteger a, BigInteger e, BigInteger n) {
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
}
