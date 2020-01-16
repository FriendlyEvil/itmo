package generated.calculator;

import actions.*;
import doman.rule.ProductionRule;
import utils.Pair;
import utils.ParseException;

import java.util.*;
import java.util.function.Function;


public class ParserCalculator {
    private LexerCalculator lexer;
    private Map<Pair<Integer, String>, Action> table = new HashMap<>() {{
        put(new Pair<>(12, "MUL"), new Reduce("", new ProductionRule("fact", List.of("fact", "FACT"), 7)));
        put(new Pair<>(17, "ADD"), new Reduce("", new ProductionRule("num", List.of("LP", "sum", "RP"), 10)));
        put(new Pair<>(4, "ADD"), new Reduce("", new ProductionRule("fact", List.of("num"), 8)));
        put(new Pair<>(13, "DIV"), new Reduce("", new ProductionRule("mul", List.of("mul", "MUL", "fact"), 6)));
        put(new Pair<>(5, "FACT"), new Shift(12));
        put(new Pair<>(3, "mul"), new GoTo(1));
        put(new Pair<>(14, "RP"), new Reduce("", new ProductionRule("mul", List.of("mul", "DIV", "fact"), 5)));
        put(new Pair<>(1, "RP"), new Reduce("", new ProductionRule("sum", List.of("mul"), 1)));
        put(new Pair<>(9, "NUM"), new Shift(6));
        put(new Pair<>(13, "ADD"), new Reduce("", new ProductionRule("mul", List.of("mul", "MUL", "fact"), 6)));
        put(new Pair<>(6, "SUB"), new Reduce("", new ProductionRule("num", List.of("NUM"), 9)));
        put(new Pair<>(0, "LP"), new Shift(3));
        put(new Pair<>(0, "num"), new GoTo(4));
        put(new Pair<>(14, "FACT"), new Shift(12));
        put(new Pair<>(13, "FACT"), new Shift(12));
        put(new Pair<>(16, "end"), new Reduce("", new ProductionRule("sum", List.of("sum", "SUB", "mul"), 2)));
        put(new Pair<>(4, "MUL"), new Reduce("", new ProductionRule("fact", List.of("num"), 8)));
        put(new Pair<>(17, "MUL"), new Reduce("", new ProductionRule("num", List.of("LP", "sum", "RP"), 10)));
        put(new Pair<>(0, "fact"), new GoTo(5));
        put(new Pair<>(2, "SUB"), new Shift(10));
        put(new Pair<>(15, "SUB"), new Reduce("", new ProductionRule("sum", List.of("sum", "ADD", "mul"), 3)));
        put(new Pair<>(9, "LP"), new Shift(3));
        put(new Pair<>(5, "DIV"), new Reduce("", new ProductionRule("mul", List.of("fact"), 4)));
        put(new Pair<>(9, "num"), new GoTo(4));
        put(new Pair<>(9, "fact"), new GoTo(5));
        put(new Pair<>(12, "end"), new Reduce("", new ProductionRule("fact", List.of("fact", "FACT"), 7)));
        put(new Pair<>(6, "RP"), new Reduce("", new ProductionRule("num", List.of("NUM"), 9)));
        put(new Pair<>(13, "MUL"), new Reduce("", new ProductionRule("mul", List.of("mul", "MUL", "fact"), 6)));
        put(new Pair<>(5, "ADD"), new Reduce("", new ProductionRule("mul", List.of("fact"), 4)));
        put(new Pair<>(11, "SUB"), new Shift(10));
        put(new Pair<>(1, "DIV"), new Shift(8));
        put(new Pair<>(14, "DIV"), new Reduce("", new ProductionRule("mul", List.of("mul", "DIV", "fact"), 5)));
        put(new Pair<>(6, "FACT"), new Reduce("", new ProductionRule("num", List.of("NUM"), 9)));
        put(new Pair<>(10, "NUM"), new Shift(6));
        put(new Pair<>(15, "RP"), new Reduce("", new ProductionRule("sum", List.of("sum", "ADD", "mul"), 3)));
        put(new Pair<>(1, "ADD"), new Reduce("", new ProductionRule("sum", List.of("mul"), 1)));
        put(new Pair<>(14, "ADD"), new Reduce("", new ProductionRule("mul", List.of("mul", "DIV", "fact"), 5)));
        put(new Pair<>(0, "mul"), new GoTo(1));
        put(new Pair<>(17, "end"), new Reduce("", new ProductionRule("num", List.of("LP", "sum", "RP"), 10)));
        put(new Pair<>(11, "RP"), new Shift(17));
        put(new Pair<>(4, "end"), new Reduce("", new ProductionRule("fact", List.of("num"), 8)));
        put(new Pair<>(5, "MUL"), new Reduce("", new ProductionRule("mul", List.of("fact"), 4)));
        put(new Pair<>(16, "SUB"), new Reduce("", new ProductionRule("sum", List.of("sum", "SUB", "mul"), 2)));
        put(new Pair<>(10, "LP"), new Shift(3));
        put(new Pair<>(10, "num"), new GoTo(4));
        put(new Pair<>(6, "DIV"), new Reduce("", new ProductionRule("num", List.of("NUM"), 9)));
        put(new Pair<>(9, "mul"), new GoTo(15));
        put(new Pair<>(13, "end"), new Reduce("", new ProductionRule("mul", List.of("mul", "MUL", "fact"), 6)));
        put(new Pair<>(14, "MUL"), new Reduce("", new ProductionRule("mul", List.of("mul", "DIV", "fact"), 5)));
        put(new Pair<>(1, "MUL"), new Shift(7));
        put(new Pair<>(10, "fact"), new GoTo(5));
        put(new Pair<>(12, "SUB"), new Reduce("", new ProductionRule("fact", List.of("fact", "FACT"), 7)));
        put(new Pair<>(6, "ADD"), new Reduce("", new ProductionRule("num", List.of("NUM"), 9)));
        put(new Pair<>(15, "DIV"), new Shift(8));
        put(new Pair<>(16, "RP"), new Reduce("", new ProductionRule("sum", List.of("sum", "SUB", "mul"), 2)));
        put(new Pair<>(7, "fact"), new GoTo(13));
        put(new Pair<>(15, "ADD"), new Reduce("", new ProductionRule("sum", List.of("sum", "ADD", "mul"), 3)));
        put(new Pair<>(2, "ADD"), new Shift(9));
        put(new Pair<>(3, "sum"), new GoTo(11));
        put(new Pair<>(3, "fact"), new GoTo(5));
        put(new Pair<>(5, "end"), new Reduce("", new ProductionRule("mul", List.of("fact"), 4)));
        put(new Pair<>(12, "RP"), new Reduce("", new ProductionRule("fact", List.of("fact", "FACT"), 7)));
        put(new Pair<>(7, "NUM"), new Shift(6));
        put(new Pair<>(6, "MUL"), new Reduce("", new ProductionRule("num", List.of("NUM"), 9)));
        put(new Pair<>(11, "ADD"), new Shift(9));
        put(new Pair<>(4, "SUB"), new Reduce("", new ProductionRule("fact", List.of("num"), 8)));
        put(new Pair<>(17, "SUB"), new Reduce("", new ProductionRule("num", List.of("LP", "sum", "RP"), 10)));
        put(new Pair<>(10, "mul"), new GoTo(16));
        put(new Pair<>(1, "end"), new Reduce("", new ProductionRule("sum", List.of("mul"), 1)));
        put(new Pair<>(14, "end"), new Reduce("", new ProductionRule("mul", List.of("mul", "DIV", "fact"), 5)));
        put(new Pair<>(3, "NUM"), new Shift(6));
        put(new Pair<>(15, "MUL"), new Shift(7));
        put(new Pair<>(13, "SUB"), new Reduce("", new ProductionRule("mul", List.of("mul", "MUL", "fact"), 6)));
        put(new Pair<>(7, "LP"), new Shift(3));
        put(new Pair<>(16, "DIV"), new Shift(8));
        put(new Pair<>(7, "num"), new GoTo(4));
        put(new Pair<>(17, "RP"), new Reduce("", new ProductionRule("num", List.of("LP", "sum", "RP"), 10)));
        put(new Pair<>(4, "RP"), new Reduce("", new ProductionRule("fact", List.of("num"), 8)));
        put(new Pair<>(16, "ADD"), new Reduce("", new ProductionRule("sum", List.of("sum", "SUB", "mul"), 2)));
        put(new Pair<>(3, "LP"), new Shift(3));
        put(new Pair<>(12, "DIV"), new Reduce("", new ProductionRule("fact", List.of("fact", "FACT"), 7)));
        put(new Pair<>(17, "FACT"), new Reduce("", new ProductionRule("num", List.of("LP", "sum", "RP"), 10)));
        put(new Pair<>(3, "num"), new GoTo(4));
        put(new Pair<>(4, "FACT"), new Reduce("", new ProductionRule("fact", List.of("num"), 8)));
        put(new Pair<>(8, "NUM"), new Shift(6));
        put(new Pair<>(13, "RP"), new Reduce("", new ProductionRule("mul", List.of("mul", "MUL", "fact"), 6)));
        put(new Pair<>(6, "end"), new Reduce("", new ProductionRule("num", List.of("NUM"), 9)));
        put(new Pair<>(12, "ADD"), new Reduce("", new ProductionRule("fact", List.of("fact", "FACT"), 7)));
        put(new Pair<>(5, "SUB"), new Reduce("", new ProductionRule("mul", List.of("fact"), 4)));
        put(new Pair<>(0, "sum"), new GoTo(2));
        put(new Pair<>(15, "end"), new Reduce("", new ProductionRule("sum", List.of("sum", "ADD", "mul"), 3)));
        put(new Pair<>(12, "FACT"), new Reduce("", new ProductionRule("fact", List.of("fact", "FACT"), 7)));
        put(new Pair<>(2, "end"), new Accept());
        put(new Pair<>(16, "MUL"), new Shift(7));
        put(new Pair<>(8, "fact"), new GoTo(14));
        put(new Pair<>(14, "SUB"), new Reduce("", new ProductionRule("mul", List.of("mul", "DIV", "fact"), 5)));
        put(new Pair<>(17, "DIV"), new Reduce("", new ProductionRule("num", List.of("LP", "sum", "RP"), 10)));
        put(new Pair<>(8, "LP"), new Shift(3));
        put(new Pair<>(1, "SUB"), new Reduce("", new ProductionRule("sum", List.of("mul"), 1)));
        put(new Pair<>(8, "num"), new GoTo(4));
        put(new Pair<>(4, "DIV"), new Reduce("", new ProductionRule("fact", List.of("num"), 8)));
        put(new Pair<>(0, "NUM"), new Shift(6));
        put(new Pair<>(5, "RP"), new Reduce("", new ProductionRule("mul", List.of("fact"), 4)));
    }};

