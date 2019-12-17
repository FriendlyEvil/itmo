import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tests {
    private CubeHash cubeHash;

    @Before
    public void before() {
        cubeHash = new CubeHash();
    }

    @Test
    public void helloTest() {
        int[] hash = cubeHash.hash(Utils.byteToInt("Hello".getBytes()));
        Assert.assertEquals("7ce309a25e2e1603ca0fc369267b4d43f0b1b744ac45d6213ca08e75675664448e2f62fdbf7bbd637ce40fc293286d75b9d09e8dda31bd029113e02ecccfd39b",
                Utils.intToHex(hash));
    }

    @Test
    public void helloTest1() {
        int[] hash = cubeHash.hash(Utils.byteToInt("hello".getBytes()));
        Assert.assertEquals("01ee7f4eb0e0ebfdb8bf77460f64993faf13afce01b55b0d3d2a63690d25010f7127109455a7c143ef12254183e762b15575e0fcc49c79a0471a970ba8a66638",
                Utils.intToHex(hash));
    }
}
