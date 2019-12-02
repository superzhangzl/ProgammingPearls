package question;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 二分查找
 *
 * @author zzl
 */
public class Q4 {
    public static void main(String[] args) {
        // 初始化一个较大的int数组
        List<Integer> list = IntStream.range(0, 1000000)
                .filter((_1) -> {
                    int nextInt = ThreadLocalRandom.current().nextInt(0, 100);
                    if (nextInt == 50) {
                        return false;
                    } else {
                        return true;
                    }
                })
                .boxed()
                .collect(Collectors.toList());
        int[] arr = new int[list.size()];
        // Integer => int, 转为基本类型
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        for (int i = 1; i < 10; i++) {
            int key = ThreadLocalRandom.current().nextInt(0, 1000000);
            int result1 = binarySearch0(arr, 0, arr.length, key);
            System.out.println("result:" + result1);
        }
    }


    /**
     * JDK中的二分查找（迭代版本）
     * {@link question.QA}
     * 计算迭代次数以及对数
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @param key
     * @return
     */
    public static int binarySearch0(int[] a, int fromIndex, int toIndex, int key) {
        int low = fromIndex;
        int high = toIndex - 1;
        int loopCount = 0;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = a[mid];
            loopCount++;

            if (midVal < key) {
                low = mid + 1;
            } else if (midVal > key) {
                high = mid - 1;
            } else {
                // key found
                // 次数还是比较接近的
                System.out.println("loop count: " + loopCount);
                System.out.println("log_{2}n: " + Math.log(a.length));
                return mid;
            }
        }
        System.out.println("loop count: " + loopCount);
        System.out.println("log_{2}n: " + Math.log(a.length));
        // key not found.
        return -(low + 1);
    }

}
