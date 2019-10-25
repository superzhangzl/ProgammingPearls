package question;

import utils.ConsumeTimeFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zzl
 */
public class Q1 {
    public static void main(String[] args) {
        int size = 1000_0000;
        List<Integer> initList = IntStream
                .range(0, size)
                .boxed()
                .collect(Collectors.toList());
        Integer[] arrayInteger = new Integer[size];
        Integer[] arrayIntegerParallel = new Integer[size];
        int[] array_int = new int[size];
        int[] array_int_parallel = new int[size];
        new ConsumeTimeFactory()
                // shuffle list
                .before(() -> Collections.shuffle(initList))
                .before(() -> initList.toArray(arrayInteger))
                .before(() -> initList.toArray(arrayIntegerParallel))
                .before(() -> {
                    for (int i = 0; i < size; i++) {
                        array_int[i] = initList.get(i);
                        array_int_parallel[i] = initList.get(i);
                    }
                })
                .start()
                // sort
                .doSth(() -> Collections.sort(initList))
                .end()
                .print();
        // tim Sort, boxed
        new ConsumeTimeFactory()
                .start()
                // sort
                .doSth(() -> Arrays.sort(arrayInteger))
                .end()
                .print();
        new ConsumeTimeFactory()
                .start()
                // sort
                .doSth(() -> Arrays.parallelSort(arrayIntegerParallel))
                .end()
                .print();

        // quick sort, no boxed
        new ConsumeTimeFactory()
                .start()
                // sort
                .doSth(() -> Arrays.sort(array_int))
                .end()
                .print();

        // parallel
        new ConsumeTimeFactory()
                .start()
                // sort
                .doSth(() -> Arrays.parallelSort(array_int_parallel))
                .end()
                .print();

    }
}
