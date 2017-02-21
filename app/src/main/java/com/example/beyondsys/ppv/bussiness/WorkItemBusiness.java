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
import com.example.beyondsys.ppv.entities.WorkItemEntity;
import com.example.beyondsys.ppv.entities.WorkValueResultParams;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhsht on 2017/2/5.工作项业务
 */
public class WorkItemBusiness {
    /*获取对应工作项*/
    public void GetWorkItem(final Handler handler, String TeamID, int state, int relation, int pageNum, String TicketID) {
        WorkItemperson person = new WorkItemperson();
        person.TicketID = TicketID;
        person.TeamID = TeamID;
        person.Status = state;
        person.RelationID = relation;
        person.PageNum = pageNum;
        final String JsonParams = GsonUtil.getGson().toJson(person);
//        Log.i("TicketID:"+TicketID,"FHZ");
//        Log.i("TeamID:"+TeamID,"FHZ");
//        Log.i("Status:"+state,"FHZ");
//        Log.i("RelationID:"+relation,"FHZ");
        new Thread() {
            public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                soapObject.addProperty("actionid", APIEntity.GETWORKITEM);
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
                    msg.what = ThreadAndHandlerLabel.GetWorkItem;
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

    /*获取工作项所需参数*/
    private class WorkItemperson implements Serializable {
        public String TicketID;
        public String TeamID;
        public int PageNum;
        public int Status;
        public int RelationID;
    }

    /*获取工作项详细信息*/
    public void GetWorkItemContent(final Handler handler,  String WorkItemID) {
        /*获取缓存*/
       // UserLoginResultEntity entity = (UserLoginResultEntity) mCache.getAsObject(LocalDataLabel.Proof);
  //      UserLoginResultEntity entity = JsonEntity.ParsingJsonForUserLoginResult(mCache.getAsString(LocalDataLabel.Proof));
        UserLoginResultEntity entity = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                entity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity != null) {
            GetWorkItemContentPerson person = new GetWorkItemContentPerson();
            person.TicketID = entity.TicketID;
            person.WorkItemID = WorkItemID;
            final String JsonParams = GsonUtil.getGson().toJson(person);
            Log.i("提交对象：" + JsonParams, "FHZ");
            new Thread() {
                public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.GETlWORKITEMCONTEXT);
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
                        msg.what = ThreadAndHandlerLabel.GetWorkItemContext;
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

    /*获取工作项详细信息参数*/
    private class GetWorkItemContentPerson implements Serializable {
        public String TicketID;
        public String WorkItemID;
    }
    /*创建新工作项*/
    public    void AddWorkItem(final Handler handler, WorkItemEntity workItemEntity)
    {
        UserLoginResultEntity entity = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                entity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(entity!=null)
        {
            final JSONObject JsonParams = AddWorkItemPerson(workItemEntity,entity.TicketID);
            Log.i("创建工作项提交对象：" + JsonParams, "FHZ");
            System.out.println("jsonObject直接创建json:" + JsonParams);
            new Thread(){
                public  void run(){
                    //////
                    /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.ADDNEWWORKITEM);
                    soapObject.addProperty("jsonvalue", JsonParams.toString());
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
                        Log.i("创建工作项：senmes" , "FHZ");
                        Message msg = Message.obtain();
                        msg.what =ThreadAndHandlerLabel.AddWorkItem ;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    } catch (IOException | XmlPullParserException e) {
                        e.printStackTrace();
                        Log.i("创建工作项：send exc", "FHZ");
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
    /*创建新工作项提交参数*/
    private JSONObject AddWorkItemPerson(WorkItemEntity workItem,String TicketID){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("TicketID",TicketID);
            JSONObject newItem = new JSONObject();
            newItem.put("TheTimeStamp",  workItem.TheTimeStamp);
            newItem.put("Assigned2", workItem.Assigned2);
            newItem.put("Belong2", workItem.Belong2);
            newItem.put("BID", workItem.BID);
            newItem.put("BusinessValue", workItem.BusinessValue);
            newItem.put("Checker", workItem.Checker);
            newItem.put("Category", workItem.Category);
            newItem.put("ClosingTime", workItem.ClosingTime);
            newItem.put("Creater",workItem.Creater);
            newItem.put("CreateTime",workItem.CreateTime);
            newItem.put("FID",workItem.FID);
            newItem.put("HardScale",workItem.HardScale);
            newItem.put("Description",workItem.Description);
            newItem.put("ID", workItem.ID);
            newItem.put("Name",workItem.Name);
            newItem.put("Status",workItem.Status);
         jsonObject.put("WorkItem",newItem);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    /*获取子工作项*/
    public void GetChildWorkItem(final Handler handler,  String WorkItemID, int pagenum) {
        /*获取缓存*/
      //  UserLoginResultEntity entity = (UserLoginResultEntity) mCache.getAsObject(LocalDataLabel.Proof);
        UserLoginResultEntity entity = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                entity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity != null) {
            ChildWorkItemPerson person = new ChildWorkItemPerson();
            person.proof = entity.TicketID;
            person.WorkItemid = WorkItemID;
            person.pageNum = pagenum;
            final String JsonParams = GsonUtil.getGson().toJson(person);
            Log.i("提交对象：" + JsonParams, "FHZ");
            new Thread() {
                public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.GETCHILDWORKITEM);
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
                        msg.what = ThreadAndHandlerLabel.GetChildWorkItem;
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

    /*获取子工作项参数*/
    private class ChildWorkItemPerson implements Serializable {
        public String proof;
        public String WorkItemid;
        public int pageNum;
    }

}
