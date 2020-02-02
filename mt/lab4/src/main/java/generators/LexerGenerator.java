package generators;

import antlr.Terminal;

import java.util.List;

public class LexerGenerator {
    private static final String template =
            "package generated.%s;\n" +
                    "\n" +
                    "import antlr.Terminal;\n" +
                    "import utils.ParseException;\n" +
                    "\n" +
                    "import java.io.IOException;\n" +
                    "import java.io.InputStream;\n" +
                    "import java.util.ArrayList;\n" +
                    "import java.util.List;\n" +
                    "import java.util.regex.Matcher;\n" +
                    "import java.util.regex.Pattern;\n" +
                    "\n" +
                    "%s" +
                    "\n" +
                    "public class Lexer%s {\n" +
                    "    private String inputStream;\n" +
                    "    private String curTokenName;\n" +
                    "    private String curTokenValue;\n" +
                    "    private List<Terminal> states = new ArrayList<>() {{\n" +
                    "%s" +
                    "    }};\n" +
                    "    private List<Pattern> patterns = new ArrayList<>();\n" +
                    "\n" +
                    "\n" +
                    "    public Lexer%s(String inputStream) {\n" +
                    "        this.inputStream = inputStream;\n" +
                    "        for (Terminal state : states) {\n" +
                    "            patterns.add(Pattern.compile(\"^\\\\s*\" + state.getRegexpr()));\n" +
                    "        }\n" +
                    "        nextToken();\n" +
                    "    }\n" +
                    "\n" +
                    "    private boolean match() {\n" +
                    "        for (int i = 0; i < patterns.size(); i++) {\n" +
                    "            Matcher matcher = patterns.get(i).matcher(inputStream);\n" +
                    "            if (matcher.find()) {\n" +
                    "                String group = matcher.group();\n" +
                    "                curTokenName = states.get(i).getName();\n" +
                    "                curTokenValue = group.strip();\n" +
                    "                subStr(group.length());\n" +
                    "                return true;\n" +
                    "            }\n" +
                    "        }\n" +
                    "        return false;\n" +
                    "    }\n" +
                    "    \n" +
                    "    private void subStr(int num) {\n" +
                    "        inputStream = inputStream.substring(num);\n" +
                    "    }" +
                    "\n" +
                    "    public String getTokenName() {\n" +
                    "        return curTokenName;\n" +
                    "    }\n" +
                    "\n" +
                    "    public String getTokenValue() {\n" +
                    "        return curTokenValue;\n" +
                    "    }\n" +
                    "\n" +
                    "    public void nextToken() {\n" +
                    "        if (inputStream.length() == 0) {\n" +
                    "            curTokenName = \"end\";\n" +
                    "            return;\n" +
                    "        }\n" +
                    "        match();\n" +
                    "    }\n" +
                    "}";
    private static final String addTemplate = "        add(new Terminal(\"%s\", %s));\n";

    public static String generateTerminals(List<Terminal> terminals) {
        StringBuilder builder = new StringBuilder();
        for (Terminal terminal : terminals) {
            builder.append(String.format(addTemplate, terminal.getName(), terminal.getRegexpr()));
        }
        return builder.toString();
    }

    public static String generate(String name, String packege, String importCode, List<Terminal> terminals) {
        return String.format(template, packege, importCode, name, generateTerminals(terminals), name);
    }
}
