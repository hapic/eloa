package com.el.oa.logic.impl;

import com.el.oa.domain.kaoqi.SignRecord;
import com.el.oa.fetch.model.KaoQinUrlModel;
import com.el.oa.fetch.util.SignRecordUtil;
import com.el.oa.logic.IKaoQinDataFetch;
import com.el.oa.mongo.dao.ISignRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Date : 2016/7/25 20:26
 */
@Service
public class KaoQinDataFetchImpl implements IKaoQinDataFetch {

    @Autowired
    private ISignRecordDao signRecordDao;

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


    }

}
