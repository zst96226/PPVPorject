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
    /*用户登录*/
    public void UserLogin(String Id, String Pwd, final Handler handler) {

        loginperson person = new loginperson(Id, Pwd);
        String JsonParams = GsonUtil.getGson().toJson(person);
        final JSONObject postJson = JsonEntity.Getjson("1", JsonParams);
        Log.i("Post:" + postJson, "TAG");
        new Thread() {
            public void run() {
                HttpURLConnection connection = null;

                try {
                    // 根据地址创建URL对象
                    URL url = new URL(APIEntity.LoginAPI);
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
                        Log.i("用户登录返回结果：" + result, "TAG");
                        Message msg = Message.obtain();
                        msg.what = ThreadAndHandlerLabel.UserLogin;
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
    }

    private class loginperson implements Serializable {
        public String Account;
        public String Password;

        public loginperson(String account, String password) {
            Account = account;
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

    /*修改密码*/
    public void ChangePassword(final Handler handler,ACache mCache, String oldpwd,String newpwd) {
        ChangePasswordpereson person=new ChangePasswordpereson();
        UserLoginResultEntity userLoginResultEntity=(UserLoginResultEntity)mCache.getAsObject(LocalDataLabel.Proof);
        person.poof=userLoginResultEntity.Proof;
        AccAndPwd accAndPwd=(AccAndPwd)mCache.getAsObject(LocalDataLabel.AccAndPwd);
        person.Account=accAndPwd.Acc;
        person.OldPassword=oldpwd;
        person.NewPassword=newpwd;
        String JsonParams = GsonUtil.getGson().toJson(person);
        final JSONObject postJson = JsonEntity.Getjson("6", JsonParams);
        Log.i("Post:" + postJson, "TAG");
        new Thread(){
            public void run(){
                HttpURLConnection connection = null;

                try {
                    // 根据地址创建URL对象
                    URL url = new URL(APIEntity.LoginAPI);
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
                        Log.i("修改密码返回结果：" + result, "TAG");
                        Message msg = Message.obtain();
                        msg.what = ThreadAndHandlerLabel.ChangePwd;
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
    }

    private class ChangePasswordpereson implements Serializable{
        public String poof;
        public String Account;
        public String OldPassword;
        public String NewPassword;
    }

    /*webServer命名空间*/
    private static final String serviceNameSpace="http://sysmagic.com.cn/";
    /*WSDL文档中的URL*/
    private static final String WSDL = "http://120.26.37.247:8181/WSPPVService.asmx";
    /*调用的方法*/
    private static final String Login="ActionCommand";

    private static final String soapAction="http://120.26.37.247:8181/ActionCommand";

    /*用户登录*/
    public void Login(String Id, String Pwd, final Handler handler){
//        loginperson person = new loginperson(Id, Pwd);
//        //String JsonParams = GsonUtil.getGson().toJson(person);
//        final JSONObject postJson = JsonEntity.Getjson("100", "");

        final String person=JsonEntity.GroupJSON("100", Id, Pwd);
        Log.i("提交对象："+person, "FHZ");

        new Thread(){
            public void run(){
                /*根据命名空间和方法得到SoapObject对象*/
                SoapObject soapObject = new SoapObject(serviceNameSpace,Login);
                soapObject.addProperty("actionJsonvalue", person);
                // 通过SOAP1.1协议得到envelop对象
                SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                // 将soapObject对象设置为envelop对象，传出消息
                envelop.bodyOut = soapObject;
                envelop.dotNet = true;
                envelop.setOutputSoapObject(soapObject);
                HttpTransportSE httpSE = new HttpTransportSE(WSDL);
                // 开始调用远程方法
                try {
                    httpSE.call(soapAction, envelop);
                    // 得到远程方法返回的SOAP对象
                    SoapObject resultObj = (SoapObject) envelop.getResponse();
                    Log.i("sta6", "FHZ");
                    Log.i(resultObj.toString(),"FHZ");
//                    SoapObject object = (SoapObject) envelop.bodyIn;
                    Log.i("sta7","FHZ");
                    int count = resultObj.getPropertyCount();
                    for (int i = 0; i < count; i++) {
                        Log.i(resultObj.getProperty(i).toString(),"FHZ");
                    }

                    Message msg = Message.obtain();
                    msg.what = ThreadAndHandlerLabel.UserLogin;
                    msg.obj = resultObj;
                    handler.sendMessage(msg);
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    Log.i(e.getMessage().toString(),"FHZ");
                }
            }
        }.start();
    }
}