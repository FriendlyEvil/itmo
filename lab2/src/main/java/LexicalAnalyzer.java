import java.io.IOException;
import java.io.InputStream;

public class LexicalAnalyzer {
    private InputStream is;
    private int curChar;
    private int curPos;
    private Token curToken;

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
        if (Character.isDigit(curChar)) {
            do {
                nextChar();
                curToken = Token.NUMBER;
            } while (Character.isDigit(curChar));
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

    public Token curToken() {
        return curToken;
    }

    public int curPos() {
        return curPos;
    }
}

