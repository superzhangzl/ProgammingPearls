package question;

/**
 * 二分查找
 *
 * @author zzl
 */
public class QA {
    public static void main(String[] args) {
        // todo add Test case，and timing test case
        int[] arr = new int[]{1, 2, 3, 4, 5, 5, 7, 8, 9};
        int result0 = binarySearch0(arr, 0, arr.length, 5);
        System.out.println(result0);
        for (int i = 1; i < 10; i++) {
            int result1 = binarySearch1(arr, 0, arr.length, i);
            System.out.println(result1);
        }
    }


    /**
     * JDK中的二分查找（迭代版本）
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

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = a[mid];

            if (midVal < key) {
                low = mid + 1;
            } else if (midVal > key) {
                high = mid - 1;
            } else {
                // key found
                return mid;
            }
        }
        // key not found.
        return -(low + 1);
    }

    /**
     * 二分查找的递归版本
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @param key
     * @return
     */
    public static int binarySearch1(int[] a, int fromIndex, int toIndex, int key) {
        // 只是为了名字好听
        int low = fromIndex;
        int high = toIndex;
        // 结束递归条件，代表没有找到
        if (low > high) {
            return -1;
        }
        // 除以2,中间值
        // >>> ：无符号右移，忽略符号位，空位都以0补齐
        int mid = (low + high) >>> 1;
        int midVal = a[mid];

        if (midVal < key) {
            low = mid + 1;
            return binarySearch1(a, low, high, key);
        } else if (midVal > key) {
            high = mid - 1;
            return binarySearch1(a, low, high, key);
        } else {
            // key found
            return mid;
        }
    }
}
