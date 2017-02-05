package com.example.beyondsys.ppv.entities;

/**
 * Created by zhsht on 2017/2/4.线程之间的通信标识
 */
public class ThreadAndHandlerLabel {
    /*访问API失败*/
    public static int CallAPIError=-1;
    /*用户登录成功*/
    public static int UserLogin=0;
    /*本地无缓存凭据*/
    public static int LocalNotdata=1;
    /*获取人员标识*/
    public static int GetIdentifying=2;
    /*获取工作项内容*/
    public static int GetWorkItem=3;
    /*获取工作价值内容*/
    public static int GetWorkValue=4;
    /*获取个人信息*/
    public static int GetOneSelf=5;
    /*修改密码*/
    public static int ChangePwd=6;
    /*上传个人信息*/
    public static int OneselfInf=7;
}
