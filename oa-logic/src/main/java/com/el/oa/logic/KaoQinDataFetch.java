package com.el.oa.logic;

import com.el.oa.domain.kaoqi.SignRecord;
import com.el.oa.fetch.ProwlerHelper;
import com.el.oa.fetch.fetch.KaoQinFetchAction;

import java.io.*;
import java.util.ArrayList;
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
 * @Date : 2016/7/6 20:48
 * 数据处理业务
 */
public class KaoQinDataFetch {


    public List<SignRecord> fetchData(Integer signId,String password){
        String URL = "http://124.65.191.70:10000/iclock/accounts/login/";
        Map<String,String> params= new HashMap<String, String>();
        params.put("username",signId+"");
        params.put("password",password);


        ProwlerHelper prowlerHelper= new ProwlerHelper(URL,params);
        String cookie = prowlerHelper.login().cookie();

        Map fetchResult = prowlerHelper.fetch(cookie, new KaoQinFetchAction());
        Integer uid = (Integer)fetchResult.get("uid");
        String data = (String)fetchResult.get("data");

        Reader reader = new StringReader(data);
        BufferedReader br = new BufferedReader(reader);
        String strLine;
        List<SignRecord> list= new ArrayList<SignRecord>();
        try {
        SignRecord signRecord=null;
        int i=0;
        while((strLine = br.readLine()) != null){

            String trim = strLine.trim();
            if(!"".equals(trim) && !"&nbsp;".equals(trim)){
//                System.out.println(trim);
                if(i==0){
                    signRecord=new SignRecord();
                    signRecord.setUid(uid);
                    signRecord.setSignId(30005);
                    signRecord.setDate(trim);
                }else if(i==1){
                    signRecord.setTarget(trim);
                }else if(i==2){
                    signRecord.setType(trim);
                }else if(i==3){
                    signRecord.setMachineNum(trim);
                }
                i++;
            }
            if(i>3){
                i=0;
                list.add(signRecord);
            }

        }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;

    }


    public static void main(String[] args) {

        KaoQinDataFetch logic= new KaoQinDataFetch();
        List<SignRecord> signRecords = logic.fetchData(40052,"188010");
        for(SignRecord sr:signRecords){
            System.out.println("sr = " + sr);

        }
        System.out.println(signRecords.size());
    }


}
