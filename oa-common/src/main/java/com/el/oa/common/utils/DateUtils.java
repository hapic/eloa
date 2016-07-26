package com.el.oa.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 *
 * @User : Hapic
 * @Date : 2016/7/25 21:58
 */
public class DateUtils {


    /**
     * 将一个字符串型日期数据（年-月-日 时：分：秒 ）转化为十位整数值（秒数）
     *
     * @param sdate
     * @return
     * @throws ParseException
     */
    public static int stringDateToInt(String sdate) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sf.parse(sdate);
        return (int) (date.getTime() / 1000);
    }

    public static int stringDayToInt(String sdate) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sf.parse(sdate);
        return (int) (date.getTime() / 1000);
    }

    /**
     * 将一个十位整形数字转换为日期格式 年-月-日 时：分：秒
     *
     * @param date
     * @return
     */
    public static String intToStringDate(int date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date * 1000L);
        return sf.format(calendar.getTime());
    }

    public static int jiaban(String time){
        try {
            String[] split = time.split(" ");
            int i = stringDateToInt(time);
            int i1 = stringDateToInt(split[0] + " " + "19:00:00");
            //加班到9点才开始计时
            return  i-i1>0?(i-i1)/60:0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int chidao(String time){
        try {
            String[] split = time.split(" ");
            int i = stringDateToInt(time);
            int i1 = stringDateToInt(split[0] + " " + "09:00:00");
            return  i-i1>0?(i-i1)/60:0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {


        try {
            System.out.println(chidao("2016-07-06 09:09:06"));
            System.out.println(jiaban("2016-07-06 22:12:09"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
