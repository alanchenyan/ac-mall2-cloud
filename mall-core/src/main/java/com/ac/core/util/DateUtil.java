package com.ac.core.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class DateUtil {

    public static final String CRON_FORMAT = "ss mm HH dd MM ? yyyy";

    /**
     * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期偏移,根据field不同加不同值（偏移会修改传入的对象）
     *
     * @param time   {@link LocalDateTime}
     * @param number 偏移量，正数为向后偏移，负数为向前偏移
     * @param field  偏移单位，见{@link ChronoUnit}，不能为null
     * @return 偏移后的日期时间
     */
    public static LocalDateTime offset(LocalDateTime time, long number, TemporalUnit field) {
        if (null == time) {
            return null;
        }

        return time.plus(number, field);
    }

    /**
     * 转换 Date 为 cron , eg.  "0 07 10 15 1 ? 2016"
     *
     * @param date 时间点
     * @return cron 表达式
     */
    public static String getCron(Date date) {
        return format(date, CRON_FORMAT);
    }

    /**
     * 根据传入的格式格式化日期.默认格式为MM月dd日
     *
     * @param d 日期
     * @param f 格式
     * @return 格式化后的字符串
     */
    public static String format(Date d, String f) {
        Date date = d;
        String format = f;
        if (date == null) {
            date = new Date();
        }
        if (format == null) {
            format = NORM_DATETIME_PATTERN;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
}
