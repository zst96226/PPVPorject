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
 * Created by zhsht on 2017/1/12.工作项页面
 */
public class WorkItemView extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.workitem_view, container, false);

        ListView listView=(ListView)rootView.findViewById(R.id.workitem_list);
        SimpleAdapter adapter =new SimpleAdapter(this.getActivity(),getData() ,R.layout.workitemliststyle,  new String[]{"workImg","workName","workState","endingTime","workValue"},
                new int[]{R.id.work_img ,R.id .workname_tex ,R.id.workstate_tex ,R.id .endingtime_tex ,R.id .workvalue_tex }) ;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), WorkItemDetail.class);
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
        map.put("workImg",  R.drawable.i1);
        map.put("workName", "事务2");
        map.put("workState", "Done");
        map.put("endingTime", "2017/11/22");
        map.put("workValue", "100分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workImg", R.drawable.i1);
        map.put("workName", "事务3");
        map.put("workState", "ToDo");
        map.put("endingTime", "2017/01/22");
        map.put("workValue", "20分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workImg", R.drawable.i1);
        map.put("workName", "事务4");
        map.put("workState", "Commit");
        map.put("endingTime", "2017/01/22");
        map.put("workValue", "50分");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("workImg",  R.drawable.i1);
        map.put("workName", "事务2");
        map.put("workState", "Done");
        map.put("endingTime", "2017/11/22");
        map.put("workValue", "100分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workImg", R.drawable.i1);
        map.put("workName", "事务3");
        map.put("workState", "ToDo");
        map.put("endingTime", "2017/01/22");
        map.put("workValue", "20分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workImg", R.drawable.i1);
        map.put("workName", "事务4");
        map.put("workState", "Commit");
        map.put("endingTime", "2017/01/22");
        map.put("workValue", "50分");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("workImg",  R.drawable.i1);
        map.put("workName", "事务2");
        map.put("workState", "Done");
        map.put("endingTime", "2017/11/22");
        map.put("workValue", "100分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workImg", R.drawable.i1);
        map.put("workName", "事务3");
        map.put("workState", "ToDo");
        map.put("endingTime", "2017/01/22");
        map.put("workValue", "20分");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workImg", R.drawable.i1);
        map.put("workName", "事务4");
        map.put("workState", "Commit");
        map.put("endingTime", "2017/01/22");
        map.put("workValue", "50分");
        list.add(map);

        return list;
    }
}
