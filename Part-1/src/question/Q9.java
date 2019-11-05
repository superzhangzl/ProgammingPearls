package question;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zzl
 */
public class Q9 {
    public static void main(String[] args) {
        final int n = 50;
        Integer[] data = new Integer[n];
        Integer[] from = new Integer[n];
        Integer[] to = new Integer[n];
        Integer top = 0;
        // set value
        data[1] = 3;
        data[13] = 2;
        data[21] = 8;
        data[42] = 18;
        for (int i = 0; i < n; i++) {
            // 模拟所在空间为内存中的乱码
            from[i] = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
            to[i] = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
        }
        // init array of `from` and `to`
        for (int i = 0; i < n; i++) {
            if (data[i] != null) {
                // 这个位置有值，则代表已经初始化过了
                from[i] = top;
                to[top] = i;
                // 原参考答案中还有初始化的步骤，这里就去掉
                // data[i] = 0;
                top++;
            }
        }
        /*
        个人觉得，这个算法更适合C语言的特性，声明了数组变量或malloc以后，需要对空间内容初始化，
        否则也是一串乱序的数字，并不能确认是手动赋的值还是内存空间中的值
        此时对数组赋值或访问等就可以根据`from[i] < top && to[from[i]] == i`来进行判断。java的话判空就好了 :)
         */
        System.out.println("from : " + Arrays.toString(from));
        System.out.println("to : " + Arrays.toString(to));
        System.out.println("top : " + top);
        // 3 2 8 18
        for (int i = 0; i < n; i++) {
            // data中位置元素有效的充要条件
            if (from[i] < top && to[from[i]] == i) {
                System.out.println("data[" + i + "] = " + data[i]);
            }
        }
    }
}
