package com.el.oa.logic.util;

import com.el.oa.common.utils.DateUtils;
import com.el.oa.domain.kaoqi.SignDayRecord;
import com.el.oa.domain.kaoqi.SignRecord;
import com.el.oa.logic.comparator.SignDayRecordComparator;
import com.el.oa.logic.comparator.StrindDateComparator;

import java.util.*;

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
 * @Date : 2016/7/26 20:00
 */
public class SignRecordFactory {

    /**
     * 整理打卡记录
     * @param signRecords
     * @return
     */
    public static List<SignDayRecord> arrangeRecord(List<SignRecord> signRecords){

        List<SignDayRecord> signDayRecordList= new ArrayList<SignDayRecord>();

        Map<String,List<String>> map= new HashMap<String, List<String>>();

        for(SignRecord sr:signRecords){
            String dateStr = sr.getDate();
            String[] split = dateStr.split(" ");
            String day=split[0];
            List<String> strings = map.get(day);
            if(strings==null){
                strings=new ArrayList<String>();
            }
            strings.add(dateStr);
            map.put(day,strings);
        }
        Set<String> strings = map.keySet();
        Iterator<String> iterator = strings.iterator();
        Integer signId=signRecords.get(0).getSignId();//打卡id
        Integer uid=signRecords.get(0).getUid();//用户id
        while(iterator.hasNext()){
            String day = iterator.next();
            List<String> signTimeList = map.get(day);

            Collections.sort(signTimeList, new StrindDateComparator());
            String on = signTimeList.get(0);//上班时间
            String off = signTimeList.get(signTimeList.size()-1);//上班时间
            SignDayRecord sdr= new SignDayRecord(signId,uid,day,on,off);
            sdr.setJiaban(DateUtils.jiaban(off));//加班时间
            sdr.setChidao(DateUtils.chidao(on));//迟到时间
            signDayRecordList.add(sdr);
        }

        Collections.sort(signDayRecordList, new SignDayRecordComparator());
        return signDayRecordList;
    }

}
