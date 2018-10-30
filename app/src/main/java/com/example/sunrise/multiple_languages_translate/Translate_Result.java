package com.example.sunrise.multiple_languages_translate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Translate_Result extends Fragment {
    private OnFragmentInteractionListener mListener;
    private String translate_word = "";
    private MediaPlayer mp=new MediaPlayer();
    TextView tv,pinyin,customer_language,sentence_origin,sentence_custom;
    //ImageButton ig_btn;
    Button collect ;
    SQLite sq;
    public Translate_Result() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){//寫Fragment 時候 物件與事件都必須寫在這個地方
        //需新增收藏按鈕事件 、 若是重複詞彙必須改為顯示未收藏
        ImageButton btn = getView().findViewById(R.id.ig_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer mp = MediaPlayer.create(getContext(), Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.example.sunrise.test/" + translate_word + ".mp3"));
                mp.start();
                /*
                if (translate_word.equals("電線桿")) {
                    MediaPlayer mp = MediaPlayer.create( getContext(), R.raw.telephone_pole);
                    mp.start();
                }
                if (translate_word.equals("窗外")) {
                    MediaPlayer mp = MediaPlayer.create( getContext(), R.raw.outside);
                    mp.start();
                }
                if (translate_word.equals("秋刀魚")) {
                    MediaPlayer mp = MediaPlayer.create( getContext(), R.raw.saury);
                    mp.start();
                }*/
            }
        });

         tv = (TextView) getView().findViewById(R.id.Translate_result_word);
        pinyin = (TextView) getView().findViewById(R.id.Translate_result_pinyin);
        customer_language = (TextView) getView().findViewById(R.id.Translate_result_custom_language);
       // sentence_origin = (TextView) getView().findViewById(R.id.Translate_ressult_sentence_origin);
        sentence_custom = (TextView) getView().findViewById(R.id.Translate_ressult_sentence_customer);
        //在手機SQLite上搜尋該詞彙資訊
        SQLite sqlite = new SQLite(getActivity());
        Cursor c = null;
        SQLiteDatabase db = sqlite.getWritableDatabase();

        c = db.rawQuery("SELECT * FROM all_word WHERE word='"+translate_word+"'",null);
        c.moveToFirst();
        // 詞彙原語言
        tv.setText(translate_word);
        // 詞彙拼音
        pinyin.setText(c.getString(3));
        // 詞彙翻譯後語言
        customer_language.setText(c.getString(2));
        // 詞彙例句
        c.close();
        c = db.rawQuery("SELECT * FROM  question WHERE name LIKE '%"+translate_word+"%'",null);
        c.moveToFirst();
        sentence_custom.setText(c.getString(0));
        /*demo用
        switch (translate_word){
            case "窗外":
                pinyin.setText("Chuāngwài");
                customer_language.setText("Outside the window");
                sentence_custom.setText("窗外的麻雀 在電線杆上多嘴");
                break;
            case "電線杆":
                pinyin.setText("Diànxiàn gǎn");
                customer_language.setText("telephone pole");
                sentence_custom.setText("窗外的麻雀 在電線杆上多嘴");
                break;
            case "秋刀魚":
                pinyin.setText("ka  Qiū dāoyú");
                customer_language.setText("Saury");
                sentence_custom.setText("秋刀魚的滋味 貓跟你都想了解");
                break;
        }*/

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            translate_word = getArguments().getString("word");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translate__result, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
