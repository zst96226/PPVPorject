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
        try {
            Reservoir.init(Welcome.this, 4096);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_welcome);
        skipActivity(2);

    }

    /**
     * 延迟多少秒进入主界面
     * @param min 秒
     */
    private void skipActivity(int min) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                UserLoginResultEntity  proof = null;
                AccAndPwd user=null;
                IdentifyResult label=null;
                try {
                    Log.i("proof try","FHZ");
                    if (Reservoir.contains(LocalDataLabel.Proof))
                    {
                        Log.i("proof","FHZ");
                        proof = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
                        Log.i("proof","FHZ");
                    }
                } catch (Exception e) {
                    Log.i("proof excep","FHZ");
                    e.printStackTrace();
                }
                try{
                    Log.i("USER try","FHZ");
                    if(Reservoir.contains(LocalDataLabel.AccAndPwd))
                    {
                        Log.i("USER","FHZ");
                        user=Reservoir.get(LocalDataLabel.AccAndPwd, AccAndPwd.class);
                        Log.i("USER","FHZ");
                    }
                }catch (Exception e){
                    Log.i("USER excep","FHZ");
                    e.printStackTrace();
                }
                try{
                    Log.i("label try","FHZ");
                    if(Reservoir.contains(LocalDataLabel.Label))
                    {
                        Log.i("label","FHZ");
                        label=Reservoir.get(LocalDataLabel.Label,IdentifyResult.class);
                        Log.i("label","FHZ");
                    }
                }catch (Exception e)
                {
                    Log.i("label excep","FHZ");
                    e.printStackTrace();
                }


                if(proof==null||user==null||label==null)
                {
                    Intent intent = new Intent(Welcome.this, Login.class);
                    Log.i("login","FHZ");
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(Welcome.this, MainPPVActivity.class);
                    Log.i("main","FHZ");
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000*min);
    }
}
