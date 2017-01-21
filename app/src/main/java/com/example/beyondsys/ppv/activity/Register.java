package com.example.beyondsys.ppv.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.beyondsys.ppv.R;
/**
 * Created by yhp on 2017/1/20.
 */
public class Register extends Activity {
    private ImageView back;
    private Button registe,reset;
    private RelativeLayout getCode_layout;
private EditText username_edt,userid_edt,phone_edt,pass_edt,checkpass_edt,checkcode_edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        setListener();
    }
    private void  init()
    {
        back = (ImageView) this.findViewById(R.id.dttail_back);
        registe=(Button)this.findViewById(R.id.registe_but);
        reset=(Button)findViewById(R.id.reset_but);
        getCode_layout=(RelativeLayout)findViewById(R.id.getCode_layout);
        username_edt=(EditText)findViewById(R.id.username_edt);
        userid_edt=(EditText)findViewById(R.id.userid_edt);
        phone_edt=(EditText)findViewById(R.id.phone_edt);
        checkpass_edt=(EditText)findViewById(R.id.checkpass_edt);
        pass_edt=(EditText)findViewById(R.id.password_edt);
        checkcode_edt=(EditText)findViewById(R.id.code_edt);
    }
    private void setListener()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if( !registe())
               {
                   AlertDialog.Builder builder  = new AlertDialog.Builder(Register.this);
                   builder.setTitle("注册失败") ;
                   builder.setMessage("请正确填写注册信息！") ;
                   builder.setPositiveButton("知道了", null);
                   builder.show();
                   return;
               }
                AlertDialog.Builder builder  = new AlertDialog.Builder(Register.this);
                builder.setTitle("注册成功") ;
                builder.setMessage("请登陆后完善个人资料！") ;
                builder.setPositiveButton("知道了", null);
                builder.show();
                skipActivity(2);

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        getCode_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //发送验证码
            }
        });
    }
    private  boolean registe()
    {
        //注册验证
        //1.输入有效验证 2.用户名和id是否正确，是否已存在
        return true;
    }
    private void reset()
    {
        //清空
        userid_edt.setText("");
        username_edt.setText("");
        phone_edt.setText("");
        pass_edt.setText("");
        checkpass_edt.setText("");
        checkcode_edt.setText("");
    }
    /**
     * 延迟多少秒进入主界面
     * @param min 秒
     */
    private void skipActivity(int min) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 1000 * min);
    }
}
