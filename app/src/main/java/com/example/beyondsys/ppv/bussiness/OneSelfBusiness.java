package com.example.beyondsys.ppv.bussiness;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.APIEntity;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.PersonInfoEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserInfoResultParams;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.tools.GsonUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by zhsht on 2017/2/5.个人信息业务逻辑
 */
public class OneSelfBusiness {
    /*获取个人信息*/
    public void GetOneSelf(final Handler handler) {
      //  UserLoginResultEntity userLoginResultEntity = (UserLoginResultEntity) mCache.getAsObject(LocalDataLabel.Proof);
        UserLoginResultEntity userLoginResultEntity = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                userLoginResultEntity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userLoginResultEntity != null) {
            final String JsonParams = GsonUtil.getGson().toJson(userLoginResultEntity.TicketID);
            Log.i("获取个人信息提交对象：" + JsonParams, "FHZ");
            new Thread() {
                public void run() {
                    /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.GETUSERMES);
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
                        Log.i("获取个人信息发送消息", "FHZ");
                        Message msg = Message.obtain();
                        msg.what = ThreadAndHandlerLabel.GetOneSelf;
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

    /*修改个人信息*/
    public void ChangeOneSelf(final Handler handler, UserInfoResultParams entity) {
        /*获取缓存*/
      //  UserLoginResultEntity entitys = (UserLoginResultEntity) mCache.getAsObject(LocalDataLabel.Proof);
        UserLoginResultEntity entitys = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                entitys= Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entitys != null) {
            InformationPerson person = new InformationPerson();
            person.TicketID = entitys.TicketID;
            person.Name = entity.Name;
            person.EMail = entity.EMail;
            person.IDNo = entity.IDNo;
            person.Tel=entity.Tel;
            person.Address = entity.Address;
            person.Sign = entity.Sign;
            final String JsonParams = GsonUtil.getGson().toJson(person);
            Log.i("提交对象：" + JsonParams, "FHZ");
            new Thread() {
                public void run() {
                    /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.CHANGONESELF);
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
                        msg.what = ThreadAndHandlerLabel.OneselfInf;
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

    /*修改个人信息参数*/
    private class InformationPerson implements Serializable {
        public String TicketID;
        public String Name;
        public String EMail;
        public String IDNo;
        public String Tel;
        public String Address;
        public String Sign;
    }
}
