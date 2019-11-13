package question;

import java.util.*;

/**
 * @author zzl
 */
public class QC {
    public static void main(String[] args) {
//        变位词
        List<String> inputWords;
        if (args == null || args.length == 0) {
            inputWords = Arrays.asList("abc", "cba", "love", "evol", "no", "nothing", "");
        } else {
            inputWords = new ArrayList<>(Arrays.asList(args));
        }
//        <label, values>
        Map<String, ArrayList<String>> labelWithWords = new HashMap<>();
        char[] chars;
        for (String word : inputWords) {
            chars = word.toCharArray();
            Arrays.sort(chars);
            String newOrder = new String(chars);
            if (labelWithWords.containsKey(newOrder)) {
                labelWithWords.get(newOrder).add(word);
            } else {
                ArrayList<String> list = new ArrayList<>();
                list.add(word);
                labelWithWords.put(newOrder, list);
            }
        }
        System.out.println(labelWithWords);
    }
}
