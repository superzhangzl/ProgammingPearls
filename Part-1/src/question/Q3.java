package question;

import org.junit.Test;
import utils.ConsumeTimeFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Q3 {
    public static void main(String[] args) {
        int size = 1000_0000;
        List<Integer> initList = IntStream
                .range(0, size)
                .boxed()
                .collect(Collectors.toList());
        int[] array_int = new int[size];
        int[] array_int_parallel = new int[size];
        int[] array_int_bit_sort = new int[size];
        new ConsumeTimeFactory()
                // shuffle list
                .before(() -> Collections.shuffle(initList))
                .before(() -> {
                    for (int i = 0; i < size; i++) {
                        array_int[i] = initList.get(i);
                        array_int_parallel[i] = initList.get(i);
                        array_int_bit_sort[i] = initList.get(i);
                    }
                })
                .start()
                .end();
        //todo 通过测试发现，在main函数仅有并行排序和位排序的情况下，先进行并行排序的时间性能反而极差，先进行位排序再进行并行排序时，并行排序时间正常。why？

        // using parallel to compare
        new ConsumeTimeFactory()
                .start()
                // sort
                .doSth(() -> Arrays.parallelSort(array_int_parallel))
                .end()
                .print();
        // bit sort
        new ConsumeTimeFactory()
                .start()
                // sort
                .doSth(() -> {
                    for (int i = 0; i < array_int_bit_sort.length; i++) {
                        Q2.BitSort.set(i);
                    }
                })
                .end()
                .print();
        // using sort to compare
        new ConsumeTimeFactory()
                .start()
                // sort
                .doSth(() -> Arrays.sort(array_int))
                .end()
                .print();
    }

    @Test
    public void bad_sort_order() {
        int size = 1000_0000;
        List<Integer> initList = IntStream
                .range(0, size)
                .boxed()
                .collect(Collectors.toList());
        int[] array_int_parallel = new int[size];
        int[] array_int_bit_sort = new int[size];
        new ConsumeTimeFactory()
                // shuffle list
                .before(() -> Collections.shuffle(initList))
                .before(() -> {
                    for (int i = 0; i < size; i++) {
                        array_int_parallel[i] = initList.get(i);
                        array_int_bit_sort[i] = initList.get(i);
                    }
                })
                .start()
                .end();
        //todo 通过测试发现，在仅有并行排序和位排序的情况下，先进行并行排序的时间性能反而极差，先进行位排序再进行并行排序时，并行排序时间正常。why？

        // using parallel to compare
        new ConsumeTimeFactory()
                .start()
                // sort
                .doSth(() -> Arrays.parallelSort(array_int_parallel))
                .end()
                .print();
        // bit sort
        new ConsumeTimeFactory()
                .start()
                // sort
                .doSth(() -> {
                    for (int i = 0; i < array_int_bit_sort.length; i++) {
                        Q2.BitSort.set(i);
                    }
                })
                .end()
                .print();
    }

}
