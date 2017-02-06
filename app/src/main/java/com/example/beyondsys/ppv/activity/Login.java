package com.example.beyondsys.ppv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.LoginBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;

import java.util.List;

import static android.view.ViewGroup.*;

public class Login extends Activity implements OnClickListener  {
    /*本地缓存操作对象*/
    ACache mCache = null;
    // 声明控件对象
    private EditText et_name, et_pass;
    private Button mLoginButton,mLoginError,mRegister,ONLYTEST;
    int selectIndex=1;
    int tempSelect=selectIndex;
    boolean isReLogin=false;
    private int SERVER_FLAG=0;
    private RelativeLayout countryselect;
    private TextView coutry_phone_sn, coutryName;//
    // private String [] coutry_phone_sn_array,coutry_name_array;
    public final static int LOGIN_ENABLE=0x01;    //注册完毕了
    public final static int LOGIN_UNABLE=0x02;    //注册完毕了
    public final static int PASS_ERROR=0x03;      //注册完毕了
    public final static int NAME_ERROR=0x04;      //注册完毕了

    final Handler UiMangerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch(msg.what){
                case LOGIN_ENABLE:
                    mLoginButton.setClickable(true);
                    //mLoginButton.setText(R.string.login);
                    break;
                case LOGIN_UNABLE:
                    mLoginButton.setClickable(false);
                    break;
                case PASS_ERROR:

                    break;
                case NAME_ERROR:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button bt_pwd_eye;
    private TextWatcher username_watcher;
    private TextWatcher password_watcher;
    private TextView log_tex;



    private Handler threadHandler=new Handler(){
        public void handleMessage (Message msg){
            if (msg.what == ThreadAndHandlerLabel.UserLogin)
            {
                if(msg.obj!=null)
                {
                    String jsonStr=msg.obj.toString();
                    /*解析Json*/
                    UserLoginResultEntity entity= JsonEntity.ParsingJsonForUserLoginResult(jsonStr);
                    if(entity!=null){
                        switch (entity.Result)
                        {
                            case -1:
                                Toast.makeText(Login.this,"服务出现问题，请稍后再试", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                /*将凭据保存缓存*/
                                mCache.put(LocalDataLabel.Proof,entity);
                                /*获取运行期间所需的标识*/
                                LoginBusiness personnelVerify =new LoginBusiness();
                                personnelVerify.GetIdentifying(threadHandler,mCache);
                                break;
                            case 1:
                                Toast.makeText(Login.this,"密码错误，请重新输入或选择忘记密码", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(Login.this,"该账号不存在，请检查输入或联系管理员", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
                else
                {
                    Toast.makeText(Login.this,"服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            }
            else if (msg.what==ThreadAndHandlerLabel.GetIdentifying)
            {
                if (msg.obj!=null)
                {
                    String jsonStr=msg.obj.toString();
                    /*解析Json*/
                    List<TeamEntity> entity= JsonEntity.ParsingJsonForTeam(jsonStr);
                    /*缓存*/
                    String personArray = GsonUtil.getGson().toJson(entity);
                    mCache.put(LocalDataLabel.Label, personArray);
                    /*跳转主Activity*/
                    startActivity(new Intent(Login.this, MainPPVActivity.class));
                    Login.this.finish();
                }
                else
                {
                    Toast.makeText(Login.this,"服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            }
            else if (msg.what == ThreadAndHandlerLabel.CallAPIError)
            {
                Toast.makeText(Login.this,"请求失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==ThreadAndHandlerLabel.LocalNotdata)
            {
                Toast.makeText(Login.this,"缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mCache=ACache.get(this);
        et_name = (EditText) findViewById(R.id.username);
        et_pass = (EditText) findViewById(R.id.password);
        log_tex=(TextView)findViewById(R.id.log_tex);

        bt_username_clear = (Button)findViewById(R.id.bt_username_clear);
        bt_pwd_clear = (Button)findViewById(R.id.bt_pwd_clear);
        bt_pwd_eye = (Button)findViewById(R.id.bt_pwd_eye);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear.setOnClickListener(this);
        bt_pwd_eye.setOnClickListener(this);
        initWatcher();
        et_name.addTextChangedListener(username_watcher);
        et_pass.addTextChangedListener(password_watcher);

        mLoginButton = (Button) findViewById(R.id.login);
        mLoginError  = (Button) findViewById(R.id.login_error);
        mRegister    = (Button) findViewById(R.id.register);
        mLoginButton.setOnClickListener(this);
        mLoginError.setOnClickListener(this);
        mRegister.setOnClickListener(this);

    }
    /**
     * 手机号，密码输入控件公用这一个watcher
     */
    private void initWatcher() {
        username_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                et_pass.setText("");
                if (s.toString().length() > 0) {
                    bt_username_clear.setVisibility(View.VISIBLE);
                } else {
                    bt_username_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
        password_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                }else{
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.login:  //登陆
                login();
                break;
            case R.id.login_error: //无法登陆(忘记密码了吧)
                Intent login_error_intent=new Intent();
                login_error_intent.setClass(Login.this, ForgetPass.class);
                startActivity(login_error_intent);
                break;
            case R.id.register:    //注册新的用户
                Intent intent=new Intent();
                intent.setClass(Login.this, Register.class);
                startActivity(intent);

                break;
            case R.id.bt_username_clear:
                et_name.setText("");
                et_pass.setText("");
                break;
            case R.id.bt_pwd_clear:
                et_pass.setText("");
                break;
            case R.id.bt_pwd_eye:
                if(et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD)){
                    bt_pwd_eye.setBackgroundResource(R.drawable.openeye);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                }else{
                    bt_pwd_eye.setBackgroundResource(R.drawable.closeeye);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pass.setSelection(et_pass.getText().toString().length());
                break;
        }
    }
    /**
     * 登陆
     */
    private void login() {
        Log.e("登陆啦", "qqww");
//        boolean nameCheck= ValidaService.isNameLength(  et_name.getText().toString());
//        boolean passCheck=ValidaService.isPasswLength(et_pass.getText().toString())&&ValidaService.isPassword(et_pass.getText().toString());
//        if(!(nameCheck&&passCheck))
//        {
//            Log.e("登陆信息不对", "qqww");
//            log_tex.setVisibility(View.VISIBLE);
//            log_tex.setText("用户名或密码不正确");
//            return;
//        }
//        LoginBusiness personnelVerify =new LoginBusiness();
//        personnelVerify.UserLogin(et_name.getText().toString(), et_pass.getText().toString(), threadHandler);

        LoginBusiness loginBusiness=new LoginBusiness();
        loginBusiness.Login(et_name.getText().toString(), et_pass.getText().toString(), threadHandler);
        Log.e("登陆成功", "qqww");
        startActivity(new Intent(Login.this, MainPPVActivity.class));
        Login.this.finish();
    }
    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(isReLogin){
                Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
                mHomeIntent.addCategory(Intent.CATEGORY_HOME);
                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Login.this.startActivity(mHomeIntent);
            }else{
                Login.this.finish();
            }
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
