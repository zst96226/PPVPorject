package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.beyondsys.ppv.R;

public class EstimateValueActivity extends AppCompatActivity {
    private LinearLayout count_layout,step1_layout;
    private EditText step1_max,step1_min,step1_scale;
    private NumberPicker stepCount;
    private View mView;
    private Button ok_but,cancel_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_value);
       init();
        setOkListener();
        setCancelListener();
    }
    private void init()
    {
        count_layout=(LinearLayout)findViewById(R.id.stepCount_layout);
        step1_layout=(LinearLayout)findViewById(R.id.step1_layout);
        stepCount=(NumberPicker)findViewById(R.id.stepCount_num);
        stepCount.setMaxValue(10);
        stepCount.setMinValue(1);
        stepCount.setValue(1);
        step1_max=(EditText)findViewById(R.id.step1_max);
        step1_min=(EditText)findViewById(R.id.step1_min);
        step1_scale=(EditText)findViewById(R.id.step1_scale);
        ok_but=(Button)findViewById(R.id.ok_dut);
        cancel_but=(Button)findViewById(R.id.cancel_but);
    }
    public String  getStepCount()
    {
        String text="";
        text= stepCount.getValue()+"";
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
    /**
     * 确定键监听器
     */
    public   void setOkListener()
    {
        ok_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rIntent = new Intent();
                rIntent.putExtra("stepCount", getStepCount());
                rIntent.putExtra("stepDetail", getStepDetail());
                setResult(1, getIntent());
                finish();
            }
        });
    }
    /**
     * 取消键监听器
     */
    public void setCancelListener()
    {
        cancel_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rIntent = new Intent();
                rIntent.putExtra("stepCount", "1");
                rIntent.putExtra("stepDetail", "");
                setResult(2, getIntent());
                finish();
            }
        });
    }
}
