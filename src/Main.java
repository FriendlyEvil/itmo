import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static int basis = 31;
    private static int basisSquare = basis * basis;
    private Map<Integer, Integer> map = new HashMap<>();

    private Map<Integer, Integer> indexHash = new HashMap<>();

    private static int minCountTrigram = 253;
    private static int countTrigrams = 10;
    private static int countVariant = 10;
    private static int lengthMax = 50;

    void addTrigramInMap(int hash, int ind) {
        Integer el = map.get(hash);
        int count = (el == null) ? 1 : 1 + el;
        if (count == 1) {
            indexHash.put(hash, ind);
        }
        map.put(hash, count);
    }

    void findTrigram(String str) {
        if (str.length() < 3) {
            return;
        }
        int hash = str.charAt(0) * basisSquare + str.charAt(1) * basis + str.charAt(2);
        addTrigramInMap(hash, 0);
        for (int i = 3; i < str.length(); i++) {
            hash = (hash - str.charAt(i - 3) * basisSquare) * basis + str.charAt(i);
            addTrigramInMap(hash, i - 2);
        }
    }

    List<Integer> getIndexBestTrigrams(String str) {
        findTrigram(str);
        List<Integer> allTrigramList = map.entrySet().stream().
                filter(entry -> entry.getValue() > minCountTrigram).
                sorted(Comparator.comparingInt(Map.Entry::getValue)).
                map(hash -> indexHash.get(hash.getKey())).collect(Collectors.toList());
        return IntStream.range(0, Math.min(countTrigrams, allTrigramList.size())).
                mapToObj(allTrigramList::get).collect(Collectors.toList());
    }

    Pair find(List<Integer> indexes, int step) {
        int count = 0;
        for (int shift = 0; shift < step; shift++) {
            for (int i : indexes) {
                if ((i - shift) % step == 0) {
                    count++;
                }
            }
        }
        return new Pair(count, step);
    }

    List<Integer> findTrigramInfo(String str, int ind) {
        String trigram = str.substring(ind, ind + 3);
        int lastInd = ind;
        int g = 1;
        Set<Integer> set = new HashSet<>();
        List<Integer> indexes = new ArrayList<>();
        for (int i = ind + 1; i < str.length() - 3; i++) {
            boolean flag = true;
            for (int j = 0; j < 3; j++) {
                flag = flag && (trigram.charAt(j) == str.charAt(i + j));
            }
            if (flag) {
                if (i - lastInd < lengthMax) {
                    set.add(i - lastInd);
                    g = i - lastInd;
                }
                indexes.add(ind);
                lastInd = i;

            }
        }

        List<Pair> ls = set.stream().map(n -> find(indexes, n)).sorted().collect(Collectors.toList());
        List<Integer> list = IntStream.range(0, Math.min(countVariant, ls.size())).
                mapToObj(n -> ls.get(n).second).collect(Collectors.toList());
        return list;

//        for (int i : set) {
//            g = gcd(g, i);
//        }
//
//        List<Integer> l = new ArrayList<>();
//        l.add(g);
//        return l;
    }

    int gcd(int a, int b) {
        return a == 0 ? b : gcd(b % a, a);
    }

    public int democracy(List<List<Integer>> list) {
        Map<Integer, Integer> map = new HashMap<>();
        for (List<Integer> ls : list) {
            for (int i = 0; i < ls.size(); i++) {
                int step = ls.get(i);
                map.putIfAbsent(step, 0);
                map.put(step, map.get(step) + countVariant - i);
            }
        }
        return map.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey).
                orElse(-1);
    }

    public int findCountAlphabet(String str) {
        List<Integer> trigramList = getIndexBestTrigrams(str);
        List<List<Integer>> list = trigramList.stream().
                map(n -> findTrigramInfo(str, n)).
                collect(Collectors.toList());
        int step = democracy(list);
//        int step = democracy(list.stream().filter(n -> n.get(0) > 1).collect(Collectors.toList()));
//        int step = list.stream().max(Comparator.comparingInt(o -> o.get(0))).get().get(0);
        if (step == -1) {
            System.exit(42);
        }
        return step;
    }

    public static void main(String[] args) {
        String filename = "war_and_peace2";
        try(Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(new File(filename))))) {
            String str = scanner.nextLine();
//            System.out.println(str.length()); //2 532 201
            minCountTrigram = str.length() / 10000;

            int n = new Main().findCountAlphabet(str);
            System.out.println(n);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    class Pair implements Comparable<Pair>{
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        int first;
        int second;

        @Override
        public int compareTo(Pair o) {
            return first - o.first;
        }
    }
}