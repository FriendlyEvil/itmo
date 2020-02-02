package generated.logic;

import actions.*;
import doman.rule.ProductionRule;
import utils.Pair;
import utils.ParseException;

import java.util.*;
import java.util.function.Function;


import doman.generated.logic.*;

public class ParserLogic {
    private LexerLogic lexer;
    private Map<Pair<Integer, String>, Action> table = new HashMap<>() {{
        put(new Pair<>(16, "AND"), new Shift(8));
        put(new Pair<>(16, "XOR"), new Reduce("", new ProductionRule("xor", List.of("xor", "XOR", "and"), 3)));
        put(new Pair<>(6, "!end"), new Reduce("", new ProductionRule("and", List.of("var"), 6)));
        put(new Pair<>(16, "RP"), new Reduce("", new ProductionRule("xor", List.of("xor", "XOR", "and"), 3)));
        put(new Pair<>(1, "AND"), new Shift(8));
        put(new Pair<>(14, "AND"), new Reduce("", new ProductionRule("var", List.of("LP", "or", "RP"), 9)));
        put(new Pair<>(1, "XOR"), new Reduce("", new ProductionRule("xor", List.of("and"), 4)));
        put(new Pair<>(10, "var"), new GoTo(6));
        put(new Pair<>(9, "OR"), new Shift(10));
        put(new Pair<>(4, "!end"), new Reduce("", new ProductionRule("or", List.of("xor"), 2)));
        put(new Pair<>(14, "XOR"), new Reduce("", new ProductionRule("var", List.of("LP", "or", "RP"), 9)));
        put(new Pair<>(14, "RP"), new Reduce("", new ProductionRule("var", List.of("LP", "or", "RP"), 9)));
        put(new Pair<>(1, "RP"), new Reduce("", new ProductionRule("xor", List.of("and"), 4)));
        put(new Pair<>(2, "LP"), new Shift(2));
        put(new Pair<>(12, "AND"), new Reduce("", new ProductionRule("var", List.of("NOT", "var"), 8)));
        put(new Pair<>(2, "VAR"), new Shift(5));
        put(new Pair<>(8, "var"), new GoTo(13));
        put(new Pair<>(12, "XOR"), new Reduce("", new ProductionRule("var", List.of("NOT", "var"), 8)));
        put(new Pair<>(10, "NOT"), new Shift(7));
        put(new Pair<>(12, "RP"), new Reduce("", new ProductionRule("var", List.of("NOT", "var"), 8)));
        put(new Pair<>(0, "LP"), new Shift(2));
        put(new Pair<>(0, "VAR"), new Shift(5));
        put(new Pair<>(13, "!end"), new Reduce("", new ProductionRule("and", List.of("and", "AND", "var"), 5)));
        put(new Pair<>(5, "OR"), new Reduce("", new ProductionRule("var", List.of("VAR"), 7)));
        put(new Pair<>(8, "NOT"), new Shift(7));
        put(new Pair<>(11, "LP"), new Shift(2));
        put(new Pair<>(11, "VAR"), new Shift(5));
        put(new Pair<>(16, "OR"), new Reduce("", new ProductionRule("xor", List.of("xor", "XOR", "and"), 3)));
        put(new Pair<>(3, "OR"), new Shift(10));
        put(new Pair<>(6, "AND"), new Reduce("", new ProductionRule("and", List.of("var"), 6)));
        put(new Pair<>(14, "OR"), new Reduce("", new ProductionRule("var", List.of("LP", "or", "RP"), 9)));
        put(new Pair<>(2, "var"), new GoTo(6));
        put(new Pair<>(1, "OR"), new Reduce("", new ProductionRule("xor", List.of("and"), 4)));
        put(new Pair<>(6, "XOR"), new Reduce("", new ProductionRule("and", List.of("var"), 6)));
        put(new Pair<>(15, "!end"), new Reduce("", new ProductionRule("or", List.of("or", "OR", "xor"), 1)));
        put(new Pair<>(6, "RP"), new Reduce("", new ProductionRule("and", List.of("var"), 6)));
        put(new Pair<>(7, "LP"), new Shift(2));
        put(new Pair<>(7, "VAR"), new Shift(5));
        put(new Pair<>(10, "and"), new GoTo(1));
        put(new Pair<>(12, "OR"), new Reduce("", new ProductionRule("var", List.of("NOT", "var"), 8)));
        put(new Pair<>(4, "XOR"), new Shift(11));
        put(new Pair<>(0, "var"), new GoTo(6));
        put(new Pair<>(2, "NOT"), new Shift(7));
        put(new Pair<>(10, "xor"), new GoTo(15));
        put(new Pair<>(4, "RP"), new Reduce("", new ProductionRule("or", List.of("xor"), 2)));
        put(new Pair<>(11, "var"), new GoTo(6));
        put(new Pair<>(15, "XOR"), new Shift(11));
        put(new Pair<>(5, "!end"), new Reduce("", new ProductionRule("var", List.of("VAR"), 7)));
        put(new Pair<>(15, "RP"), new Reduce("", new ProductionRule("or", List.of("or", "OR", "xor"), 1)));
        put(new Pair<>(0, "NOT"), new Shift(7));
        put(new Pair<>(13, "AND"), new Reduce("", new ProductionRule("and", List.of("and", "AND", "var"), 5)));
        put(new Pair<>(13, "XOR"), new Reduce("", new ProductionRule("and", List.of("and", "AND", "var"), 5)));
        put(new Pair<>(11, "NOT"), new Shift(7));
        put(new Pair<>(13, "RP"), new Reduce("", new ProductionRule("and", List.of("and", "AND", "var"), 5)));
        put(new Pair<>(7, "var"), new GoTo(12));
        put(new Pair<>(6, "OR"), new Reduce("", new ProductionRule("and", List.of("var"), 6)));
        put(new Pair<>(2, "and"), new GoTo(1));
        put(new Pair<>(12, "!end"), new Reduce("", new ProductionRule("var", List.of("NOT", "var"), 8)));
        put(new Pair<>(7, "NOT"), new Shift(7));
        put(new Pair<>(4, "OR"), new Reduce("", new ProductionRule("or", List.of("xor"), 2)));
        put(new Pair<>(9, "RP"), new Shift(14));
        put(new Pair<>(2, "xor"), new GoTo(4));
        put(new Pair<>(10, "LP"), new Shift(2));
        put(new Pair<>(10, "VAR"), new Shift(5));
        put(new Pair<>(0, "and"), new GoTo(1));
        put(new Pair<>(15, "OR"), new Reduce("", new ProductionRule("or", List.of("or", "OR", "xor"), 1)));
        put(new Pair<>(2, "or"), new GoTo(9));
        put(new Pair<>(16, "!end"), new Reduce("", new ProductionRule("xor", List.of("xor", "XOR", "and"), 3)));
        put(new Pair<>(3, "!end"), new Accept());
        put(new Pair<>(8, "LP"), new Shift(2));
        put(new Pair<>(0, "xor"), new GoTo(4));
        put(new Pair<>(5, "AND"), new Reduce("", new ProductionRule("var", List.of("VAR"), 7)));
        put(new Pair<>(11, "and"), new GoTo(16));
        put(new Pair<>(8, "VAR"), new Shift(5));
        put(new Pair<>(5, "XOR"), new Reduce("", new ProductionRule("var", List.of("VAR"), 7)));
        put(new Pair<>(13, "OR"), new Reduce("", new ProductionRule("and", List.of("and", "AND", "var"), 5)));
        put(new Pair<>(0, "or"), new GoTo(3));
        put(new Pair<>(14, "!end"), new Reduce("", new ProductionRule("var", List.of("LP", "or", "RP"), 9)));
        put(new Pair<>(1, "!end"), new Reduce("", new ProductionRule("xor", List.of("and"), 4)));
        put(new Pair<>(5, "RP"), new Reduce("", new ProductionRule("var", List.of("VAR"), 7)));
    }};

