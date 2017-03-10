package com.example.beyondsys.ppv.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.UpdateManager;

public class Setting extends Activity {
    private LinearLayout back;
    private RelativeLayout fontSize,versionControl,nightModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final Context context=this;
        back = (LinearLayout) this.findViewById(R.id.dttail_back);
        fontSize=(RelativeLayout)this.findViewById(R.id.fontsize_layout);
        versionControl=(RelativeLayout)this.findViewById(R.id.versionControl_layout);
        nightModel=(RelativeLayout)this.findViewById(R.id.nightmodel_layout);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        versionControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //版本控制
                int i = getVerCode();
                Log.i("VERSION:i=", i + "");
                if (1 > i) {
                    // 这里来检测版本是否需要更新
                    Toast.makeText(context, "需更新",Toast.LENGTH_SHORT).show();
//                    UpdateManager mUpdateManager = new UpdateManager(context);
//                    mUpdateManager.checkUpdateInfo();
                } else {
                    Toast.makeText(context, "已经是最新版本了无需更新",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // 获取当前应用的版本号
    public int getVerCode() {
        int verCode = -1;
        try {
            verCode = getPackageManager().getPackageInfo("com.example.beyondsys.ppv", 0).versionCode;
            Log.i("VERSION:", verCode + "");
        } catch (PackageManager.NameNotFoundException e) {
        }
        return verCode;
    }
    private String getVersionName() throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
        String version = packInfo.versionName;
        return version;
    }
}
