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
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
 * @Date : 2016/7/5 20:15
 */
public class First {
    private final static String URL = "http://124.65.191.70:10000/iclock/accounts/login/";

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);

        List<NameValuePair> parms = new ArrayList<NameValuePair>();
        parms.add(new BasicNameValuePair("username", "30005"));
        parms.add(new BasicNameValuePair("password", "30005"));


        UrlEncodedFormEntity entity;

        try {
            entity = new UrlEncodedFormEntity(parms, "UTF-8");
            post.setEntity(entity);
            System.out.println("executing request " + post.getURI());

            HttpResponse response;
            response = client.execute(post);
            HttpEntity entity2 = response.getEntity();
            if (entity != null) {
                System.out.println("--------------------------------------");
                System.out.println("Response content: "
                        + EntityUtils.toString(entity2, "utf-8"));
                System.out.println("--------------------------------------");
                System.out.println(response.getStatusLine());
                Header[] allHeaders =
                        response.getAllHeaders();
                for(Header header:allHeaders){
                    System.out.println(header);
                }

                System.out.println();
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
    }
}
