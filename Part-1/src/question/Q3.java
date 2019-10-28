package question;

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
        // using parallel to compare
        new ConsumeTimeFactory()
                .start()
                // sort
                .doSth(() -> Arrays.parallelSort(array_int_parallel))
                .end()
                .print();
    }

}
