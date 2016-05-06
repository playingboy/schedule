package net.iyouyu.schedule;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Element;
import android.util.Xml;
import android.view.*;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Jesse on 2015/4/8.
 * this class gets data we need from the internet
 * and write all this data to the database
 *
 */
public class GetData extends Activity {
    List<Cookie> cookies;                      //保存获取的cookie
    DefaultHttpClient client = new DefaultHttpClient();
    HttpResponse httpResponse;
    String result = "";                         //源码数据
   // public static final String GET_URL = "http://202.202.1.176:8080/_data/index_login.aspx";
    public static final String POST_URL = "http://202.202.1.176:8080/_data/index_login.aspx";
    public static final String uriAPI = "http://202.202.1.176:8080/znpk/Pri_StuSel_rpt.aspx";
    public final static String TABLE_NAME = "classes";// 表名
    private ProgressBar progressBar;
    private Button backButton;
    private String userNameValue,passwordValue;
    private SharedPreferences sp;

    MySQLiteOpenHelper myOpenHelper;
    SQLiteDatabase mysql; // 实例数据库
    String day;
    String no;
    String weekStart;
    String sweekEnd;
    String detail;
    String name;
    String address;
    WebView webView;
    String key;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.logo);

        webView = (WebView) findViewById(R.id.webView);
        myOpenHelper = new MySQLiteOpenHelper(this);

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            mysql = SQLiteDatabase.openOrCreateDatabase(FileDB.f, null);
        else
            mysql = myOpenHelper.getWritableDatabase();
        myOpenHelper.onCreate(mysql);

        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        // rem_pw = (CheckBox) findViewById(R.id.cb_mima);

        userNameValue = sp.getString("USER_NAME", "");
        passwordValue = sp.getString("PASSWORD", "");
        System.out.println("+++++++++++++++++++++++" + userNameValue + "+++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++++++++++" + passwordValue + "+++++++++++++++++++++++++++++++");
        progressBar = (ProgressBar) findViewById(R.id.pgBar);
        backButton = (Button) findViewById(R.id.btn_back);
        System.out.println("+++++++++++++++++++++++login++++++++       +++++++++++++++++++++++");
        md5(userNameValue, passwordValue);

        finish();

        // finish();



        //Intent intent = new Intent(this, WelcomeAvtivity.class);
        //LogoActivity.this.startActivity(intent);

        backButton.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                System.out.println("js返回结果" + key);
                finish();

            }
        });
    }

    public boolean LogIn(String userNameValue,String passwordValue) throws IOException {
        Thread thread = Thread.currentThread();

        HttpPost httpRequest = new HttpPost(POST_URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("cCode", ""));

        //md5(yhm+md5(obj.value).substring(0,30).toUpperCase()+schoolcode).substring(0,30).toUpperCase();
        params.add(new BasicNameValuePair("txt_dsdfdfgfouyy", passwordValue));   //密码
        params.add(new BasicNameValuePair("txt_dsdsdsdjkjkjc", userNameValue));    //这是学号
        params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "CAA0A5A7"));
        params.add(new BasicNameValuePair("efdfdfuuyyuuckjg", key));//efdfdfuuyyuuckjg	4E42E5AFDAA8DD3FCB38C4829103B4	47
        params.add(new BasicNameValuePair("__VIEWSTATE", "dDw1OTgzNjYzMjM7dDw7bDxpPDE+O2k8Mz47aTw1Pjs+O2w8dDxwPGw8VGV4dDs+O2w86YeN5bqG5aSn5a2mOz4+Ozs+O3Q8cDxsPFRleHQ7PjtsPFw8c2NyaXB0IHR5cGU9InRleHQvamF2YXNjcmlwdCJcPgpcPCEtLQpmdW5jdGlvbiBDaGtWYWx1ZSgpewogdmFyIHZVPWRvY3VtZW50LmFsbC5VSUQuaW5uZXJUZXh0XDsKIHZhciB2Y0ZsYWcgPSAiTk8iXDt0cnl7CiBpZiAoZG9jdW1lbnQuYWxsLlVzZXJJRC52YWx1ZT09JycpewogYWxlcnQoJ+mhu+W9leWFpScrdlUrJ++8gScpXDtkb2N1bWVudC5hbGwuVXNlcklELmZvY3VzKClcO3JldHVybiBmYWxzZVw7Cn0KIGVsc2UgaWYgKGRvY3VtZW50LmFsbC5QYXNzV29yZC52YWx1ZT09JycpewogYWxlcnQoJ+mhu+W9leWFpeWvhuegge+8gScpXDtkb2N1bWVudC5hbGwuUGFzc1dvcmQuZm9jdXMoKVw7cmV0dXJuIGZhbHNlXDsKfQogZWxzZSBpZiAoZG9jdW1lbnQuYWxsLmNDb2RlLnZhbHVlPT0nJyAmJiB2Y0ZsYWcgPT0gIllFUyIpewogYWxlcnQoJ+mhu+W9leWFpemqjOivgeegge+8gScpXDtkb2N1bWVudC5hbGwuY0NvZGUuZm9jdXMoKVw7cmV0dXJuIGZhbHNlXDsKfQogZWxzZSB7IGRvY3VtZW50LmFsbC5kaXZMb2dOb3RlLmlubmVySFRNTD0n5q2j5Zyo6YCa6L+H6Lqr5Lu96aqM6K+BLi4u6K+356iN5YCZISdcOwogcmV0dXJuIHRydWVcO30KfWNhdGNoKGUpe30KfQpmdW5jdGlvbiBTZWxUeXBlKG9iail7CiB2YXIgcz1vYmoub3B0aW9uc1tvYmouc2VsZWN0ZWRJbmRleF0udXNySURcOwogZG9jdW1lbnQuYWxsLlVJRC5pbm5lckhUTUw9c1w7CiBzZWxUeWVOYW1lKClcOwp9CmZ1bmN0aW9uIG9wZW5XaW5Mb2codGhlVVJMLHcsaCl7CnZhciBUZm9ybSxyZXRTdHJcOwpldmFsKCJUZm9ybT0nd2lkdGg9Iit3KyIsaGVpZ2h0PSIraCsiLHNjcm9sbGJhcnM9bm8scmVzaXphYmxlPW5vJyIpXDsKcG9wPXdpbmRvdy5vcGVuKHRoZVVSTCwnd2luS1BUJyxUZm9ybSlcOyAvL3BvcC5tb3ZlVG8oMCw3NSlcOwpldmFsKCJUZm9ybT0nZGlhbG9nV2lkdGg6Iit3KyJweFw7ZGlhbG9nSGVpZ2h0OiIraCsicHhcO3N0YXR1czpub1w7c2Nyb2xsYmFycz1ub1w7aGVscDpubyciKVw7CmlmKHR5cGVvZihyZXRTdHIpIT0ndW5kZWZpbmVkJykgYWxlcnQocmV0U3RyKVw7Cn0KZnVuY3Rpb24gc2hvd0xheShkaXZJZCl7CnZhciBvYmpEaXYgPSBldmFsKGRpdklkKVw7CmlmIChvYmpEaXYuc3R5bGUuZGlzcGxheT09Im5vbmUiKQp7b2JqRGl2LnN0eWxlLmRpc3BsYXk9IiJcO30KZWxzZXtvYmpEaXYuc3R5bGUuZGlzcGxheT0ibm9uZSJcO30KfQpmdW5jdGlvbiBzZWxUeWVOYW1lKCl7CiAgZG9jdW1lbnQuYWxsLnR5cGVOYW1lLnZhbHVlPWRvY3VtZW50LmFsbC5TZWxfVHlwZS5vcHRpb25zW2RvY3VtZW50LmFsbC5TZWxfVHlwZS5zZWxlY3RlZEluZGV4XS50ZXh0XDsKfQpmdW5jdGlvbiB3aW5kb3cub25sb2FkKCl7Cgl2YXIgc1BDPXdpbmRvdy5uYXZpZ2F0b3IudXNlckFnZW50K3dpbmRvdy5uYXZpZ2F0b3IuY3B1Q2xhc3Mrd2luZG93Lm5hdmlnYXRvci5hcHBNaW5vclZlcnNpb24rJyBTTjpOVUxMJ1w7CnRyeXtkb2N1bWVudC5hbGwucGNJbmZvLnZhbHVlPXNQQ1w7fWNhdGNoKGVycil7fQp0cnl7ZG9jdW1lbnQuYWxsLlVzZXJJRC5mb2N1cygpXDt9Y2F0Y2goZXJyKXt9CnRyeXtkb2N1bWVudC5hbGwudHlwZU5hbWUudmFsdWU9ZG9jdW1lbnQuYWxsLlNlbF9UeXBlLm9wdGlvbnNbZG9jdW1lbnQuYWxsLlNlbF9UeXBlLnNlbGVjdGVkSW5kZXhdLnRleHRcO31jYXRjaChlcnIpe30KfQpmdW5jdGlvbiBvcGVuV2luRGlhbG9nKHVybCxzY3IsdyxoKQp7CnZhciBUZm9ybVw7CmV2YWwoIlRmb3JtPSdkaWFsb2dXaWR0aDoiK3crInB4XDtkaWFsb2dIZWlnaHQ6IitoKyJweFw7c3RhdHVzOiIrc2NyKyJcO3Njcm9sbGJhcnM9bm9cO2hlbHA6bm8nIilcOwp3aW5kb3cuc2hvd01vZGFsRGlhbG9nKHVybCwxLFRmb3JtKVw7Cn0KZnVuY3Rpb24gb3Blbldpbih0aGVVUkwpewp2YXIgVGZvcm0sdyxoXDsKdHJ5ewoJdz13aW5kb3cuc2NyZWVuLndpZHRoLTEwXDsKfWNhdGNoKGUpe30KdHJ5ewpoPXdpbmRvdy5zY3JlZW4uaGVpZ2h0LTMwXDsKfWNhdGNoKGUpe30KdHJ5e2V2YWwoIlRmb3JtPSd3aWR0aD0iK3crIixoZWlnaHQ9IitoKyIsc2Nyb2xsYmFycz1ubyxzdGF0dXM9bm8scmVzaXphYmxlPXllcyciKVw7CnBvcD1wYXJlbnQud2luZG93Lm9wZW4odGhlVVJMLCcnLFRmb3JtKVw7CnBvcC5tb3ZlVG8oMCwwKVw7CnBhcmVudC5vcGVuZXI9bnVsbFw7CnBhcmVudC5jbG9zZSgpXDt9Y2F0Y2goZSl7fQp9CmZ1bmN0aW9uIGNoYW5nZVZhbGlkYXRlQ29kZShPYmopewp2YXIgZHQgPSBuZXcgRGF0ZSgpXDsKT2JqLnNyYz0iLi4vc3lzL1ZhbGlkYXRlQ29kZS5hc3B4P3Q9IitkdC5nZXRNaWxsaXNlY29uZHMoKVw7Cn0KXFwtLVw+Clw8L3NjcmlwdFw+Oz4+Ozs+O3Q8O2w8aTwxPjs+O2w8dDw7bDxpPDA+Oz47bDx0PHA8bDxUZXh0Oz47bDxcPG9wdGlvbiB2YWx1ZT0nU1RVJyB1c3JJRD0n5a2m5Y+3J1w+5a2m55SfXDwvb3B0aW9uXD4KXDxvcHRpb24gdmFsdWU9J1RFQScgdXNySUQ9J+W4kOWPtydcPuaVmeW4iFw8L29wdGlvblw+Clw8b3B0aW9uIHZhbHVlPSdTWVMnIHVzcklEPSfluJDlj7cnXD7nrqHnkIbkurrlkZhcPC9vcHRpb25cPgpcPG9wdGlvbiB2YWx1ZT0nQURNJyB1c3JJRD0n5biQ5Y+3J1w+6Zeo5oi357u05oqk5ZGYXDwvb3B0aW9uXD4KOz4+Ozs+Oz4+Oz4+Oz4+Oz5LPDu09JwWUv4PEpMwAMhnZh1Thg=="));
        params.add(new BasicNameValuePair("pcInfo", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)x86;SP3; SN:NULL"));
        params.add(new BasicNameValuePair("Sel_Type", "STU"));    //以学生身份登录
        params.add(new BasicNameValuePair("typeName", "ѧ??"));




        try {
            // 发出HTTP request

            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            // 取得HTTP response

            httpResponse = client.execute(httpRequest);   //执行
            // 若状态码为200 ok

            if (httpResponse.getStatusLine().getStatusCode() == 200) {   //返回值正常
                // 获取返回的cookie
                cookies = /*((AbstractHttpClient) client)*/client.getCookieStore().getCookies();
                String result = EntityUtils.toString(httpResponse.getEntity());
                //System.out.println(result);
                String note = filterHtml(result);
                //System.out.println(note);
                    if (!note.equals("正在加载权限数据...")) {
                        System.out.println(note);
                        Toast.makeText(this, "账号或密码不正确！请重新输入!" + note, Toast.LENGTH_LONG).show();
                        return false;
                    }else {
                        Toast.makeText(this, note, Toast.LENGTH_LONG).show();
                        return true;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    protected void getdata() throws IOException {
        Thread thread = Thread.currentThread();
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            mysql = SQLiteDatabase.openOrCreateDatabase(FileDB.f, null);
        else
            mysql = myOpenHelper.getWritableDatabase();
        if(myOpenHelper.tableis(mysql,"classes"))
            mysql.execSQL("DROP TABLE classes;");
        myOpenHelper.onCreate(mysql);

        List<NameValuePair> params2 = new ArrayList<NameValuePair>();
        HttpPost httpRequest3 = new HttpPost(uriAPI);
       // params2.add(new BasicNameValuePair("sel_xn", "2011")); // 学年

        params2.add(new BasicNameValuePair("px", "1"));
        params2.add(new BasicNameValuePair("rad", "on"));
        //params2.add(new BasicNameValuePair("zfx_flag", "0"));
        params2.add(new BasicNameValuePair("Sel_XNXQ", "20141"));  // 学期：0 第一学期，1 第二学期
        params2.add(new BasicNameValuePair("Submit01", "????"));
        try {
            System.out.println("++++++++++++++++cookies:"+cookies.get(0).getValue());
            httpRequest3.setHeader("Cookie","ASP.NET_SessionId="+ cookies.get(0).getValue());
            httpRequest3.setEntity(new UrlEncodedFormEntity(params2, HTTP.UTF_8));
            /* 发出HTTP request */
            HttpResponse httpResponse2 = new DefaultHttpClient().execute(httpRequest3);

            if (httpResponse2.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpResponse2.getEntity());
                //System.out.println(result);
                GetClassInformation(result);
                Toast.makeText(this, "导入成功", Toast.LENGTH_SHORT).show();
                //thread.sleep ( 5000 );
                finish();
            } else {
                Toast.makeText(this, "导入失败", Toast.LENGTH_SHORT).show();
                //thread.sleep ( 5000 );
                //finish();
            }
         } catch (Exception e) {
            e.printStackTrace();
             }
        //System.out.println(result);
    }
    private String filterHtml(String source) throws XmlPullParserException {
        if (null == source) {
            return "";
        }
        StringBuffer sff = new StringBuffer();
        String note = null;
        int i = 0, j = 0;
        String html = source;
        Document doc = Jsoup.parse(html);   //把HTML代码加载到doc中
        Elements links_class = doc.select("span[id=divLogNote]");
        note = links_class.text();
        return note;
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void GetClassInformation(String source) {
        int day;
        int no;
        int weekStart;
        int weekEnd;
        String detail;
        String name;
        String address;
        Document doc=Jsoup.parse(source);
        Elements links_class = doc.getElementsByTag("tbody");
        Elements student_class = links_class.select("tr");
        for(org.jsoup.nodes.Element e : student_class )
        {

            System.out.println(e.text());
            //day
            //Elements tmp = e.getElementsByTag("td");
            //System.out.println(tmp.select("[style=width:9%;text-align:left]").text());
            String tmp = e.select("[style=width:9%;text-align:left]").text();
            if(!tmp.isEmpty()){
                detail = e.text();
                System.out.println(tmp);
                weekStart = Integer.parseInt(tmp.split(" ")[0].split("-")[0]);
                weekEnd = Integer.parseInt(tmp.split(" ")[0].split("-")[1]);
                name = e.select("[style=width:21%;text-align:left]").text() + " " + e.select("[style=width:7%;text-align:left]").text();
                if(!e.select("[style=width:13%;text-align:left]").isEmpty())
                    address = e.select("[style=width:13%;text-align:left]").text();
                else
                    address = " ";

                switch (tmp.split(" ")[1].charAt(0))
                {
                    case '日' :
                        day = 0;
                        break;
                    case '一':
                        day = 1;
                        break;
                    case '二':
                        day = 2;
                        break;
                    case '三':
                        day = 3;
                        break;
                    case '四':
                        day = 4;
                        break;
                    case '五':
                        day = 5;
                        break;
                    case '六':
                        day = 6;
                        break;
                    default:
                        day = -1;
                        break;
                }
                //System.out.println(tmp.split(" ")[1].substring(2, tmp.split(" ")[1].indexOf('节')));
                switch (tmp.split(" ")[1].substring(2, tmp.split(" ")[1].indexOf('节'))){
                    case "1-2":
                        no = 1;
                        break;
                    case "3-4":
                        no = 2;
                        break;
                    case "5-6":
                        no = 3;
                        break;
                    case "7-8":
                        no = 4;
                        break;
                    case "9-10":
                        no = 5;
                        break;
                    case "11-12":
                        no = 6;
                        break;
                    default :
                        no = -1;
                        break;
                }
                System.out.println(day +" " + no + " " + name +" " + detail +" " + weekStart +" " + weekEnd +" ");
                String temp[] = myOpenHelper.onSelect(mysql, day, no, weekStart, weekEnd);
                if (temp != null) {
                    mysql.execSQL("UPDATE classes SET name='"
                            + name + "', address='" + address +"', information='" + detail + "' WHERE day="
                            + day + " AND no="
                            + no + " AND weekstart="
                            + weekStart + " AND weekend=" + weekEnd + ";");
                    //Toast.makeText(Settings.this, "课程名修改成功", Toast.LENGTH_LONG).show();
                }
                else {
                    myOpenHelper.onInsert(mysql, day, no, name, address, weekStart, weekEnd, detail);
                    //Toast.makeText(Settings.this, "课程名修改成功", Toast.LENGTH_LONG).show();
                }

            }
        }
    }



    public void md5(String uid, String pw){

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsToJava(), "stub");
        //webView.loadUrl("file:///sdcard/cqu/data/md5.html");
        String url = "javascript:setValuesJson(\""+userNameValue+"\",\""+passwordValue+"\")";
        System.out.println(url);
        webView.loadUrl("javascript:"+ md5.code1);
        webView.loadUrl(url);
        //webView.loadUrl("javascript:"+ md5.code);

        //webView.loadUrl("javascript:windows.writeToMD5.write(s);");
    }
    private class JsToJava
    {
        public void jsMethod(String paramFromJS) throws IOException {

            //Log.i("CDH", paramFromJS);
            System.out.println("js返回结果" + paramFromJS);//处理返回的结果
            key = paramFromJS;
            if(LogIn(userNameValue, passwordValue)) {

                getdata();

            }

        }
    }
    //UPDATE classes SET name='[18024860]毕业设计, address='A二次排课, information='3 [18024860]毕业设计 6.00 12.0 12.0 0.0 必修 实践 考试 黄利 1-12 日[9-10节] A二次排课' WHERE day=0 AND no=5 AND weekstart=1 AND weekend=12;

}
