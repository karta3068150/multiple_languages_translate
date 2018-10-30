package com.example.sunrise.multiple_languages_translate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Upload_02.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Upload_02#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Upload_02 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    // TODO: Rename and change types of parameters
    private FragmentManager manager; //負責管理Fragment
    private android.support.v4.app.FragmentTransaction transaction;
    private String chapter_name = "" ;  // 作品名稱
    private String[] word_data,sentence_data,word_ex_data,word_trans_data,pinyin ;  //  ,原語言所有詞彙
    private ArrayList<ArrayList<String>> all_word = new ArrayList<ArrayList<String>>(); //儲存每一頁應存單字
    private OnFragmentInteractionListener mListener;
    private int page = 0; // 目前所翻譯之句子
    SQLite sq; //資料庫操作

    public Upload_02() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        manager = getActivity().getSupportFragmentManager();
        transaction = manager.beginTransaction();
        TextView tv = getView().findViewById(R.id.Upload_02_chapter_name);
        tv.setText("歌名 : "+chapter_name);
        word_data = new String[]{"窗外","的","麻雀","在","電線桿","上","多嘴","nextline","妳","說","這","一句","很","有","夏天","的","感覺","nextline","手中","的","鉛筆","在","紙上","來","來","回回","nextline","我用","幾行","字","形容","妳是","我","的","誰","nextline","秋刀魚","的","滋味","貓跟","妳","都","想","瞭解"};
        sentence_data = new String[]{"The sparrow outside the window is talking on the pole","Oh, this sentence is very summer.","Pencil in hand is coming back and forth on paper","I described a few lines of words You are my Who","The taste of saury, the cat and you all want to know","The fragrance of the first love is thus retrieved by us.","The warm sunshine is like the freshly picked strawberries."};

        sq= new SQLite(getActivity());                //開啟SQLite資料庫
        sq.createTable(sq.getWritableDatabase(),"AllWord");

        SQLiteDatabase db = sq.getWritableDatabase();   //操作SQLite資料庫
        String sql = "CREATE TABLE IF NOT EXISTS "+"AllWord"+
                "(word VARCHAR NOT NULL PRIMARY KEY ,"+"customer_language VARCHAR NOT NULL,"+"pinyin VARCHAR  NULL,"
                +"mean   NULL);";
        db.execSQL(sql);

        // String sql=String.format("INSERT INTO AllWord (tv, customer_language, sentence_origin, sentence_custom) VALUES ( '%s', '%s', '%s', '%s', '%s')" ,original,roman,language,oTranslate,Translate);

        all_word.clear();
        ArrayList<String> ary = new ArrayList<String>();
        int sentence = 0;
        for (int i=0 ; i < word_data.length ; i++) {
            if (word_data[i].equals("nextline")){
                sentence++;
                all_word.add(ary);
                ary = new ArrayList<String>();
                ary.clear();
                continue;
            }
            ary.add(word_data[i]);
          /*  if (i<10) {
                sql = String.format("INSERT INTO AllWord (tv, customer_language, pinyin, mean) VALUES ('%s','%s','%s','%s')", word_data[i], word_trans_data[i], pinyin[i], null);
                db.execSQL(sql);
            }*/
        }
        LinearLayout layout = getView().findViewById(R.id.Upload_02_row_1_word);
        for (int i = page ; i < page+4 ; i++){
            TextView textview = null;
            switch (i-page){
                case 0:
                    layout = getView().findViewById(R.id.Upload_02_row_1_word);
                    textview = getView().findViewById(R.id.Upload_02_row_1_sentence);
                    break;
                case 1:
                    layout = getView().findViewById(R.id.Upload_02_row_2_word);
                    textview = getView().findViewById(R.id.Upload_02_row_2_sentence);
                    break;
                case 2:
                    layout = getView().findViewById(R.id.Upload_02_row_3_word);
                    textview = getView().findViewById(R.id.Upload_02_row_3_sentence);
                    break;
                case 3:
                    layout = getView().findViewById(R.id.Upload_02_row_4_word);
                    textview = getView().findViewById(R.id.Upload_02_row_4_sentence);
                    break;
            }
            textview.setText(sentence_data[i]);
            for (int j=0 ; j < all_word.get(i).size() ; j++){
                Button btn = new Button(getActivity());
              //  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                //        LinearLayout.LayoutParams.WRAP_CONTENT);
               // btn.setLayoutParams(params);
                btn.setText(all_word.get(i).get(j));
                if (all_word.get(i).get(j).equals("窗外") || all_word.get(i).get(j).equals("電線桿") || all_word.get(i).get(j).equals("秋刀魚") ) {
                    final String str = all_word.get(i).get(j);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle transfer_data = new Bundle(); // Fragment之間傳輸資料
                            transfer_data.putString("word",str);
                            Translate_Result next_fragment = new Translate_Result();
                            next_fragment.setArguments(transfer_data);
                            transaction.replace(R.id.fragment,next_fragment,"Translate_Result").addToBackStack("Upload_02").commit();
                        }
                    });
                }
                layout.addView(btn);
            }

         //   sql=String.format("INSERT INTO AllWord (word, customer_language, sentence_origin, mean) VALUES ( '%s', '%s', '%s','%s')" ,sp_word[0],roman,language,oTranslate,Translate);
        }


    }
    // TODO: Rename and change types and number of parameters
    public static Upload_02 newInstance(String param1, String param2) {
        Upload_02 fragment = new Upload_02();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            chapter_name = getArguments().getString("translate_chapter");
            /*
            word_data = getArguments().getStringArray("word_data");
            sentence_data = getArguments().getStringArray("sentence_data");
         //   word_ex_data = getArguments().getStringArray("word_ex_data");
            word_trans_data = getArguments().getStringArray("word_trans_data");
            pinyin = getArguments().getStringArray("pinyin");*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_02, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
