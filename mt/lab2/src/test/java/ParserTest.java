import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Random;


public class ParserTest {
    private Parser parser = new Parser();
    private Random random = new Random();

    private void test(String expr) {
        System.out.println(expr);
        String expected = expr.replaceAll("-2", "2").replaceAll(" ", "");
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

    @Test
    public void bigTest() {
        test("((((((((((2) + 2) - (2)) - ((2) * 2)) - (((2) - 2) + (2))) + ((((2) - 2) * (2)) - ((2) - 2))) - (((((2) - 2) - (2)) + ((2) - 2)) - (((2) + 2) * (2)))) * ((((((2) - 2) - (2)) - ((2) - 2)) - (((2) * 2) + (2))) - ((((2) * 2) * (2)) * ((2) + 2)))) * (((((((2) - 2) + (2)) * ((2) - 2)) - (((2) - 2) - (2))) - ((((2) - 2) * (2)) - ((2) - 2))) - (((((2) + 2) - (2)) - ((2) * 2)) * (((2) - 2) - (2))))) - ((((((((2) - 2) * (2)) + ((2) - 2)) - (((2) - 2) - (2))) * ((((2) - 2) - (2)) - ((2) - 2))) * (((((2) - 2) + (2)) + ((2) + 2)) - (((2) - 2) - (2)))) - ((((((2) - 2) + (2)) - ((2) - 2)) - (((2) + 2) + (2))) - ((((2) * 2) + (2)) + ((2) - 2))))) - (((((((((2) + 2) * (2)) * ((2) - 2)) + (((2) - 2) + (2))) - ((((2) - 2) + (2)) - ((2) * 2))) + (((((2) + 2) + (2)) - ((2) + 2)) - (((2) - 2) + (2)))) + ((((((2) * 2) - (2)) + ((2) - 2)) - (((2) + 2) + (2))) - ((((2) * 2) * (2)) - ((2) + 2)))) * (((((((2) - 2) * (2)) - ((2) + 2)) * (((2) - 2) * (2))) - ((((2) * 2) - (2)) - ((2) * 2))) * (((((2) - 2) - (2)) - ((2) - 2)) - (((2) + 2) * (2)))))");
    }

    @Test
    public void randomTest() {
        for (int i = 0; i < 50; i++) {
            String expression = generateExpression(30);
            test(expression);
        }
    }

    private String generateExpression(int len) {
        return generateExpr(len) + generateOp(len);
    }

    private String generateOp(int len) {
        int r = random.nextInt(4);
        if (len == 0) {
            return "";
        }
        String generate = generateExpr(len - 1);
        String op = " - ";
        switch (r) {
            case 0:
                op = " + ";
                break;
            case 1:
                op = " * ";
                break;
        }
        return op + generate;
    }

    private String generateExpr(int len) {
        if (len == 0) {
            return "2";
        }
        int r = random.nextInt(4);
        switch (r) {
            case 0:
                return "-2";
            case 1:
                return "2";
        }
        return "(" + generateExpression(len - 1) + ")";
    }
}
