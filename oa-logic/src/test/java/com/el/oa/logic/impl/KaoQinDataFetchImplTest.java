package com.el.oa.logic.impl;

import com.el.oa.BaseTest;
import com.el.oa.domain.kaoqi.SignDayRecord;
import com.el.oa.domain.kaoqi.SignRecord;
import com.el.oa.logic.IKaoQinDataFetch;
import com.el.oa.logic.util.SignRecordFactory;
import com.el.oa.mongo.dao.ISignRecordDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
 * @Date : 2016/7/25 20:36
 */
public class KaoQinDataFetchImplTest extends BaseTest{

    @Autowired
    private IKaoQinDataFetch kaoQinDataFetch;

    @Test
    public void testfetchAndSaveSignRecord(){
        kaoQinDataFetch.fetchAndSaveSignRecord(30045,"30045");
    }


    @Test
    public void testloadSingRecordByTime(){
        List<SignRecord> list = kaoQinDataFetch.loadSingRecordByTime(30001, "2016-07-06 07:09:06", "2016-07-06 21:12:09");
        System.out.println(list);
    }

    @Test
    public void testarrangeRecord(){
        List<SignRecord> list = kaoQinDataFetch.loadSingRecordByTime(30001, "2016-07-01 00:00:00", "2016-07-26 23:59:59");
        List<SignDayRecord> signDayRecords = SignRecordFactory.arrangeRecord(list);
        for(SignDayRecord sdr:signDayRecords){
            System.out.println(sdr);
        }

    }

    @Test
    public void testloadJiabanSignDayRecord(){
        List<SignDayRecord> list = kaoQinDataFetch.loadJiabanSignDayRecord(30045, "2016-07-01 00:00:00", "2016-07-26 23:59:59");
        System.out.println(list.size());
        for(SignDayRecord sdr:list){
            System.out.println(sdr);
        }
    }
}
