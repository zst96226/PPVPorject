package com.example.beyondsys.ppv.bussiness;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.APIEntity;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.tools.GsonUtil;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhsht on 2017/2/5.工作项业务
 */
public class WorkItemBusiness {
    /*获取对应工作项*/
    public void GetWorkItem(final Handler handler,int state,int relation,int pageNum,ACache mCache){
        WorkItemupload person=new WorkItemupload();
        /*获取缓存中的凭据和团队ID*/
        UserLoginResultEntity userLoginResultEntity=(UserLoginResultEntity)mCache.getAsObject(LocalDataLabel.Proof);
        person.poof=userLoginResultEntity.Proof;
        TeamEntity teamEntity=(TeamEntity)mCache.getAsObject(LocalDataLabel.Label);
        person.teamID=teamEntity.TeamID;
        person.state=state;
        person.relation=relation;
        person.pageNum=pageNum;
        String JsonParams= GsonUtil.getGson().toJson(person);
        final JSONObject postJson= JsonEntity.Getjson("3", JsonParams);
        Log.i("Post:" + postJson, "TAG");
        new Thread(){
            public void run(){
                HttpURLConnection connection = null;

                try {
                    // 根据地址创建URL对象
                    URL url = new URL(APIEntity.APIURL);
                    // 根据URL对象打开链接
                    HttpURLConnection urlConnection = (HttpURLConnection) url
                            .openConnection();
                    // 设置请求的方式
                    urlConnection.setRequestMethod("POST");
                    // 设置请求的超时时间
                    urlConnection.setReadTimeout(5000);
                    urlConnection.setConnectTimeout(5000);
                    // 传递的数据
                    String data ="parameter="+ postJson.toString();
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
                        msg.what= ThreadAndHandlerLabel.GetWorkItem;
                        msg.obj=result;
                        handler.sendMessage(msg);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what= ThreadAndHandlerLabel.CallAPIError;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    public class WorkItemupload implements Serializable{
        public String poof;
        public String teamID;
        public int state;
        public int relation;
        public int pageNum;
    }
}
