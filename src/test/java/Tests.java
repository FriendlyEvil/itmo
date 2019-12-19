import org.junit.Assert;
import org.junit.Test;

public class Tests {
    @Test
    public void checkInit() {
        CubeHash cubeHash = new CubeHash(8, 1, 512);
        int[] hash = cubeHash.getHashDebug();
        Assert.assertEquals("6998f35dfb0930c760948910e626160f36077cf3b58b0d0c57cf193d3341e7b8" +
                        "a334805b2089f9ef31ffc4142aef3850fe121839e940a4527d5293a27045ca12" +
                        "9358096e81bf70349a90a44a93c33edb14c3e9844a87dbd0bc451df25212b3ac" +
                        "6aabe51c5df0f63bddbb8ae8fad3cf0fd52582fbad2e2446094025a521a23d5c",
                Utils.intToHex(hash));

    }

    @Test
    public void helloTest() {
        CubeHash cubeHash = new CubeHash(16, 32, 512);
        cubeHash.updateHash("Hello".getBytes());
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("9a9c33ce64676e2910053b643d29bef57da7dfe2295ce3baaf6bddfcc41f1fccb2ef3dd37f13240ac8ef366354f026956dffc4df0109753d45ee515485fe193b",
                Utils.intToHex(hash));
    }

    @Test
    public void helloTest1() {
        CubeHash cubeHash = new CubeHash(8, 8, 512);
        cubeHash.updateHash("hello".getBytes());
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("ded3ed55e33be19b29b4c5e626a0da7aa6e5f721d31975e04c34b85888cf2c1b50002c8d34e08b44f86170c9f83b0c28b31b22762ae705cca3c37b8cabedcbe6",
                Utils.intToHex(hash));
    }

    @Test
    public void fox() {
        CubeHash cubeHash = new CubeHash(8, 8, 512);
        cubeHash.updateHash("The quick brown fox jumps over the lazy dog".getBytes());
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("a86a3f7e733e01ee444f214028867cfbde7d1e17156ec5915137f8339a92a69306a2723042603c297db41b3dbd985d970f50ebc7d9dc7f23f56772c2b2f44a04",
                Utils.intToHex(hash));
    }
}
