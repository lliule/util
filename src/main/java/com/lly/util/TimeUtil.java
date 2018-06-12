package com.lly.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具
 */
public class TimeUtil {

    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<>();
    static {
        simpleDateFormatThreadLocal.set(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"));
    }
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

    /**
     * 使用 YYYY-MM-dd HH:mm:ss 格式化日期格式
     * @param date
     * @return
     */
    public static String format(Date date){
        return getFormat(date,null);
    }


    /**
     * 使用 format 格式化日期格式
     * @param date  时间
     * @param format  格式化
     * @return
     */
    public static String format(Date date,String format){
        return getFormat(date,format);
    }


    private static String getFormat(Date date,String format){
        if(StringUtils.isEmpty(format)){
            format = "YYYY-MM-dd HH:mm:ss";
        }
        /**
         * 因为 simpleDateFormatThreadLocal 的 simpleDateFormat 是在
         *  static代码块中初始化的，那么在多线程中，只有第一次初始化的线程拥有
         *  simpleDateFormat,其他线程是取不到值的。
         */
        SimpleDateFormat simpleDateFormat = simpleDateFormatThreadLocal.get();
        if(simpleDateFormat == null){
            System.out.println(Thread.currentThread().getName() + " --- init");
            simpleDateFormat = new SimpleDateFormat(format);
            simpleDateFormatThreadLocal.set(simpleDateFormat);
            return simpleDateFormat.format(date);
        }else{
            return simpleDateFormat.format(date);
        }
    }
}
