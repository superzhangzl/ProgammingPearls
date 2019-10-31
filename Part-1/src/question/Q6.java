package question;


/**
 * @author zzl
 */
public class Q6 {
    public static class BitSortNotOnlyOne {

        public static final int TIMES = 10;// 每个数字出现最多的次数
        public static final int BIT_SIZE = 32;
        public static final int SHIFT = 5;
        public static final int MASK = 0x1F;
        public static final int N = 1000_0000;
        public static final int[] arr;

        static {
            if (TIMES <= 0 || TIMES > Integer.MAX_VALUE) {
                System.exit(-9);
            }
            int i = 0;
            while (true) {
                double pow = Math.pow(2, i);
                double nextPow = Math.pow(2, i + 1);
                // 需要pow -1，0次代表没有出现，所以最多只能表示一个数出现2^(i+1)-1次
                if ((pow - 1) < TIMES && (nextPow - 1) >= TIMES) {
                    arr = new int[1 + N / (BIT_SIZE / (i + 1))];
                    break;
                } else if (pow == TIMES) {
                    arr = new int[1 + N / (BIT_SIZE)];
                    break;
                }
                i++;
            }
            System.out.println(arr.length);
        }

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

    public static void main(String[] args) {
        BitSortNotOnlyOne.set(1);
    }
}
