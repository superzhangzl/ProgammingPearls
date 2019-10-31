package question;

/**
 * @author zzl
 */
public class Q5 {

    public static class BitSortMemoryStrict {

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
}
