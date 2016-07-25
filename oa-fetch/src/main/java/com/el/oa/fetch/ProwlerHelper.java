package com.el.oa.fetch;

import com.el.oa.fetch.fetch.FetchAction;
import com.el.oa.fetch.fetch.KaoQinFetchAction;
import com.el.oa.fetch.model.KaoQinUrlModel;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
public class ProwlerHelper {
    private String loginUrl;
    private Map<String,String> params;

    private HttpPost post;
    private HttpClient client=null;
    private boolean islogin=false;
    public ProwlerHelper(String loginUrl, Map<String, String> params) {
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

    public Map fetch(String cookie,KaoQinUrlModel model,FetchAction fetchAction){
        return fetchAction.analyze(cookie,model);
    }

    public ProwlerHelper login(){
        tianchong();

        UrlEncodedFormEntity entity;

        try {
            entity = new UrlEncodedFormEntity(parms, "UTF-8");
            post.setEntity(entity);

            HttpResponse response;
            response = client.execute(post);
//            HttpEntity entity2 = response.getEntity();
            if (entity != null) {

                Header[] allHeaders =
                        response.getAllHeaders();
                for(Header header:allHeaders){
//                    System.out.println(header);
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
        KaoQinUrlModel model= new KaoQinUrlModel();

        Map<String,String> params= new HashMap<String, String>();
        params.put("username","30005");
        params.put("password","30005");
        ProwlerHelper prowlerHelper= new ProwlerHelper(model.getURL(),params);
        String cookie = prowlerHelper.login().cookie();

        Map fetchResult = prowlerHelper.fetch(cookie,model, new KaoQinFetchAction());

        System.out.println(fetchResult.get("data"));

    }
}
