package com.example.beyondsys.ppv.bussiness;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.APIEntity;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
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
 * Created by zhsht on 2017/2/7.辅助业务
 */
public class OtherBusiness {
    /*获取团队下全部人员信息*/
    public void GetAllStaffForTeam(final Handler handler,String TeamID){
      //  UserLoginResultEntity entity=(UserLoginResultEntity)mCachem.getAsObject(LocalDataLabel.Proof);
        UserLoginResultEntity entity = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                entity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity!=null){
            AllStaffPerson person = new AllStaffPerson();
            person.TicketID = entity.TicketID;
            person.TeamID=TeamID;
            final String JsonParams = GsonUtil.getGson().toJson(person);
            Log.i("提交值："+JsonParams,"zst_test");
            new Thread() {
                public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.GETALLSTAFF);
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
                        msg.what = ThreadAndHandlerLabel.GetAllStaff;
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
        }else {
            Message msg = Message.obtain();
            msg.what = ThreadAndHandlerLabel.LocalNotdata;
            handler.sendMessage(msg);
        }
    }

    /*获取团队下全部人员信息参数*/
    private class AllStaffPerson implements Serializable{
        public String TicketID ;
        public String TeamID;
    }
}
