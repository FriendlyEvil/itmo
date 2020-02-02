import generators.MainGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        String str = String.join("\n", Files.readAllLines(Path.of("source/Calculator.dkr")));
        new MainGenerator().generate(str, "Calculator", "calculator");

//        String str = String.join("\n", Files.readAllLines(Path.of("source/PascalVar.dkr")));
//        new MainGenerator().generate(str, "PascalVar", "pascal");
//        str = String.join("\n", Files.readAllLines(Path.of("source/Logic.dkr")));
//        new MainGenerator().generate(str, "Logic", "logic");
        System.out.println("Сode generated");
    }
}