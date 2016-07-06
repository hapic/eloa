package com.el.oa.fetch.fetch;

import com.el.oa.common.utils.HTMLSpirit;
import com.el.oa.fetch.ConsolePipeline;
import com.el.oa.fetch.KaoQinRecordFetchPageProcessor;
import com.el.oa.fetch.UserInfoFetchPageProcessor;
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
    public Map analyze(String cookie) {

        Map map= new HashMap();
         String userInfoTargetUrl="http://124.65.191.70:10000/iclock/staff/";

        ConsolePipeline consolePipeline = new ConsolePipeline();

        Spider.create(new UserInfoFetchPageProcessor(cookie))
                .addUrl(userInfoTargetUrl)
                .addPipeline(consolePipeline)
                .thread(5)
                .run();
        Object uid = consolePipeline.getResult().get("uid");
        map.put("uid",uid);

        String dataTargetUrl="http://124.65.191.70:10000/iclock/staff/transaction/?p=1&t=staff_transaction.html&UserID__id__exact="+uid+"&fromTime=&toTime=";;
        Spider.create(new KaoQinRecordFetchPageProcessor(cookie))
                .addUrl(dataTargetUrl)
                .addPipeline(consolePipeline)
                //保存结果
                .thread(5)
                //启动爬虫
                .run();
        Map<String, Object> result = consolePipeline.getResult();
        String content = (String)result.get("content");
        String change = HTMLSpirit.delHTMLTag(content);
        map.put("data",change);
        return map;
    }
}
