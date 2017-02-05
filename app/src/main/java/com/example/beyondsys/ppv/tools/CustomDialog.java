package com.example.beyondsys.ppv.tools;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.beyondsys.ppv.R;

/**
 * Created by Sysmagic on 2017/1/18.
 */
public class CustomDialog extends Dialog {
    private EditText oldpass_edt,newpass_edt,checkpass_edt;
    private Button positiveButton, negativeButton;
    private TextView title;

    public CustomDialog(Context context) {
        super(context,R.style.CustomDialog);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.changepassword, null);
        title = (TextView) mView.findViewById(R.id.title);
        oldpass_edt = (EditText) mView.findViewById(R.id.oldpass_edt);
        newpass_edt=(EditText) mView.findViewById(R.id.newpass_edt);
        checkpass_edt=(EditText) mView.findViewById(R.id.checkpass_edt);
        positiveButton = (Button) mView.findViewById(R.id.positiveButton);
        negativeButton = (Button) mView.findViewById(R.id.negativeButton);
        super.setContentView(mView);
    }

    public String getNewPass()
    {
        String newpass="";
        if(TextUtils.isEmpty(newpass_edt.getText()))
        {
            return newpass;
        }
        newpass=newpass_edt.getText().toString();
        return  newpass;
    }

    public  String getChePass()
    {
        String chepass="";
        if(TextUtils.isEmpty(checkpass_edt.getText()))
        {
            return chepass;
        }
        chepass=checkpass_edt.getText().toString();
        return  chepass;
    }

    public String  getoldpass(){
        String  oldpass="";
        if (TextUtils.isEmpty(oldpass_edt.getText().toString()))
        {
            return  oldpass;
        }
       oldpass=oldpass_edt.getText().toString();
        return oldpass;
    }

    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener){
        positiveButton.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setOnNegativeListener(View.OnClickListener listener){
        negativeButton.setOnClickListener(listener);
    }
}
