package generated.calculator;

import antlr.Terminal;
import utils.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LexerCalculator {
    private String inputStream;
    private String curTokenName;
    private String curTokenValue;
    private List<Terminal> states = new ArrayList<>() {{
        add(new Terminal("ADD", "\\+"));
        add(new Terminal("SUB", "-"));
        add(new Terminal("MUL", "\\*"));
        add(new Terminal("FACT", "!"));
        add(new Terminal("DIV", "/"));
        add(new Terminal("LP", "\\("));
        add(new Terminal("RP", "\\)"));
        add(new Terminal("NUM", "[0-9]+"));
    }};
    private List<Pattern> patterns = new ArrayList<>();


    public LexerCalculator(String inputStream) {
        this.inputStream = inputStream;
        for (Terminal state : states) {
            patterns.add(Pattern.compile("^\\s*" + state.getRegexpr()));
        }
        nextToken();
    }

    private boolean match() {
        for (int i = 0; i < patterns.size(); i++) {
            Matcher matcher = patterns.get(i).matcher(inputStream);
            if (matcher.find()) {
                String group = matcher.group();
                curTokenName = states.get(i).getName();
                curTokenValue = group.strip();
                subStr(group.length());
                return true;
            }
        }
        return false;
    }
    
    private void subStr(int num) {
        inputStream = inputStream.substring(num);
    }
    public String getTokenName() {
        return curTokenName;
    }

    public String getTokenValue() {
        return curTokenValue;
    }

    public void nextToken() {
        if (inputStream.length() == 0) {
            curTokenName = "end";
            return;
        }
        match();
    }
}