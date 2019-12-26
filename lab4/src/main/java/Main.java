import antlr.*;
import org.antlr.v4.runtime.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String str =
                "@header {a}" +
                "s returns[String a]: n {a} {a}; " +
                "n returns[String a]: v A e {a} | e {a} {a}; " +
                "e returns[String a]: v {a} {a}; " +

                "v returns[String a]: B {a} | C e {a} {a}; " +

                "A: '=';" +
                "B: 'x';" +
                "C: '*';";
        System.out.println(str);

        CharStream inputStream = CharStreams.fromString(str);
        GrammerLexer lexer = new GrammerLexer(inputStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        GrammerParser parser = new GrammerParser(tokenStream);
        ParserInput grammer = parser.grammer().val;
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();


        syntaxAnalyzer.createItemsSet(grammer.getRuless(), grammer.getStart());
        System.out.println("!");
    }



}