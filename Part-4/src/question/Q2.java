package question;

import java.util.Arrays;

public class Q2 {

    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 2, 3, 4, 4, 4, 4, 5, 7, 8, 9, 9};
        System.out.println(findFirstPositionInOrderedList(arr1, 0));
        System.out.println(findFirstPositionInOrderedList(arr1, 1));
        System.out.println(findFirstPositionInOrderedList(arr1, 2));
        System.out.println(findFirstPositionInOrderedList(arr1, 4));
        System.out.println(findFirstPositionInOrderedList(arr1, 9));
        int[] arr2 = new int[]{-1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        System.out.println(findFirstPositionInOrderedList(arr2, 0));
        System.out.println(findFirstPositionInOrderedList(arr2, 1));
    }

    /**
     * 查找有序列表中第一个出现的位置，即有重复数据
     * 思路：使用二分查找，查找到任一位置，然后前向查找
     *
     * @param arr
     * @param key
     * @return
     */
    public static int findFirstPositionInOrderedList(int[] arr, int key) {
        int randomPosition = Arrays.binarySearch(arr, key);
        System.out.println("current index: " + randomPosition);
        if (randomPosition < 0) {
            return randomPosition;
        }
        for (int i = randomPosition; i >= 0; i--) {
            if (arr[i] == key) {
                continue;
            }
            if (arr[i] != key && arr[i + 1] == key) {
                return i + 1;
            }
        }
        return 0;
    }

}
