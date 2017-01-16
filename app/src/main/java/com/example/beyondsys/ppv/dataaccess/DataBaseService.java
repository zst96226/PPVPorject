package com.example.beyondsys.ppv.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhsht on 2017/1/13.数据库支持服务
 */
public class DataBaseService extends SQLiteOpenHelper{

    public DataBaseService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建全部表
        StringBuilder sqlstr=new StringBuilder();
        sqlstr.append(" CREATE TABLE T_User ( ");
        sqlstr.append(" ID nvarchar(50) NOT NULL DEFAULT '', ");
        sqlstr.append(" BID nvarchar(50) NOT NULL DEFAULT '', ");
        sqlstr.append(" PID nvarchar(50) NOT NULL DEFAULT '', ");
        sqlstr.append(" Name nvarchar(100) NOT NULL DEFAULT '', ");
        sqlstr.append(" [Description] nvarchar(500) NOT NULL DEFAULT '', ");
        sqlstr.append(" TheTimeStamp int NOT NULL DEFAULT 0, ");
        sqlstr.append(" Account nvarchar(50) NOT NULL DEFAULT '', ");
        sqlstr.append(" PassWord nvarchar(50) NOT NULL DEFAULT '', ");
        sqlstr.append(" RealityScore nvarchar(50) NOT NULL DEFAULT '', ");
        sqlstr.append(" [Image] nvarchar(500) NOT NULL DEFAULT '', ");
        sqlstr.append(" Sex int NOT NULL DEFAULT 0, ");
        sqlstr.append(" IDCard nvarchar(18) NOT NULL DEFAULT '', ");
        sqlstr.append(" Email nvarchar(50) NOT NULL DEFAULT '', ");
        sqlstr.append(" phone nvarchar(20) NOT NULL DEFAULT '', ");
        sqlstr.append(" Level nvarchar(50) NOT NULL DEFAULT '', ");
        sqlstr.append(" Address nvarchar(50) NOT NULL DEFAULT '', ");
        sqlstr.append(" state nvarchar(50) NOT NULL DEFAULT '', ");
        sqlstr.append(" constraint PK_T_User primary key (ID, BID, TheTimeStamp) ");
        sqlstr.append(" ) GO ");
        db.execSQL(sqlstr.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
