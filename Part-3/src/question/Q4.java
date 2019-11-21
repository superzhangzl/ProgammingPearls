package question;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * @author zzl
 */
public class Q4 {


    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2017, 12, 16);
        LocalDate today = LocalDate.now();

        System.out.println(getBetweenDays(date, today));
        System.out.println(getDayOfWeek(today));
        printMonthMap();
    }

    public static long getBetweenDays(LocalDate earlyDate, LocalDate lateDate) {
//        Period between = Period.between(earlyDate, lateDate);
        long bwDays = ChronoUnit.DAYS.between(earlyDate, lateDate);
        return bwDays;
    }

    public static int getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }

    /**
     * (@link https://blog.csdn.net/qq_32409957/article/details/89190255)
     */
    public static void printMonthMap() {
        //获得当前系统时间
        LocalDate now = LocalDate.now();
        String separator = "\t";
        String lineSeparator = "\n";
        StringBuilder builder = new StringBuilder();
        builder.append("当前时间是")
                .append(now.getYear()).append("年")
                .append(now.getMonthValue()).append("月")
                .append(now.getDayOfMonth()).append("日")
                .append(lineSeparator);
        builder.append("一").append(separator)
                .append("二").append(separator)
                .append("三").append(separator)
                .append("四").append(separator)
                .append("五").append(separator)
                .append("六").append(separator)
                .append("七").append(lineSeparator);
        //一个月开始从1号开始，到最后一天结束
        for (int j = 1; j <= now.lengthOfMonth(); j++) {
            LocalDate localDate = now.withDayOfMonth(j);
            //如果为一个月的第一天，需要计算是否需要空行
            if (j == 1) {
                int value = localDate.getDayOfWeek().getValue();
                for (int i = 1; i < value; i++) {
                    builder.append("  ").append(separator);
                }
            }
            //将其他的日期按照格式写入
            builder.append(localDate.get(ChronoField.DAY_OF_MONTH)).append(separator);
            //判断当天是否是星期天，星期天则需要换行
            if (localDate.getDayOfWeek().getValue() == 7) {
                builder.append(lineSeparator);
            }
        }
        System.out.println(builder.toString());
    }
}
