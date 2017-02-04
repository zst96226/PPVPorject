package com.example.beyondsys.ppv.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.InputScoreDialog;
import com.example.beyondsys.ppv.tools.PopupMenuForWorkItem;

public class AddNewWorkItem extends Activity {

    ImageView back;
    private LinearLayout inputScore_layout;
    private InputScoreDialog dialog;
    private TextView input_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_work_item);

        InitView();
        Listener();

    }

    private void InitView(){
        back=(ImageView)this.findViewById(R.id.anwi_back);
        inputScore_layout=(LinearLayout)findViewById(R.id.inputScore_layout);
        input_score=(TextView)findViewById(R.id.input_score);
    }

    private void Listener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inputScore_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showDialog();
                estimateValue();
            }
        });
    }
    private void showDialog()
    {
        dialog=new InputScoreDialog(AddNewWorkItem.this);
        dialog.setOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定键
                Log.e("qqwwok","qqww");
                if(inputCheck())
                {
                    Log.e("qqww调用1","qqww");
                    inputShow();
                }
                //提示输入有误
                dialog.dismiss();
            }
        });
        dialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
   private void  estimateValue()
   {
       Intent estimate=new Intent(AddNewWorkItem.this,EstimateValueActivity.class);
       Log.e("esti","qqww");
       startActivityForResult(estimate,1);
   }
    private  boolean inputCheck()
    {
        //对各输入框进行输入验证
        return  true;
    }
    private void inputShow()
    {
        //将合法输入填充到控件上
        input_score.setText(dialog.getStepDetail());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if(resultCode==1)
                {
                    input_score.setText(data.getStringExtra("stepDetail"));

                }
                else
                {
                    input_score.setText("未估算分值");
                }
                break;
            default:
                break;
        }
    }
}
