import java.io.*;
import java.util.*;

public class Sort {
    public static String getLine(Scanner in) throws Exception {
        return in.nextLine().stripLeading() + "\n";
    }

    public static void main(String[] args) throws Exception {
        String input = "tosort.tex";
        String output = "sorted.tex";
        try (Scanner in = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(input))));
             PrintWriter out = new PrintWriter(output)) {
            Map<String, String> map = new TreeMap<>();
            String line = getLine(in);
            while (in.hasNext()) {
                String key = line;
                StringBuilder value = new StringBuilder();
                while (in.hasNext()) {
                    line = getLine(in);
                    if (line.startsWith("\\section")) {
                        break;
                    }
                    value.append(line);
                }
                map.put(key, value.toString());
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                out.write(entry.getKey() + entry.getValue());
            }
        }
    }
}
