import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = "2&3 + 2 * 3 & 3 * 1";
//        str = scanner.nextLine();
        try {
            Tree tree = new Parser().parse(new ByteArrayInputStream(str.getBytes()));
            tree.show2();
//            tree.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
