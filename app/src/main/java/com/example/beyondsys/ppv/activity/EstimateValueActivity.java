package com.example.beyondsys.ppv.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.beyondsys.ppv.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstimateValueActivity extends AppCompatActivity {
    private LinearLayout count_layout,step1_layout,step2_layout,step3_layout,step4_layout,step5_layout,step6_layout,step7_layout,step8_layout,step9_layout,step10_layout;
    private EditText step1_name,step1_max,step1_min,step1_scale, step2_name,step2_max,step2_min,step2_scale,
            step3_name,step3_max,step3_min,step3_scale, step4_name,step4_max,step4_min,step4_scale,
            step5_name,step5_max,step5_min,step5_scale, step6_name,step6_max,step6_min,step6_scale,
            step7_name,step7_max,step7_min,step7_scale, step8_name,step8_max,step8_min,step8_scale,
            step9_name,step9_max,step9_min,step9_scale, step10_name,step10_max,step10_min,step10_scale;
    private NumberPicker stepCount_num;
    private View mView;
    private Button ok_but,cancel_but;
    private  String stepDetail;
    private int stepCount=1,flag=-1;
    private LinearLayout back;
    private ImageView ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_estimate_value);
        init();
//        setOkListener();
//        setCancelListener();
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
        stepCount_num.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        step1_name=(EditText)findViewById(R.id.step1_name);
        step1_max=(EditText)findViewById(R.id.step1_max);
        step1_min=(EditText)findViewById(R.id.step1_min);
        step1_scale=(EditText)findViewById(R.id.step1_scale);
        step2_name=(EditText)findViewById(R.id.step2_name);
        step2_max=(EditText)findViewById(R.id.step2_max);
        step2_min=(EditText)findViewById(R.id.step2_min);
        step2_scale=(EditText)findViewById(R.id.step2_scale);
        step3_name=(EditText)findViewById(R.id.step3_name);
        step3_max=(EditText)findViewById(R.id.step3_max);
        step3_min=(EditText)findViewById(R.id.step3_min);
        step3_scale=(EditText)findViewById(R.id.step3_scale);
        step4_name=(EditText)findViewById(R.id.step4_name);
        step4_max=(EditText)findViewById(R.id.step4_max);
        step4_min=(EditText)findViewById(R.id.step4_min);
        step4_scale=(EditText)findViewById(R.id.step4_scale);
        step5_name=(EditText)findViewById(R.id.step5_name);
        step5_max=(EditText)findViewById(R.id.step5_max);
        step5_min=(EditText)findViewById(R.id.step5_min);
        step5_scale=(EditText)findViewById(R.id.step5_scale);
        step6_name=(EditText)findViewById(R.id.step6_name);
        step6_max=(EditText)findViewById(R.id.step6_max);
        step6_min=(EditText)findViewById(R.id.step6_min);
        step6_scale=(EditText)findViewById(R.id.step6_scale);
        step7_name=(EditText)findViewById(R.id.step7_name);
        step7_max=(EditText)findViewById(R.id.step7_max);
        step7_min=(EditText)findViewById(R.id.step7_min);
        step7_scale=(EditText)findViewById(R.id.step7_scale);
        step8_name=(EditText)findViewById(R.id.step8_name);
        step8_max=(EditText)findViewById(R.id.step8_max);
        step8_min=(EditText)findViewById(R.id.step8_min);
        step8_scale=(EditText)findViewById(R.id.step8_scale);
        step9_name=(EditText)findViewById(R.id.step9_name);
        step9_max=(EditText)findViewById(R.id.step9_max);
        step9_min=(EditText)findViewById(R.id.step9_min);
        step9_scale=(EditText)findViewById(R.id.step9_scale);
        step10_name=(EditText)findViewById(R.id.step10_name);
        step10_max=(EditText)findViewById(R.id.step10_max);
        step10_min=(EditText)findViewById(R.id.step10_min);
        step10_scale=(EditText)findViewById(R.id.step10_scale);
        back=(LinearLayout)this.findViewById(R.id.anwi_back);
        ok=(ImageView)this.findViewById(R.id.anwi_ok);
        setPricePoint(step1_scale);
        setPricePoint(step2_scale);
        setPricePoint(step3_scale);
        setPricePoint(step4_scale);
        setPricePoint(step5_scale);
        setPricePoint(step6_scale);
        setPricePoint(step7_scale);
        setPricePoint(step8_scale);
        setPricePoint(step9_scale);
        setPricePoint(step10_scale);
