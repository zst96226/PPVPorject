package com.example.beyondsys.ppv.bussiness;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.APIEntity;
import com.example.beyondsys.ppv.entities.AccAndPwd;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.tools.MD5;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zhsht on 2017/2/4.登录业务
 */
public class LoginBusiness {
    /*用户登录*/
    public void Login(String Id, String Pwd, final Handler handler) {
        AccAndPwd person = new AccAndPwd(Id, Pwd);
        final String JsonParams = GsonUtil.getGson().toJson(person);
        Log.i("提交对象：" + JsonParams, "FHZ");
        new Thread() {
            public void run() {
                try {
                    /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.LOGIN);
                    soapObject.addProperty("jsonvalue", JsonParams);
                    // 通过SOAP1.1协议得到envelop对象
                    SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    // 将soapObject对象设置为envelop对象，传出消息
                    envelop.bodyOut = soapObject;
                    envelop.dotNet = true;
                    HttpTransportSE httpSE = new HttpTransportSE(APIEntity.WSDL_URL);
                    // 开始调用远程方法s
                    httpSE.call(APIEntity.NAME_SPACE + APIEntity.METHOD_NAME, envelop);
                    // 得到远程方法返回的SOAP对象
                    SoapPrimitive result = (SoapPrimitive) envelop.getResponse();
//                    SoapObject result = (SoapObject) envelop.getResponse();
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
    public void UserLogo(final Handler handler,String TicketID) {
        /*从缓存中获取凭据*/
//        UserLoginResultEntity entity = JsonEntity.ParsingJsonForUserLoginResult(mCache.getAsString(LocalDataLabel.Proof));
//        UserLoginResultEntity entity = null;
//        try {
//            if (Reservoir.contains(LocalDataLabel.Proof)) {
//                entity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (entity != null && !entity.TicketID.equals("")) {
            UserLogoPerson person=new UserLogoPerson();
            person.TicketID= TicketID;
            final String JsonParams = GsonUtil.getGson().toJson(person);
            Log.i("提交参数：actionid:"+APIEntity.GETLOGO+ " jsonvalue:"+ JsonParams, "FHZ");
            new Thread() {
                public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                    try {
                        SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                        soapObject.addProperty("actionid", APIEntity.GETLOGO);
                        soapObject.addProperty("jsonvalue", JsonParams);
                        // 通过SOAP1.1协议得到envelop对象
                        SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        // 将soapObject对象设置为envelop对象，传出消息
                        envelop.bodyOut = soapObject;
                        envelop.dotNet = true;
                        HttpTransportSE httpSE = new HttpTransportSE(APIEntity.WSDL_URL);
                        // 开始调用远程方法
                        httpSE.call(APIEntity.NAME_SPACE + APIEntity.METHOD_NAME, envelop);
                        // 得到远程方法返回的SOAP对象
                        SoapPrimitive result = (SoapPrimitive) envelop.getResponse();
//                        SoapObject result = (SoapObject) envelop
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
//        } else {
//            Message msg = Message.obtain();
//            msg.what = ThreadAndHandlerLabel.LocalNotdata;
//          //  msg.what = ThreadAndHandlerLabel.CallAPIError;
//            handler.sendMessage(msg);
//        }
    }

    /*退出登录*/
    public void LogOut(final Handler handler) {
        /*从缓存中获取凭据*/
    //    UserLoginResultEntity entity = (UserLoginResultEntity) mCache.getAsObject(LocalDataLabel.Proof);
        UserLoginResultEntity entity = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                entity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity != null && !entity.TicketID.equals("")) {
//            String _poof = entity.TicketID;
//            final String JsonParams = GsonUtil.getGson().toJson(_poof);
            UserLogoPerson person=new UserLogoPerson();
            person.TicketID= entity.TicketID;
            final String JsonParams = GsonUtil.getGson().toJson(person);
            Log.i("提交参数：actionid:"+APIEntity.LOGOUT+ " jsonvalue:"+ JsonParams, "FHZ");
            Log.i("提交对象：" + JsonParams, "FHZ");
            new Thread() {
                public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.LOGOUT);
                    soapObject.addProperty("jsonvalue", JsonParams);
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
                        msg.what = ThreadAndHandlerLabel.LogOut;
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
        } else {
            Message msg = Message.obtain();
            msg.what = ThreadAndHandlerLabel.LocalNotdata;
            handler.sendMessage(msg);
        }
    }

    /*修改密码*/
    public void ChangePWD(final Handler handler, ACache mCache, String newpwd) {
        /*获取缓存中的账号*/
    //    UserLoginResultEntity entitys = (UserLoginResultEntity) mCache.getAsObject(LocalDataLabel.Proof);
        UserLoginResultEntity entitys = null;
        AccAndPwd entity=null;
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                entitys = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            Log.i("ChangePWDentity try","FHZ");
            if(Reservoir.contains(LocalDataLabel.AccAndPwd))
            {
                Log.i("ChangePWDentity","FHZ");
                entity=Reservoir.get(LocalDataLabel.AccAndPwd, AccAndPwd.class);
                Log.i("ChangePWDUSER","FHZ");
            }
        }catch (Exception e){
            Log.i("ChangePWDentity excep","FHZ");
            e.printStackTrace();
        }
       // AccAndPwd entity = (AccAndPwd) mCache.getAsObject(LocalDataLabel.AccAndPwd);
        if (entity != null && entitys != null) {
            ChangePWDPerson person = new ChangePWDPerson();
            person.TicketID = entitys.TicketID;
            person.AccountName = entity.AccountName;
            person.OldPassword = entity.Password;
            person.NewPassword = newpwd;
            final String JsonParams = GsonUtil.getGson().toJson(person);
            Log.i("提交对象：" + JsonParams, "FHZ");
            new Thread() {
                public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.CHANGPWD);
                    soapObject.addProperty("jsonvalue", JsonParams);
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
                        msg.what = ThreadAndHandlerLabel.ChangePwd;
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
        } else {
            Message msg = Message.obtain();
            msg.what = ThreadAndHandlerLabel.LocalNotdata;
            handler.sendMessage(msg);
        }
    }

    /*修改密码参数*/
    private class ChangePWDPerson implements Serializable {
        public String TicketID;
        public String AccountName;
        public String OldPassword;
        public String NewPassword;
    }

    private class UserLogoPerson implements Serializable{
        public String TicketID;
    }
}