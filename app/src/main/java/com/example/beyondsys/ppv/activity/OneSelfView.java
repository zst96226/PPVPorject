package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.beyondsys.ppv.R;

/**
 * Created by zhsht on 2017/1/13.个人信息界面
 */
public class OneSelfView extends Fragment {
private LinearLayout personInfoLayout;
    private LinearLayout passwordChangeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.oneself_view, container, false);
        personInfoLayout=(LinearLayout)rootView.findViewById(R.id.personInfo_layout);
        passwordChangeLayout=(LinearLayout)rootView.findViewById(R.id.passwordChange_layout);
        personInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonInfo.class);
                startActivity(intent);
            }
        });
        passwordChangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PasswordChange.class);
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

}
