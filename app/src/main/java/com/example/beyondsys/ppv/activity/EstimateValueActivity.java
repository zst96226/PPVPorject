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
    private LinearLayout count_layout,step1_layout,step2_layout,step3_layout,step4_layout,step5_layout,step6_layout,step7_layout,step8_layout,step9_layout,step10_layout;
    private EditText step1_max,step1_min,step1_scale,step2_max,step2_min,step2_scale,step3_max,step3_min,step3_scale
            ,step4_max,step4_min,step4_scale,step5_max,step5_min,step5_scale,step6_max,step6_min,step6_scale
            ,step7_max,step7_min,step7_scale,step8_max,step8_min,step8_scale,step9_max,step9_min,step9_scale
            ,step10_max,step10_min,step10_scale;
    private NumberPicker stepCount_num;
    private View mView;
    private Button ok_but,cancel_but;
    private  String stepDetail;
    private int stepCount=1;
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
        step2_layout=(LinearLayout)findViewById(R.id.step2_layout);
        step3_layout=(LinearLayout)findViewById(R.id.step3_layout);
        step4_layout=(LinearLayout)findViewById(R.id.step4_layout);
        step5_layout=(LinearLayout)findViewById(R.id.step5_layout);
        step6_layout=(LinearLayout)findViewById(R.id.step6_layout);
        step7_layout=(LinearLayout)findViewById(R.id.step7_layout);
        step8_layout=(LinearLayout)findViewById(R.id.step8_layout);
        step9_layout=(LinearLayout)findViewById(R.id.step9_layout);
        step10_layout=(LinearLayout)findViewById(R.id.step10_layout);
        stepCount_num=(NumberPicker)findViewById(R.id.stepCount_num);
        stepCount_num.setMaxValue(10);
        stepCount_num.setMinValue(1);
        stepCount_num.setValue(1);
        step1_max=(EditText)findViewById(R.id.step1_max);
        step1_min=(EditText)findViewById(R.id.step1_min);
        step1_scale=(EditText)findViewById(R.id.step1_scale);
        step2_max=(EditText)findViewById(R.id.step2_max);
        step2_min=(EditText)findViewById(R.id.step2_min);
        step2_scale=(EditText)findViewById(R.id.step2_scale);
        step3_max=(EditText)findViewById(R.id.step3_max);
        step3_min=(EditText)findViewById(R.id.step3_min);
        step3_scale=(EditText)findViewById(R.id.step3_scale);
        step4_max=(EditText)findViewById(R.id.step4_max);
        step4_min=(EditText)findViewById(R.id.step4_min);
        step4_scale=(EditText)findViewById(R.id.step4_scale);
        step5_max=(EditText)findViewById(R.id.step5_max);
        step5_min=(EditText)findViewById(R.id.step5_min);
        step5_scale=(EditText)findViewById(R.id.step5_scale);
        step6_max=(EditText)findViewById(R.id.step6_max);
        step6_min=(EditText)findViewById(R.id.step6_min);
        step6_scale=(EditText)findViewById(R.id.step6_scale);
        step7_max=(EditText)findViewById(R.id.step7_max);
        step7_min=(EditText)findViewById(R.id.step7_min);
        step7_scale=(EditText)findViewById(R.id.step7_scale);
        step8_max=(EditText)findViewById(R.id.step8_max);
        step8_min=(EditText)findViewById(R.id.step8_min);
        step8_scale=(EditText)findViewById(R.id.step8_scale);
        step9_max=(EditText)findViewById(R.id.step9_max);
        step9_min=(EditText)findViewById(R.id.step9_min);
        step9_scale=(EditText)findViewById(R.id.step9_scale);
        step10_max=(EditText)findViewById(R.id.step10_max);
        step10_min=(EditText)findViewById(R.id.step10_min);
        step10_scale=(EditText)findViewById(R.id.step10_scale);
        ok_but=(Button)findViewById(R.id.ok_dut);
        cancel_but=(Button)findViewById(R.id.cancel_but);
        stepCount_num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                stepCount = newVal;
            }
        });
    }

    public int getStepCount()
    {;
        return stepCount;
    }
    private void  stepShow()
    {
        switch (stepCount)
        {
            case 1:
                step1_layout.setVisibility(View.VISIBLE);
                step2_layout.setVisibility(View.GONE);
                step3_layout.setVisibility(View.GONE);
                step4_layout.setVisibility(View.GONE);
                step5_layout.setVisibility(View.GONE);
                step6_layout.setVisibility(View.GONE);
                step7_layout.setVisibility(View.GONE);
                step8_layout.setVisibility(View.GONE);
                step9_layout.setVisibility(View.GONE);
                step10_layout.setVisibility(View.GONE);
                break;
            case 2:
                step1_layout.setVisibility(View.VISIBLE);
                step2_layout.setVisibility(View.VISIBLE);
                step3_layout.setVisibility(View.GONE);
                step4_layout.setVisibility(View.GONE);
                step5_layout.setVisibility(View.GONE);
                step6_layout.setVisibility(View.GONE);
                step7_layout.setVisibility(View.GONE);
                step8_layout.setVisibility(View.GONE);
                step9_layout.setVisibility(View.GONE);
                step10_layout.setVisibility(View.GONE);
                break;
            case 3:
                step1_layout.setVisibility(View.VISIBLE);
                step2_layout.setVisibility(View.VISIBLE);
                step3_layout.setVisibility(View.VISIBLE);
                step4_layout.setVisibility(View.GONE);
                step5_layout.setVisibility(View.GONE);
                step6_layout.setVisibility(View.GONE);
                step7_layout.setVisibility(View.GONE);
                step8_layout.setVisibility(View.GONE);
                step9_layout.setVisibility(View.GONE);
                step10_layout.setVisibility(View.GONE);
                break;
            case 4:
                step1_layout.setVisibility(View.VISIBLE);
                step2_layout.setVisibility(View.VISIBLE);
                step3_layout.setVisibility(View.VISIBLE);
                step4_layout.setVisibility(View.VISIBLE);
                step5_layout.setVisibility(View.GONE);
                step6_layout.setVisibility(View.GONE);
                step7_layout.setVisibility(View.GONE);
                step8_layout.setVisibility(View.GONE);
                step9_layout.setVisibility(View.GONE);
                step10_layout.setVisibility(View.GONE);
                break;
            case 5:
                step1_layout.setVisibility(View.VISIBLE);
                step2_layout.setVisibility(View.VISIBLE);
                step3_layout.setVisibility(View.VISIBLE);
                step4_layout.setVisibility(View.VISIBLE);
                step5_layout.setVisibility(View.VISIBLE);
                step6_layout.setVisibility(View.GONE);
                step7_layout.setVisibility(View.GONE);
                step8_layout.setVisibility(View.GONE);
                step9_layout.setVisibility(View.GONE);
                step10_layout.setVisibility(View.GONE);
                break;
            case 6:
                step1_layout.setVisibility(View.VISIBLE);
                step2_layout.setVisibility(View.VISIBLE);
                step3_layout.setVisibility(View.VISIBLE);
                step4_layout.setVisibility(View.VISIBLE);
                step5_layout.setVisibility(View.VISIBLE);
                step6_layout.setVisibility(View.VISIBLE);
                step7_layout.setVisibility(View.GONE);
                step8_layout.setVisibility(View.GONE);
                step9_layout.setVisibility(View.GONE);
                step10_layout.setVisibility(View.GONE);
            break;
            case 7:
                step1_layout.setVisibility(View.VISIBLE);
                step2_layout.setVisibility(View.VISIBLE);
                step3_layout.setVisibility(View.VISIBLE);
                step4_layout.setVisibility(View.VISIBLE);
                step5_layout.setVisibility(View.VISIBLE);
                step6_layout.setVisibility(View.VISIBLE);
                step7_layout.setVisibility(View.VISIBLE);
                step8_layout.setVisibility(View.GONE);
                step9_layout.setVisibility(View.GONE);
                step10_layout.setVisibility(View.GONE);
                break;
            case 8:
                step1_layout.setVisibility(View.VISIBLE);
                step2_layout.setVisibility(View.VISIBLE);
                step3_layout.setVisibility(View.VISIBLE);
                step4_layout.setVisibility(View.VISIBLE);
                step5_layout.setVisibility(View.VISIBLE);
                step6_layout.setVisibility(View.VISIBLE);
                step7_layout.setVisibility(View.VISIBLE);
                step8_layout.setVisibility(View.VISIBLE);
                step9_layout.setVisibility(View.GONE);
                step10_layout.setVisibility(View.GONE);
                break;
            case 9:
                step1_layout.setVisibility(View.VISIBLE);
                step2_layout.setVisibility(View.VISIBLE);
                step3_layout.setVisibility(View.VISIBLE);
                step4_layout.setVisibility(View.VISIBLE);
                step5_layout.setVisibility(View.VISIBLE);
                step6_layout.setVisibility(View.VISIBLE);
                step7_layout.setVisibility(View.VISIBLE);
                step8_layout.setVisibility(View.VISIBLE);
                step9_layout.setVisibility(View.VISIBLE);
                step10_layout.setVisibility(View.GONE);
                break;
            case 10:
                step1_layout.setVisibility(View.VISIBLE);
                step2_layout.setVisibility(View.VISIBLE);
                step3_layout.setVisibility(View.VISIBLE);
                step4_layout.setVisibility(View.VISIBLE);
                step5_layout.setVisibility(View.VISIBLE);
                step6_layout.setVisibility(View.VISIBLE);
                step7_layout.setVisibility(View.VISIBLE);
                step8_layout.setVisibility(View.VISIBLE);
                step9_layout.setVisibility(View.VISIBLE);
                step10_layout.setVisibility(View.VISIBLE);
            break;
            default:
                break;

        }
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
