package com.lly.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具
 */
public class TimeUtil {

    private TimeUtil(){}

    /**
     * 获取东八时区当前时间戳
     * @return ZonedDateTime
     */
    public static ZonedDateTime getNow(){
        return Instant.now().atZone(ZoneOffset.ofHours(8));
    }

    /**
     * 格式化日期格式
     * @param dateTime 给定日期
     * @param patten 格式
     * @return 格式化结果
     */
    public static String format(LocalDateTime dateTime, String patten){
        return dateTime.format(DateTimeFormatter.ofPattern(patten));
    }

    /**
     * 使用 YYYY-MM-dd HH:mm:ss 格式化日期格式
     * @param dateTime 给定日期；
     * @return 格式化结果
     */
    public static String format(LocalDateTime dateTime){
        return format(dateTime,"YYYY-MM-dd HH:mm:ss");
    }
}
