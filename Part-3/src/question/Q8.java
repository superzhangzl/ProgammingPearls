package question;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zzl
 */
public class Q8 {
    public static final List<Integer> NUM_FORMAT;

    static {
        NUM_FORMAT = Stream.of(
                Arrays.asList(0, 2, 3, 4, 5, 6),    //0
                Arrays.asList(4, 6),                //1
                Arrays.asList(2, 4, 1, 5, 0),       //2
                Arrays.asList(2, 4, 1, 6, 0),       //3
                Arrays.asList(3, 1, 4, 6),          //4
                Arrays.asList(2, 3, 1, 6, 0),       //5
                Arrays.asList(2, 3, 1, 5, 6, 0),    //6
                Arrays.asList(2, 4, 6),             //7
                Arrays.asList(0, 1, 2, 3, 4, 5, 6), //8
                Arrays.asList(0, 1, 2, 3, 4, 6))    //9
                .map(Q8::init)
                .collect(Collectors.toList());
        System.out.println(NUM_FORMAT);
    }


    private static Integer init(List<Integer> positions) {
        int result = 0;
        for (int i = 0; i < 10; i++) {
            if (positions.contains(i)) {
                result = result | 1 << i;
            }
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
