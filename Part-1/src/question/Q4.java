package question;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.exit;

/**
 * @author zzl
 */
public class Q4 {

    public static void main(String[] args) {
        List<Integer> collect = IntStream.range(0, 20)
                .boxed()
				.collect(Collectors.toList());
        randomOrder(collect, 20);
        System.out.println(collect);
    }

    /**
     * 生成0到n-1之间k个不同的随机序列的随机整数
     * <p>
     * Generate a random integer of k different random sequences between 0 and n-1
     *
     * @param arr
     * @param k
     */
    public static void randomOrder(List arr, int k) {
        if (k > arr.size()) {
            System.err.println("arr.size must greater or equal than k");
            exit(1);
        }
        for (int i = 0; i < k; i++) {
            // nextInt means [origin,bound)
            Collections.swap(arr, i, ThreadLocalRandom.current().nextInt(i, arr.size()));
        }
    }
}
