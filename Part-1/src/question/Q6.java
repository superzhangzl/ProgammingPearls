package question;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zzl
 */
public class Q6 {
    public static class BitSortNotOnlyOne {

        public static final int TIMES = 10;// 每个数字出现最多的次数
        public static final int SITE_SIZE;// 占用的位数
        public static final int BIT_SIZE = 32;
        public static final int SHIFT;
        public static final int MASK = 0x1F;
        public static final int N = 1000_0000;
        public static final int[] arr;

        static {
            if (TIMES <= 0 || TIMES >= BIT_SIZE) {
                System.exit(-9);
            }
            int i = 0;
            while (true) {
                double pow = Math.pow(2, i);
                double nextPow = Math.pow(2, i + 1);
                // 需要pow -1，0次代表没有出现，所以最多只能表示一个数出现2^(i+1)-1次
                if ((pow - 1) < TIMES && (nextPow - 1) >= TIMES) {
                    SITE_SIZE = i + 1;
                    break;
                } else if (pow == TIMES) {
                    SITE_SIZE = i;
                    break;
                }
                i++;
            }
            // todo 假设长度n为1,2,4,8 等满足2^n方的数字，且小于32。即重复数字低于2^32个，多了也没必要
            arr = new int[1 + N / (BIT_SIZE / SITE_SIZE)];
            SHIFT = BIT_SIZE / SITE_SIZE;
        }

        public static void set(int i) {
            System.out.println("input : " + i);
            // 获取需要偏移的位置
            int suffix = (i & MASK) * SITE_SIZE;
            System.out.println("suffix : " + suffix);
            // 拿到长度为 n 的长度的字段，然后取出这一段长度所代表的数字，然后+1，再还原回去
            // 截取指定字段的长度
            System.out.println("begin : " + arr[i / SHIFT]);
            int count = ((arr[i / SHIFT] >> suffix) & 0x0000000f);
            System.out.println("count : " + count);
            count++;
            if (count > TIMES) {
                System.err.println("the dup num is more than : " + TIMES);
                System.exit(TIMES);
            }
            // 先归0，然后再设值，不然直接进行或操作会导致计算数量错误
            int zeroIndex = ~(0x0000000f << suffix);
            System.out.println(String.format("%08x", zeroIndex));
            arr[i / SHIFT] &= (zeroIndex);
            // 将值再设置到对应位置上
            arr[i / SHIFT] |= (count << suffix);
            System.out.println("result : " + arr[i / SHIFT]);
            System.out.println("====================");
        }

        public static void clear(int i) {
            System.out.println("input : " + i);
            // 获取需要偏移的位置
            int suffix = (i & MASK) * SITE_SIZE;
            System.out.println("suffix : " + suffix);
            // 拿到长度为 n 的长度的字段，然后取出这一段长度所代表的数字，然后+1，再还原回去
            // 截取指定字段的长度
            System.out.println("begin : " + arr[i / SHIFT]);
            int count = ((arr[i / SHIFT] >> suffix) & 0x0000000f);
            System.out.println("count : " + count);
            count--;
            if (count > TIMES) {
                System.err.println("the dup num is more than : " + TIMES);
                System.exit(TIMES);
            }
            // 先归0，然后再设值，不然直接进行或操作会导致计算数量错误
            int zeroIndex = ~(0x0000000f << suffix);
            System.out.println(String.format("%08x", zeroIndex));
            arr[i / SHIFT] &= (zeroIndex);
            // 将值再设置到对应位置上
            arr[i / SHIFT] |= (count << suffix);
            System.out.println("result : " + arr[i / SHIFT]);
            System.out.println("====================");
        }

        public static boolean test(int i) {
            // 获取需要偏移的位置
            int suffix = (i & MASK) * SITE_SIZE;
            // 拿到长度为 n 的长度的字段，然后取出这一段长度所代表的数字，然后+1，再还原回去
            // 截取指定字段的长度
            int count = ((arr[i / SHIFT] >> suffix) & 0x0000000f);
            return count > 0;
        }

    }

    public static void main(String[] args) {
        //todo 新增生成随机个数及长度的序列 工具类
        List<Integer> collect = IntStream.of(3, 3, 1, 3, 0, 0, 1, 0, 0, 1).boxed().collect(Collectors.toList());
        Collections.shuffle(collect);
        collect.forEach(Q6.BitSortNotOnlyOne::set);
        System.out.println("~~~~~~~~~~~~~~~~");
        System.out.println(Q6.BitSortNotOnlyOne.test(2));
        for (int i = 0; i < collect.size(); i++) {
            Q6.BitSortNotOnlyOne.clear(collect.get(i));
        }
        System.out.println(Q6.BitSortNotOnlyOne.test(1));
        System.out.println(Q6.BitSortNotOnlyOne.test(0));
        System.out.println(Q6.BitSortNotOnlyOne.test(3));
    }
}
