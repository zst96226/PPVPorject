package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.PopupMenuForWorkItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkItemDetail extends AppCompatActivity {

    ImageView back;
    ImageView wid_show_chid;
    ImageView returen;
    ImageView menu;
    LinearLayout main_workitem;
    ListView child_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_item_detail);

        initView();

        Listener();

        SetData();
    }

    private void initView(){
        back=(ImageView)this.findViewById(R.id.wid_back);
        wid_show_chid=(ImageView)findViewById(R.id.wid_show_chid);
        menu=(ImageView)findViewById(R.id.anwi_menu);
        main_workitem=(LinearLayout)findViewById(R.id.main_workitem);
        child_list=(ListView)findViewById(R.id.wid_list);
        returen=(ImageView)findViewById(R.id.wid_return);
    }

    private void Listener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        main_workitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child_list.getVisibility() == View.GONE) {
                    child_list.setVisibility(View.VISIBLE);
                    SetList();
                    wid_show_chid.setImageResource(R.drawable.arrow_up_float);
                } else {
                    child_list.setVisibility(View.GONE);
                    wid_show_chid.setImageResource(R.drawable.arrow_down_float);
                }
            }
        });
        child_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //有子项则
                SetData();
            }
        });
        returen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //有父项则
                SetData();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenuForWorkItem popWindow = new PopupMenuForWorkItem(WorkItemDetail.this);
                popWindow.showPopupWindow(findViewById(R.id.anwi_menu));
            }
        });
    }

    private void SetList(){
        SimpleAdapter adapter =new SimpleAdapter(this,getData() ,R.layout.child_list_item,  new String[]{"workName","workValue","workState","strartTime","endingTime"},
                new int[]{R.id .workname_tex ,R.id.workvalue_tex ,R.id .work_state_img ,R.id .strarttime_tex ,R.id.endtime_tex}) ;
        child_list.setAdapter(adapter);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("workName", "事务1");
        map.put("workState", R.drawable.img_done);
        map.put("endingTime", "17-11-22");
        map.put("workValue", "100");
        map.put("strartTime", "17-11-22");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workName", "事务2");
        map.put("workState", R.drawable.img_pro);
        map.put("endingTime", "17-11-22");
        map.put("workValue", "100");
        map.put("strartTime", "17-11-22");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workName", "事务3");
        map.put("workState", R.drawable.img_proing);
        map.put("endingTime", "17-11-22");
        map.put("workValue", "100");
        map.put("strartTime", "17-11-22");
        list.add(map);

        return list;
    }

    private void SetData(){

    }
}
