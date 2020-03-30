package team.gjz.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @description:
 * @author: jxk
 * @create: 2020-03-26 09:22
 **/
public class TimeTest {

    @Test
    public void test1() {
        LocalDate oneday = LocalDate.now();
        System.out.println("今天的日期：" + oneday);
        //取2016年10月的第1天
        LocalDate firstDay = oneday.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(firstDay);
        //取2016年10月的第1天，另外一种写法
        LocalDate firstDay2 = oneday.withDayOfMonth(1);
        System.out.println(firstDay2);
        //取2016年10月的最后1天，不用考虑大月，小月，平年，闰年
        LocalDate lastDay = oneday.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(lastDay);
        //当前日期＋1天
        LocalDate tomorrow = oneday.plusDays(1);
        System.out.println(tomorrow);
        //判断是否为闰年
        boolean isLeapYear = tomorrow.isLeapYear();
        System.out.println(isLeapYear);
    }

    /**
     * 判断生日
     */
    @Test
    public void test2() {
        LocalDate birthday = LocalDate.of(1990, 10, 12);
        MonthDay birthdayMd = MonthDay.of(birthday.getMonth(), birthday.getDayOfMonth());
        MonthDay today = MonthDay.from(LocalDate.of(2016, 10, 12));
        System.out.println(today.equals(birthdayMd));
    }

    @Test
    public void test3() {
        //获取当前的时间
        LocalTime nowTime = LocalTime.now(); //结果14:29:40.558

        //如果不想显示毫秒
        LocalTime nowTime2 = LocalTime.now().withNano(0); //14:43:14

        //指定时间
        LocalTime time = LocalTime.of(14, 10, 21); //14:10:21
        LocalTime time2 = LocalTime.parse("12:00:01"); // 12:00:01

        //当前时间增加2小时
        LocalTime nowTimePlus2Hour = nowTime.plusHours(2); //16:47:23.144
        //或者
        LocalTime nowTimePlus2Hour2 = nowTime.plus(2, ChronoUnit.HOURS);
    }


}
