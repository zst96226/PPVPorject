package com.example.beyondsys.ppv.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.anupcowkur.reservoir.Reservoir;
import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.entities.AccAndPwd;
import com.example.beyondsys.ppv.entities.IdentifyResult;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.tools.WecommPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends AppCompatActivity {
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_welcome);
        try {
            Reservoir.init(this, 2048);
        } catch (Exception e) {
            e.printStackTrace();
        }

        skipActivity(2);

    }


    /**
     * 延迟多少秒进入主界面
     *
     * @param min 秒
     */
    private void skipActivity(int min) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(Welcome.this, Login.class);
                startActivity(intent);
                finish();

//                    Intent intent = new Intent(Welcome.this, MainPPVActivity.class);
//                    Log.i("main","FHZ");
//                    startActivity(intent);
//                    finish();
            }
        }, 1000 * min);
    }
}
