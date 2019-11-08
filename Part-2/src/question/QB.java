package question;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzl
 */
public class QB {

    public static void main(String[] args) {
        int i = 3;
        List<Object> arr = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");
        int n = arr.size();

        System.out.println(reverse(arr, i, n));
        System.out.println(reverse(arr, -1, n));
        System.out.println(reverse(arr, -1, -1));
        System.out.println(reverse(null, -1, -1));

    }

    public static List<Object> reverse(List<Object> arr, int times, int size) {
        if (arr == null || arr.isEmpty()) {
            return arr;
        }
        if (size <= 0 || times <= 0) {
            return arr;
        }
        times = Math.floorMod(times, size);
        System.out.println(times);
        List<Object> doArr = arr.stream().collect(Collectors.toList());
        doReverseRange(doArr, 0, times - 1);
        doReverseRange(doArr, times, size - 1);
        doReverseRange(doArr, 0, size - 1);
        return doArr;
    }

    /**
     * 反转指定范围的数组
     *
     * @param arr
     * @param start
     * @param end
     */
    private static void doReverseRange(List<Object> arr, int start, int end) {
        while (true) {
            // 通过交换指定范围两端的值，逐渐逼近中心位置，达到局部反转的目的
            if (start != end && (start - end) != 1) {
                // 使用jdk提供的交换函数
                Collections.swap(arr, start, end);
                start++;
                end--;
            } else {
                return;
            }
        }
    }
}
