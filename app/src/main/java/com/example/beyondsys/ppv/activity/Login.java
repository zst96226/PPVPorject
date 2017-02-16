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

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.LoginBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.AccAndPwd;
import com.example.beyondsys.ppv.entities.IdentifyResult;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.PersonInfoEntity;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.tools.MD5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.view.ViewGroup.*;

public class Login extends Activity implements OnClickListener {
    /*本地缓存操作对象*/
    ACache mCache = null;
    // 声明控件对象
    private EditText et_name, et_pass;
    private Button mLoginButton, mLoginError, mRegister;
    boolean isReLogin = false;

    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button bt_pwd_eye;
    private TextWatcher username_watcher;
    private TextWatcher password_watcher;
    private TextView log_tex;
    private String UAcc,uPwd;

    private Handler threadHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ThreadAndHandlerLabel.UserLogin) {

                if (msg.obj != null) {
                    Log.i("登录返回值：" + msg.obj, "FHZ");
                    String jsonStr = msg.obj.toString();
                    /*解析Json*/
                    try {
                        UserLoginResultEntity entity = JsonEntity.ParsingJsonForUserLoginResult(jsonStr);
                        if (entity != null) {
                            switch (entity.LoginResult) {
                                case -1:
                                    Toast.makeText(Login.this, "服务出现问题，请稍后再试", Toast.LENGTH_SHORT).show();
                                    break;
                                case 0:
                                    /*将凭据保存缓存*/
                                    Reservoir.putAsync(LocalDataLabel.Proof, entity, new ReservoirPutCallback() {
                                        @Override
                                            public void onSuccess() {
                                            /*获取运行期间所需的标识*/
                                            LoginBusiness personnelVerify = new LoginBusiness();
                                            personnelVerify.UserLogo(threadHandler, mCache);
                                        }
                                        @Override
                                        public void onFailure(Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    break;
                                case 1:
                                    Toast.makeText(Login.this, "密码错误，请重新输入或选择忘记密码", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    Toast.makeText(Login.this, "该账号不存在，请检查输入或联系管理员", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    } catch (Exception e) {
                    }
                } else {
                    Toast.makeText(Login.this, "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == ThreadAndHandlerLabel.GetIdentifying) {
                if (msg.obj != null) {
                    String jsonStr = msg.obj.toString();
                    /*解析Json*/
                    if (!jsonStr.equals("anyType{}")) {
                        final IdentifyResult result = JsonEntity.ParseJsonForIdentifyResult(jsonStr);
                        if (result != null) {
                            switch (result.AccessResult)
                            {
                                case 0:
                                    /*存储账号和密码*/
                                    AccAndPwd user = new AccAndPwd(UAcc, uPwd);
                                    final String UID=result.UID;
                                    Reservoir.putAsync(LocalDataLabel.AccAndPwd, user, new ReservoirPutCallback() {
                                        @Override
                                        public void onSuccess() {
                                            /*存用户ID*/
                                            PersonInfoEntity entity =new PersonInfoEntity();
                                            entity.ID=UID;
                                            Reservoir.putAsync(LocalDataLabel.UserID, entity, new ReservoirPutCallback() {
                                                @Override
                                                public void onSuccess() {
                                                    /*存储团队信息*/
                                                    Reservoir.putAsync(LocalDataLabel.Label, result.Team, new ReservoirPutCallback() {
                                                        @Override
                                                        public void onSuccess() {
                                                            /*跳转主Activity*/
                                                            startActivity(new Intent(Login.this, MainPPVActivity.class));
                                                            Login.this.finish();
                                                        }

                                                        @Override
                                                        public void onFailure(Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailure(Exception e) {
                                                    e.printStackTrace();
                                                }
                                            });
                                        }
                                        @Override
                                        public void onFailure(Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    break;
                                case 1:
                                    Toast.makeText(Login.this, "请求失败，请重新尝试", Toast.LENGTH_SHORT).show();
                                    break;
                                case -3:
                                    Toast.makeText(Login.this, "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    } else {
                        Toast.makeText(Login.this, "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == ThreadAndHandlerLabel.CallAPIError) {
                Toast.makeText(Login.this, "请求失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            } else if (msg.what == ThreadAndHandlerLabel.LocalNotdata) {
                Toast.makeText(Login.this, "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        et_name = (EditText) findViewById(R.id.username);
        et_pass = (EditText) findViewById(R.id.password);
        log_tex = (TextView) findViewById(R.id.log_tex);

        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
        bt_pwd_eye = (Button) findViewById(R.id.bt_pwd_eye);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear.setOnClickListener(this);
        bt_pwd_eye.setOnClickListener(this);
        initWatcher();
        et_name.addTextChangedListener(username_watcher);
        et_pass.addTextChangedListener(password_watcher);

        mLoginButton = (Button) findViewById(R.id.login);
        mLoginError = (Button) findViewById(R.id.login_error);
        mRegister = (Button) findViewById(R.id.register);
        mLoginButton.setOnClickListener(this);
        mLoginError.setOnClickListener(this);
        mRegister.setOnClickListener(this);

        try {
            Reservoir.init(this, 2048);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                } else {
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
                Intent login_error_intent = new Intent();
                login_error_intent.setClass(Login.this, ForgetPass.class);
                startActivity(login_error_intent);
                break;
            case R.id.register:    //注册新的用户
                Intent intent = new Intent();
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
                if (et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    bt_pwd_eye.setBackgroundResource(R.drawable.openeye);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    bt_pwd_eye.setBackgroundResource(R.drawable.closeeye);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pass.setSelection(et_pass.getText().toString().length());
                break;
        }
    }

    /**
     * 登陆
     */
    private void login() {
//        boolean nameCheck= ValidaService.isNameLength(et_name.getText().toString());
//        boolean passCheck=ValidaService.isPasswLength(et_pass.getText().toString())&&ValidaService.isPassword(et_pass.getText().toString());
//        if(!(nameCheck&&passCheck))
//        {
//            Log.e("登陆信息不对", "qqww");
//            log_tex.setVisibility(View.VISIBLE);
//            log_tex.setText("用户名或密码不正确");
//            return;
//        }
//        log_tex.setVisibility(View.GONE);
//        log_tex.setText("");
        UAcc=et_name.getText().toString().trim();
        LoginBusiness loginBusiness = new LoginBusiness();
        uPwd = MD5.getMD5(et_pass.getText().toString().trim());
        loginBusiness.Login(et_name.getText().toString().trim(), uPwd, threadHandler);
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
            if (isReLogin) {
                Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
                mHomeIntent.addCategory(Intent.CATEGORY_HOME);
                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Login.this.startActivity(mHomeIntent);
            } else {
                Login.this.finish();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
