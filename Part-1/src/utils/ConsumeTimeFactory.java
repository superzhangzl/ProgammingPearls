package utils;

/**
 * @author zzl
 */
public class ConsumeTimeFactory {
    private long startTime = 0L;
    private long endTime = 0L;
    private long consumeTime = 0L;

    public ConsumeTimeFactory start() {
        checkInit();
        this.startTime = System.currentTimeMillis();
        return this;
    }

    public ConsumeTimeFactory doSth(ExecuteFunctionCallable function) {
        try {
            checkStart();
            function.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return this;
    }

    public ConsumeTimeFactory end() {
        this.endTime = System.currentTimeMillis();
        this.consumeTime = endTime - startTime;
        this.startTime = 0;
        this.endTime = 0;
        return this;
    }

    private void checkInit() {
        if (startTime != 0 || endTime != 0) {
            System.err.println("do not have end method");
            System.exit(-1);
        }
    }

    private void checkEnd() {
        if (startTime != 0) {
            System.err.println("do not have end method");
            System.exit(-1);
        }
    }

    private void checkStart() {
        if (startTime == 0) {
            System.err.println("do not have start method");
            System.exit(-1);
        }
    }

    public void print() {
        checkEnd();
        double totalTime = consumeTime / 1000d;
        if (totalTime < 1) {
            System.out.println("consumed time is: " + consumeTime + "ms");
        } else {
            System.out.println("consumed time is: " + totalTime + "s");
        }
        consumeTime = 0L;
    }

}
