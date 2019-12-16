import java.io.InputStream;

public class Parser {
    LexicalAnalyzer lex;

    Tree parse(InputStream is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return Expr();
    }

    Tree Mul() throws ParseException {
        switch (lex.curToken()) {
            case MINUS:
            case PLUS:
                lex.nextToken();
                return new Tree("M", Mul());
            case NUMBER:
                lex.nextToken();
                return new Tree("M");
            case LEFT_BRACKET:
                lex.nextToken();
                Tree res = Expr();
                if (lex.curToken() != Token.RIGHT_BRACKET) {
                    throw new ParseException("", lex.curPos());
                }
                return new Tree("M", res);
            default:
                throw new ParseException("message", lex.curPos());
        }
    }

    Tree T() throws ParseException {
        switch (lex.curToken()) {
            case MULTIPLY:
                lex.nextToken();
                return new Tree("T", Mul(), T());
            case MINUS:
            case PLUS:
            case RIGHT_BRACKET:
            case END:
                return new Tree("T");
            default:
                throw new ParseException("message", lex.curPos());
        }
    }

    Tree Term() throws ParseException {
        switch (lex.curToken()) {
            case PLUS:
            case MINUS:
            case NUMBER:
            case LEFT_BRACKET:
                lex.nextToken();
                return new Tree("R", Mul(), T());
            case RIGHT_BRACKET:
            case END:
                return new Tree("R");
            default:
                throw new ParseException("message", lex.curPos());
        }
    }

    Tree E() throws ParseException {
        switch (lex.curToken()) {
            case PLUS:
            case MINUS:
                return new Tree("E", Term(), E());
            case RIGHT_BRACKET:
            case END:
                return new Tree("E");
            default:
                throw new ParseException("message", lex.curPos());
        }
    }

    Tree Expr() throws ParseException {
        switch (lex.curToken()) {
            case PLUS:
            case MINUS:
            case NUMBER:
            case LEFT_BRACKET:
                return new Tree("X", Term(), E());
            case RIGHT_BRACKET:
            case END:
                return new Tree("X");
            default:
                throw new ParseException("message", lex.curPos());
        }
    }
}
