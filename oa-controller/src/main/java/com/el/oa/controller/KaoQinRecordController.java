package com.el.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.el.oa.common.utils.DateUtils;
import com.el.oa.controller.common.BaseController;
import com.el.oa.controller.common.Constant;
import com.el.oa.controller.model.RuleModel;
import com.el.oa.controller.model.User;
import com.el.oa.fetch.ProwlerHelper;
import com.el.oa.fetch.model.KaoQinUrlModel;
import com.el.oa.logic.IKaoQinDataFetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @Date : 2016/7/26 19:15
 */
@RestController
@RequestMapping(value="/kq")
public class KaoQinRecordController extends BaseController{

    @Autowired
    private IKaoQinDataFetch kaoQinDataFetch;


    @RequestMapping("login")
    public JSONObject login(final User user){
//        getSession().setAttribute("userName",model.getUserName());
        Map<String,String> params= new HashMap<String, String>();
        params.put("username",user.getUserName());
        params.put("password",user.getPassword());

        ProwlerHelper prowlerHelper= new ProwlerHelper(user.getURL(),params);
        String cookie = prowlerHelper.login().cookie();

        if(cookie!=null){
            getSession().setAttribute(Constant.USER,user);

            final KaoQinUrlModel kaoQinRecord;
            if(null == user.getHost()){
                kaoQinRecord=new KaoQinUrlModel();
            }else
                kaoQinRecord=new KaoQinUrlModel(user.getHost());

            String s = kaoQinDataFetch.lastInpointDate(Integer.parseInt(user.getUserName()));
            if(s!=null){
                kaoQinRecord.setStartTime(s);
            }

            getSession().setAttribute(Constant.URLMODEL,kaoQinRecord);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    kaoQinDataFetch.fetchAndSaveSignRecord(Integer.parseInt(user.getUserName()),user.getPassword(),kaoQinRecord);
                }
            }).start();


            return buildResult("success");
        }
        return buildResult("pwdError") ;
    }

    @RequestMapping("fetch")
    public String fetchMyData(RuleModel model){
        Object attribute = getSession().getAttribute(Constant.USER);
        if(null == attribute){
            return "noLogin";
        }
        User user= (User)attribute;

        KaoQinUrlModel kaoQinUrlModel = (KaoQinUrlModel)getSession().getAttribute(Constant.URLMODEL);
        kaoQinUrlModel.setStartTime(model.getStartTime());
        kaoQinUrlModel.setEndTime(model.getEndTime());
        getSession().setAttribute(Constant.URLMODEL,kaoQinUrlModel);
        kaoQinDataFetch.fetchAndSaveSignRecord(Integer.parseInt(user.getUserName()),user.getPassword(),kaoQinUrlModel);

        return null;
    }

    @RequestMapping("jiaban")
    public JSONObject  jiaBan(){
        Object attribute = getSession().getAttribute(Constant.USER);
        User user= (User)attribute;
        String startDate=DateUtils.intToStringDateByDay(DateUtils.getMonthStartDate(DateUtils.getCurrentTime()));

        String endDate=DateUtils.intToStringDateByDay(DateUtils.getMonthEndDate(DateUtils.getCurrentTime()));

        List listResult=kaoQinDataFetch.loadJiabanSignDayRecord(Integer.parseInt(user.getUserName()),startDate ,endDate);

//        JSONObject resultJson=new JSONObject();
        return buildResult(listResult);

    }




}