    public static List<Function<List<Object>, Object>> code = new ArrayList<>() {{
        add(l -> {
            Integer $$;
            $$ = (Integer)l.get(0);
            return (Integer) $$;
        });
        add(l -> {
            Integer $$;
            $$ = (Integer)l.get(0);
            return (Integer) $$;
        });
        add(l -> {
            Integer $$;
            $$ = (Integer)l.get(0) - (Integer)l.get(2);
            return (Integer) $$;
        });
        add(l -> {
            Integer $$;
            $$ = (Integer)l.get(0) + (Integer)l.get(2);
            return (Integer) $$;
        });
        add(l -> {
            Integer $$;
            $$ = (Integer)l.get(0);
            return (Integer) $$;
        });
        add(l -> {
            Integer $$;
            $$ = (Integer)l.get(0) / (Integer)l.get(2);
            return (Integer) $$;
        });
        add(l -> {
            Integer $$;
            $$ = (Integer)l.get(0) * (Integer)l.get(2);
            return (Integer) $$;
        });
        add(l -> {
            Integer $$;
            $$ = 1; for (int i = 2; i <= (Integer)l.get(0); i++) {$$ *= i;};;
            return (Integer) $$;
        });
        add(l -> {
            Integer $$;
            $$ = (Integer)l.get(0);
            return (Integer) $$;
        });
        add(l -> {
            Integer $$;
            $$ = Integer.parseInt((String)l.get(0));
            return (Integer) $$;
        });
        add(l -> {
            Integer $$;
            $$ = (Integer)l.get(1);
            return (Integer) $$;
        });
    }};

    public ParserCalculator(String inputStream) {
        lexer = new LexerCalculator(inputStream);
    }

    public Integer parse() throws ParseException {
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

        return (Integer) stack.pollFirst();
    }

}
