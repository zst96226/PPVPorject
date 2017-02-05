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
import com.example.beyondsys.ppv.entities.WorkItemEntity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhsht on 2017/1/12.工作项页面
 */
public class WorkItemView extends Fragment {

   private ListView listView;
    private View rootView;
    private LinearLayout wi_s_one;
    private TextView  wi_s_one_txt;
    private WorkItemEntity workItemEntity;

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
                intent.putExtra("ItemName","");//view.findViewById(R.id.workname_tex)
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
        int listLength=10;
        for(int i=0;i<listLength;i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            workItemEntity=new WorkItemEntity();
            workItemEntity.BID="BID"+i;
            workItemEntity.ID="ID"+i;
            workItemEntity.FID="FID"+i;
            workItemEntity.Name="Name"+i;
            workItemEntity.Description="Des"+i;
            workItemEntity.Category=i;
            workItemEntity.Status=i;
            workItemEntity.Assigned2="Assigned"+i;
            workItemEntity.Belong2="Belong"+i;
            workItemEntity.Checker="Check"+i;
            workItemEntity.Creater="Creater"+i;
            workItemEntity.CreateTime="Createtime"+i;
            workItemEntity.ClosingTime="ClosingTime"+i;
            workItemEntity.Modifier="Modifier"+i;
            workItemEntity.ModifyTime="ModifiyTime"+i;
            workItemEntity.BusinessValue=i;
            workItemEntity.BasicScore=i;
            workItemEntity.CheckedScore=i;
            workItemEntity.HardScale=i;
            workItemEntity.Remark="Remark"+i;
            map.put("workimg", R.drawable.work_item);
            map.put("workName", workItemEntity.Name);
            map.put("workState", R.drawable.img_done);
            map.put("endingTime", workItemEntity.ClosingTime);
            map.put("workValue", workItemEntity.BusinessValue);
            map.put("strartTime", workItemEntity.CreateTime);
        list.add(map);
        }


//        map.put("workimg", R.drawable.work_item);
//        map.put("workName", "事务1");
//        map.put("workState", R.drawable.img_done);
//        map.put("endingTime", "2017/11/22");
//        map.put("workValue", "100");
//        map.put("strartTime", "2017/11/22");
//        list.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("workimg", R.drawable.work_item);
//        map.put("workName", "事务2");
//        map.put("workState", R.drawable.img_pro);
//        map.put("endingTime", "2017/11/22");
//        map.put("workValue", "100");
//        map.put("strartTime", "2017/11/22");
//        list.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("workimg", R.drawable.work_item);
//        map.put("workName", "事务3");
//        map.put("workState", R.drawable.img_proing);
//        map.put("endingTime", "2017/11/22");
//        map.put("workValue", "100");
//        map.put("strartTime", "2017/11/22");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("workimg", R.drawable.work_item);
//        map.put("workName", "事务4");
//        map.put("workState", R.drawable.img_del);
//        map.put("endingTime", "2017/11/22");
//        map.put("workValue", "100");
//        map.put("strartTime", "2017/11/22");
//        list.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("workimg", R.drawable.work_item);
//        map.put("workName", "事务5");
//        map.put("workState", R.drawable.img_proing);
//        map.put("endingTime", "2017/11/22");
//        map.put("workValue", "100");
//        map.put("strartTime", "2017/11/22");
//        list.add(map);
//
//        map = new HashMap<String, Object>();
//        map.put("workimg", R.drawable.work_item);
//        map.put("workName", "事务6");
//        map.put("workState", R.drawable.img_proing);
//        map.put("endingTime", "2017/11/22");
//        map.put("workValue", "100");
//        map.put("strartTime", "2017/11/22");
//        list.add(map);
        return list;
    }
}
