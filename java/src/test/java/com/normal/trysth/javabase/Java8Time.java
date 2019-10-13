package com.normal.trysth.javabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

/**
 * Created by hefei on 2017/4/11.
 */
public class Java8Time {
    public static void main(String[] args) {
        //localTime： 值对象，和String 一样，不应该以引用相同作为相等的依据
        //日期：是某个特定的日期，不包含时分秒
        LocalDate nowDate = LocalDate.now();
        System.out.println(format("nowDate:%s", nowDate));
        //时间：和具体的时区没有关系，不包含年月日
        LocalTime nowTime = LocalTime.now();
        System.out.println(format("nowTime:%s", nowTime));
        //日期和时间
        System.out.println(LocalDateTime.now());

        //格式化
        String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(nowStr);

        //设置日期到固定的时间点，with 方法
        nowDate.with(LocalDate.MIN);
    }

}
