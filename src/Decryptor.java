import org.junit.Assert;

import java.io.*;
import java.util.*;


public class Decryptor {
    List<Pair> freq = new ArrayList<>();

    public Decryptor() throws IOException {
        Map<Character, Integer> frequency = new HashMap<>();
        InputStreamReader reader = new InputStreamReader(new FileInputStream("crypt_kasiski_test/war_and_peace"));
        int c = 0;
        while ((c = reader.read()) >= 0) {
            if (Character.isAlphabetic(c))
                frequency.merge(Character.toLowerCase((char) c), 1, Integer::sum);
        }
        int total = 0;
        for (Map.Entry<Character, Integer> e : frequency.entrySet()) {
            total += e.getValue();
        }
        for (Map.Entry<Character, Integer> e : frequency.entrySet()) {
            freq.add(new Pair(e.getKey(), e.getValue() / (double) total));
        }
        freq.sort(Comparator.comparing(a -> -a.freq));
        for (Pair p :
                freq) {
        }
    }

    String decrypt(String m, int alphabets) {
        List<Map<Character, Integer>> letters = new ArrayList<>();
        for (int i = 0; i < alphabets; i++) {
            letters.add(new HashMap<>());
        }
        for (int i = 0; i < m.length(); i++) {
            letters.get(i % alphabets).merge(Character.toLowerCase(m.charAt(i)), 1, Integer::sum);
        }
//        for (Map.Entry<Character, Integer> e:letters.get(0).entrySet()){
//            System.out.println(e.getKey() + " ---------------------" + e.getValue());
//        }
        Integer[] shifts = new Integer[alphabets];
        for (int i = 0; i < alphabets; i++) {
//            System.out.println(letters.get(i).entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey());
//            System.out.println(letters.get(i).entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getValue());
            int maxFreq = letters.get(i).entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();

            shifts[i] = maxFreq - freq.get(0).ch >= 0 ?
                    maxFreq - freq.get(0).ch :
                    26 + maxFreq - freq.get(0).ch;
        }
//        Arrays.asList(shifts).forEach(r -> System.out.println("shift - " + r));
//        System.out.println(shifts.length);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m.length(); i++) {
            sb.append((char) ('a' + (26 + m.charAt(i) -'a' - shifts[i % alphabets]) % 26));
        }

        return sb.toString();
    }

    void crypt() throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream("crypt_kasiski_test/war_and_peace"));
        Writer writer = new OutputStreamWriter(new FileOutputStream("war_and_peace1"));
        int[] shifts = {25, 3, 24};
//        int[] shifts = {0, 1, 2, 3, 4, 5};
//        int[] shifts = {0, 1, 2, 3, 4, 5};
        int c;
        int i = 0;
        while ((c = reader.read()) >= 0) {
            if (Character.isAlphabetic(c)) {
                c = Character.toLowerCase(c);
                writer.write('a' + (c - 'a' + shifts[i % shifts.length]) % 26);
                i++;
            }
        }
        writer.close();
    }


    public static void main(String[] args) throws IOException {
//        Decryptor d = new Decryptor();
//        d.crypt();
//        InputStreamReader reader = new InputStreamReader(new FileInputStream("war_and_peace1"));
//        Writer writer = new OutputStreamWriter(new FileOutputStream("war_and_peace2"));
//
//        StringBuilder sb = new StringBuilder();
//        int c = 0;
//        while ((c = reader.read()) >= 0) {
//            sb.append((char)c);
//        }
//        String res = d.decrypt(sb.toString(), new Main().findCountAlphabet(sb.toString()));
//        for (int i = 0; i < res.length(); i++) {
//            writer.write(res.charAt(i));
//        }
//        reader.close();
//        writer.close();
        for (int i = 1; i < 20; i++) {
            String crypt = crypt(i);
            Assert.assertEquals(new Decryptor().decrypt(crypt, new Main().findCountAlphabet(crypt, false)), war_and_peace);
        }
//        System.out.println(d.decrypt(Decryptor.test1.m, test1.alps));
    }

    static class Pair {
        Character ch;

        public Pair(Character ch, Double freq) {
            this.ch = ch;
            this.freq = freq;
        }

        Double freq;
    }

    static class Test {
        String m;
        int alps;

        public Test(String m, int alps) {
            this.m = m;
            this.alps = alps;
        }
    }

    static String war_and_peace;

    public static String crypt(int length) throws IOException {
        if (war_and_peace == null) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream("crypt_kasiski_test/war_and_peace"));
                StringBuilder sb = new StringBuilder();
                int c;
                while ((c = reader.read()) >= 0) {
                    if (Character.isAlphabetic(c)) {
                        c = Character.toLowerCase(c);
                        sb.append((char)c);
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
            System.out.println(sb.length());
            sb.append(((char)('a' + (war_and_peace.charAt(j) - 'a' + shifts[j % length])    % 26)));
        }
        return sb.toString();
    }


}
