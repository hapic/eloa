package com.el.oa.fetch;

import com.el.oa.common.utils.HTMLSpirit;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

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
 * @Date : 2016/7/5 19:31
 */
public class UserInfoFetchPageProcessor implements PageProcessor {


    private String cookie;

    public UserInfoFetchPageProcessor(String cookie) {
        this.cookie = cookie;
    }


    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        String s = page.getHtml()
                .regex("uid=\"\\d*\"")
                .replace("\"","")
                .toString().split("=")[1];
        String nameStr = page.getHtml().regex("<strong>员工 [\\u4e00-\\u9fa5]*</strong>").replace("员工 ", "").toString();
        String realName= HTMLSpirit.delHTMLTag(nameStr);

        page.putField("uid", Integer.parseInt(s));
        page.putField("realName", realName);
    }

    @Override
    public Site getSite() {
        //部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
        return Site.me().setRetryTimes(3).setSleepTime(1000)
//                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0")
                .addHeader("Cookie",cookie);
    }


}
