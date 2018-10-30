package com.example.sunrise.multiple_languages_translate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Index.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Index#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Index extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentManager manager; //負責管理Fragment
    private android.support.v4.app.FragmentTransaction transaction;
    private Button[] index_bt = new Button[6];
    private LinearLayout index_table;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        aaa();
      //  ProgressDialog pd = ProgressDialog.show(getActivity(),"讀取中...","讀取中");
//        public void onClick(View v) {
//            Intent intent = new Intent();
//            intent.setClass(MainActivity01.this , Page2.class);
//            startActivity(intent);
//        }
    }

    public Index() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Index.
     */
    // TODO: Rename and change types and number of parameters
    public static Index newInstance(String param1, String param2) {
        Index fragment = new Index();
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
        //  2018 / 05 / 30 update
        manager = getActivity().getSupportFragmentManager();


        //  2018 / 05 / 30 update
    }
public void aaa (){
    index_table = (LinearLayout) getView().findViewById (R.id.Index_table);
    index_bt[0] = (Button)getView().findViewById (R.id.Index_upload);
    index_bt[1] = (Button)getView().findViewById (R.id.Index_dictionary);
    index_bt[2] = (Button)getView().findViewById (R.id.Index_vision_test);
    index_bt[3] = (Button)getView().findViewById (R.id.Index_listening_test);
    index_bt[4] = (Button)getView().findViewById (R.id.Index_quiz);
    index_bt[5] = (Button)getView().findViewById (R.id.Index_logout);
    DisplayMetrics metrics = new DisplayMetrics();

    getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    //將按鈕大小依照手機螢幕大小劃分
    index_table.post(new Runnable(){
        public void run(){
            Log.e("ww",index_table.getWidth()+"");
            Log.e("hh",index_table.getHeight()+"");
            for ( int i = 0 ; i < index_bt.length ; i++){
                index_bt[i].setWidth(index_table.getWidth()/2 );
                index_bt[i].setHeight(index_table.getHeight()/3);
            }
        }
    });
    //按鈕設置
    index_bt[0].setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           transaction = manager.beginTransaction();
                                           Log.e("eee",""+manager.getFragments());
                                           Upload_01 newFra=new Upload_01();
                                           transaction.replace(R.id.fragment,newFra,"Upload_01").addToBackStack("Upload_01");

                                           transaction.commit();
                                       }
                                   }
    );
    index_bt[1].setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           transaction = manager.beginTransaction();
                                           Log.e("eee",""+manager.getFragments());
                                           Fragment newFra=new Dictionary_01();
                                           Bundle bundle = new Bundle();
                                           bundle.putString("features_type","Dictionary"); //使系統判別是由哪個功能進入
                                           newFra.setArguments(bundle);
                                           transaction.replace(R.id.fragment,newFra,"Dictionary_01").addToBackStack("Dictionary_01");
                                           transaction.commit();
                                       }
                                   }
    );
    index_bt[2].setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           transaction = manager.beginTransaction();
                                           Log.e("eee",""+manager.getFragments());
                                           Fragment newFra=new Dictionary_01();
                                           Bundle bundle = new Bundle();
                                           bundle.putString("features_type","Vision");  //使系統判別是由哪個功能進入
                                           newFra.setArguments(bundle);
                                           transaction.replace(R.id.fragment,newFra,"VisionTest").addToBackStack("VisionTest");
                                           transaction.commit();
                                       }
                                   }
    );
    index_bt[3].setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           transaction = manager.beginTransaction();
                                           Log.e("eee",""+manager.getFragments());
                                           ListeningPraticeChoose newFra=new ListeningPraticeChoose();
                                           transaction.replace(R.id.fragment,newFra,"ListeningPraticeChoose").addToBackStack("ListeningPraticeChoose");
                                           transaction.commit();
                                       }
                                   }
    );
    index_bt[4].setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           transaction = manager.beginTransaction();
                                           Log.e("eee",""+manager.getFragments());
                                           TestIndex newFra=new TestIndex();
                                           transaction.replace(R.id.fragment,newFra,"TestIndex").addToBackStack("TestIndex");
                                           transaction.commit();
                                       }
                                   }
    );
    index_bt[5].setOnClickListener(new Button.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           transaction = manager.beginTransaction();
                                           Log.e("eee",""+manager.getFragments());
                                           Grade newFra=new Grade();
                                           transaction.replace(R.id.fragment,newFra,"Grade").addToBackStack("Grade");
                                           transaction.commit();
                                       }
                                   }
    );


}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onPause() {
        super.onPause();

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
