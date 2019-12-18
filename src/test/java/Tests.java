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
        CubeHash cubeHash = new CubeHash(8, 8, 512);
        cubeHash.updateHash("Hello".getBytes());
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("e092f3c74ec866f2314bc04b8220a419ec0bf26f8b615ba2c901d7c91c45843d6748a341a7c1f49c8c1da285467f6448107f1f91902ecf540f354cad80a37621",
                Utils.intToHex(hash));
    }

    @Test
    public void helloTest1() {
        CubeHash cubeHash = new CubeHash(8, 8, 512);
        cubeHash.updateHash("hello".getBytes());
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("b5b8a3b1c5a52fc9e4db2bc5e9d84f4aeff9444027a785d4134215b68e6c0066cd8e95e6c6d02c07ef25ba2791011932e2d7a8404e557421b0b991b21077b9e6",
                Utils.intToHex(hash));
    }

    @Test
    public void fox() {
        CubeHash cubeHash = new CubeHash(8, 8, 512);
        cubeHash.updateHash("The quick brown fox jumps over the lazy dog".getBytes());
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("d77233bc808f2cfe54d56fc95ee051a496c8752f9642e80286ddb5ff6615cbdde2ab1a4fd0a1ffabcea0922415f458a5f5ef02a540f04acddbc9794d301813a4",
                Utils.intToHex(hash));
    }
}
