package question;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author zzl
 */
public class Q2 {
    public static class BitSort {
        public static final int BIT_SIZE = 32;
        public static final int SHIFT = 5;
        public static final int MASK = 0x1F;
        public static final int N = 1000_0000;
        public static final int[] arr = new int[1 + N / BIT_SIZE];

        public static void set(int i) {
            arr[i >> SHIFT] |= (1 << (i & MASK));
        }

        public static void clear(int i) {
            arr[i >> SHIFT] &= ~(1 << (i & MASK));
        }

        public static boolean test(int i) {
            return ((arr[i >> SHIFT] & (1 << (i & MASK))) != 0);
        }
    }


    @Test
    public void test() {
        int i = 35;
        //设置i，也就是置相应位置位1
        BitSort.set(i);
        //测试是否置1了
        Assert.assertTrue(BitSort.test(i));
        // 清除i
        BitSort.clear(i);
        Assert.assertFalse(BitSort.test(i));
    }
}
