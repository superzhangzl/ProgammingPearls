package question;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzl
 */
public class Q1 {
    private static final BigDecimal startRange = BigDecimal.valueOf(2200.0d);
    private static final BigDecimal minPercent = BigDecimal.valueOf(0.14d);
    private static final BigDecimal basePercent = BigDecimal.valueOf(0.01d);
    private static final BigDecimal maxPercent = BigDecimal.valueOf(0.180d);
    private static final BigDecimal div = BigDecimal.valueOf(500d);
    // 新建税率表，然后直接根据表二分查找带入公式计算即可
    private static List<TaxQueen> table;

    static {
        table = new ArrayList<>();
        TaxQueen min = new TaxQueen(startRange, BigDecimal.valueOf(0.0d), minPercent);
        table.add(min);
        TaxQueen current;
        int index = 1;
        for (BigDecimal i = minPercent.add(basePercent); i.compareTo(maxPercent) != 0; i = i.add(basePercent)) {
            BigDecimal currentBasePercent = BigDecimal.valueOf(div.doubleValue() * table.get(index - 1).currentPercent.doubleValue() + table.get(index - 1).baseTax.doubleValue());
            current = new TaxQueen(BigDecimal.valueOf(table.get(index - 1).baseRange.doubleValue() + div.doubleValue()), currentBasePercent, i);
            table.add(current);
            index++;
        }
    }

    public static void main(String[] args) {
        System.out.println(table);
    }


    static class TaxQueen {
        private BigDecimal baseRange;
        private BigDecimal baseTax;
        private BigDecimal currentPercent;

        public TaxQueen(BigDecimal baseRange, BigDecimal baseTax, BigDecimal currentPercent) {
            this.baseRange = baseRange;
            this.baseTax = baseTax;
            this.currentPercent = currentPercent;
        }

        @Override
        public String toString() {
            return "TaxQueen{" +
                    "baseRange=" + baseRange +
                    ", baseTax=" + baseTax +
                    ", currentPercent=" + currentPercent +
                    '}';
        }
    }
}
