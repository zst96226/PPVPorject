package com.example.beyondsys.ppv.bussiness;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.APIEntity;
import com.example.beyondsys.ppv.entities.AccAndPwd;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.tools.GsonUtil;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhsht on 2017/2/4.登录业务
 */
public class LoginBusiness {
    private class loginperson implements Serializable {
        public String AccountName;
        public String Password;

        public loginperson(String account, String password) {
            AccountName = account;
            Password = password;
        }
    }
    /*获取程序运行期间的标识*/
    public void GetIdentifying(final Handler handler, ACache mCache) {
        /*从缓存中获取凭据*/
        UserLoginResultEntity model = (UserLoginResultEntity) mCache.getAsObject(LocalDataLabel.Proof);
        if (model != null && !model.Proof.equals("")) {
            String _poof = model.Proof;
            final JSONObject postJson = JsonEntity.Getjson("8", _poof);
            Log.i("用户Post字符串：" + postJson, "TAG");
            new Thread() {
                public void run() {
                    HttpURLConnection connection = null;
                    try {
                        // 根据地址创建URL对象
                        URL url = new URL(APIEntity.IdentifyingAPI);
                        // 根据URL对象打开链接
                        HttpURLConnection urlConnection = (HttpURLConnection) url
                                .openConnection();
                        // 设置请求的方式
                        urlConnection.setRequestMethod("POST");
                        // 设置请求的超时时间
                        urlConnection.setReadTimeout(5000);
                        urlConnection.setConnectTimeout(5000);
                        // 传递的数据
                        String data = "parameter="+postJson.toString();
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
                            Log.i("获取程序运行期间的标识返回结果：" + result, "TAG");
                            Message msg = Message.obtain();
                            msg.what = ThreadAndHandlerLabel.GetIdentifying;
                            msg.obj = result;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Message msg = Message.obtain();
                        msg.what = ThreadAndHandlerLabel.CallAPIError;
                        handler.sendMessage(msg);
                    }
                }
            }.start();
        } else {
            Message msg = Message.obtain();
            msg.what = ThreadAndHandlerLabel.LocalNotdata;
            handler.sendMessage(msg);
        }
    }

    /*用户登录*/
    public void Login(String Id, String Pwd, final Handler handler){
        loginperson person = new loginperson(Id, Pwd);
        final String JsonParams = GsonUtil.getGson().toJson(person);
        Log.i("提交对象："+JsonParams, "FHZ");
        new Thread(){
            public void run(){
                /*根据命名空间和方法得到SoapObject对象*/
                SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE,APIEntity.METHOD_NAME);
                soapObject.addProperty("actionid",APIEntity.LOGIN);
                soapObject.addProperty("jsonvalue",JsonParams);
                // 通过SOAP1.1协议得到envelop对象
                SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                // 将soapObject对象设置为envelop对象，传出消息
                envelop.bodyOut = soapObject;
                envelop.dotNet = true;
                HttpTransportSE httpSE = new HttpTransportSE(APIEntity.WSDL_URL);
                // 开始调用远程方法
                try {
                    httpSE.call(APIEntity.NAME_SPACE + APIEntity.METHOD_NAME, envelop);
                    // 得到远程方法返回的SOAP对象
                    SoapPrimitive result = (SoapPrimitive) envelop.getResponse();
                    Message msg = Message.obtain();
                    msg.what = ThreadAndHandlerLabel.UserLogin;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = ThreadAndHandlerLabel.CallAPIError;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    /*获取程序运行期间的标识*/
    public void UserLogo(final Handler handler, ACache mCache){
        /*从缓存中获取凭据*/
        UserLoginResultEntity entity = (UserLoginResultEntity) mCache.getAsObject(LocalDataLabel.Proof);
        if (entity != null && !entity.Proof.equals("")) {
            String _poof = entity.Proof;
            final String JsonParams = GsonUtil.getGson().toJson(_poof);
            Log.i("提交对象："+JsonParams, "FHZ");
            new Thread(){
                public void run(){
                /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE,APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid",APIEntity.GETLOGO);
                    soapObject.addProperty("jsonvalue",JsonParams);
                    // 通过SOAP1.1协议得到envelop对象
                    SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    // 将soapObject对象设置为envelop对象，传出消息
                    envelop.bodyOut = soapObject;
                    envelop.dotNet = true;
                    HttpTransportSE httpSE = new HttpTransportSE(APIEntity.WSDL_URL);
                    // 开始调用远程方法
                    try {
                        httpSE.call(APIEntity.NAME_SPACE + APIEntity.METHOD_NAME, envelop);
                        // 得到远程方法返回的SOAP对象
                        SoapPrimitive result = (SoapPrimitive) envelop.getResponse();
                        Message msg = Message.obtain();
                        msg.what = ThreadAndHandlerLabel.GetIdentifying;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    } catch (IOException | XmlPullParserException e) {
                        e.printStackTrace();
                        Message msg = Message.obtain();
                        msg.what = ThreadAndHandlerLabel.CallAPIError;
                        handler.sendMessage(msg);
                    }
                }
            }.start();
        }
        else{
            Message msg = Message.obtain();
            msg.what = ThreadAndHandlerLabel.LocalNotdata;
            handler.sendMessage(msg);
        }
    }
}