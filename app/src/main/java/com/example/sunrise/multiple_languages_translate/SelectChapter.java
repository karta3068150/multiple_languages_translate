package com.example.sunrise.multiple_languages_translate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectChapter#newInstance} factory method to
 * create an instance of this fragment.
 */

// 個人詞彙典 與小試身手 的選擇章節頁面
public class SelectChapter extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentManager manager; //負責管理Fragment
    private android.support.v4.app.FragmentTransaction transaction;
    private String[] all_user_save_translate  ;//使用者所有的翻譯書籍
    //使用者所有的翻譯書籍分類 : 音樂 電影 電子書
    private ArrayList<String> music_user_save = new ArrayList<String>()
            ,movie_user_save = new ArrayList<String>()
            ,ebook_user_save = new ArrayList<String>()
            ,user_choice = null;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private RadioButton[] rb = new RadioButton [3];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        all_user_save_translate =  getResources().getStringArray(R.array.dictionary_word);
        music_user_save.clear();movie_user_save.clear();ebook_user_save.clear();
        for (String s : all_user_save_translate){
            if (s.contains("_music")){
                music_user_save.add(s.replace("_music",""));
            }
            if (s.contains("_movie")){
                movie_user_save.add(s.replace("_movie",""));
            }
            if (s.contains("_ebook")){
                ebook_user_save.add(s.replace("_ebook",""));
            }
        }
        rb[0] = (RadioButton) getView().findViewById(R.id.SelectChapter_music_class);
        rb[1] = (RadioButton) getView().findViewById(R.id.SelectChapter_movie_class);
        rb[2] = (RadioButton) getView().findViewById(R.id.SelectChapter_ebook_class);
        rb[0].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                user_choice = music_user_save;
                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1 , music_user_save);
                listView.setAdapter(adapter);
            }
        });
        rb[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_choice = movie_user_save;
                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1 , movie_user_save);
                listView.setAdapter(adapter);
            }
        });
        rb[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_choice = ebook_user_save;
                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1 , ebook_user_save);
                listView.setAdapter(adapter);
            }
        });
        listView = (ListView)getView().findViewById(R.id.SelectChapter_listview);
        if (user_choice == null){
            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1 , music_user_save);
        }else{
            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1 , user_choice);
        }

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                transaction = manager.beginTransaction();
                String s = (String) listView.getItemAtPosition(i);
                Dictionary_02 next_fragment = new Dictionary_02();
                Bundle transfer_data = new Bundle(); // Fragment之間傳輸資料
                transfer_data.putString("chapter",s);
                next_fragment.setArguments(transfer_data);
                transaction.replace(R.id.fragment , next_fragment ,"Dictionary_02").addToBackStack("SelectChapter");
                transaction.commit();
            }
        });


    }

    // 依照選擇類別顯示相對應的章節


    public SelectChapter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectChapter.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectChapter newInstance(String param1, String param2) {
        SelectChapter fragment = new SelectChapter();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        manager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_selectchapter, container, false);
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
        /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
