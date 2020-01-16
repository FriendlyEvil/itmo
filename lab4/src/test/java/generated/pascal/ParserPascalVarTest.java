package generated.pascal;


import utils.ParseException;

public class ParserPascalVarTest {

    public static void main(String[] args) throws ParseException {
        String input = "var a, b: integer; c : string; d, t, gasf: integer;";
        System.out.println(new ParserPascalVar(input).parse());
    }
}
