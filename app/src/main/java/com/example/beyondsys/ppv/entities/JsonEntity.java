package com.example.beyondsys.ppv.entities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by zhsht on 2017/2/5.组织上传Json
 */
public class JsonEntity implements Serializable{

    public String MethodID;
    public String JsonParams;
//    public JsonEntity(String methodID,String jsonParams){
//        this.MethodID=methodID;
//        this.JsonParams=jsonParams;
//    }

    public static String JsonStr(String methodID,String jsonParams){

        StringBuilder sb=new StringBuilder();
        Log.i("sta1","TAG");
        sb.append("{\"MethodID\":\"");
        Log.i("sta2" + sb.toString(), "TAG");
        sb.append(methodID);
        Log.i("sta3" + sb.toString(), "TAG");
        sb.append("\",\"JsonParams\":");
        Log.i("sta4" + sb.toString(), "TAG");
        sb.append(jsonParams.toString());
        Log.i("sta5" + sb.toString(), "TAG");
        sb.append("}");
        Log.i("sta6" + sb.toString(), "TAG");

        return sb.toString();
    }

    public static JSONObject Getjson(String methodID,String jsonParams) {

        JSONObject object = new JSONObject();//创建一个总的对象，这个对象对整个json串
        try {
            object.put("MethodID", methodID);
            object.put("JsonParams", jsonParams);//向总对象里面添加包含pet的数组
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }
}
