import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tests {
    private CubeHash cubeHash;
    private static final int b = 32;
    @Before
    public void before() {
        cubeHash = new CubeHash(8, b, 512);
    }

    @Test
    public void emptyTest() {
        cubeHash.updateHash(Utils.byteToInt("".getBytes()));
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("4a1d00bbcfcb5a9562fb981e7f7db3350fe2658639d948b9d57452c22328bb32f468b072208450bad5ee178271408be0b16e5633ac8a1e3cf9864cfbfc8e043a",
                Utils.intToHex(hash));
    }

    @Test
    public void helloTest() {
        cubeHash.updateHash(Utils.byteToInt("Hello".getBytes()));
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("7ce309a25e2e1603ca0fc369267b4d43f0b1b744ac45d6213ca08e75675664448e2f62fdbf7bbd637ce40fc293286d75b9d09e8dda31bd029113e02ecccfd39b",
                Utils.intToHex(hash));
    }

    @Test
    public void helloTest1() {
        cubeHash.updateHash(Utils.byteToInt("hello".getBytes()));
        int[] hash = cubeHash.getHash();
        Assert.assertEquals("01ee7f4eb0e0ebfdb8bf77460f64993faf13afce01b55b0d3d2a63690d25010f7127109455a7c143ef12254183e762b15575e0fcc49c79a0471a970ba8a66638",
                Utils.intToHex(hash));
    }

    public static void main(String[] args) {
//        byte[] bytes = {127, -128};
//        int[] ints = Utils.byteToInt(bytes);
//        int[] a = {2139095040};
//        byte[] bytes = Utils.intToBytes(a);
//        System.out.println("!");
        Utils.apeendToLenght(new int[] {1, 2}, 4);
    }
}
