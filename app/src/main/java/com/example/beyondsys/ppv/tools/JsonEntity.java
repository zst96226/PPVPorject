package com.example.beyondsys.ppv.tools;

import android.util.Log;

import com.example.beyondsys.ppv.activity.PersonInfo;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.AccAndPwd;
import com.example.beyondsys.ppv.entities.IdentifyResult;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.ModifyPwdResult;
import com.example.beyondsys.ppv.entities.PersonInfoEntity;
import com.example.beyondsys.ppv.entities.SubmitInfoResult;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.UserInfoResultParams;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.entities.WorkDetailResult;
import com.example.beyondsys.ppv.entities.WorkItemEntity;
import com.example.beyondsys.ppv.entities.WorkItemResultEntity;
import com.example.beyondsys.ppv.entities.WorkItemResultParams;
import com.example.beyondsys.ppv.entities.WorkValueEntity;
import com.example.beyondsys.ppv.entities.WorkValueResultEntity;
import com.example.beyondsys.ppv.entities.WorkValueResultParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;


/**
 * Created by zhsht on 2017/2/5.JSON工具类
 */
public class JsonEntity{

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
            object.put("Param", jsonParams);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object;
    }

    public static String GroupJSON(String methodID,String id,String pwd){
        String jsonstr="{&quot;MethodID&quot;:&quot;"+methodID+"&quot;,&quot;Param&quot;:&quot;{&quot;AccountName&quot;:&quot;"+id+"&quot;,&quot;Password&quot;:&quot;"+pwd+"&quot;}&quot;}";
        //Log.i(jsonstr, "FHZ");
        StringBuilder str=new StringBuilder();
        str.append("{&quot;MethodID&quot;:&quot;");
        str.append(methodID);
        str.append("&quot;,&quot;Param&quot;:&quot;{&quot;AccountName&quot;:&quot;");
        str.append(id);
        str.append("&quot;,&quot;Password&quot;:&quot;");
        str.append(pwd);
        str.append("&quot;}&quot;}");
        Log.i(str.toString(),"FHZ");
        return str.toString();
    }

    public static UserLoginResultEntity ParsingJsonForUserLoginResult(String result){
        UserLoginResultEntity entity=null;
        entity =GsonUtil.json2T(result, UserLoginResultEntity.class);
        if (entity!=null){
            return entity;
        }else{
            return null;
        }
    }
 public  static WorkItemResultEntity ParseJsonForWorkItemResult(String result)
 {
     WorkItemResultEntity workItemResultEntity=null;
     workItemResultEntity=GsonUtil.json2T(result, WorkItemResultEntity.class);
     if(workItemResultEntity!=null)
     {
         return  workItemResultEntity;
     }else{
         return null;
     }
 }

    public static List<WorkItemResultParams> ParseJsonForworkItemResultParamsList(String result)
    {
        List<WorkItemResultParams> workItemResultParamsList=null;
        workItemResultParamsList=GsonUtil.json2Collection(result, WorkItemResultParams.class);
        if(workItemResultParamsList!=null)
        {
            return  workItemResultParamsList;
        }else{
            return null;
        }
    }

    public  static WorkValueResultEntity ParseJsonForWorkValueResult(String result)
    {
        WorkValueResultEntity workValueResultEntity=null;
        workValueResultEntity=GsonUtil.json2T(result, WorkValueResultEntity.class);
        if(workValueResultEntity!=null)
        {
            return workValueResultEntity;
        }else{
            return  null;
        }
    }
    public static List<WorkValueResultParams> ParseJsonForWorkValueParamsList (String result)
    {
       List<WorkValueResultParams> entityList=null;
        entityList=GsonUtil.json2Collection(result, WorkValueResultParams.class);
        if(entityList!=null)
        {
            return entityList;
        }else{
            return null;
        }
    }
    public  static UserInfoResultParams ParseJsonForUserInfoResult(String result)
    {
        UserInfoResultParams userInfoResultParams=null;
        userInfoResultParams=GsonUtil.json2T(result, UserInfoResultParams.class);
        if(userInfoResultParams!=null)
        {
            return  userInfoResultParams;
        }else{
            return null;
        }
    }
    public  static ModifyPwdResult ParseJsonForModifyPwdResult(String result)
    {
        ModifyPwdResult modifyPwdResult=null;
        modifyPwdResult=GsonUtil.json2T(result, ModifyPwdResult.class);
        if(modifyPwdResult!=null)
        {
            return  modifyPwdResult;
        }else{
            return  null;
        }
    }
    public  static IdentifyResult ParseJsonForIdentifyResult(String result)
    {
        IdentifyResult identifyResult=null;
        identifyResult=GsonUtil.json2T(result, IdentifyResult.class);
        if(identifyResult!=null)
        {
            return  identifyResult;
        }else{
            return  null;
        }
    }
