package com.el.oa.service.kaoqin;

import com.el.oa.common.utils.DateUtils;
import com.el.oa.domain.kaoqi.LastPoint;
import com.el.oa.domain.kaoqi.SignDayRecord;
import com.el.oa.domain.kaoqi.SignRecord;
import com.el.oa.iservice.kaoqin.IKaoQinDataService;
import com.el.oa.mongo.dao.ILastPointDao;
import com.el.oa.mongo.dao.ISignDayRecordDao;
import com.el.oa.mongo.dao.ISignRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
 * @Date : 2016/7/28 20:40
 */
@Service
public class KaoQinDataServiceImpl implements IKaoQinDataService {

    @Autowired
    private ISignRecordDao signRecordDao;

    @Autowired
    private ISignDayRecordDao signDayRecordDao;

    @Autowired
    private ILastPointDao<LastPoint> lastPointDao;

    @Override
    public void saveSignRecord(SignRecord sr) {
        signRecordDao.insert(sr,"signRecord_"+sr.getSignId());
    }

    @Override
    public void saveSignDayRecord(SignDayRecord sdr) {
        signDayRecordDao.insert(sdr,"signDayRecord_"+sdr.getSignId());
    }

    @Override
    public List<SignRecord> loadSingRecordByTime(Integer userName, String startTime, String endTime) {
        Criteria criteria= Criteria.where("date").gte(startTime).lte(endTime);
        return signRecordDao.find(criteria, "signRecord_" + userName);
    }

    @Override
    public List<SignDayRecord> loadJiabanSignDayRecord(Integer userName, String startTime, String endTime) {
        Criteria criteria= Criteria.where("jiaban").gte(2*60);
        Criteria day = Criteria.where("day").gte(startTime).lte(endTime).andOperator(criteria);
        return signDayRecordDao.find(day, "signDayRecord_" + userName);
    }

    @Override
    public String lastInpointDate(Integer userName) {
        Criteria criteria= Criteria.where("signId").is(userName);
        LastPoint point = lastPointDao.findOne(criteria, LastPoint.class.getSimpleName());
        return point.getPointDate();
    }

    @Override
    public void pointDate(Integer userName){
        LastPoint point= new LastPoint();
        point.setSignId(userName);
        int i = ((int) System.currentTimeMillis()) / 1000;
        DateUtils.intToStringDate(i);
        lastPointDao.insert(point,LastPoint.class.getSimpleName());
    }
}
