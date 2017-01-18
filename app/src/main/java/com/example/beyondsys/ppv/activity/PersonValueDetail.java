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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonValueDetail extends AppCompatActivity {
    private ListView listView;
    private TextView textView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient clientnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_value_detail);
        listView = (ListView) findViewById(R.id.MonthDeatil_list);

        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.valuedetailstyle, new String[]{"itemImg", "itemName", "planValue", "trueValue"},
                new int[]{R.id.Item_img, R.id.ItemName_tex, R.id.planValue, R.id.trueValue});
        listView.setAdapter(adapter);
        ImageView back = (ImageView) this.findViewById(R.id.dttail_back);
        textView = (TextView) findViewById(R.id.selectTime_tex);
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

//    protected void showDatePickDlg() {
////        Calendar calendar = Calendar.getInstance();
////        final DatePickerDialog datePickerDialog = new MonPickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
////            @Override
////            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
////
////                calendar.set(Calendar.YEAR, year);
////                calendar.set(Calendar.MONTH, monthOfYear);
////                textView.setText(DateUtil.clanderTodatetime(calendar, "yyyy-MM"));
////
////            }
////        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();
//
//    }

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

}
