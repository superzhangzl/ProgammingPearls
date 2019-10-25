package utils;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Simple Test
 *
 * @author zzl
 */
public class ConsumeTimeFactoryTest {
    @Test
    public void testNoStart() {
        new ConsumeTimeFactory()
                .doSth(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("do sth1");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .end()
                .print();

    }

    @Test
    public void testNoEnd() {
        new ConsumeTimeFactory()
                .start()
                .doSth(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("do sth");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .print();

    }
}