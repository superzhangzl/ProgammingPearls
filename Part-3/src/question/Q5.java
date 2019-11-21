package question;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zzl
 */
public class Q5 {
    public static List<String> suffix_c;

    static {
        suffix_c = Stream.of(
                "et-ic", "al-is-tic", "p-tic", "-lyt-ic", "ot-ic", "an-tic", "n-tic", "c-tic", "at-ic", "h-nic",
                "n-ic", "m-ic", "l-lic", "b-lic", "-clic", "l-ic", "h-ic", "f-ic", "d-ic", "-bic", "a-ic", "-mac", "i-ac"
        ).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(getSuffix("ethnic"));
        System.out.println(getSuffix("clinic"));
        System.out.println(getSuffix("cliniiiiiic"));
    }

    public static String getSuffix(String word) {
        if (!word.endsWith("c")) {
            return null;
        }
        for (String s : suffix_c) {
            if (word.endsWith(s.replaceAll("-", ""))) {
                return s;
            }
        }
        return null;
    }
}
