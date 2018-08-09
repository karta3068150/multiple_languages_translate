package com.example.sunrise.multiple_languages_translate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


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

    private String chapter_name = "";
    private OnFragmentInteractionListener mListener;

    public Upload_02() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView tv = getView().findViewById(R.id.Upload_02_chapter_name);
        tv.setText("歌名 : "+chapter_name);
        final Button bt2 = getView().findViewById(R.id.button2) , bt4 = getView().findViewById(R.id.button4);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate_Result next_fragment = new Translate_Result();
                Bundle transfer_data = new Bundle();
                transfer_data.putString("word",String.valueOf( bt2.getText()) );
                next_fragment.setArguments(transfer_data);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment,next_fragment,"Translate_Result").addToBackStack("Upload_02").commit();
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate_Result next_fragment = new Translate_Result();
                Bundle transfer_data = new Bundle();
                transfer_data.putString("word",String.valueOf( bt4.getText()) );
                next_fragment.setArguments(transfer_data);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment,next_fragment,"Translate_Result").addToBackStack("Upload_02").commit();
            }
        });
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
