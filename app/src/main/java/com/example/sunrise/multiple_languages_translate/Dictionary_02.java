package com.example.sunrise.multiple_languages_translate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Dictionary_02.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Dictionary_02#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dictionary_02 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String translate_name = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentManager manager; //負責管理Fragment
    private android.support.v4.app.FragmentTransaction transaction; // 進行交談的Fragment 變數
    private String[] all_user_save_translate  ;//使用者所有的翻譯單字
    private ArrayList<String>all_user_save_translate1 =new ArrayList<String>();//使用者所收藏的翻譯單字
    private ArrayList<String> user_choice = new ArrayList<>();
    private ListView listView;
    //自定義的 ListView
    private SlideList adapter;
    SQLite sq;

    private OnFragmentInteractionListener mListener;

    public Dictionary_02() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dictionary_02.
     */
    // TODO: Rename and change types and number of parameters
    public static Dictionary_02 newInstance(String param1, String param2) {
        Dictionary_02 fragment = new Dictionary_02();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume () {
        super.onResume();
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
        TextView tv = (TextView) getView().findViewById(R.id.Dictionary_02_chapter);
        tv.setText(translate_name);
        SQLite sqlite = new SQLite(getActivity());
        Cursor c = null;
        SQLiteDatabase db = sqlite.getWritableDatabase();
       // all_user_save_translate =  getResources().getStringArray(R.array.dictionary_word);
        //三年級應修改部分  從資料庫裡撈出以收藏詞彙
        /*Cursor c=sq.getReadableDatabase().rawQuery(String.format("SELECT * FROM %s",translate_name),null);
        c.moveToFirst();
        all_user_save_translate1.clear();
        for(int i=0;i<c.getCount();i++){
            all_user_save_translate1.add(c.getString(0));
            c.moveToNext();
        }*/

        c=db.rawQuery("SELECT * FROM "+translate_name,null);
        //all_user_save_translate = new String []{"窗外","麻雀","秋刀魚","電線桿","夏天","了解","唯一"};
        all_user_save_translate = new String[c.getCount()];
        c.moveToFirst();
        // 讀取表格裡所有詞彙
        for (int i = 0 ; i < c.getCount() ; i++){
            all_user_save_translate[i] = c.getString(0);
            c.moveToNext();
        }

        //三年級應修改部分 -------------------------------------------------- end
     /*   user_choice.clear();
        for(int i=0;i<all_user_save_translate1.size();i++){
            user_choice.add(all_user_save_translate1.get(i).toString());
        }*/
     //  -----------------學長原本的------------
              for(String s : all_user_save_translate){
                       user_choice.add(s);
                  }
    //      ----------------end-------------------------
        listView = (ListView)getView().findViewById(R.id.Dictionary_02_listview);
        adapter = new SlideList(getActivity() , user_choice , Dictionary_02.this);
        listView.setAdapter(adapter);

        // 設置listView 的點擊事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv =(TextView) view.findViewById(R.id.slide_tv);
                Toast.makeText(getActivity(),String.valueOf(tv.getText()),Toast.LENGTH_SHORT);
                Log.e("qwe",String.valueOf(tv.getText()));
            }
        });

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        manager = getActivity().getSupportFragmentManager();
        //取得傳過來的章節名稱 利用章節名稱尋找資料庫
        translate_name = getArguments().getString("chapter");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary_02, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onResume() {
        super.onResume();
        if (listView!= null) {
            listView.invalidateViews();
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }*/

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


class SQLite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="vocabulary";
    public static final int VERSION=1;

    //private final static String Result = "result";
    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public void createTable(SQLiteDatabase db,String tbName){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+tbName+
                "(tv VARCHAR NOT NULL,"+"pinyin VARCHAR NOT NULL,"+"customer_language VARCHAR NOT NULL,"+"sentence_origin VARCHAR NOT NULL,"
                +"sentence_custom NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
