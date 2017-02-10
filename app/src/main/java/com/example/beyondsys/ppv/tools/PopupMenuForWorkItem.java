package com.example.beyondsys.ppv.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.activity.AddNewWorkItem;
import com.example.beyondsys.ppv.activity.WorkItemDetail;

/**
 * Created by zhsht on 2017/1/18.工作项详细界面菜单
 */
public class PopupMenuForWorkItem extends PopupWindow {
    private View conentView;
    public  View add_child, del_father,del_child,submit,change_status;

    public PopupMenuForWorkItem(final Activity context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_menu, null);
        add_child= conentView.findViewById(R.id.popup_menu_add_chid);
        del_father= conentView.findViewById(R.id.popup_menu_invalid);
        del_child= conentView.findViewById(R.id.popup_menu_del_chid);
        submit=  conentView.findViewById(R.id.popup_confirm_result) ;
        change_status=  conentView.findViewById(R.id.popup_menu_changestatus) ;
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 3+ 40);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

//        conentView.findViewById(R.id.popup_menu_add_chid).setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                //do something you need here
//                //跳转添加子项界面
//
//                PopupMenuForWorkItem.this.dismiss();
//            }
//        });
//        conentView.findViewById(R.id.popup_menu_del_chid).setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // do something before signing out
//                //删除选中的子项
//                PopupMenuForWorkItem.this.dismiss();
//            }
//        });
//        conentView.findViewById(R.id.popup_menu_invalid).setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // do something you need here
//                // 状态置为废除
//                PopupMenuForWorkItem.this.dismiss();
//            }
//        });
//        conentView.findViewById(R.id.popup_menu_assign).setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // do something you need here
//                // 弹框选择指派人
//                PopupMenuForWorkItem.this.dismiss();
//            }
//        });
//        conentView.findViewById(R.id.popup_confirm_result).setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // do something you need here
//                // 提交修改结果
//                PopupMenuForWorkItem.this.dismiss();
//            }
//        });
//        conentView.findViewById(R.id.popup_menu_confirm).setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // do something you need here
//                // 确认状态，进入下一状态
//                PopupMenuForWorkItem.this.dismiss();
//            }
//        });
    }


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 3, 5);
        } else {
            this.dismiss();
        }
    }
}
