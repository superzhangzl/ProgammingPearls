package question;

import utils.ConsumeTimeFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zzl
 */
public class Q1 {
    public static void main(String[] args) {
        List<Integer> initList = IntStream
                .range(1, 1000_0000)
                .boxed()
                .collect(Collectors.toList());
        // shuffle list
        Collections.shuffle(initList);

        new ConsumeTimeFactory()
                .start()
                .doSth(() -> {
                    Collections.sort(initList);
                })
                .end()
                .print();

    }
}
