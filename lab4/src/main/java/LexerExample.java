import antlr.Terminal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LexerExample {
    private InputStream inputStream;
    private int curChar = 0;
    private int curPos = 0;
    private Terminal curToken;
    private List<Terminal> states = List.of(new Terminal("a", "b"));
    private List<Pattern> patterns = new ArrayList<>();

    public LexerExample(InputStream inputStream) {
        this.inputStream = inputStream;
        for (Terminal state : states) {
            patterns.add(Pattern.compile(state.getRegexpr()));
        }
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = inputStream.read();
        } catch (IOException e) {
            throw new ParseException((char) curChar, curPos);
        }
        while (curChar != -1 && Character.isWhitespace(curChar)) {
            curPos++;
            try {
                curChar = inputStream.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean match(StringBuilder text) {
        for (int i = 0; i < patterns.size(); i++) {
            if (patterns.get(i).matcher(text).find()) {
                curToken = states.get(i);
                return true;
            }
        }
        return false;
    }

    public void nextToken() throws ParseException {
        StringBuilder text = new StringBuilder().append(curChar);
        if (curChar == -1) {
            curToken = new Terminal("!EOF", "${'$'}");
            return;
        }
        while (curChar != -1 && match(text)) {
            nextChar();
            text.append(curChar);
        }
    }
}
