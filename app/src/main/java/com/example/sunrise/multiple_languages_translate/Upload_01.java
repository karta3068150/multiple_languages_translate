package com.example.sunrise.multiple_languages_translate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Upload_01.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Upload_01#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Upload_01 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match



    // TODO: Rename and change types of parameters
    private FragmentManager manager; //負責管理Fragment
    private android.support.v4.app.FragmentTransaction transaction;
    private Button btn;
    private OnFragmentInteractionListener mListener;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btn = (Button) getView().findViewById(R.id.Upload_01_btn);
        manager = getActivity().getSupportFragmentManager();
        transaction = manager.beginTransaction();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) getView().findViewById(R.id.Upload_01_website);
                Upload_02 next_fragment = new Upload_02();
                Bundle transfer_data = new Bundle(); // Fragment之間傳輸資料
                transfer_data.putString("translate_chapter",String.valueOf( tv.getText() ) ) ;
                next_fragment.setArguments(transfer_data);
                transaction.replace(R.id.fragment,next_fragment,"Upload_02").addToBackStack("Upload_01").commit();
            }
        });
    }
    public Upload_01() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Upload_01 newInstance(String param1, String param2) {
        Upload_01 fragment = new Upload_01();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_01, container, false);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
