package com.example.beyondsys.ppv.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.beyondsys.ppv.R;

public class PersonInfo extends AppCompatActivity {
private  ImageView back;
    private  ImageView modify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_person_info);
        back = (ImageView) this.findViewById(R.id.dttail_back);
        modify=(ImageView) this.findViewById(R.id.infoModify_img);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
