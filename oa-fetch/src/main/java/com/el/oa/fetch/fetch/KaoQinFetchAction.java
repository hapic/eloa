package com.el.oa.fetch.fetch;

import com.el.oa.common.utils.HTMLSpirit;
import com.el.oa.domain.kaoqi.SignRecord;
import com.el.oa.fetch.ConsolePipeline;
import com.el.oa.fetch.KaoQinRecordFetchPageProcessor;
import com.el.oa.fetch.ProwlerHelper;
import com.el.oa.fetch.UserInfoFetchPageProcessor;
import com.el.oa.fetch.model.KaoQinUrlModel;
import us.codecraft.webmagic.Spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
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
 * @Date : 2016/7/6 20:08
 */
public class KaoQinFetchAction implements FetchAction {


    /**
     * 分两步抓取用户的记录
     * @param cookie
     * @return
     */
    @Override
    public Map analyze(String cookie, KaoQinUrlModel model) {

        Map map= new HashMap();

        ConsolePipeline consolePipeline = new ConsolePipeline();

        Spider.create(new UserInfoFetchPageProcessor(cookie))
                .addUrl(model.getUserInfoTargetUrl())
                .addPipeline(consolePipeline)
                .thread(5)
                .run();

        Object uid = consolePipeline.getResult().get("uid");
        map.put("uid",uid);


        //开始第一次抓取工作
        run(cookie,model,uid+"",1,consolePipeline);

        Map<String, Object> result = consolePipeline.getResult();

        String content = (String)result.get("content");
        String change = HTMLSpirit.delHTMLTag(content);
        StringBuffer sb= new StringBuffer(change);
        Object totalPage = result.get("totalPage");
        int num=1;
        if(null != totalPage && !totalPage.equals("") && Integer.parseInt(totalPage+"")>1){
            num=Integer.parseInt(totalPage+"");
        }
        for(int i=2;i<=num;i++){
            run(cookie,model,uid+"",num,consolePipeline);
            result=consolePipeline.getResult();
            content = (String)result.get("content");
            change = HTMLSpirit.delHTMLTag(content);
            sb.append(change);
        }
        map.put("data",sb.toString());
        return map;
    }


    private void run(String cookie,KaoQinUrlModel model,String uid,Integer page,ConsolePipeline consolePipeline){
        String url = model.getDataTargetUrl()
                .replaceAll("&UserID__id__exact=", "&UserID__id__exact=" + uid)
                .replaceAll("\\?p=", "?p=" + page);
        if(model.getStartTime()!=null){
            url= url.replaceAll("&fromTime=","&fromTime="+model.getStartTime().split(" ")[0]);
        }
        if(model.getEndTime()!=null){
            url= url.replaceAll("&toTime=","&toTime="+model.getEndTime().split(" ")[0]);
        }
        Spider.create(new KaoQinRecordFetchPageProcessor(cookie))
                .addUrl(url)
                .addPipeline(consolePipeline)
                //保存结果
                .thread(5)
                //启动爬虫
                .run();
    }




}
