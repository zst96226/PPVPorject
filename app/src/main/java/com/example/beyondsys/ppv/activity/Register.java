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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.ValidaService;

/**
 * Created by yhp on 2017/1/20.
 */
public class Register extends Activity {
    private LinearLayout back;
    private Button registe,reset;
    private RelativeLayout getCode_layout;
private EditText username_edt,userid_edt,phone_edt,pass_edt,checkpass_edt,checkcode_edt;
    private LinearLayout log_layout;
    private TextView log_tex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        setListener();
    }
    private void  init()
    {
        back = (LinearLayout) this.findViewById(R.id.dttail_back);
        registe=(Button)this.findViewById(R.id.registe_but);
        reset=(Button)findViewById(R.id.reset_but);
        getCode_layout=(RelativeLayout)findViewById(R.id.getCode_layout);
        username_edt=(EditText)findViewById(R.id.username_edt);
        userid_edt=(EditText)findViewById(R.id.userid_edt);
        phone_edt=(EditText)findViewById(R.id.phone_edt);
        checkpass_edt=(EditText)findViewById(R.id.checkpass_edt);
        pass_edt=(EditText)findViewById(R.id.password_edt);
        checkcode_edt=(EditText)findViewById(R.id.code_edt);
        log_layout=(LinearLayout)findViewById(R.id.log_layout);
        log_tex=(TextView)findViewById(R.id.log_tex);
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
                skipActivity(1);

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
        username_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    boolean uname = ValidaService.isNameLength(username_edt.getText().toString());
                    if (!uname) {
                        log_layout.setVisibility(View.VISIBLE);
                        log_tex.setText("用户名长度为2~25个字符");
                        return;
                    }
                    log_layout.setVisibility(View.GONE);
                    log_tex.setText("");
                }
            }
        });
        userid_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    boolean uID=ValidaService.isValidIdCard(userid_edt.getText().toString());
                    if(!uID)
                    {
                        log_layout.setVisibility(View.VISIBLE);
                        log_tex.setText("请输入正确的身份证号码！");
                        return;
                    }
                    log_layout.setVisibility(View.GONE);
                    log_tex.setText("");
                }
            }
        });
        pass_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    boolean upass=ValidaService.isPasswLength(pass_edt.getText().toString())&&ValidaService.isPassword(pass_edt.getText().toString());
                    if(!upass)
                    {
                        log_layout.setVisibility(View.VISIBLE);
                        log_tex.setText("密码由字母数字.*任两种组合而成长度为6~20！");
                        return;
                    }
                    log_layout.setVisibility(View.GONE);
                    log_tex.setText("");
                }
            }
        });
        checkcode_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    boolean reupass=checkcode_edt.getText().equals(pass_edt.getText());
                    if (!reupass)
                    {
                        log_layout.setVisibility(View.VISIBLE);
                        log_tex.setText("两次输入密码不一致！");
                        return;
                    }
                    log_layout.setVisibility(View.GONE);
                    log_tex.setText("");
                }
            }
        });
        phone_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    boolean uphone=ValidaService.isPhone(phone_edt.getText().toString());
                    if(!uphone)
                    {
                        log_layout.setVisibility(View.VISIBLE);
                        log_tex.setText("请输入正确的手机号码！");
                        return;
                    }
                    log_layout.setVisibility(View.GONE);
                    log_tex.setText("");
                }
            }
        });
        checkcode_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    //验证码的检验未写
                    boolean checkcode=false;
                    if(!checkcode)
                    {
                        log_layout.setVisibility(View.VISIBLE);
                        log_tex.setText("验证码不正确！");
                        return;
                    }
                    log_layout.setVisibility(View.GONE);
                    log_tex.setText("");
                }
            }
        });
    }

    private  boolean registe()
    {
        //注册验证
        //1.输入有效验证 2.用户名和id是否正确，是否已存在
        boolean uname= ValidaService.isNameLength(username_edt.getText().toString());
        boolean uID=ValidaService.isValidIdCard(userid_edt.getText().toString());
        boolean upass=ValidaService.isPasswLength(pass_edt.getText().toString())&&ValidaService.isPassword(pass_edt.getText().toString());
        boolean reupass=checkcode_edt.getText().equals(pass_edt.getText());
        boolean uphone=ValidaService.isPhone(phone_edt.getText().toString());
        //验证码的检验未写
        boolean checkcode=false;
        if(!(uname && uID && upass && reupass && uphone))
        {
            log_layout.setVisibility(View.VISIBLE);
            log_tex.setText("注册信息不正确，请检查后提交");
            return false;
        }
        if(!checkcode)
        {
            log_layout.setVisibility(View.VISIBLE);
            log_tex.setText("验证码错误!");
            return false;
        }
        log_layout.setVisibility(View.GONE);
        log_tex.setText("");
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
