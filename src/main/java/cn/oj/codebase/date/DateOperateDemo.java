package cn.oj.codebase.date;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * @program: codebase
 * @description: 日期操作Demo
 * @author: 郑剑锋
 * @create: 2021-04-16 17:22
 **/
@Slf4j
public class DateOperateDemo {

    public void getDate(){
        //当前时间
        Date date = DateUtil.date();
        //当前时间
        Date date2 = DateUtil.date(Calendar.getInstance());
        //当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        //当前日期字符串，格式：yyyy-MM-dd
        String today= DateUtil.today();
    }

    public void parseStrToDate(){
        log.info("字符串时间转日期");
        String dateStr = "2017-03-01";
        Date date = DateUtil.parse(dateStr);
        log.info("格式化");
        Date date1 = DateUtil.parse(dateStr, "yyyy-MM-dd");
    }

    public void parseDateToStr(){
        String dateStr = "2017-03-01";
        Date date = DateUtil.parse(dateStr);
        //结果 2017/03/01
        String format = DateUtil.format(date, "yyyy/MM/dd");
        //常用格式的格式化，结果：2017-03-01
        String formatDate = DateUtil.formatDate(date);
        //结果：2017-03-01 00:00:00
        String formatDateTime = DateUtil.formatDateTime(date);
        //结果：00:00:00
        String formatTime = DateUtil.formatTime(date);
    }

}
