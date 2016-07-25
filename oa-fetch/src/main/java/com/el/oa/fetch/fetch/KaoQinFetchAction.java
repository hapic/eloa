package com.el.oa.fetch.fetch;

import com.el.oa.common.utils.HTMLSpirit;
import com.el.oa.fetch.ConsolePipeline;
import com.el.oa.fetch.KaoQinRecordFetchPageProcessor;
import com.el.oa.fetch.UserInfoFetchPageProcessor;
import com.el.oa.fetch.model.KaoQinUrlModel;
import us.codecraft.webmagic.Spider;

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
//         String userInfoTargetUrl="http://124.65.191.70:10000/iclock/staff/";

        ConsolePipeline consolePipeline = new ConsolePipeline();

        Spider.create(new UserInfoFetchPageProcessor(cookie))
                .addUrl(model.getUserInfoTargetUrl())
                .addPipeline(consolePipeline)
                .thread(5)
                .run();

        Object uid = consolePipeline.getResult().get("uid");
        map.put("uid",uid);

//        String dataTargetUrl="http://124.65.191.70:10000/iclock/staff/transaction/?p=1&t=staff_transaction.html&UserID__id__exact="+uid+"&fromTime=&toTime=";;

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
        Spider.create(new KaoQinRecordFetchPageProcessor(cookie))
                .addUrl(model.getDataTargetUrl().replaceAll("#uid#",uid+"").replaceAll("#page#",page+""))
                .addPipeline(consolePipeline)
                //保存结果
                .thread(5)
                //启动爬虫
                .run();
    }
}
