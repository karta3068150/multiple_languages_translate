package com.example.sunrise.multiple_languages_translate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.AbsListView.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sunrise on 2018/6/9.
 */

public class SlideList extends BaseAdapter { // 自訂滑動listview
    private ArrayList<String> arrays = null;
    private Context mContext; // 目前在哪個Activity
    private float pos_x,move_x; // pos_x 一開始觸碰時的位置 , move_x 移動時的位置
    private  Dictionary_02 current_fragment;
   // private boolean flag;
    public SlideList(Context mContext, ArrayList<String> arrays , Dictionary_02 current_fragment) {
        this.mContext = mContext;
        this.arrays = arrays;
        this.current_fragment = current_fragment;
    }


    public int getCount() {
        return this.arrays.size();
    }
    public Object getItem(int position) {
        return null;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View view, final ViewGroup arg2) { // 創建所有Item 和 notifyDataSetChanged(); 時會執行的東西
        SubView sub = null;

        if (view == null ) {

            sub = new SubView();
            view = LayoutInflater.from(mContext).inflate(R.layout.slide_listview, null);
            sub.left_img = (Button) view.findViewById(R.id.slied_left_image);
            sub.right_img = (Button) view.findViewById(R.id.slied_right_image);
            view.setTag(sub);
        }
        else { // 會將以丟棄的Item 重複再利用
            sub = (SubView) view.getTag();
        }
        // LayoutParams 控制物件的大小,邊距

        sub.tv = (TextView) view.findViewById(R.id.slide_tv);
        sub.tv.setText(arrays.get(position));
        LinearLayout.LayoutParams textview = (LinearLayout.LayoutParams) sub.tv.getLayoutParams();
        textview.leftMargin = 0; textview.rightMargin= 0;
        sub.tv.setLayoutParams(textview);

        //为每一個view項設置觸控監聽

        view.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(final View v, MotionEvent event) {
                final SubView sub = (SubView) v.getTag();
                // LayoutParams 控制物件的大小,邊距
                LinearLayout.LayoutParams textview = (LinearLayout.LayoutParams) sub.tv.getLayoutParams()
                        ,left = (LinearLayout.LayoutParams) sub.left_img.getLayoutParams()
                        ,right = (LinearLayout.LayoutParams) sub.right_img.getLayoutParams();
                move_x = event.getX();

                //當按下時處理
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //設置背景为選中狀態
                    //獲取按下時的x軸坐標
                    pos_x = event.getX();
                    //判斷之前是否出現了刪除按鈕如果存在就隱藏
                } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) { // 松開處理
                    //設置背景为未選中正常狀態
                    sub.tv.setBackgroundColor(0xFFFFFF);
                    move_x = event.getX();
                    //如果滑動大於一定程度
                    if (Math.abs(move_x - pos_x) > 300) {
                        //刪除ArrayList裡面的資料後 更新Item
                        arrays.remove(position);
                        notifyDataSetChanged();
                    }else if (event.getAction() == MotionEvent.ACTION_UP){
                        FragmentManager manager = current_fragment.getActivity().getSupportFragmentManager(); //負責管理Fragment
                        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
                        Translate_Result next_fragment = new Translate_Result ();

                        Bundle transfer_data = new Bundle(); // Fragment之間傳輸資料
                        transfer_data.putString("word",arrays.get(position));
                        next_fragment.setArguments(transfer_data);

                        transaction.replace( R.id.fragment , next_fragment ,"Translate_Result").addToBackStack("Dictionary_02");
                        transaction.commit();
                        Log.e("qwe",String.valueOf(arrays.get(position)));
                    }
                    textview.leftMargin = 0;
                    left.leftMargin=-50;
                    right.rightMargin=-50;
                    sub.tv.setLayoutParams(textview);
                    sub.right_img.setLayoutParams(right);  sub.right_img.setVisibility(View.GONE);
                    sub.left_img.setLayoutParams(left); sub.left_img.setVisibility(View.GONE);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE ) {//當滑動時背景为選中狀態
                    Log.e("pos",""+pos_x);
                    Log.e("move",""+move_x);
                     if (move_x - pos_x  >= 0) { //手勢往左滑
                              sub.right_img.setVisibility(View.GONE);
                             if ( Math.abs(move_x - pos_x) >= 50 ) sub.left_img.setVisibility(View.VISIBLE);
                             if ( Math.abs(move_x - pos_x)-50 <= 250){
                                 left.leftMargin=(int) Math.abs(move_x - pos_x)-50;
                             }else{
                                 left.leftMargin=250;
                             }
                      }else{ //手勢往右滑
                             sub.left_img.setVisibility(View.GONE);
                             if ( Math.abs(move_x - pos_x) >= 50) sub.right_img.setVisibility(View.VISIBLE);
                             if ( Math.abs(move_x - pos_x)-50 <= 250){
                                 textview.leftMargin = ( (int) Math.abs(move_x - pos_x)-50 ) * -1 ;
                                 right.rightMargin=(int) Math.abs(move_x - pos_x)-50;
                             }else{
                                 right.rightMargin=250;
                             }
                     }
                    sub.tv.setLayoutParams(textview);
                    sub.right_img.setLayoutParams(right);
                    sub.left_img.setLayoutParams(left);
                } else {//其他模式
                    //設置背景为未選中正常狀態
                   sub.tv.setBackgroundColor(0xFFFFFF);
                 /*   textview.leftMargin=0;textview.rightMargin=0;
                    left.leftMargin=0;left.weight=0;
                    right.rightMargin=0;right.weight=0;
                    sub.tv.setLayoutParams(textview);
                    sub.right_img.setLayoutParams(right);
                    sub.left_img.setLayoutParams(left);*/
                }
                return true;
            }
        });

        return view;
    }
    final static class SubView {
        TextView tv;
        Button left_img,right_img;
    }

}
