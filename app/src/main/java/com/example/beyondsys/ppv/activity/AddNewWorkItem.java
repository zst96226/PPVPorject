package com.example.beyondsys.ppv.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.PopupMenuForWorkItem;

public class AddNewWorkItem extends AppCompatActivity {

    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_work_item);

        InitView();
        Listener();

    }

    private void InitView(){
        back=(ImageView)this.findViewById(R.id.anwi_back);
    }

    private void Listener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
