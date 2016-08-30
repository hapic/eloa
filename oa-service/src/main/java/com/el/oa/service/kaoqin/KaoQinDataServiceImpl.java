package com.el.oa.service.kaoqin;

import com.el.oa.common.Constant;
import com.el.oa.common.utils.DateUtils;
import com.el.oa.domain.kaoqi.LastPoint;
import com.el.oa.domain.kaoqi.SignDayRecord;
import com.el.oa.domain.kaoqi.SignRecord;
import com.el.oa.mongo.dao.ILastPointDao;
import com.el.oa.mongo.dao.ISignDayRecordDao;
import com.el.oa.mongo.dao.ISignRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Criteria criteria= Criteria.where("date").is(sr.getDate());
        SignRecord srb= (SignRecord)signRecordDao.findOne(criteria,"signRecord_"+sr.getSignId());
        if(srb!=null){
            return;
        }
        signRecordDao.insert(sr,"signRecord_"+sr.getSignId());
    }

    @Override
    public void saveSignRecord(String userName, List<SignRecord> signRecords) {
        Criteria criteria= Criteria.where("signId").is(userName);
        signRecordDao.addRecord(criteria,signRecords,"signRecord_"+userName);
    }

    @Override
    public void saveSignDayRecord(SignDayRecord sdr) {
        Criteria criteria= Criteria.where("day").is(sdr.getDay());
        SignDayRecord srb= (SignDayRecord)signDayRecordDao.findOne(criteria,"signDayRecord_"+sdr.getSignId());
        if(srb!=null){
            return;
        }
        signDayRecordDao.insert(sdr,"signDayRecord_"+sdr.getSignId());
    }
    @Override
    public void saveSignDayRecord(String userName, List<SignDayRecord> signDayRecords) {
        Criteria criteria= Criteria.where("signId").is(userName);
        signDayRecordDao.addRecord(criteria,signDayRecords,"signRecord_"+userName);
    }

    @Override
    public List<SignRecord> loadSingRecordByTime(Integer userName, String startTime, String endTime) {
        Criteria criteria= Criteria.where("date").gte(startTime).lte(endTime);
        return signRecordDao.find(criteria, "signRecord_" + userName);
    }

    @Override
    public List<SignDayRecord> loadJiabanSignDayRecord(Integer userName, String startTime, String endTime) {
        Criteria criteria= Criteria.where("jiaban").gte(Constant.JBSCALE*60);
        Criteria day = Criteria.where("day").gte(startTime).lte(endTime).andOperator(criteria);
        return signDayRecordDao.find(day, "signDayRecord_" + userName);
    }

    @Override
    public String lastInpointDate(Integer userName) {
        Criteria criteria= Criteria.where("signId").is(userName);
        LastPoint point = lastPointDao.findOne(criteria, LastPoint.class.getSimpleName());
        if(point==null){
            return null;
        }
        return point.getPointDate();
    }

    @Override
    public void pointDate(Integer userName){
        Criteria criteria= Criteria.where("signId").is(userName);
        List<LastPoint> points=lastPointDao.find(criteria,LastPoint.class.getSimpleName());
        if(points!=null && !points.isEmpty()){
            int i = (int)(System.currentTimeMillis()/1000) ;
            String s = DateUtils.intToStringDate(i);

            Map map= new HashMap();

            map.put("pointDate",s);
            lastPointDao.update(criteria,map,LastPoint.class.getSimpleName());
            return;
        }

        LastPoint point= new LastPoint();
        point.setSignId(userName);
        int i = (int)(System.currentTimeMillis()/1000) ;
        String s = DateUtils.intToStringDate(i);
        point.setPointDate(s);
        lastPointDao.insert(point,LastPoint.class.getSimpleName());
    }
}
