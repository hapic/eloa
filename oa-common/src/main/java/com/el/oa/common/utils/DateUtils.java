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

    public static int stringDateToInt2(String sdate) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
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

    /**
     * 将一个十位整形数字转换为日期格式 年-月-日
     *
     * @param date
     * @return
     */
    public static String intToStringDateByDay(int date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
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

    /**
     * 获取当天开始时间
     */
    public static Integer getCurrentdate(int date){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date * 1000L);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 根据给予的月份计算该月的开始时间
     */
    public static int getMonthStartDate(int date){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date * 1000L);
//        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH));
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return (int) (cal.getTimeInMillis() / 1000);
    }

    /**
     * 根据给予的时间计算该月的结束时间
     *
     * @param date
     * @return
     */
    public static Integer getMonthEndDate(Integer date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date * 1000L);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return (int) (cal.getTimeInMillis() / 1000)-1;
    }

    /**
     * 获取以毫秒为单位的当前时间。保留10位
     *
     * @return
     */
    public static int getCurrentTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }



    public static String getWeekOfDate(String day) {
        String[] weekDays = new String[7];
        int w = 0;
        try {

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sf.parse(day);

            weekDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weekDays[w];
    }

    /**
     * 判断是否为周末
     * @param day
     * @return
     */
    public static boolean isWeek(String day){
        return getWeekOfDate(day).contains("日") || getWeekOfDate(day).contains("六");
    }




    public static String yueRi(String day){
        return day.substring(day.indexOf("-")+1);
    }


    public static String da(int num){

        String[] str={"零","一","二","三","四","五","六","七","八","九","十"};

        if(num>str.length-1){
            return "";
        }
        return str[num];
    }


    public static void main(String[] args) {


        try {
            System.out.println(da(11));
            System.out.println(getWeekOfDate("2016-08-29"));
            System.out.println(getMonthEndDate(getCurrentTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
