package com.example.beyondsys.ppv.bussiness;

import android.os.Handler;
import android.os.Message;

import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.APIEntity;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by zhsht on 2017/2/5.工作价值业务
 */
public class WorkValueBusiness {
    /*获取工作价值*/
    public void GetWorkValue(final Handler handler, String TeamID, int state, int pageNum, ACache mCache) {
        WorkValueperson person = new WorkValueperson();
       // UserLoginResultEntity userLoginResultEntity = (UserLoginResultEntity) mCache.getAsObject(LocalDataLabel.Proof);
        UserLoginResultEntity userLoginResultEntity = JsonEntity.ParsingJsonForUserLoginResult(mCache.getAsString(LocalDataLabel.Proof));
        if (userLoginResultEntity != null) {
            person.TicketID = userLoginResultEntity.TicketID;
            person.TeamID = TeamID;
            person.PageNum = pageNum;
            person.Status = state;
            final String JsonParams = GsonUtil.getGson().toJson(person);
            new Thread() {
                public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.GETWORKVALUE);
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
                        msg.what = ThreadAndHandlerLabel.GetWorkValue;
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

    /*获取工作价值参数*/
    private class WorkValueperson implements Serializable {
        public String TicketID;
        public String TeamID;
        public int PageNum;
        public int Status;
    }

    /*获取详细价值*/
    public void GetWorkValueContext(final Handler handler, ACache mCache, String UserID, String TeamID, String DateTime, int pagenum) {
        /*获取缓存*/
        UserLoginResultEntity entity = (UserLoginResultEntity) mCache.getAsObject(LocalDataLabel.Proof);
        if (entity != null) {
            WorkValueContextPerson person = new WorkValueContextPerson();
            person.TicketID = entity.TicketID;
            person.TeamID = TeamID;
            person.UserID = UserID;
            person.Time = DateTime;
            person.PageNum = pagenum;
            final String JsonParams = GsonUtil.getGson().toJson(person);
            new Thread() {
                public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.GETWORKVALUECONTEXT);
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
                        msg.what = ThreadAndHandlerLabel.GetWorkValueContext;
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

    /*获取详细价值参数*/
    private class WorkValueContextPerson implements Serializable {
        public String TicketID;
        public String UserID;
        public String TeamID;
        public String Time;
        public int PageNum;
    }
}
