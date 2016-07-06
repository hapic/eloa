package com.el.oa.fetch;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
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
public class KaoQinRecordFetchPageProcessor implements PageProcessor {


    private String cookie;

    public KaoQinRecordFetchPageProcessor(String cookie) {
        this.cookie = cookie;
    }

    //
//            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");
//            .addCookie("sessionid", "2e901ca556247daf56db7770f896d81fb");

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
//        page.putField("author", page.getUrl().regex("http://my\\.oschina\\.net/flashsword/blog/\\d+").toString());
//        page.putField("title", page.getHtml().xpath("//div[@class='BlogEntity']/div[@class='BlogTitle']/h1").toString());
        page.putField("content", page.getHtml().xpath("//table[@id]/tbody").toString());
//        page.putField("tags", page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
    }

    @Override
    public Site getSite() {
        //部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
        return Site.me().setRetryTimes(3).setSleepTime(1000)
//                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0")
                .addHeader("Cookie",cookie);
    }

   /* public static void main(String[] args) {

        String targetUrl="http://124.65.191.70:10000/iclock/staff/transaction/?p=1&t=staff_transaction.html&UserID__id__exact=2304&fromTime=&toTime=";
        Spider.create(new KaoQinRecordFetchPageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl(targetUrl)
                //保存结果
                //开启5个线程抓取
                .thread(5)

                //启动爬虫
                .run();
    }*/

}
