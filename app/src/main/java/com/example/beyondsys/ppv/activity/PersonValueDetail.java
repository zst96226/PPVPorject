package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.beyondsys.ppv.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonValueDetail extends AppCompatActivity {
    private  ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_value_detail);
        getSupportActionBar().hide();
       listView=(ListView)findViewById(R.id.MonthDeatil_list );
        SimpleAdapter adapter =new SimpleAdapter(this,getData() ,R.layout.valuedetailstyle,  new String[]{"itemImg","itemName","planValue","trueValue"},
                new int[]{R.id.Item_img  ,R.id.ItemName_tex ,R.id.planValue, R.id.trueValue}) ;
        listView.setAdapter(adapter);
        ImageView back=(ImageView)this.findViewById(R.id.dttail_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemImg",  R.drawable.i1);
        map.put("itemName", "任务B");
        map.put("planValue", "13分");
        map.put("trueValue", "8分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg",  R.drawable.i1);
        map.put("itemName", "事务1");
        map.put("planValue", "10分");
        map.put("trueValue", "7分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg",  R.drawable.i1);
        map.put("itemName", "任务A");
        map.put("planValue", "10分");
        map.put("trueValue", "5分");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("itemImg",  R.drawable.i1);
        map.put("itemName", "任务B");
        map.put("planValue", "13分");
        map.put("trueValue", "8分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg",  R.drawable.i1);
        map.put("itemName", "事务1");
        map.put("planValue", "10分");
        map.put("trueValue", "7分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg",  R.drawable.i1);
        map.put("itemName", "任务A");
        map.put("planValue", "10分");
        map.put("trueValue", "5分");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("itemImg",  R.drawable.i1);
        map.put("itemName", "任务B");
        map.put("planValue", "13分");
        map.put("trueValue", "8分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg",  R.drawable.i1);
        map.put("itemName", "事务1");
        map.put("planValue", "10分");
        map.put("trueValue", "7分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("itemImg",  R.drawable.i1);
        map.put("itemName", "任务A");
        map.put("planValue", "10分");
        map.put("trueValue", "5分");
        list.add(map);
        return list;
    }


}
