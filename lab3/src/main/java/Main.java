import antlr.PrefixExpressionLexer;
import antlr.PrefixExpressionParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ANTLRInputStream inputStream = new ANTLRInputStream(scanner.nextLine());
//        String str = "{= a 0 = b 0 if == a 0 print 5 print 3}";
//        String str = "{= a 0 = b 0 if == a 0 {print 5 print 3}{print + a b if == a b print + - a b * c d}}";
//        String str = "{= a 0 = b 0 if == a 0 print 3 if == b 0 print 3}";

//        ANTLRInputStream inputStream = new ANTLRInputStream(str);
        PrefixExpressionLexer lexer = new PrefixExpressionLexer(inputStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        PrefixExpressionParser parser = new PrefixExpressionParser(tokenStream);
        System.out.println(parser.expr().val);
    }



}