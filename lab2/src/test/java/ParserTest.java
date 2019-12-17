import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;


public class ParserTest {
    private Parser parser = new Parser();

    private void test(String expr) {
        String expected = expr.replaceAll("-2", " 2").replaceAll(" ", "");
        try {
            Tree tree = parser.parse(new ByteArrayInputStream(expr.getBytes()));
            Assert.assertEquals(expected.intern(), tree.toString().intern());
        } catch (ParseException e) {
            Assert.fail();
        }
    }

    @Test
    public void simpleTest() {
        test("2 + 2 * 2");
    }

    @Test
    public void simpleUnaryMinusTest() {
        test("-2");
    }

    @Test
    public void unaryMinusTest() {
        test("-(2 + 2 - 2 * 2)");
    }

    @Test
    public void bigExpressionTest() {
        test("(2 * 2 + 2 - 2 * 2 * 2 * 2 + 2) * 2 + 2 - -2 * (2 - 2 + 2 * 2) * -2");
    }

}
