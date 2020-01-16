package generators;

import actions.Action;
import doman.rule.Rule;
import utils.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserGenerator {
    private static final String template =
            "package generated.%1$s;\n" +
                    "\n" +
                    "import actions.*;\n" +
                    "import doman.rule.ProductionRule;\n" +
                    "import utils.Pair;\n" +
                    "import utils.ParseException;\n" +
                    "\n" +
                    "import java.util.*;\n" +
                    "import java.util.function.Function;\n" +
                    "\n" +
                    "%2$s" +
                    "\n" +
                    "public class Parser%3$s {\n" +
                    "    private Lexer%3$s lexer;\n" +
                    "    private Map<Pair<Integer, String>, Action> table = new HashMap<>() {{\n" +
                    "%4$s" +
                    "    }};\n" +
                    "\n" +
                    "    public static List<Function<List<Object>, Object>> code = new ArrayList<>() {{\n" +
                    "%5$s" +
                    "    }};\n" +
                    "\n" +
                    "    public Parser%3$s(String inputStream) {\n" +
                    "        lexer = new Lexer%3$s(inputStream);\n" +
                    "    }\n" +
                    "\n" +
                    "    public %6$s parse() throws ParseException {\n" +
                    "        Deque<Integer> state = new ArrayDeque<>();\n" +
                    "        Deque<Object> stack = new ArrayDeque<>();\n" +
                    "        state.add(0);\n" +
                    "\n" +
                    "        Pair<Integer, String> temp = new Pair<>(state.peekFirst(), lexer.getTokenName());\n" +
                    "        Action action = table.get(temp);\n" +
                    "\n" +
                    "        while (!(action instanceof Accept)) {\n" +
                    "\n" +
                    "            if (action instanceof Shift) {\n" +
                    "                state.addFirst(((Shift) action).getTo());\n" +
                    "                stack.addFirst(lexer.getTokenValue());\n" +
                    "                lexer.nextToken();\n" +
                    "            } else if (action instanceof Reduce) {\n" +
                    "                Reduce reduce = (Reduce) action;\n" +
                    "                List<Object> args = new ArrayList<>();\n" +
                    "                ProductionRule rule = reduce.getRule();\n" +
                    "\n" +
                    "                for (int i = 0; i < rule.getTokens().size(); i++) {\n" +
                    "                    args.add(stack.pollFirst());\n" +
                    "                    state.removeFirst();\n" +
                    "                }\n" +
                    "\n" +
                    "                Collections.reverse(args);\n" +
                    "                int ind = rule.getId();\n" +
                    "                state.addFirst(((GoTo) table.get(new Pair<>(state.peekFirst(), rule.getName()))).getTo());\n" +
                    "                stack.addFirst(code.get(ind).apply(args));\n" +
                    "            }\n" +
                    "\n" +
                    "            temp = new Pair<>(state.peekFirst(), lexer.getTokenName());\n" +
                    "            action = table.get(temp);\n" +
                    "        }\n" +
                    "\n" +
                    "        return (%6$s) stack.pollFirst();\n" +
                    "    }\n" +
                    "\n" +
                    "}\n";


    private static final String putTemplate = "        put(new Pair<>(%d, \"%s\"), %s);\n";

    private static String getNewAction(Action action) {
        return action.generateNewObject();
    }

    private static String putGenerate(Map<Pair<Integer, String>, Action> table) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Pair<Integer, String>, Action> entry : table.entrySet()) {
            builder.append(String.format(putTemplate, entry.getKey().getFirst(), entry.getKey().getSecond(), getNewAction(entry.getValue())));
        }
        return builder.toString();
    }

    private static final String addTemplate =
            "        add(l -> {\n" +
                    "            %s $$;\n" +
                    "            %s;\n" +
                    "            return (%s) $$;\n" +
                    "        });\n";
    private static final Pattern pattern = Pattern.compile("\\$\\d+");

    private static String addGenerate(List<Rule> rules) {
        StringBuilder builder = new StringBuilder();

        Map<String, String> types = new HashMap<>();
        for (Rule rule : rules) {
            types.put(rule.getName(), rule.getReturnType());
        }



        for (Rule rule : rules) {
            String code = rule.getCode();
            String type = rule.getReturnType();

            code = code.substring(1, code.length() - 1);

            Matcher matcher = pattern.matcher(code);
            while (matcher.find()) {
                String group = matcher.group();
                int num = Integer.parseInt(group.substring(1));
                code = code.replace(group, '(' + types.getOrDefault(rule.getTokens().get(num), "String") + ')' + "l.get(" + num + ')');
            }

            builder.append(String.format(addTemplate, type, code, type));
        }
        return builder.toString();
    }

    public static String generate(String name, String packege, String importCode, Map<Pair<Integer, String>, Action> table, List<Rule> rules) {
        return String.format(template, packege, importCode, name, putGenerate(table), addGenerate(rules), rules.get(0).getReturnType());
    }
}
