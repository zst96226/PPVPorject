package com.example.beyondsys.ppv.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.InputScoreDialog;
import com.example.beyondsys.ppv.tools.PopupMenuForWorkItem;

import java.util.Calendar;

public class AddNewWorkItem extends Activity {

    ImageView back;
    private LinearLayout inputScore_layout;
    private InputScoreDialog dialog;
    private TextView input_score;
    String[] list = new String[]{"空","张三", "李四", "王五", "赵六"};
    EditText input_AssignedTo, input_Head, input_Checker,input_CloseTime;
    ListPopupWindow AssignedTo_pop, Head_pop, Checker_pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_work_item);

        InitView();
        Listener();
        SetPopWinForAssignedTo();
        SetPopWinForHead();
        SetPopWinForChecker();
    }

    private void InitView() {
        back = (ImageView) this.findViewById(R.id.anwi_back);
        inputScore_layout = (LinearLayout) findViewById(R.id.inputScore_layout);
        input_score = (TextView) findViewById(R.id.input_score);
        input_AssignedTo = (EditText) findViewById(R.id.input_AssignedTo);
        input_Head = (EditText) findViewById(R.id.input_Head);
        input_Checker = (EditText) findViewById(R.id.input_Checker);
        input_CloseTime=(EditText)findViewById(R.id.input_endtime);
    }

    protected void showDatePickDlg() {
        int currentapiVersion=android.os.Build.VERSION.SDK_INT;
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewWorkItem.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                input_CloseTime.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    private void Listener() {
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
        input_CloseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showDatePickDlg();
            }
        });
        input_AssignedTo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (v.getWidth() - ((EditText) v)
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        AssignedTo_pop.show();

                        return true;
                    }
                }
                return false;
            }
        });
        input_AssignedTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputstr = input_AssignedTo.getText().toString();
                int strlen = inputstr.length();
                for (int i = 0; i < list.length; i++) {
                    if (list[i].length() > strlen) {
                        String str = list[i].substring(0, strlen);
                        if (str.equals(inputstr)) {
                            input_AssignedTo.setText(list[i]);
                            input_AssignedTo.setSelection(list[i].length());
                        }
                    }
                }
            }
        });
        input_Head.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (v.getWidth() - ((EditText) v)
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Head_pop.show();
                        return true;
                    }
                }
                return false;
            }
        });
        input_Head.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputstr = input_Head.getText().toString();
                int strlen = inputstr.length();
                for (int i = 0; i < list.length; i++) {
                    if (list[i].length() > strlen) {
                        String str = list[i].substring(0, strlen);
                        if (str.equals(inputstr)) {
                            input_Head.setText(list[i]);
                            input_Head.setSelection(list[i].length());
                        }
                    }
                }
            }
        });
        input_Checker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (v.getWidth() - ((EditText) v)
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Checker_pop.show();
                        return true;
                    }
                }
                return false;
            }
        });
        input_Checker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputstr = input_Checker.getText().toString();
                int strlen = inputstr.length();
                for (int i = 0; i < list.length; i++) {
                    if (list[i].length() > strlen) {
                        String str = list[i].substring(0, strlen);
                        if (str.equals(inputstr)) {
                            input_Checker.setText(list[i]);
                            input_Checker.setSelection(list[i].length());
                        }
                    }
                }
            }
        });
    }

    private void showDialog() {
        dialog = new InputScoreDialog(AddNewWorkItem.this);
        dialog.setOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定键
                Log.e("qqwwok", "qqww");
                if (inputCheck()) {
                    Log.e("qqww调用1", "qqww");
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

    private void estimateValue() {
        Intent estimate = new Intent(AddNewWorkItem.this, EstimateValueActivity.class);
        Log.e("esti", "qqww");
        startActivityForResult(estimate, 1);
    }

    private boolean inputCheck() {
        //对各输入框进行输入验证
        return true;
    }

    private void inputShow() {
        //将合法输入填充到控件上
        input_score.setText(dialog.getStepDetail());
    }

    private void SetPopWinForAssignedTo() {
        AssignedTo_pop = new ListPopupWindow(this);
        AssignedTo_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        AssignedTo_pop.setAnchorView(input_AssignedTo);
        AssignedTo_pop.setModal(true);
        AssignedTo_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list[position];
                input_AssignedTo.setText(item);
                input_AssignedTo.setSelection(item.length());
                AssignedTo_pop.dismiss();
            }
        });
    }

    private void SetPopWinForHead() {
        Head_pop = new ListPopupWindow(this);
        Head_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        Head_pop.setAnchorView(input_Head);
        Head_pop.setModal(true);
        Head_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list[position];
                input_Head.setText(item);
                input_Head.setSelection(item.length());
                Head_pop.dismiss();
            }
        });
    }

    private void SetPopWinForChecker() {
        Checker_pop = new ListPopupWindow(this);
        Checker_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        Checker_pop.setAnchorView(input_Checker);
        Checker_pop.setModal(true);
        Checker_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list[position];
                input_Checker.setText(item);
                input_Checker.setSelection(item.length());
                Checker_pop.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    input_score.setText(data.getStringExtra("stepDetail"));

                } else {
                    input_score.setText("未估算分值");
                }
                break;
            default:
                break;
        }
    }

}
