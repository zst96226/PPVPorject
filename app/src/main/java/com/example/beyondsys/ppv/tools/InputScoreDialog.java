package com.example.beyondsys.ppv.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.beyondsys.ppv.R;

/**
 * Created by yhp on 2017/1/23.
 */
public class InputScoreDialog extends Dialog {
    private LinearLayout count_layout,step1_layout;
    private EditText stepCount,step1_max,step1_min,step1_scale;
    private View mView;
    private Button ok_but,cancel_but;

    public InputScoreDialog(Context context) {
        super(context,R.style.CustomDialog);
        setInputScoreDialog();
    }
    private void setInputScoreDialog()
    {
        mView=LayoutInflater.from(getContext()).inflate(R.layout.dialog_inputscore,null);
        count_layout=(LinearLayout)mView.findViewById(R.id.stepCount_layout);
        step1_layout=(LinearLayout)mView.findViewById(R.id.step1_layout);
        stepCount=(EditText)mView.findViewById(R.id.stepCount_edt);
        step1_max=(EditText)mView.findViewById(R.id.step1_max);
        step1_min=(EditText)mView.findViewById(R.id.step1_min);
        step1_scale=(EditText)mView.findViewById(R.id.step1_scale);
        ok_but=(Button)mView.findViewById(R.id.ok_dut);
        cancel_but=(Button)mView.findViewById(R.id.cancel_but);
        super.setContentView(mView);
    }

    public String  getStepCount()
    {
        String text="";
        text= stepCount.getText().toString();
        return text;
    }

    public String getStepDetail()
    {
        String text="共分为"+getStepCount()+"个步骤";//传递参数
        if(null!=step1_layout)
        {
            text+="第一步："+"max:"+step1_max.getText().toString()+"min:"+step1_min.getText().toString()+"scale:"+step1_scale.getText().toString();
        }

        return text;
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
   public   void setOkListener(View.OnClickListener listener)
    {
        ok_but.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setCancelListener(View.OnClickListener listener)
    {
        cancel_but.setOnClickListener(listener);
    }
}