    public static List<Function<List<Object>, Object>> code = new ArrayList<>() {{
        add(l -> {
            Logic $$;
            $$ = (Logic)l.get(0);
            return (Logic) $$;
        });
        add(l -> {
            Logic $$;
            $$ = new Or((Logic)l.get(0), (Logic)l.get(2));
            return (Logic) $$;
        });
        add(l -> {
            Logic $$;
            $$ = (Logic)l.get(0);
            return (Logic) $$;
        });
        add(l -> {
            Logic $$;
            $$ = new Xor((Logic)l.get(0), (Logic)l.get(2));
            return (Logic) $$;
        });
        add(l -> {
            Logic $$;
            $$ = (Logic)l.get(0);
            return (Logic) $$;
        });
        add(l -> {
            Logic $$;
            $$ = new And((Logic)l.get(0), (Logic)l.get(2));
            return (Logic) $$;
        });
        add(l -> {
            Logic $$;
            $$ = (Logic)l.get(0);
            return (Logic) $$;
        });
        add(l -> {
            Logic $$;
            $$ = new Var((String)l.get(0));
            return (Logic) $$;
        });
        add(l -> {
            Logic $$;
            $$ = new Not((Logic)l.get(1));
            return (Logic) $$;
        });
        add(l -> {
            Logic $$;
            $$ = (Logic)l.get(1);
            return (Logic) $$;
        });
    }};

    public ParserLogic(String inputStream) {
        lexer = new LexerLogic(inputStream);
    }

    public Logic parse() throws ParseException {
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

        return (Logic) stack.pollFirst();
    }

}
