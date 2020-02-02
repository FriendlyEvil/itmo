package generated.calculator;


import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import utils.ParseException;

public class ParserCalculatorTest {
    @SneakyThrows
    private void test(String test, Integer expected) {
        Assert.assertEquals(new ParserCalculator(test).parse(), expected);
    }

    @Test
    public void someTests() {
        test("2", 2);
        test("10000", 10000);
        test("1234567890", 1234567890);
        test("2 + 2", 4);
        test("2 + 2 * 2", 6);
        test("(2 + 2) * 2", 8);
        test("(2 + 2) * 2 * 30 + 1", 241);
        test("(2 + 2) * 2 * 30 - 1", 239);
        test("1 - 2 - 3", -4);
        test("2 * 2 / 2 * 2", 4);
        test("2 / 2 / 2 / 2", 0);
        test("2147483647 + 1", Integer.MIN_VALUE);
    }

    @Test
    public void test1() {
        test("(2 + 2)!", 24);
        test("3!!", 720);
        test("(2 + 2!)! * 2!", 48);
    }

    public static void main(String[] args) throws ParseException {
        String input = "2 + 2 - 2";
        System.out.println(new ParserCalculator(input).parse());
    }
}
