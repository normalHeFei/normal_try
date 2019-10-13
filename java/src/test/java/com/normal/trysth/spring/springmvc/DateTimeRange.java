package com.normal.trysth.spring.springmvc;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by hefei on 2017/8/26.
 */
public class DateTimeRange {
    private LocalDateTime begin;
    private LocalDateTime end;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DateTimeRange() {
    }

    public DateTimeRange(LocalDateTime begin, LocalDateTime end) {
        this.begin = begin;
        this.end = end;
    }

    public LocalDateTime getBegin() {
        return begin.with(LocalTime.MIN);
    }

    public String getBeginStr() {
        return getBegin().format(formatter);
    }

    public LocalDateTime getEnd() {
        return end.with(LocalTime.MAX);
    }

    public String getEndStr() {
        return getEnd().format(formatter);
    }

    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }


}
