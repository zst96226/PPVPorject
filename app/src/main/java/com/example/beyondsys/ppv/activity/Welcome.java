package com.example.beyondsys.ppv.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.WecommPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends AppCompatActivity {
    // 首次使用程序的显示的欢迎图片
    private int[] ids = { R.drawable.webcom_frist_image,
            R.drawable.webcom_two_image, R.drawable.webcom_three_image,
            R.drawable.niwodai_welcom };

    SharedPreferences share;
    private List<View> guides = new ArrayList<View>();
    private ViewPager pager;
    private ImageView curDot;
    // 位移量
    private int offset;
    // 记录当前的位置
    private int curPos = 0;

    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_welcome);

        skipActivity(5);

    }


    /**
     * 延迟多少秒进入主界面
     * @param min 秒
     */
    private void skipActivity(int min) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(Welcome.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 1000*min);
    }
}
