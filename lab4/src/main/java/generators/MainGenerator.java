package generators;

import antlr.GrammerLexer;
import antlr.GrammerParser;
import antlr.NonTerminal;
import antlr.ParserInput;
import doman.Input;
import doman.algorithm.Table;
import lombok.SneakyThrows;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainGenerator {

    @SneakyThrows
    public void generate(String inputGrammer, String name, String pack) {
        String path = "src/main/java/generated/" + pack.replaceAll("\\.", "/");
        Files.createDirectories(Path.of(path));

        CharStream inputStream = CharStreams.fromString(inputGrammer);
        GrammerLexer lexer = new GrammerLexer(inputStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        GrammerParser parser = new GrammerParser(tokenStream);
        ParserInput grammer = parser.grammer().val;

        NonTerminal start = grammer.getStart();
        Input input = new Input(grammer);
        Table table = new Table(input.getOnlyRules(), input.getTokens(), start.getName());


        try (BufferedWriter bf = new BufferedWriter(new FileWriter(path + "/Lexer" + name + ".java"))) {
            String generatedLexer = LexerGenerator.generate(name, pack, input.getHeader(), input.getTerminals());
            bf.write(generatedLexer);
        }


        try (BufferedWriter bf = new BufferedWriter(new FileWriter(path + "/Parser" + name + ".java"))) {
            String generatedParser = ParserGenerator.generate(name, pack, input.getHeader(), table.getResTable(), table.getRules());
            bf.write(generatedParser);
        }
    }
}
