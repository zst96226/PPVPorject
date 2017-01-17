package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.workvalue_view, container, false);

         listView=(ListView)rootView.findViewById(R.id.value_list );
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
        return rootView;
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
