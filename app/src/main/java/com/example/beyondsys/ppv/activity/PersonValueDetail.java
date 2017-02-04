package com.example.beyondsys.ppv.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.DateUtil;
import com.example.beyondsys.ppv.tools.MonPickerDialog;
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonValueDetail extends AppCompatActivity {
    private ListView listView;
    private TextView textView,monthSum,valueSum;
    private ImageView back,lastone,nextone,lastmonth,nextmonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_value_detail);
        init();
        setListener();
    }

    private void  init()
    {
        listView = (ListView) findViewById(R.id.MonthDeatil_list);
        back = (ImageView) this.findViewById(R.id.dttail_back);
        textView = (TextView) findViewById(R.id.selectTime_tex);
        lastone=(ImageView)findViewById(R.id.lastone_img);
        nextone=(ImageView)findViewById(R.id.nextone_img);
        lastmonth=(ImageView)findViewById(R.id.lastMonth);
        nextmonth=(ImageView)findViewById(R.id.nextMonth);
        monthSum=(TextView)findViewById(R.id.monthsum_tex);
        valueSum=(TextView)findViewById(R.id.valuesum_tex);
    }
    private void setListener()
    {
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.valuedetailstyle, new String[]{"itemImg", "itemName", "planValue", "trueValue"},
                new int[]{R.id.Item_img, R.id.ItemName_tex, R.id.planValue, R.id.trueValue});
        listView.setAdapter(adapter);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    selectMonthTime();
                    return true;
                }
                return false;
            }
        });
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    selectMonthTime();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
     lastmonth.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             //判断是否为最初月份，否则减一
             SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM");
             String month=monthSum.getText().toString();
             String  oldTime=textView.getText().toString();
             String newTime="";
             Date oldDate=null;
             Date minDate=null;
             String nowTime=sdf.format(new  java.util.Date());
             Log.e("time","qq");
             int change= Integer.parseInt(month);
             String minTime=dateFormat(nowTime, -change);
             Log.e("time2","qq");
             try {
                 oldDate=sdf.parse(oldTime);
                 minDate=sdf.parse(minTime);
             } catch (ParseException e) {
                 e.printStackTrace();
             }
             Log.e("time3","qq");
             if(oldDate.getTime()<minDate.getTime()||oldDate.getTime()==minDate.getTime())
             {
                 newTime=oldTime;
             }
             else {
                  newTime = dateFormat(oldTime, -1);
             }
             textView.setText(newTime);
         }
     });
        lastone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上一个人
            }
        });
        nextmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为当前月份，否则加一
                String  oldTime=textView.getText().toString();
                String newTime="";
            SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM");
                Date oldDate=null;
                Date nowDate=null;
                Log.e("time","qq");
                String nowTime=sdf.format(new  java.util.Date());
                Log.e("time2","qq");
                try {
                     oldDate=sdf.parse(oldTime);
                     nowDate=sdf.parse(nowTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("time3","qq");
                if(oldDate.getTime()>nowDate.getTime()||oldDate.getTime()==nowDate.getTime())
                {
                    newTime=oldTime;
                }
                 else
                {
                    newTime=dateFormat(oldTime, +1);
                }
                textView.setText(newTime);
            }
        });
        nextone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //下一个人
            }
        });
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemImg", R.drawable.work_item);
        map.put("itemName", "任务B");
        map.put("planValue", "13分");
        map.put("trueValue", "8分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg",R.drawable.work_item);
        map.put("itemName", "事务1");
        map.put("planValue", "10分");
        map.put("trueValue", "7分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg",R.drawable.work_item);
        map.put("itemName", "任务A");
        map.put("planValue", "10分");
        map.put("trueValue", "5分");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("itemImg",R.drawable.work_item);
        map.put("itemName", "任务B");
        map.put("planValue", "13分");
        map.put("trueValue", "8分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg",R.drawable.work_item);
        map.put("itemName", "事务1");
        map.put("planValue", "10分");
        map.put("trueValue", "7分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg",R.drawable.work_item);
        map.put("itemName", "任务A");
        map.put("planValue", "10分");
        map.put("trueValue", "5分");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("itemImg",R.drawable.work_item);
        map.put("itemName", "任务B");
        map.put("planValue", "13分");
        map.put("trueValue", "8分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg", R.drawable.work_item);
        map.put("itemName", "事务1");
        map.put("planValue", "10分");
        map.put("trueValue", "7分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg", R.drawable.work_item);
        map.put("itemName", "任务A");
        map.put("planValue", "10分");
        map.put("trueValue", "5分");
        list.add(map);
        return list;
    }


    private void selectMonthTime() {
       final Calendar calendar = Calendar.getInstance();
        new MonPickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                textView.setText(DateUtil.clanderTodatetime(calendar, "yyyy-MM"));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();

    }
    private static String getDateStr(String day,int dayAddNum) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate2 = new Date(nowDate.getTime() + dayAddNum * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }
    private static String dateFormat(String datetime,int change) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, change);
        date = cl.getTime();
        return sdf.format(date);
    }

}
