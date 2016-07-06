package com.el.oa.fetch;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Spider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

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
 * @Date : 2016/7/5 20:23
 */
public class LoginAction {
    private String loginUrl;
    private Map<String,String> params;

    private HttpPost post;
    private HttpClient client=null;
    private boolean islogin=false;
    public LoginAction(String loginUrl, Map<String, String> params) {
        this.loginUrl = loginUrl;
        this.params = params;
        post=new HttpPost(loginUrl);
        client= new DefaultHttpClient();
    }




    private List<NameValuePair> parms = new ArrayList<NameValuePair>();
    Map<String,String> headerMap=new HashMap<String, String>();


    private void haveLogin(){
        if(!islogin){
            throw new RuntimeException("no login!");
        }
    }


    public String cookie(){
        haveLogin();
        String s = headerMap.get("Set-Cookie");
        String[] split = s.split(";");
        return split[0];
    }

    public LoginAction login(){
        tianchong();

        UrlEncodedFormEntity entity;

        try {
            entity = new UrlEncodedFormEntity(parms, "UTF-8");
            post.setEntity(entity);

            HttpResponse response;
            response = client.execute(post);
            HttpEntity entity2 = response.getEntity();
            if (entity != null) {

                Header[] allHeaders =
                        response.getAllHeaders();
                for(Header header:allHeaders){
                    System.out.println(header);
                    headerMap.put(header.getName(),header.getValue());
                }
                islogin=true;

            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            client.getConnectionManager().shutdown();
        }
        return this;

    }

    private void tianchong(){
        Set<String> strings = params.keySet();
        for(String key:strings){
            parms.add(new BasicNameValuePair(key, params.get(key)));

        }
    }


    public static void main(String[] args) {
        String URL = "http://124.65.191.70:10000/iclock/accounts/login/";
        Map<String,String> params= new HashMap<String, String>();
        params.put("username","40052");
        params.put("password","188010");
        LoginAction loginAction= new LoginAction(URL,params);
        String cookie = loginAction.login().cookie();
        System.out.println(cookie);


        ConsolePipeline consolePipeline = new ConsolePipeline();
        String targetUrl="http://124.65.191.70:10000/iclock/staff/transaction/?p=1&t=staff_transaction.html&UserID__id__exact=2673&fromTime=&toTime=";
        Spider.create(new KaoQinRecordFetchPageProcessor(cookie))
                //从"https://github.com/code4craft"开始抓
                .addUrl(targetUrl)
                .addPipeline(consolePipeline)
                //保存结果
                //开启5个线程抓取
                .thread(5)

                //启动爬虫
                .run();
        System.out.println(consolePipeline.getResult());

        targetUrl="http://124.65.191.70:10000/iclock/staff/";
        Spider.create(new UserInfoFetchPageProcessor(cookie))
                //从"https://github.com/code4craft"开始抓
                .addUrl(targetUrl)
                .addPipeline(consolePipeline)
                //保存结果
                //开启5个线程抓取
                .thread(5)

                //启动爬虫
                .run();
        System.out.println(consolePipeline.getResult());


    }
}
