package com.el.oa.controller;

import com.el.oa.controller.common.BaseController;
import com.el.oa.controller.common.Constant;
import com.el.oa.controller.model.RuleModel;
import com.el.oa.controller.model.User;
import com.el.oa.domain.kaoqi.KaoQinRecord;
import com.el.oa.fetch.ProwlerHelper;
import com.el.oa.fetch.model.KaoQinUrlModel;
import com.el.oa.logic.IKaoQinDataFetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    public String login(User user){
//        getSession().setAttribute("userName",model.getUserName());
        Map<String,String> params= new HashMap<String, String>();
        params.put("username",user.getUserName());
        params.put("password",user.getPassword());

        ProwlerHelper prowlerHelper= new ProwlerHelper(user.getURL(),params);
        String cookie = prowlerHelper.login().cookie();

        if(cookie!=null){
            getSession().setAttribute(Constant.USER,user);

            KaoQinUrlModel kaoQinRecord;
            if(null == user.getHost()){
                kaoQinRecord=new KaoQinUrlModel();
            }else
                kaoQinRecord=new KaoQinUrlModel(user.getHost());

            getSession().setAttribute(Constant.URLMODEL,kaoQinRecord);
            return "success";
        }
        return "pwdError";
    }

    @RequestMapping("fetch")
    public String fetchMyData(RuleModel model){
        Object attribute = getSession().getAttribute(Constant.USER);
        if(null == attribute){
            return "noLogin";
        }
        User user= (User)attribute;

        KaoQinUrlModel kaoQinUrlModel = (KaoQinUrlModel)getSession().getAttribute(Constant.URLMODEL);
        kaoQinDataFetch.fetchAndSaveSignRecord(Integer.parseInt(user.getUserName()),user.getPassword(),kaoQinUrlModel);



        return null;
    }




}
