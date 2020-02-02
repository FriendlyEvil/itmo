import antlr.PrefixExpressionLexer;
import antlr.PrefixExpressionParser;
import org.antlr.v4.runtime.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        String str = scanner.nextLine();
//        String str = "{= a 0 = b 0 if == a 0 print 5 print 3}";
//        String str = "{= a 0 = b 0 if == a 0 {print 5 print 3}{print + a b if == a b print + - a b * c d}}";
//        String str = "{= a 0 = b 0 if == a 0 print 3 if == b 0 print 3}";
//        String str = "{= a 0 = b 0 if == a 0 print 3 if == b 0 print 3}";

//        String str = "while == a 0 print a";
        String str = "do == 0 0 { print 2 print 5 while == a 0 print 3 print 2}";

        CharStream inputStream = CharStreams.fromString(str);
        PrefixExpressionLexer lexer = new PrefixExpressionLexer(inputStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        PrefixExpressionParser parser = new PrefixExpressionParser(tokenStream);
        System.out.println(parser.expr().val);
    }



}