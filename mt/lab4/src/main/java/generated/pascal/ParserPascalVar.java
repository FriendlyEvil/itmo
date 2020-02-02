package generated.pascal;

import actions.*;
import doman.rule.ProductionRule;
import utils.Pair;
import utils.ParseException;

import java.util.*;
import java.util.function.Function;


import doman.generated.pascal.*;

public class ParserPascalVar {
    private LexerPascalVar lexer;
    private Map<Pair<Integer, String>, Action> table = new HashMap<>() {{
        put(new Pair<>(0, "VAR"), new Shift(1));
        put(new Pair<>(0, "var"), new GoTo(2));
        put(new Pair<>(7, "names"), new GoTo(10));
        put(new Pair<>(7, "NAME"), new Shift(3));
        put(new Pair<>(4, "vars"), new GoTo(8));
        put(new Pair<>(1, "names"), new GoTo(6));
        put(new Pair<>(12, "!end"), new Reduce("", new ProductionRule("variables", List.of("names", "COLON", "NAME", "SEMICOLON"), 4)));
        put(new Pair<>(4, "variables"), new GoTo(4));
        put(new Pair<>(3, "COMMA"), new Shift(7));
        put(new Pair<>(5, "!end"), new Reduce("", new ProductionRule("var", List.of("VAR", "vars"), 1)));
        put(new Pair<>(12, "NAME"), new Reduce("", new ProductionRule("variables", List.of("names", "COLON", "NAME", "SEMICOLON"), 4)));
        put(new Pair<>(6, "COLON"), new Shift(9));
        put(new Pair<>(4, "!end"), new Reduce("", new ProductionRule("vars", List.of("variables"), 3)));
        put(new Pair<>(9, "NAME"), new Shift(11));
        put(new Pair<>(10, "COLON"), new Reduce("", new ProductionRule("names", List.of("NAME", "COMMA", "names"), 5)));
        put(new Pair<>(11, "SEMICOLON"), new Shift(12));
        put(new Pair<>(4, "NAME"), new Shift(3));
        put(new Pair<>(4, "names"), new GoTo(6));
        put(new Pair<>(1, "vars"), new GoTo(5));
        put(new Pair<>(2, "!end"), new Accept());
        put(new Pair<>(1, "NAME"), new Shift(3));
        put(new Pair<>(8, "!end"), new Reduce("", new ProductionRule("vars", List.of("variables", "vars"), 2)));
        put(new Pair<>(3, "COLON"), new Reduce("", new ProductionRule("names", List.of("NAME"), 6)));
        put(new Pair<>(1, "variables"), new GoTo(4));
    }};

    public static List<Function<List<Object>, Object>> code = new ArrayList<>() {{
        add(l -> {
            List<Vars> $$;
            $$ = (List<Vars>)l.get(0);
            return (List<Vars>) $$;
        });
        add(l -> {
            List<Vars> $$;
            $$ = (List<Vars>)l.get(1);
            return (List<Vars>) $$;
        });
        add(l -> {
            List<Vars> $$;
            $$ = (List<Vars>)l.get(1); $$.add((Vars)l.get(0));
            return (List<Vars>) $$;
        });
        add(l -> {
            List<Vars> $$;
            $$ = new ArrayList<>(); $$.add((Vars)l.get(0));
            return (List<Vars>) $$;
        });
        add(l -> {
            Vars $$;
            $$ = new Vars((List<String>)l.get(0), (String)l.get(2));
            return (Vars) $$;
        });
        add(l -> {
            List<String> $$;
            $$ = (List<String>)l.get(2); $$.add((String)l.get(0));
            return (List<String>) $$;
        });
        add(l -> {
            List<String> $$;
            $$ = new ArrayList<>(); $$.add((String)l.get(0));
            return (List<String>) $$;
        });
    }};

    public ParserPascalVar(String inputStream) {
        lexer = new LexerPascalVar(inputStream);
    }

    public List<Vars> parse() throws ParseException {
        Deque<Integer> state = new ArrayDeque<>();
        Deque<Object> stack = new ArrayDeque<>();
        state.add(0);

        Pair<Integer, String> temp = new Pair<>(state.peekFirst(), lexer.getTokenName());
        Action action = table.get(temp);

        while (!(action instanceof Accept)) {

            if (action instanceof Shift) {
                state.addFirst(((Shift) action).getTo());
                stack.addFirst(lexer.getTokenValue());
                lexer.nextToken();
            } else if (action instanceof Reduce) {
                Reduce reduce = (Reduce) action;
                List<Object> args = new ArrayList<>();
                ProductionRule rule = reduce.getRule();

                for (int i = 0; i < rule.getTokens().size(); i++) {
                    args.add(stack.pollFirst());
                    state.removeFirst();
                }

                Collections.reverse(args);
                int ind = rule.getId();
                state.addFirst(((GoTo) table.get(new Pair<>(state.peekFirst(), rule.getName()))).getTo());
                stack.addFirst(code.get(ind).apply(args));
            }

            temp = new Pair<>(state.peekFirst(), lexer.getTokenName());
            action = table.get(temp);
        }

        return (List<Vars>) stack.pollFirst();
    }

}