public  static SubmitInfoResult ParseJsonForSubmitResult(String result)
{
    SubmitInfoResult submitInfoResult=null;
    submitInfoResult=GsonUtil.json2T(result, SubmitInfoResult.class);
    if(submitInfoResult!=null)
    {
        return  submitInfoResult;
    }else{
        return  null;
    }
}
    public static WorkDetailResult ParseJsonForWorkDetailResult(String result)
    {
        WorkDetailResult workDetailResult=null;
        workDetailResult=GsonUtil.json2T(result, WorkDetailResult.class);
        if(workDetailResult!=null)
        {
            return workDetailResult;
        }else{
            return null;
        }
    }
    public static AccAndPwd ParseJsonForAccAndPwd(String str)
    {
        AccAndPwd accAndPwd=null;
        accAndPwd=GsonUtil.json2T(str,AccAndPwd.class);
        if(accAndPwd!=null)
        {
            return accAndPwd;
        }else{
            return  null;
        }
    }
    public  static List<WorkItemEntity> ParsingJsonForWorkItemList(String result)
    {
       List<WorkItemEntity > entityList=null;
        entityList=GsonUtil.json2Collection(result, WorkItemEntity.class);
        if(entityList!=null)
        {
            return entityList;
        }else
        {
            return  null;
        }
    }
    public  static List<WorkValueEntity> ParseJsonForWorkValueList(String result)
    {
        List<WorkValueEntity> entityList=null;
        entityList=GsonUtil.json2Collection(result, WorkValueEntity.class);
        if(entityList!=null)
        {
            return entityList;
        }else{
            return  null;
        }
    }
    public static List<TeamEntity> ParsingJsonForTeamList(String result){
        List<TeamEntity> entity=null;
        entity =GsonUtil.json2Collection(result, TeamEntity.class);
        if (entity!=null){
            return entity;
        }else{
            return null;
        }
    }
    public  static  TeamEntity ParsingJsonForTeamResult(String result)
    {
        TeamEntity teamEntity=null;
        teamEntity=GsonUtil.json2T(result,TeamEntity.class);
        if(teamEntity!=null)
        {
            return teamEntity;
        }else{
            return null;
        }
    }
    public static  PersonInfoEntity ParseJsonForPerson(String result)
    {
        PersonInfoEntity personInfoEntity=null;
        personInfoEntity=GsonUtil.json2T(result, PersonInfoEntity.class);
        if (personInfoEntity!=null)
        {
            return  personInfoEntity;
        }else {
            return  null;
        }
    }

    public  static WorkItemEntity ParseJsonForWorkItem(String result)
    {
        WorkItemEntity workItemEntity=null;
        workItemEntity=GsonUtil.json2T(result,WorkItemEntity.class);
        if(workItemEntity!=null)
        {
            return  workItemEntity;
        }else {
            return null;
        }

    }

    public static List<TeamEntity> readDatasForTeamEntity(JSONArray result) {

        Type mType = new TypeToken<List<TeamEntity>>(){}.getType();

        List<TeamEntity> persons = GsonUtil.getGson().fromJson(result.toString(), mType);
        return persons;
    }
}
