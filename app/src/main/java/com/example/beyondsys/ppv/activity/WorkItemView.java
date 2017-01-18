package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.beyondsys.ppv.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhsht on 2017/1/12.工作项页面
 */
public class WorkItemView extends Fragment {

    ListView listView;
    View rootView;
    LinearLayout wi_s_one;
    TextView  wi_s_one_txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.workitem_view, container, false);

        IninView();

        SetList();

        Listener();

        return rootView;
    }

    private void IninView(){
        listView=(ListView)rootView.findViewById(R.id.workitem_list);
        wi_s_one=(LinearLayout)rootView.findViewById(R.id.wi_s_one);
        wi_s_one_txt=(TextView)rootView.findViewById(R.id.wi_s_one_txt);
    }

    private void SetList(){
        SimpleAdapter adapter =new SimpleAdapter(this.getActivity(),getData() ,R.layout.workitemliststyle,  new String[]{"workimg","workName","workValue","workState","strartTime","endingTime"},
                new int[]{R.id.work_img,R.id .workname_tex ,R.id.workvalue_tex ,R.id .work_state_img ,R.id .strarttime_tex ,R.id.endtime_tex}) ;
        listView.setAdapter(adapter);
    }

    private void Listener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), WorkItemDetail.class);
                startActivity(intent);
            }
        });
        wi_s_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nowShowTxt=wi_s_one_txt.getText().toString();
                Log.e(nowShowTxt,"123");
                if (nowShowTxt.equals("指派给我"))
                {
                    wi_s_one_txt.setText(R.string.wi_s_one_txt_2);
                }
                else
                {
                    wi_s_one_txt.setText(R.string.wi_s_one_txt);
                }
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
        map.put("workimg", R.drawable.work_item);
        map.put("workName", "事务1");
        map.put("workState", R.drawable.img_done);
        map.put("endingTime", "2017/11/22");
        map.put("workValue", "100");
        map.put("strartTime", "2017/11/22");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workimg", R.drawable.work_item);
        map.put("workName", "事务2");
        map.put("workState", R.drawable.img_pro);
        map.put("endingTime", "2017/11/22");
        map.put("workValue", "100");
        map.put("strartTime", "2017/11/22");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workimg", R.drawable.work_item);
        map.put("workName", "事务3");
        map.put("workState", R.drawable.img_proing);
        map.put("endingTime", "2017/11/22");
        map.put("workValue", "100");
        map.put("strartTime", "2017/11/22");
        list.add(map);
        map = new HashMap<String, Object>();
        map.put("workimg", R.drawable.work_item);
        map.put("workName", "事务4");
        map.put("workState", R.drawable.img_del);
        map.put("endingTime", "2017/11/22");
        map.put("workValue", "100");
        map.put("strartTime", "2017/11/22");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workimg", R.drawable.work_item);
        map.put("workName", "事务5");
        map.put("workState", R.drawable.img_proing);
        map.put("endingTime", "2017/11/22");
        map.put("workValue", "100");
        map.put("strartTime", "2017/11/22");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workimg", R.drawable.work_item);
        map.put("workName", "事务6");
        map.put("workState", R.drawable.img_proing);
        map.put("endingTime", "2017/11/22");
        map.put("workValue", "100");
        map.put("strartTime", "2017/11/22");
        list.add(map);
        return list;
    }
}
