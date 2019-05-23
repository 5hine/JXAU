package com.example.lanyu.health.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by lanyu on 2018/12/26.
 */
public class Http_utils {

    //private final static String HOST = "http://192.168.1.101:8080/health/";
    private final static String HOST = "http://119.29.60.170/";
    //private final static String  HOST = "http://39.108.190.131:8080/";
    public static String doPost(String method, HashMap<String,Object> params){
        try {
            URL url = new URL(HOST+method);
        //建立连接  获取连接对象
            HttpURLConnection conn = (HttpURLConnection) (url.openConnection());
        //设置请求方式
            conn.setRequestMethod("POST");
        //设置连接超时
            conn.setConnectTimeout(5000);
        //设置读取超时
            conn.setReadTimeout(5000);

            if(params.entrySet().size()>0){
                //有参数  开启输出流
                conn.setDoOutput(true);
                //获取输出流对象
                OutputStream os = conn.getOutputStream();
                //写入数据&
                String reqData = param(params);
                //写入数据
                os.write(reqData.getBytes("utf-8"));
            }

        //获取响应码
        if(conn.getResponseCode()==200){ //200 == HttpURLConnection.HTTP_OK
            //响应成功
            //获取输入流对象
            InputStream is = conn.getInputStream();

            //读取输入流
            int len;        //用于接收每次读取的长度
            byte[] b = new byte[1024];//每次读取的字节数
            StringBuffer sb = new StringBuffer();

            //循环读取数据，临时存储在byte数组中，读取到长度为-1为止
            while((len = is.read(b))!=-1){
                sb.append(new String(b,0,len,"utf-8"));
            }
            //读取完 关闭输入流
            is.close();
            conn.disconnect();
            return sb.toString();


        }else{
            //响应失败
            return "请检查url";

        }

        } catch (Exception e) {
            e.printStackTrace();
            return "异常";
        }


    }

    private static String param(HashMap<String,Object>params){
        StringBuffer sb = new StringBuffer();
        for(HashMap.Entry entry:params.entrySet()){
            sb.append("&"+entry.getKey()+"="+entry.getValue());
        }

        if(sb.length()>0){

            sb.deleteCharAt(0);
        }

        return sb.toString();
    }

}
