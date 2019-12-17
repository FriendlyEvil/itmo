import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class LexicalAnalyzer {
    private InputStream is;
    private int curChar;
    private int curPos;
    private Token curToken = Token.START;
    private static final Set<Token> operations = Set.of(Token.MINUS, Token.PLUS, Token.MULTIPLY, Token.START, Token.LEFT_BRACKET);

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c) {
        return Character.isWhitespace(c);
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        if (parseNumber()) {
            return;
        }
        switch (curChar) {
            case '(':
                nextChar();
                curToken = Token.LEFT_BRACKET;
                break;
            case ')':
                nextChar();
                curToken = Token.RIGHT_BRACKET;
                break;
            case '+':
                nextChar();
                curToken = Token.PLUS;
                break;
            case '-':
                nextChar();
                if (operations.contains(curToken)) {
                    if (parseNumber()) {
                        return;
                    }
                }
                curToken = Token.MINUS;
                break;
            case '*':
                nextChar();
                curToken = Token.MULTIPLY;
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                throw new ParseException("Illegal character " + (char) curChar, curPos);
        }
    }

    private boolean parseNumber() throws ParseException {
        if (Character.isDigit(curChar)) {
            do {
                nextChar();
                curToken = Token.NUMBER;
            } while (Character.isDigit(curChar));
            return true;
        }
        return false;
    }

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }
}

