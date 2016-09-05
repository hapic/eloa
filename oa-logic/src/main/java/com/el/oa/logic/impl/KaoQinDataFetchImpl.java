package com.el.oa.logic.impl;

import com.el.oa.domain.kaoqi.SignDayRecord;
import com.el.oa.domain.kaoqi.SignRecord;
import com.el.oa.fetch.model.KaoQinUrlModel;
import com.el.oa.fetch.util.SignRecordUtil;
import com.el.oa.service.kaoqin.IKaoQinDataService;
import com.el.oa.logic.IKaoQinDataFetch;
import com.el.oa.logic.util.SignRecordFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @Date : 2016/7/25 20:26
 */
@Service
public class KaoQinDataFetchImpl implements IKaoQinDataFetch {



    @Autowired
    private IKaoQinDataService kaoQinDataService;

    /**
     * 获取考勤记录并保存到mongo里面
     * @param userName
     * @param password
     */
    @Override
    public void fetchAndSaveSignRecord(Integer userName,String password){
       fetchAndSaveSignRecord(userName,password,new KaoQinUrlModel());
    }

    @Override
    public void fetchAndSaveSignRecord(Integer userName, String password, KaoQinUrlModel model){

        List<SignRecord> signRecordList = SignRecordUtil.fetchData(userName, password, model);


//        kaoQinDataService.saveSignRecord(userName+"",signRecordList);

        for(SignRecord sr:signRecordList){
//            signRecordDao.insert(sr,"signRecord_"+userName);
            kaoQinDataService.saveSignRecord(sr);
        }

        if(signRecordList!=null && !signRecordList.isEmpty()){
            List<SignDayRecord> signDayRecords = SignRecordFactory.arrangeRecord(signRecordList);
//            kaoQinDataService.saveSignDayRecord(userName+"",signDayRecords);
            for(SignDayRecord sdr:signDayRecords){
//            signDayRecordDao.insert(sdr,"signDayRecord_"+userName);
                kaoQinDataService.saveSignDayRecord(sdr);
            }

        }

        kaoQinDataService.pointDate(userName);
    }

    /**
     * 上次导入的时间
     * @param userName
     * @return
     */
    @Override
    public String lastInpointDate(Integer userName){
        return kaoQinDataService.lastInpointDate(userName);
    }

    /**
     * 按时间区间查找
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<SignRecord> loadSingRecordByTime(Integer userName, String startTime, String endTime){
        return kaoQinDataService.loadSingRecordByTime(userName,startTime,endTime);
    }

    /**
     * 查找加班记录
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<SignDayRecord> loadJiabanSignDayRecord(Integer userName, String startTime, String endTime){
        List<SignDayRecord> list= new ArrayList<SignDayRecord>();
        //加载周末加班记录
//        List<SignDayRecord> signDayRecords = kaoQinDataService.loadWeekSignDayRecord(userName, startTime, endTime);
//        list.addAll(signDayRecords);
        //加载平时加班记录
        List<SignDayRecord> signDayRecords1 = kaoQinDataService.loadJiabanSignDayRecord(userName, startTime, endTime);
        list.addAll(signDayRecords1);

        return list;
    }













}

