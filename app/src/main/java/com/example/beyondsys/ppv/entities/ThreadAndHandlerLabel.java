package com.example.beyondsys.ppv.entities;

/**
 * Created by zhsht on 2017/2/4.线程之间的通信标识
 */
public class ThreadAndHandlerLabel {
    /*访问API失败*/
    public static int CallAPIError = -1;
    /*用户登录*/
    public static int UserLogin = 0;
    /*本地无缓存凭据*/
    public static int LocalNotdata = 1;
    /*获取人员标识*/
    public static int GetIdentifying = 2;
    /*获取工作项内容*/
    public static int GetWorkItem = 3;
    /*获取工作价值内容*/
    public static int GetWorkValue = 4;
    /*获取个人信息*/
    public static int GetOneSelf = 5;
    /*修改密码*/
    public static int ChangePwd = 6;
    /*上传个人信息*/
    public static int OneselfInf = 7;
    /*退出登录*/
    public static int LogOut = 8;
    /*获取工作项详细信息*/
    public static int GetWorkItemContext = 9;
    /*获取工作子项*/
    public static int GetChildWorkItem = 10;
    /*获取工作价值详细信息*/
    public static int GetWorkValueContext = 11;
    /*获取团队人员*/
    public static int GetAllStaff=12;
    /*获取上传头像返回值*/
    public static int UploadImg=13;
    /*创建工作项*/
    public  static  int AddWorkItem=14;
    /*修改工作项*/
    public  static int  UpdateWorkItem=15;
    /*版本更新*/
    public  static int VersionControl=16;

}
