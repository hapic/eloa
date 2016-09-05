package com.el.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.el.oa.common.utils.DateUtils;
import com.el.oa.controller.common.BaseController;
import com.el.oa.controller.common.Constant;
import com.el.oa.controller.model.RuleModel;
import com.el.oa.controller.model.User;
import com.el.oa.domain.kaoqi.SignDayRecord;
import com.el.oa.fetch.ProwlerHelper;
import com.el.oa.fetch.model.KaoQinUrlModel;
import com.el.oa.logic.IKaoQinDataFetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
    public ModelAndView login(final User user,HttpServletResponse response){
//        getSession().setAttribute("userName",model.getUserName());
        Map<String,String> params= new HashMap<String, String>();
        params.put("username",user.getUserName());
        params.put("password",user.getPassword());

        ProwlerHelper prowlerHelper= new ProwlerHelper(user.getURL(),params);
        String cookie = prowlerHelper.login().cookie();
        Map<String,String> map= new HashMap<String, String>();
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
            /*new Thread(new Runnable() {
                @Override
                public void run() {

                    kaoQinDataFetch.fetchAndSaveSignRecord(Integer.parseInt(user.getUserName()),user.getPassword(),kaoQinRecord);
                }
            }).start();*/


//            return buildResult("success");
            map.put("tip","登陆成功");

            return new ModelAndView("home/index");
        }
//        return buildResult("pwdError") ;

        map.put("userName",user.getUserName());
        map.put("tip","密码错误");
        return new ModelAndView("login/index","data",map);
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


    @RequestMapping("jiaban2")
    public void  jiaBan2(String time,HttpServletResponse response){
        Object attribute = getSession().getAttribute(Constant.USER);
        User user= (User)attribute;
        Integer tim= null;
        try {
            tim = DateUtils.stringDateToInt2(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String startDate=DateUtils.intToStringDateByDay(DateUtils.getMonthStartDate(tim));

        String endDate=DateUtils.intToStringDateByDay(DateUtils.getMonthEndDate(tim));

        List<SignDayRecord> listResult=kaoQinDataFetch.loadJiabanSignDayRecord(Integer.parseInt(user.getUserName()),startDate ,endDate);
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(SignDayRecord sdr:listResult){
                out.print(sdr.getDay()+"  "+sdr.getOffDuty()+"  "+sdr.getJiaban()+"<br/>");

        }
//        JSONObject resultJson=new JSONObject();
//        return buildResult(listResult);

    }





}
