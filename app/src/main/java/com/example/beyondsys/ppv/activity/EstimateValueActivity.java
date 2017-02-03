package com.example.beyondsys.ppv.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.beyondsys.ppv.R;

public class EstimateValueActivity extends AppCompatActivity {
    private LinearLayout count_layout,step1_layout;
    private EditText stepCount,step1_max,step1_min,step1_scale;
    private View mView;
    private Button ok_but,cancel_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_value);
        init();
    }
    private void init()
    {
        count_layout=(LinearLayout)findViewById(R.id.stepCount_layout);
        step1_layout=(LinearLayout)findViewById(R.id.step1_layout);
        stepCount=(EditText)findViewById(R.id.stepCount_edt);
        step1_max=(EditText)findViewById(R.id.step1_max);
        step1_min=(EditText)findViewById(R.id.step1_min);
        step1_scale=(EditText)findViewById(R.id.step1_scale);
        ok_but=(Button)findViewById(R.id.ok_dut);
        cancel_but=(Button)findViewById(R.id.cancel_but);
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
