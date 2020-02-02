package generated.logic;


import doman.generated.logic.Logic;
import doman.generated.logic.Visualizer;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import utils.ParseException;

import java.util.Collections;

public class ParserLogicTest {
    @SneakyThrows
    private void test(String parse, String expected) {
        Assert.assertEquals(new ParserLogic(parse).parse().toStringRecursive(), expected);
    }

    @Test
    public void someTests() {
        test("a and b", "(a and b)");
        test("a or b", "(a or b)");
        test("a xor b", "(a xor b)");
        test("not a", "(not a)");
        test("a and a or t or f", "(((a and a) or t) or f)");
        test("not a and not b", "((not a) and (not b))");
        test("a and b or c xor (d and g or r or not t)", "((a and b) or (c xor (((d and g) or r) or (not t))))");
    }

    public static void main(String[] args) throws ParseException {
        String expression = "a and b or c xor not (d and g or r or not t)";
//        String expression = "a and b";
        Logic logic = new ParserLogic(expression).parse();
        Visualizer.show(Collections.singletonList(logic));
    }

}
