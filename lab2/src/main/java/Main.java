import java.io.ByteArrayInputStream;

public class Main {
    public static void main(String[] args) {
        String str = "2 * 2";
        try {
            Tree tree = new Parser().parse(new ByteArrayInputStream(str.getBytes()));
            tree.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
