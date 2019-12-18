import org.junit.Assert;
import org.junit.Test;

public class Tests {
    private CubeHash cubeHash;
    private static int b;

    public static void main(String[] args) {
        CubeHash cubeHash;
        for (int i = 1; i != 0; i <<= 1) {
            b = 1;
            cubeHash = new CubeHash(1, b, 256);
            cubeHash.updateHash(new int[]{i});
            int[] hash = cubeHash.getHash();
            System.out.println(Utils.intToHex(hash));
        }
    }

    @Test
    public void checkInit() {
        b = 1;
        cubeHash = new CubeHash(8, b, 512);
        int[] hash = cubeHash.getHashDebug();
        Assert.assertEquals("6998f35dfb0930c760948910e626160f36077cf3b58b0d0c57cf193d3341e7b8" +
                        "a334805b2089f9ef31ffc4142aef3850fe121839e940a4527d5293a27045ca12" +
                        "9358096e81bf70349a90a44a93c33edb14c3e9844a87dbd0bc451df25212b3ac" +
                        "6aabe51c5df0f63bddbb8ae8fad3cf0fd52582fbad2e2446094025a521a23d5c",
                Utils.intToHex(hash));

    }

    @Test
    public void emptyTest() {
        b = 1;
        cubeHash = new CubeHash(8, b, 512);
//        cubeHash.updateHash(Utils.appendToLength("".getBytes(), b));
        cubeHash.updateHash(new byte[]{-128, 0, 0, 0, 0, 0, 0, 0});
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("38d1e8a22d7baac6fd5262d83de89cacf784a02caa866335299987722aeabc59",
                Utils.intToHex(hash));
    }

    @Test
    public void helloTest() {
        b = 1;
        cubeHash = new CubeHash(8, b, 512);
        cubeHash.updateHash(Utils.byteToInt("Hello".getBytes()));
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("7ce309a25e2e1603ca0fc369267b4d43f0b1b744ac45d6213ca08e75675664448e2f62fdbf7bbd637ce40fc293286d75b9d09e8dda31bd029113e02ecccfd39b",
                Utils.intToHex(hash));
    }

    @Test
    public void helloTest1() {
        b = 1;
        cubeHash = new CubeHash(8, b, 512);
        cubeHash.updateHash(Utils.byteToInt("hello".getBytes()));
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("01ee7f4eb0e0ebfdb8bf77460f64993faf13afce01b55b0d3d2a63690d25010f7127109455a7c143ef12254183e762b15575e0fcc49c79a0471a970ba8a66638",
                Utils.intToHex(hash));
    }
}
