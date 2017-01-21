package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.beyondsys.ppv.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhsht on 2017/1/13.工作价值
 */
public class WorkValueView extends Fragment {
    //、、
    private  ListView listView ;
    private View rootView;
    private  LinearLayout sort_layout,filter_layout;
    private RelativeLayout topme_layout;
    private CheckBox topme_che;
    private ImageView sort_img;
    private TextView sort_tex,filter_tex;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.workvalue_view, container, false);
         init();
        setAdapter();
        setListenter();
        return rootView;
    }
    private void init()
    {
        listView=(ListView)rootView.findViewById(R.id.value_list );
        sort_layout=(LinearLayout)rootView.findViewById(R.id.wv_sort);
        filter_layout=(LinearLayout)rootView.findViewById(R.id.wv_filter);
        topme_layout=(RelativeLayout)rootView.findViewById(R.id.topme_layout);
        topme_che=(CheckBox)rootView.findViewById(R.id.topme_che);
        sort_img=(ImageView)rootView.findViewById(R.id.sort_img);
        sort_tex=(TextView)rootView.findViewById(R.id.wv_sort_txt);
        filter_tex=(TextView)rootView.findViewById(R.id.wv_filter_txt);

    }
    private void setListenter()
    {
        Log.e("qqww1","qqww");
      sort_layout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Log.e("qqww2","qqww");
              if(sort_tex.getText().toString().equals(getResources().getString(R.string.sortup)))
              {
                  Log.e("qqww3","qqww");
                  sort_img.setImageResource(R.drawable.sort_down);
                  sort_tex.setText(R.string.sortdown);
              }
            else
              {
                  sort_img.setImageResource(R.drawable.sort_up);
                  sort_tex.setText(R.string.sortup);
              }
          }
      });
        filter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qqww4","qqww");
                if(filter_tex.getText().toString().equals(getResources().getString(R.string.filter_by_currentmonth)))
                {
                    Log.e("qqww5","qqww");
                    filter_tex.setText(R.string.filter_by_all);
                }
                else
                {
                    filter_tex.setText(R.string.filter_by_currentmonth);
                }
            }
        });
        topme_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topme_che.isChecked())
                {
                    topme_che.setChecked(false);
                }else
                {
                    topme_che.setChecked(true);
                }
            }
        });
    }
    private void setAdapter()
    {
        SimpleAdapter adapter =new SimpleAdapter(this.getActivity(),getData() ,R.layout.valueliststyle ,  new String[]{"personImg","personName","valueSum","monthSum"},
                new int[]{R.id.person_img  ,R.id.personname_tex  ,R.id.valuesum_tex ,R.id .monthsum_tex  }) ;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), PersonValueDetail.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personImg",  R.drawable.person );
        map.put("personName", "张三");
        map.put("valueSum", "100分");
        map.put("monthSum", "3个月");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("personImg",  R.drawable.person);
        map.put("personName", "王五");
        map.put("valueSum", "70分");
        map.put("monthSum", "4个月");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("personImg",  R.drawable.person);
        map.put("personName", "李四");
        map.put("valueSum", "30分");
        map.put("monthSum", "7个月");
        list.add(map);

        return list;
    }


}
