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


    /**
     * 初始化亮灯的表
     *
     * @param positions
     * @return
     */
    private static Integer init(List<Integer> positions) {
        int result = 0;
        for (int i = 0; i < 10; i++) {
            if (positions.contains(i)) {
                // 对应位置若有值，则在当前二进制位上设为1
                result = result | 1 << i;
            }
        }
        return result;
    }

    /**
     * 将10进制数分解，然后从初始化的表中获取数据即可
     *
     * @param input
     * @return
     */
    public static int[] getPrintPositions(Integer input) {
        String nums = input.toString();
        char[] chars = nums.toCharArray();
        int[] result = new int[chars.length];
        int index = 0;
        for (char c : chars) {
            int i = c - '0';
            System.out.println("num  :" + i);
            int light = NUM_FORMAT.get(i);
            System.out.println("light:" + light);
            result[index++] = light;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] lights = getPrintPositions(1013513);
    }
}
