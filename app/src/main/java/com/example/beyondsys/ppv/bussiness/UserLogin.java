package com.example.beyondsys.ppv.bussiness;

import android.app.Notification;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.beyondsys.ppv.entities.ThreadAndHandlerMessage;
import com.example.beyondsys.ppv.tools.GsonUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhsht on 2017/2/4.用户登录
 */
public class UserLogin {

    public String LoginAPI="";
//  用户登录
    public void UserLogin(final String Id,final String Pwd,final Handler handler){
        new Thread(){
            public void run(){
                HttpURLConnection connection = null;
                loginperson person=new loginperson(Id,Pwd);
                String postJson= GsonUtil.getGson().toJson(person);
                Log.i("用户Post字符串："+postJson,"TAG");
                try {
                    // 根据地址创建URL对象
                    URL url = new URL(LoginAPI);
                    // 根据URL对象打开链接
                    HttpURLConnection urlConnection = (HttpURLConnection) url
                            .openConnection();
                    // 设置请求的方式
                    urlConnection.setRequestMethod("POST");
                    // 设置请求的超时时间
                    urlConnection.setReadTimeout(5000);
                    urlConnection.setConnectTimeout(5000);
                    // 传递的数据
                    String data = postJson;
                    // 设置请求的头
                    urlConnection.setRequestProperty("Connection", "keep-alive");
                    // 设置请求的头
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    // 设置请求的头
                    urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
                    // 设置请求的头
                    urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

                    urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
                    urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入,setDoInput的默认值就是true

                    //获取输出流
                    OutputStream os = urlConnection.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    if (urlConnection.getResponseCode() == 200) {
                        // 获取响应的输入流对象
                        InputStream is = urlConnection.getInputStream();
                        // 创建字节输出流对象
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        // 定义读取的长度
                        int len = 0;
                        // 定义缓冲区
                        byte buffer[] = new byte[1024];
                        // 按照缓冲区的大小，循环读取
                        while ((len = is.read(buffer)) != -1) {
                            // 根据读取的长度写入到os对象中
                            baos.write(buffer, 0, len);
                        }
                        // 释放资源
                        is.close();
                        baos.close();
                        // 返回字符串
                        String result = new String(baos.toByteArray());
                        Log.i("用户登录返回结果："+result,"TAG");
                        Message msg = Message.obtain();
                        msg.what= ThreadAndHandlerMessage.UserLogin;
                        msg.obj=result;
                        handler.sendMessage(msg);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            };
        }.start();
    }
    public class loginperson implements Serializable{
        public String Account;
        public String Password;
        public loginperson(String account,String password){
            Account=account;
            Password=password;
        }
    }
}
