package com.example.beyondsys.ppv.entities;

/**
 * Created by zhsht on 2017/2/5.API类
 */
public class APIEntity {
    /*通用API地址*/
    public static String APIURL = "";

    /*webServer命名空间*/
    public static final String NAME_SPACE = "http://sysmagic.com.cn/";

    /*调用的方法*/
    public static final String METHOD_NAME = "ActionCommand";

    /*上传头像*/
    public static final String METHOD_IMG_NAME = "UploadImage";

    /*WSDL文档中的URL*/;
    public static final String WSDL_URL = "http://120.26.37.247:8181/WSPPVService.asmx?wsdl";

    /*登录接口标识*/
    public static final int LOGIN = 100;
    /*获取标识接口标识*/
    public static final int GETLOGO =104;
    /*登出接口标识*/
    public static final int LOGOUT = 200;
    /*获取工作项接口标识*/
    public static final int GETWORKITEM = 3;
    /*获取工作价值接口标识*/
    public static final int GETWORKVALUE = 4;
    /*获取个人信息接口标识*/
    public static final int GETUSERMES = 5;
    /*修改密码接口标识*/
    public static final int CHANGPWD=6;
    /*修改个人信息接口标识*/
    public static final int CHANGONESELF=7;
    /*获取工作项详细信息接口标识*/
    public static final int GETlWORKITEMCONTEXT=9;
    /*获取工作子项信息*/
    public static final int GETCHILDWORKITEM=10;
    /*获取工作价值详细信息*/
    public static final int GETWORKVALUECONTEXT=11;
    /*获取团队内部人员*/
    public static final int GETALLSTAFF=14;
}
