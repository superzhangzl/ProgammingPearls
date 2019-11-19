package question;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzl
 */
public class Q1 {
    private static final BigDecimal startRange = BigDecimal.valueOf(2200.0d);
    private static final BigDecimal minPercent = BigDecimal.valueOf(0.14d);
    private static final BigDecimal basePercent = BigDecimal.valueOf(0.01d);
    private static final BigDecimal maxPercent = BigDecimal.valueOf(0.700d);
    private static final BigDecimal div = BigDecimal.valueOf(500d);
    // 新建税率表，然后直接根据表二分查找带入公式计算即可
    private static List<TaxQueen> table;

    static {
        table = new ArrayList<>();
        TaxQueen min = new TaxQueen(startRange, BigDecimal.valueOf(0.0d), minPercent);
        table.add(min);
        TaxQueen current;
        int index = 1;
        for (BigDecimal i = minPercent.add(basePercent); i.compareTo(maxPercent) <= 0; i = i.add(basePercent)) {
            BigDecimal currentBasePercent = BigDecimal.valueOf(div.doubleValue() * table.get(index - 1).currentPercent.doubleValue() + table.get(index - 1).baseTax.doubleValue());
            current = new TaxQueen(BigDecimal.valueOf(table.get(index - 1).baseRange.doubleValue() + div.doubleValue()), currentBasePercent, i);
            table.add(current);
            index++;
        }
    }

    public static void main(String[] args) {
        printTable(table);
        System.out.println(getTax(3500));
    }

    public static double getTax(double salary) {
        int index = -1;
        for (int i = 1; i < table.size(); i++) {
            TaxQueen lastLine = table.get(i - 1);
            TaxQueen currentLine = table.get(i);
            if (salary >= lastLine.baseRange.doubleValue() && salary < currentLine.baseRange.doubleValue()) {
                index = i - 1;
                break;
            }
        }
        System.out.println(index);
        TaxQueen line = table.get(index);
        System.out.println(line);
        BigDecimal v = line.currentPercent.multiply((BigDecimal.valueOf(salary).subtract(line.baseRange)));
        BigDecimal result = line.baseTax.add(v);
        return result.doubleValue();

    }

    public static void printTable(List<TaxQueen> table) {
        table.forEach(tax -> {
            String line = String.format("|%10.2f|%10.2f|%10.2f|", tax.baseRange.floatValue(), tax.baseTax.floatValue(), tax.currentPercent.floatValue());
            System.out.println(line);
        });
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
