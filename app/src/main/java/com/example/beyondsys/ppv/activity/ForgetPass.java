package com.example.beyondsys.ppv.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.beyondsys.ppv.R;
/**
 * Created by yhp on 2017/1/20.
 */
public class ForgetPass extends AppCompatActivity {
    private ImageView back;
    private LinearLayout phone_layout,email_layout,service_layout,sub_layout;
    private RelativeLayout sure_layout,cancel_layout;
    private TextView road_tex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        init();
     setListener();
    }
    private  void init ()
    {
        back = (ImageView) this.findViewById(R.id.dttail_back);
        phone_layout=(LinearLayout)findViewById(R.id.phone_layout);
        email_layout=(LinearLayout)findViewById(R.id.email_layout);
        service_layout=(LinearLayout)findViewById(R.id.service_layout);
        sub_layout=(LinearLayout)findViewById(R.id.sub_layout);
        sure_layout=(RelativeLayout)findViewById(R.id.sure);
        cancel_layout=(RelativeLayout)findViewById(R.id.cancel);
        road_tex=(TextView)findViewById(R.id.road_tex);
    }
    private void setListener()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        phone_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((email_layout.getVisibility()==View.VISIBLE||service_layout.getVisibility()==View.VISIBLE)&&sub_layout.getVisibility()==View.GONE)
                {
                    email_layout.setVisibility(View.GONE);
                    service_layout.setVisibility(View.GONE);
                    sub_layout.setVisibility(View.VISIBLE);
                    road_tex.setText(getResources().getString(R.string.userPhone));
                }
                else
                {
                    email_layout.setVisibility(View.VISIBLE);
                    service_layout.setVisibility(View.VISIBLE);
                    sub_layout.setVisibility(View.GONE);
                }
            }
        });
        email_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((phone_layout.getVisibility()==View.VISIBLE||service_layout.getVisibility()==View.VISIBLE)&&sub_layout.getVisibility()==View.GONE)
                {
                    phone_layout.setVisibility(View.GONE);
                    service_layout.setVisibility(View.GONE);
                    sub_layout.setVisibility(View.VISIBLE);
                    road_tex.setText(getResources().getString(R.string.userEmail));
                }
                else
                {
                    phone_layout.setVisibility(View.VISIBLE);
                    service_layout.setVisibility(View.VISIBLE);
                    sub_layout.setVisibility(View.GONE);
                }
            }

        });
        service_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_layout.setVisibility(View.GONE);
//                if((phone_layout.getVisibility()==View.VISIBLE||email_layout.getVisibility()==View.VISIBLE)&&sub_layout.getVisibility()==View.GONE)
//                {
//                    phone_layout.setVisibility(View.GONE);
//                    email_layout.setVisibility(View.GONE);
//                    sub_layout.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    phone_layout.setVisibility(View.VISIBLE);
//                    email_layout.setVisibility(View.VISIBLE);
//                    sub_layout.setVisibility(View.GONE);
//                }
                AlertDialog.Builder builder  = new AlertDialog.Builder(ForgetPass.this);
                builder.setTitle("联系客服") ;
                builder.setMessage("请拨打电话：1111111，按照语音提示输入验证信息！") ;
                builder.setPositiveButton("知道了",null);
                builder.show();
            }

        });
        sure_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(ForgetPass.this);
                builder.setTitle("找回成功") ;
                builder.setMessage("密码重置链接及验证码已发送至您的手机或邮箱,有效期为10分钟,请注意查收！") ;
                builder.setPositiveButton("知道了",null);
                builder.show();
            }
        });
        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub_layout.setVisibility(View.GONE);
                phone_layout.setVisibility(View.VISIBLE);
                email_layout.setVisibility(View.VISIBLE);
                service_layout.setVisibility(View.VISIBLE);
            }
        });
    }
}
