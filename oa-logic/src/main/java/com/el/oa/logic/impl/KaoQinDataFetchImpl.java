package com.el.oa.logic.impl;

import com.el.oa.common.utils.DateUtils;
import com.el.oa.domain.kaoqi.KaoQinRecord;
import com.el.oa.domain.kaoqi.SignDayRecord;
import com.el.oa.domain.kaoqi.SignRecord;
import com.el.oa.fetch.model.KaoQinUrlModel;
import com.el.oa.fetch.util.SignRecordUtil;
import com.el.oa.logic.IKaoQinDataFetch;
import com.el.oa.logic.util.SignRecordFactory;
import com.el.oa.mongo.dao.ISignDayRecordDao;
import com.el.oa.mongo.dao.ISignRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
    private ISignRecordDao signRecordDao;

    @Autowired
    private ISignDayRecordDao signDayRecordDao;

    /**
     * 获取考勤记录并保存到mongo里面
     * @param userName
     * @param password
     */
    @Override
    public void fetchAndSaveSignRecord(Integer userName,String password){
        List<SignRecord> signRecordList = SignRecordUtil.fetchData(userName, password, new KaoQinUrlModel());
        for(SignRecord sr:signRecordList){
            signRecordDao.insert(sr,"signRecord_"+userName);
        }

        List<SignDayRecord> signDayRecords = SignRecordFactory.arrangeRecord(signRecordList);
        for(SignDayRecord sdr:signDayRecords){
            signDayRecordDao.insert(sdr,"signDayRecord_"+userName);
        }
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
        Criteria criteria= Criteria.where("date").gte(startTime).lte(endTime);
        return signRecordDao.find(criteria, "signRecord_" + userName);
    }

    /**
     * 获取加班的记录
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<SignDayRecord> loadJiabanSignDayRecord(Integer userName, String startTime, String endTime){
        Criteria criteria= Criteria.where("jiaban").gte(2*60);
        return signDayRecordDao.find(criteria, "signDayRecord_" + userName);
    }








}