//        ok_but=(Button)findViewById(R.id.ok_dut);
//        cancel_but=(Button)findViewById(R.id.cancel_but);
        stepCount_num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                stepCount = newVal;
                stepShow();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除焦点，确保提交的难度系数都满足条件
                step1_scale.clearFocus();
                step2_scale.clearFocus();
                step3_scale.clearFocus();
                step4_scale.clearFocus();
                step5_scale.clearFocus();
                step6_scale.clearFocus();
                step7_scale.clearFocus();
                step8_scale.clearFocus();
                step9_scale.clearFocus();
                step10_scale.clearFocus();
                AlertDialog.Builder builder=new AlertDialog.Builder(EstimateValueActivity.this);
                builder.setTitle("是否保存更改？");
                builder.setMessage("");
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        step1_scale.clearFocus();
                        double result=0.00,sumScale=0.00;
                        Log.e("aaaa" + getStepDetail(), "qqww");
                        ArrayList<String> valueParam=param();
                        if(valueParam==null||valueParam.size()==0)
                        {
                            Log.i("kong  ","FHZ");
                        }else{
                            Log.i(" not kong  ","FHZ");
                            for (int i=0;i<valueParam.size()-3;i=i+3)
                            {
                                String maxStr=valueParam.get(i);
                                String minStr=valueParam.get(i+1);
                                String scaleStr=valueParam.get(i+2);
                                Double max,min,scale;
                                if(maxStr!=null&&!maxStr.isEmpty())
                                {
                                    max= Double.valueOf(maxStr);
                                }else
                                {
                                    max=0.00;
                                }
                                if(minStr!=null&&!minStr.isEmpty())
                                {
                                    min=Double.valueOf(minStr);
                                }else{
                                    min=0.00;
                                }
                                if(scaleStr!=null&&!scaleStr.isEmpty())
                                {
                                    scale=Double.valueOf(scaleStr);
                                }else{
                                    scale=1.00;
                                }
                                result += computValue(max,min,scale);
                                sumScale+=scale*computValue(max,min,scale);
                                Log.i("max:" + max, "FHZ");
                                Log.i("min:"+min,"FHZ");
                                Log.i("scale:"+scale,"FHZ");
                                Log.i("result:"+result,"FHZ");
                            }
                            if(result>0)
                            {
                                sumScale=sumScale/result;
                            }else{
                                sumScale=1;
                            }

                        }
                        Intent rIntent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("stepCount",String.valueOf(getStepCount()));
                        bundle.putString("stepDetail", getStepDetail());
                        bundle.putString("sumScale",String.valueOf(sumScale));
                        bundle.putString("valueParam", String.valueOf(result));
                        //bundle.putSerializable("valueParam", (Serializable) getValueParam());
                        rIntent.putExtras(bundle);
                        setResult(1, rIntent);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent rIntent = new Intent();
                        rIntent.putExtra("stepCount",getStepCount());
                        rIntent.putExtra("stepDetail", "");
                        rIntent.putExtra("valueParam","");
                        setResult(2, rIntent);
                        finish();
                    }
                });
                builder.show();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除焦点，确保提交的难度系数都满足条件
                step1_scale.clearFocus();
                step2_scale.clearFocus();
                step3_scale.clearFocus();
                step4_scale.clearFocus();
                step5_scale.clearFocus();
                step6_scale.clearFocus();
                step7_scale.clearFocus();
                step8_scale.clearFocus();
                step9_scale.clearFocus();
                step10_scale.clearFocus();
                double result=0.00,sumScale=0.00;
                Log.e("aaaa" + getStepDetail(), "qqww");
                ArrayList<String> valueParam=param();
                if(valueParam==null||valueParam.size()==0)
                {
                    Log.i("kong  ","FHZ");
                }else{
                    Log.i(" not kong  ","FHZ");
                    for (int i=0;i<valueParam.size()-3;i=i+3)
                    {
                        String maxStr=valueParam.get(i);
                        String minStr=valueParam.get(i+1);
                        String scaleStr=valueParam.get(i+2);
                        Double max,min,scale;
                        if(maxStr!=null&&!maxStr.isEmpty())
                        {
                            max= Double.valueOf(maxStr);
                        }else
                        {
                            max=0.00;
                        }
                        if(minStr!=null&&!minStr.isEmpty())
                        {
                            min=Double.valueOf(minStr);
                        }else{
                            min=0.00;
                        }
                        if(scaleStr!=null&&!scaleStr.isEmpty())
                        {
                            scale=Double.valueOf(scaleStr);
                        }else{
                            scale=1.00;
                        }
                        result += computValue(max,min,scale);
                        sumScale+=scale*computValue(max,min,scale);
                        Log.i("max:" + max, "FHZ");
                        Log.i("min:"+min,"FHZ");
                        Log.i("scale:"+scale,"FHZ");
                        Log.i("result:"+result,"FHZ");
                    }
                    if(result>0)
                    {
                        sumScale=sumScale/result;
                    }else{
                        sumScale=1;
                    }

                }
                Intent rIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("stepCount",String.valueOf(getStepCount()));
                bundle.putString("stepDetail", getStepDetail());
                bundle.putString("sumScale",String.valueOf(sumScale));
                bundle.putString("valueParam", String.valueOf(result));
                //bundle.putSerializable("valueParam", (Serializable) getValueParam());
                rIntent.putExtras(bundle);

                setResult(1, rIntent);
                finish();
            }
        });
    }
    private  double computValue(double max,double min,double scale )
    {
        double  value=0;
       if(max<min)
       {
           double d=max;
           max=min;
           min=d;
       }
        value=((max/scale)+(min*scale))/2;
        String s=String.valueOf(value);
        if(s.contains("."))
        {
            if (s.length() - 1 - s.indexOf(".") > 2) {
                s = (String) s.subSequence(0, s.toString().indexOf(".") + 3);
                value=Double.valueOf(s);
            }
        }
        return  value;
    }
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
//                if(!s.toString().isEmpty())
//                {
//                    double d=Double.valueOf(s.toString().trim());
//                    if(d>2.0||d<0.5)
//                    {
//                        editText.setText("1");
//                        editText.setSelection(1);
//                    }
//                }


            }

        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    String s=editText.getText().toString().trim();
                    if(!s.isEmpty())
                    {
                        try{
                            double d=Double.valueOf(s.trim());
                            if(d>2.0||d<0.5)
                            {
                                editText.setText("1");
                                editText.setSelection(1);
                            }

                        }catch (Exception e){}
                    }
                }
            }
        });

    }

    public  ArrayList<String> param()
    {
        ArrayList<String> list=new ArrayList<String>();
        list.add((step1_max.getText().toString().isEmpty())?(""):(step1_max.getText().toString().trim()));
        list.add((step1_min.getText().toString().isEmpty())?(""):(step1_min.getText().toString().trim()));
        list.add((step1_scale.getText().toString().isEmpty())?(""):(step1_scale.getText().toString().trim()));

        list.add((step2_max.getText().toString().isEmpty())?(""):(step2_max.getText().toString().trim()));
        list.add((step2_min.getText().toString().isEmpty())?(""):(step2_min.getText().toString().trim()));
        list.add((step2_scale.getText().toString().isEmpty())?(""):(step2_scale.getText().toString().trim()));

        list.add((step3_max.getText().toString().isEmpty())?(""):(step3_max.getText().toString().trim()));
        list.add((step3_min.getText().toString().isEmpty())?(""):(step3_min.getText().toString().trim()));
        list.add((step3_scale.getText().toString().isEmpty())?(""):(step3_scale.getText().toString().trim()));

        list.add((step4_max.getText().toString().isEmpty())?(""):(step4_max.getText().toString().trim()));
        list.add((step4_min.getText().toString().isEmpty())?(""):(step4_min.getText().toString().trim()));
        list.add((step4_scale.getText().toString().isEmpty())?(""):(step4_scale.getText().toString().trim()));

        list.add((step5_max.getText().toString().isEmpty())?(""):(step5_max.getText().toString().trim()));
        list.add((step5_min.getText().toString().isEmpty())?(""):(step5_min.getText().toString().trim()));
        list.add((step5_scale.getText().toString().isEmpty())?(""):(step5_scale.getText().toString().trim()));

        list.add((step6_max.getText().toString().isEmpty())?(""):(step6_max.getText().toString().trim()));
        list.add((step6_min.getText().toString().isEmpty())?(""):(step6_min.getText().toString().trim()));
        list.add((step6_scale.getText().toString().isEmpty())?(""):(step6_scale.getText().toString().trim()));

        list.add((step7_max.getText().toString().isEmpty())?(""):(step7_max.getText().toString().trim()));
        list.add((step7_min.getText().toString().isEmpty())?(""):(step7_min.getText().toString().trim()));
        list.add((step7_scale.getText().toString().isEmpty())?(""):(step7_scale.getText().toString().trim()));

        list.add((step8_max.getText().toString().isEmpty())?(""):(step8_max.getText().toString().trim()));
        list.add((step8_min.getText().toString().isEmpty())?(""):(step8_min.getText().toString().trim()));
        list.add((step8_scale.getText().toString().isEmpty())?(""):(step8_scale.getText().toString().trim()));

        list.add((step9_max.getText().toString().isEmpty())?(""):(step9_max.getText().toString().trim()));
        list.add((step9_min.getText().toString().isEmpty())?(""):(step9_min.getText().toString().trim()));
        list.add((step9_scale.getText().toString().isEmpty())?(""):(step9_scale.getText().toString().trim()));

        list.add((step10_max.getText().toString().isEmpty())?(""):(step10_max.getText().toString().trim()));
        list.add((step10_min.getText().toString().isEmpty())?(""):(step10_min.getText().toString().trim()));
        list.add((step10_scale.getText().toString().isEmpty())?(""):(step10_scale.getText().toString().trim()));

        return list;
    }
    public List<Map<String,Object>> getValueParam()
    {
        List<Map<String,Object>> mylist=new ArrayList<>();
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("max",step1_max.getText().toString().trim());
            map1.put("min",step1_min.getText().toString().trim());
            map1.put("scale",step1_scale.getText().toString().trim());
            mylist.add(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("max",step2_max.getText().toString().trim());
        map2.put("min",step2_min.getText().toString().trim());
        map2.put("scale", step2_scale.getText().toString().trim());
        mylist.add(map2);
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("max",step3_max.getText().toString().trim());
        map3.put("min",step3_min.getText().toString().trim());
        map3.put("scale",step3_scale.getText().toString().trim());
        mylist.add(map3);
        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("max",step4_max.getText().toString().trim());
        map4.put("min",step4_min.getText().toString().trim());
        map4.put("scale",step4_scale.getText().toString().trim());
        mylist.add(map4);
        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("max",step5_max.getText().toString().trim());
        map5.put("min",step5_min.getText().toString().trim());
        map5.put("scale",step5_scale.getText().toString().trim());
        mylist.add(map5);
        Map<String, Object> map6 = new HashMap<String, Object>();
        map6.put("max",step6_max.getText().toString().trim());
        map6.put("min",step6_min.getText().toString().trim());
        map6.put("scale",step6_scale.getText().toString().trim());
        mylist.add(map6);
        Map<String, Object> map7 = new HashMap<String, Object>();
        map7.put("max",step7_max.getText().toString().trim());
        map7.put("min",step7_min.getText().toString().trim());
        map7.put("scale",step7_scale.getText().toString().trim());
        mylist.add(map7);
        Map<String, Object> map8 = new HashMap<String, Object>();
        map8.put("max",step8_max.getText().toString().trim());
        map8.put("min",step8_min.getText().toString().trim());
        map8.put("scale",step8_scale.getText().toString().trim());
        mylist.add(map8);
        Map<String, Object> map9 = new HashMap<String, Object>();
        map9.put("max",step9_max.getText().toString().trim());
        map9.put("min",step9_min.getText().toString().trim());
        map9.put("scale",step9_scale.getText().toString().trim());
        mylist.add(map9);
        Map<String, Object> map10 = new HashMap<String, Object>();
        map10.put("max",step10_max.getText().toString().trim());
        map10.put("min",step10_min.getText().toString().trim());
        map10.put("scale",step10_scale.getText().toString().trim());
        mylist.add(map10);

        return mylist;
    }
    public int getStepCount()
    {
        return stepCount;
    }
    private void  stepShow()
    {
        switch (getStepCount())
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
        String text="";
     switch (stepCount)
     {
         case 1:
//             builder.append("共分为一个步骤：\\n第一步：");
//             Log.e(builder.toString(), "qwe");
//             builder.append(step1_name.getText().toString());
             text="共分为一个步骤："
                    + "第一步："+step1_name.getText().toString()
                     +"max:"+step1_max.getText().toString()
                     +"min:"+step1_min.getText().toString()
                     +"scale:"+step1_scale.getText().toString();
             Log.e("hghhvvjhbj","qwe");
             break;
         case 2:
             text="共分为二个步骤："
                     +"第一步："+step1_name.getText().toString()
                     +"max:"+step1_max.getText().toString()
                     +"min:"+step1_min.getText().toString()
                     +"scale:"+step1_scale.getText().toString()
                     +"\n"+
                   "第二步："+step2_name.getText().toString()
                     +"max:"+step2_max.getText().toString()
                     +"min:"+step2_min.getText().toString()
                     +"scale:"+step2_scale.getText().toString();
             break;
         case 3:
             text="共分为三个步骤："
                    +"第一步："+step1_name.getText().toString()
                     +"max:"+step1_max.getText().toString()
                     +"min:"+step1_min.getText().toString()
                     +"scale:"+step1_scale.getText().toString()
                    +
                     "第二步："+step2_name.getText().toString()
                     +"max:"+step2_max.getText().toString()
                     +"min:"+step2_min.getText().toString()
                     +"scale:"+step2_scale.getText().toString()
                    +
                     "第三步："+step3_name.getText().toString()
                     +"max:"+step3_max.getText().toString()
                     +"min:"+step3_min.getText().toString()
                     +"scale:"+step3_scale.getText().toString();
             break;
         case 4:
             text="共分为四个步骤："
                    +
                     "第一步："+step1_name.getText().toString()
                     +"max:"+step1_max.getText().toString()
                     +"min:"+step1_min.getText().toString()
                     +"scale:"+step1_scale.getText().toString()
                     +
                     "第二步："+step2_name.getText().toString()
                     +"max:"+step2_max.getText().toString()
                     +"min:"+step2_min.getText().toString()
                     +"scale:"+step2_scale.getText().toString()
                    +
                     "第三步："+step3_name.getText().toString()
                     +"max:"+step3_max.getText().toString()
                     +"min:"+step3_min.getText().toString()
                     +"scale:"+step3_scale.getText().toString()
                    +
                     "第四步："+step3_name.getText().toString()
                     +"max:"+step3_max.getText().toString()
                     +"min:"+step3_min.getText().toString()
                     +"scale:"+step3_scale.getText().toString();
            break;
         case 5:
             text="共分为五个步骤："
                     +
                     "第一步："+step1_name.getText().toString()
                     +"max:"+step1_max.getText().toString()
                     +"min:"+step1_min.getText().toString()
                     +"scale:"+step1_scale.getText().toString()
                    +
                     "第二步："+step2_name.getText().toString()
                     +"max:"+step2_max.getText().toString()
                     +"min:"+step2_min.getText().toString()
                     +"scale:"+step2_scale.getText().toString()
                   +
                     "第三步："+step3_name.getText().toString()
                     +"max:"+step3_max.getText().toString()
                     +"min:"+step3_min.getText().toString()
                     +"scale:"+step3_scale.getText().toString()
                    +
                     "第四步："+step4_name.getText().toString()
                     +"max:"+step4_max.getText().toString()
                     +"min:"+step4_min.getText().toString()
                     +"scale:"+step4_scale.getText().toString()
                    +
                     "第五步："+step5_name.getText().toString()
                     +"max:"+step5_max.getText().toString()
                     +"min:"+step5_min.getText().toString()
                     +"scale:"+step5_scale.getText().toString();
            break;
         case 6:
             text="共分为六个步骤："
                   +
                     "第一步："+step1_name.getText().toString()
                     +"max:"+step1_max.getText().toString()
                     +"min:"+step1_min.getText().toString()
                     +"scale:"+step1_scale.getText().toString()
                    +
                     "第二步："+step2_name.getText().toString()
                     +"max:"+step2_max.getText().toString()
                     +"min:"+step2_min.getText().toString()
                     +"scale:"+step2_scale.getText().toString()
                   +
                     "第三步："+step3_name.getText().toString()
                     +"max:"+step3_max.getText().toString()
                     +"min:"+step3_min.getText().toString()
                     +"scale:"+step3_scale.getText().toString()
                    +
                     "第四步："+step4_name.getText().toString()
                     +"max:"+step4_max.getText().toString()
                     +"min:"+step4_min.getText().toString()
                     +"scale:"+step4_scale.getText().toString()
                    +
                     "第五步："+step5_name.getText().toString()
                     +"max:"+step5_max.getText().toString()
                     +"min:"+step5_min.getText().toString()
                     +"scale:"+step5_scale.getText().toString()
                    +
                     "第六步："+step6_name.getText().toString()
                     +"max:"+step6_max.getText().toString()
                     +"min:"+step6_min.getText().toString()
                     +"scale:"+step6_scale.getText().toString();
            break;
         case 7:
             text="共分为七个步骤："
                   +
                     "第一步："+step1_name.getText().toString()
                     +"max:"+step1_max.getText().toString()
                     +"min:"+step1_min.getText().toString()
                     +"scale:"+step1_scale.getText().toString()
                    +
                     "第二步："+step2_name.getText().toString()
                     +"max:"+step2_max.getText().toString()
                     +"min:"+step2_min.getText().toString()
                     +"scale:"+step2_scale.getText().toString()
                   +
                     "第四步："+step4_name.getText().toString()
                     +"max:"+step4_max.getText().toString()
                     +"min:"+step4_min.getText().toString()
                     +"scale:"+step4_scale.getText().toString()
                    +
                     "第五步："+step5_name.getText().toString()
                     +"max:"+step5_max.getText().toString()
                     +"min:"+step5_min.getText().toString()
                     +"scale:"+step5_scale.getText().toString()
                    +
                     "第六步："+step6_name.getText().toString()
                     +"max:"+step6_max.getText().toString()
                     +"min:"+step6_min.getText().toString()
                     +"scale:"+step6_scale.getText().toString()
                    +
                     "第七步："+step7_name.getText().toString()
                     +"max:"+step7_max.getText().toString()
                     +"min:"+step7_min.getText().toString()
                     +"scale:"+step7_scale.getText().toString();
             break;
         case 8:
             text="共分为八个步骤："
                    +
                     "第一步："+step1_name.getText().toString()
                     +"max:"+step1_max.getText().toString()
                     +"min:"+step1_min.getText().toString()
                     +"scale:"+step1_scale.getText().toString()
                    +
                     "第二步："+step2_name.getText().toString()
                     +"max:"+step2_max.getText().toString()
                     +"min:"+step2_min.getText().toString()
                     +"scale:"+step2_scale.getText().toString()
                    +
                     "第三步："+step3_name.getText().toString()
                     +"max:"+step3_max.getText().toString()
                     +"min:"+step3_min.getText().toString()
                     +"scale:"+step3_scale.getText().toString()
                    +
                     "第四步："+step4_name.getText().toString()
                     +"max:"+step4_max.getText().toString()
                     +"min:"+step4_min.getText().toString()
                     +"scale:"+step4_scale.getText().toString()
                    +
                     "第五步："+step5_name.getText().toString()
                     +"max:"+step5_max.getText().toString()
                     +"min:"+step5_min.getText().toString()
                     +"scale:"+step5_scale.getText().toString()
                    +
                     "第六步："+step6_name.getText().toString()
                     +"max:"+step6_max.getText().toString()
                     +"min:"+step6_min.getText().toString()
                     +"scale:"+step6_scale.getText().toString()
                    +
                     "第七步："+step7_name.getText().toString()
                     +"max:"+step7_max.getText().toString()
                     +"min:"+step7_min.getText().toString()
                     +"scale:"+step7_scale.getText().toString()
                   +
                     "第八步："+step8_name.getText().toString()
                     +"max:"+step8_max.getText().toString()
                     +"min:"+step8_min.getText().toString()
                     +"scale:"+step8_scale.getText().toString();
             break;
         case 9:
             text="共分为九个步骤："
                    +
                     "第一步："+step1_name.getText().toString()
                     +"max:"+step1_max.getText().toString()
                     +"min:"+step1_min.getText().toString()
                     +"scale:"+step1_scale.getText().toString()
                    +
                     "第二步："+step2_name.getText().toString()
                     +"max:"+step2_max.getText().toString()
                     +"min:"+step2_min.getText().toString()
                     +"scale:"+step2_scale.getText().toString()
                    +
                     "第三步："+step3_name.getText().toString()
                     +"max:"+step3_max.getText().toString()
                     +"min:"+step3_min.getText().toString()
                     +"scale:"+step3_scale.getText().toString()
                    +
                     "第四步："+step4_name.getText().toString()
                     +"max:"+step4_max.getText().toString()
                     +"min:"+step4_min.getText().toString()
                     +"scale:"+step4_scale.getText().toString()
                    +
                     "第五步："+step5_name.getText().toString()
                     +"max:"+step5_max.getText().toString()
                     +"min:"+step5_min.getText().toString()
                     +"scale:"+step5_scale.getText().toString()
                    +
                     "第六步："+step6_name.getText().toString()
                     +"max:"+step6_max.getText().toString()
                     +"min:"+step6_min.getText().toString()
                     +"scale:"+step6_scale.getText().toString()
                    +
                     "第七步："+step7_name.getText().toString()
                     +"max:"+step7_max.getText().toString()
                     +"min:"+step7_min.getText().toString()
                     +"scale:"+step7_scale.getText().toString()
                     +
                     "第八步："+step8_name.getText().toString()
                     +"max:"+step8_max.getText().toString()
                     +"min:"+step8_min.getText().toString()
                     +"scale:"+step8_scale.getText().toString()
                     +
                     "第九步："+step9_name.getText().toString()
                     +"max:"+step9_max.getText().toString()
                     +"min:"+step9_min.getText().toString()
                     +"scale:"+step9_scale.getText().toString();
            break;
         case 10:
             text="共分为十个步骤："
                     +
                     "第一步："+step1_name.getText().toString()
                     +"max:"+step1_max.getText().toString()
                     +"min:"+step1_min.getText().toString()
                     +"scale:"+step1_scale.getText().toString()
                     +
                     "第二步："+step2_name.getText().toString()
                     +"max:"+step2_max.getText().toString()
                     +"min:"+step2_min.getText().toString()
                     +"scale:"+step2_scale.getText().toString()
                     +
                     "第三步："+step3_name.getText().toString()
                     +"max:"+step3_max.getText().toString()
                     +"min:"+step3_min.getText().toString()
                     +"scale:"+step3_scale.getText().toString()
                     +
                     "第四步："+step4_name.getText().toString()
                     +"max:"+step4_max.getText().toString()
                     +"min:"+step4_min.getText().toString()
                     +"scale:"+step4_scale.getText().toString()
                     +
                     "第五步："+step5_name.getText().toString()
                     +"max:"+step5_max.getText().toString()
                     +"min:"+step5_min.getText().toString()
                     +"scale:"+step5_scale.getText().toString()
                     +
                     "第六步："+step6_name.getText().toString()
                     +"max:"+step6_max.getText().toString()
                     +"min:"+step6_min.getText().toString()
                     +"scale:"+step6_scale.getText().toString()
                     +
                     "第七步："+step7_name.getText().toString()
                     +"max:"+step7_max.getText().toString()
                     +"min:"+step7_min.getText().toString()
                     +"scale:"+step7_scale.getText().toString()
                     +
                     "第八步："+step8_name.getText().toString()
                     +"max:"+step8_max.getText().toString()
                     +"min:"+step8_min.getText().toString()
                     +"scale:"+step8_scale.getText().toString()
                     +
                     "第九步："+step9_name.getText().toString()
                     +"max:"+step9_max.getText().toString()
                     +"min:"+step9_min.getText().toString()
                     +"scale:"+step9_scale.getText().toString()
                     +
                     "第十步："+step10_name.getText().toString()
                     +"max:"+step10_max.getText().toString()
                     +"min:"+step10_min.getText().toString()
                     +"scale:"+step10_scale.getText().toString();
             break;


         default:
             text="请估算分值";
             break;
     }

        Log.e(text,"qwe");
        return text;
    }
//    /**
//     * 确定键监听器
//     */
//    public   void setOkListener()
//    {
//        ok_but.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("aaaa"+getStepDetail(), "qqww");
//                Intent rIntent = new Intent();
//                rIntent.putExtra("stepCount", getStepCount());
//                rIntent.putExtra("stepDetail", getStepDetail());
//                setResult(1, rIntent);
//                finish();
//            }
//        });
//    }
//    /**
//     * 取消键监听器
//     */
//    public void setCancelListener()
//    {
//        cancel_but.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent rIntent = new Intent();
//                rIntent.putExtra("stepCount",getStepCount());
//                rIntent.putExtra("stepDetail", "");
//                setResult(2, rIntent);
//                finish();
//            }
//        });
//    }
}
