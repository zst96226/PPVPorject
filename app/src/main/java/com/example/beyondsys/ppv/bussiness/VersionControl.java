package com.example.beyondsys.ppv.bussiness;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.beyondsys.ppv.entities.APIEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.tools.GsonUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by s on 2017/3/10.
 */
public class VersionControl {
    public void UpdateVersion(final Handler handler,int version,String TicketID){
        UpdateParam  param=new UpdateParam();
        param.TicketID=TicketID;
        param.version=version;
        final String JsonParams = GsonUtil.getGson().toJson(param);
        Log.i("version", "版本更新提交对象：" + JsonParams);
        new Thread() {
            public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                soapObject.addProperty("actionid", APIEntity.UPDATEVERSION);
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
                    Log.i("zst_test", "版本连接API");
                    // 得到远程方法返回的SOAP对象
                    SoapPrimitive result = (SoapPrimitive) envelop.getResponse();
//                    SoapObject result = (SoapObject) envelop.getResponse();
                    Log.i("zst_test","版本返回值：" + result);
                    Message msg = Message.obtain();
                    msg.what = ThreadAndHandlerLabel.VersionControl;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    Log.i("获取版本信息连接API失败？", "zst_test");
                    Message msg = Message.obtain();
                    msg.what = ThreadAndHandlerLabel.CallAPIError;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    private  class  UpdateParam
    {
        public  int version;
        public  String TicketID;
    }
}
