import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DecryptorTest {
    static String war_and_peace;

    public static String crypt(int length) throws IOException {
        if (war_and_peace == null) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream("crypt_kasiski_test/war_and_peace"));
                StringBuilder sb = new StringBuilder();
                int c;
                while ((c = reader.read()) >= 0) {
                    if (Character.isAlphabetic(c) && c <= 'z' && c >= 'a') {
                        c = Character.toLowerCase(c);
                        sb.append((char) c);
                    }
                }
                war_and_peace = sb.toString();
                reader.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        int[] shifts = new int[length];
        for (int i = 0; i < length; i++) {
            shifts[i] = (int) (Math.random() * 26);
        }
        int c;
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < war_and_peace.length(); j++) {
            sb.append((char) ('a' + (war_and_peace.charAt(j) - 'a' + shifts[j % length]) % 26));
        }
        return sb.toString();
    }


    @Test
    public void decrypt() throws IOException {
        Decryptor decryptor = new Decryptor();
        int count = 0;
        int good = 0;
        for (int i = 1; i < 20; i++) {
//            for (int j = 0; j < 30; j++) {
                String crypt = crypt(i);
                int countAlphabet = new Main().findCountAlphabet(crypt, true);

//                Assert.assertEquals(0, countAlphabet % i);
//                Assert.assertEquals(decryptor.decrypt(crypt, countAlphabet), war_and_peace);
                count++;
                if (countAlphabet % i == 0) {
                    System.out.println(countAlphabet / i);
                    good++;
                } else {
                    System.out.println("Incorrect : " + countAlphabet + " actual : " + i);
                }
//                System.out.println("Test " + i + " - " + " OK");

//            }
        }
        System.out.println("Good " + good + " from " + count);
    }
}