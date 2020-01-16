import java.io.InputStream;

public class Parser {
    LexicalAnalyzer lex;

    Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return K();
    }

    Tree M() throws ParseException {
        switch (lex.curToken()) {
            case MINUS:
                lex.nextToken();
                return new Tree("M", new Tree("-"), M());
            case NUMBER:
                lex.nextToken();
                return new Tree("M", new Tree("n"));
            case LEFT_BRACKET:
                lex.nextToken();
                Tree res = E();
                if (lex.curToken() != Token.RIGHT_BRACKET) {
                    throw new ParseException("", lex.curPos());
                }
                lex.nextToken();
                return new Tree("M", new Tree("("), res, new Tree(")"));
            default:
                throw new ParseException("Expected: - n (", lex.curPos());
        }
    }

    Tree T1() throws ParseException {
        String temp;
        switch (lex.curToken()) {
            case MULTIPLY:
                temp = "*";
                break;
            case MINUS:
            case PLUS:
            case RIGHT_BRACKET:
            case END:
            case AND:
                return new Tree("T1");
            default:
                throw new ParseException("Expected: * - + n ) &", lex.curPos());
        }
        lex.nextToken();
        return new Tree("T1", new Tree(temp), M(), T1());
    }

    Tree T() throws ParseException {
        switch (lex.curToken()) {
            case MINUS:
            case NUMBER:
            case LEFT_BRACKET:
                return new Tree("T", M(), T1());
            default:
                throw new ParseException("Expected: - n (", lex.curPos());
        }
    }

    Tree E1() throws ParseException {
        String temp;
        switch (lex.curToken()) {
            case PLUS:
                temp = "+";
                break;
            case MINUS:
                temp = "-";
                break;

            case RIGHT_BRACKET:
            case END:
            case AND:
                return new Tree("E1");
            default:
                throw new ParseException("Expected: - + n ) &", lex.curPos());
        }
        lex.nextToken();
        return new Tree("E1", new Tree(temp), T(), E1());
    }

    Tree E() throws ParseException {
        switch (lex.curToken()) {
            case MINUS:
            case NUMBER:
            case LEFT_BRACKET:
                return new Tree("E", T(), E1());
            default:
                throw new ParseException("Expected: - n (", lex.curPos());
        }
    }

    Tree K1() throws ParseException {
        String temp;
        switch (lex.curToken()) {
            case AND:
                temp = "&";
                break;
            case RIGHT_BRACKET:
            case END:
                return new Tree("K1");
            default:
                throw new ParseException("Expected: ) &", lex.curPos());
        }
        lex.nextToken();
        return new Tree("K1", new Tree(temp), E(), K1());
    }

    Tree K() throws ParseException {
        switch (lex.curToken()) {
            case MINUS:
            case NUMBER:
            case LEFT_BRACKET:
                return new Tree("K", E(), K1());
            default:
                throw new ParseException("Expected: - n (", lex.curPos());
        }
    }
}
