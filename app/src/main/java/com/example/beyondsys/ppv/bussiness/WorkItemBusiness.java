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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void GetWorkItemContent(final Handler handler, String WorkItemID, String proof) {
        GetWorkItemContentPerson person = new GetWorkItemContentPerson();
        person.TicketID = proof;
        person.WorkItemID = WorkItemID;
        final String JsonParams = GsonUtil.getGson().toJson(person);
        Log.i("zst_test", "详细信息提交对象：" + JsonParams);
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
                    Log.i("zst_test", "详细信息连接API");
                    // 得到远程方法返回的SOAP对象
                    SoapPrimitive result = (SoapPrimitive) envelop.getResponse();
//                    SoapObject result = (SoapObject) envelop.getResponse();
                    Log.i("zst_test","详细信息返回值：" + result);
                    Message msg = Message.obtain();
                    msg.what = ThreadAndHandlerLabel.GetWorkItemContext;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    Log.i("获取工作项详细信息连接API失败？", "zst_test");
                    Message msg = Message.obtain();
                    msg.what = ThreadAndHandlerLabel.CallAPIError;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    /*获取工作项详细信息参数*/
    private class GetWorkItemContentPerson implements Serializable {
        public String TicketID;
        public String WorkItemID;
    }

    /*创建新工作项*/
    public void AddWorkItem(final Handler handler, WorkItemEntity workItemEntity) {
        UserLoginResultEntity entity = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                entity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity != null) {
            final JSONObject JsonParams = AddWorkItemPerson(workItemEntity, entity.TicketID);
            Log.i("创建工作项提交对象：" + JsonParams, "FHZ");
            System.out.println("jsonObject直接创建json:" + JsonParams);
            new Thread() {
                public void run() {
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
                        Log.i("创建工作项：senmes", "FHZ");
                        Message msg = Message.obtain();
                        msg.what = ThreadAndHandlerLabel.AddWorkItem;
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

    /*新建工作项提交参数*/
    private JSONObject AddWorkItemPerson(WorkItemEntity workItem, String TicketID) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("TicketID", TicketID);
            JSONObject newItem = new JSONObject();
            newItem.put("TheTimeStamp", workItem.TheTimeStamp);
            newItem.put("Assigned2", workItem.Assigned2);
            newItem.put("Belong2", workItem.Belong2);
            newItem.put("BID", workItem.BID);
            newItem.put("BusinessValue", workItem.BusinessValue);
            newItem.put("BasicScore",workItem.BasicScore);
            newItem.put("CheckedScore",workItem.CheckedScore);
            newItem.put("Checker", workItem.Checker);
            newItem.put("Category", workItem.Category);
            newItem.put("ClosingTime", workItem.ClosingTime);
            newItem.put("Creater", workItem.Creater);
            newItem.put("Modifier",workItem.Modifier);
            newItem.put("CreateTime", workItem.CreateTime);
            newItem.put("FID", workItem.FID);
            newItem.put("RID",workItem.RID);
            newItem.put("HardScale", workItem.HardScale);
            newItem.put("Description", workItem.Description);
            newItem.put("ID", workItem.ID);
            newItem.put("Name", workItem.Name);
            newItem.put("Remark",workItem.Remark);
            newItem.put("Status", workItem.Status);
            jsonObject.put("WorkItem", newItem);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    /*更新工作项*/
    public void UpdateWorkItem(final Handler handler, List<WorkItemEntity> workItemEntitys) {
        UserLoginResultEntity entity = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                entity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (entity != null) {
          // String TicketID = "7ad8876f-5044-48be-b180-ff6b31e5e60a";
            final JSONObject JsonParams = UpdateWorkItemPerson(workItemEntitys,entity.TicketID);
            Log.i("更新工作项提交对象：" + JsonParams, "FHZ");
            System.out.println("更新工作项json:" + JsonParams);
            new Thread() {
                public void run() {
                    //////
                    /*根据命名空间和方法得到SoapObject对象*/
                    SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_NAME);
                    soapObject.addProperty("actionid", APIEntity.UPNEWWORKITEM);
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
                        Log.i("更新工作项：senmes", "FHZ");
                        Message msg = Message.obtain();
                        msg.what = ThreadAndHandlerLabel.UpdateWorkItem;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    } catch (IOException | XmlPullParserException e) {
                        e.printStackTrace();
                        Log.i("更新工作项：send exc", "FHZ");
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

    /*更新工作项提交参数*/
    private JSONObject UpdateWorkItemPerson(List<WorkItemEntity> workItemList, String TicketID) {
   //     JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("TicketID", TicketID);
//            List<JSONObject> workItems = new ArrayList<JSONObject>();
//            for (WorkItemEntity workItem:workItemList) {
//                JSONObject newItem = new JSONObject();
//                newItem.put("BID", workItem.BID);
//                newItem.put("FID", workItem.FID);
//                newItem.put("ID", workItem.ID);
//                newItem.put("TheTimeStamp", workItem.TheTimeStamp);
//                newItem.put("RID",workItem.RID);
//                newItem.put("Name", workItem.Name);
//                newItem.put("Description", workItem.Description);
//                newItem.put("Category",workItem.Category);
////                 newItem.put("Status", workItem.Status);
//                newItem.put("Assigned2", workItem.Assigned2);
//                newItem.put("Belong2", workItem.Belong2);
////                 newItem.put("Checker", workItem.Checker);
//                newItem.put("ClosingTime", workItem.ClosingTime);
//                newItem.put("Creater", workItem.Creater);
//                newItem.put("CreateTime", workItem.CreateTime);
////                  newItem.put("Modifier",workItem.Modifier);
////               newItem.put("ModifyTime",workItem.ModifyTime);
//                newItem.put("BusinessValue", workItem.BusinessValue);
////                newItem.put("HardScale", workItem.HardScale);
//                newItem.put("BasicScore",workItem.BasicScore);
////                newItem.put("CheckedScore",workItem.CheckedScore);
//                newItem.put("Remark",workItem.Remark);
////
//
//                workItems.add(newItem);
//            }
//             jsonObject.put("WorkItems", workItems);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("TicketID", TicketID);
        List<Map> jsonObjects = new ArrayList<Map>();
        for (WorkItemEntity workItem:workItemList) {
        Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("BID", workItem.BID);
            map1.put("FID", workItem.FID);
            map1.put("ID", workItem.ID);
            map1.put("TheTimeStamp", workItem.TheTimeStamp);
            map1.put("RID",workItem.RID);
            map1.put("Name", workItem.Name);
            map1.put("Description", workItem.Description);
            map1.put("Category",workItem.Category);
            map1.put("Assigned2", workItem.Assigned2);
            map1.put("Belong2", workItem.Belong2);
            map1.put("Checker", workItem.Checker);
            map1.put("ClosingTime", workItem.ClosingTime);
            map1.put("Modifier",workItem.Modifier);
            map1.put("ModifyTime",workItem.ModifyTime);
            map1.put("Creater", workItem.Creater);
            map1.put("CreateTime", workItem.CreateTime);
            map1.put("BusinessValue", workItem.BusinessValue);
            map1.put("Remark",workItem.Remark);
            map1.put("Status", workItem.Status);
            map1.put("HardScale", workItem.HardScale);
            map1.put("BasicScore",workItem.BasicScore);
            map1.put("CheckedScore",workItem.CheckedScore);
            jsonObjects.add(map1);
        }
        map.put("WorkItems", jsonObjects);
        return  new JSONObject(map);
    }

    /*获取子工作项*/
    public void GetChildWorkItem(final Handler handler, String WorkItemID, String TickID, int pagenum) {
        ChildWorkItemPerson person = new ChildWorkItemPerson();
        person.TicketID = TickID;
        person.WorkItemIdD = WorkItemID;
        person.PageNum = pagenum;
        final String JsonParams = GsonUtil.getGson().toJson(person);
        Log.i("子工作项提交对象：" + JsonParams, "FHZ");
        System.out.print("子工作项提交对象：" + JsonParams);
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
    }

    /*获取子工作项参数*/
    private class ChildWorkItemPerson implements Serializable {
        public String TicketID;
        public String WorkItemIdD;
        public int PageNum;
    }

}
