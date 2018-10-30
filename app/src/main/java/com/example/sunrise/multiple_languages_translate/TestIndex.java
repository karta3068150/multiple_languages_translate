package com.example.sunrise.multiple_languages_translate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TestIndex.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TestIndex#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestIndex extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btn,btn2,btn3,btn4;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentManager manager; //負責管理Fragment
    private android.support.v4.app.FragmentTransaction transaction;
    private OnFragmentInteractionListener mListener;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btn = (Button) getView().findViewById(R.id.listening_test);
        btn2 = (Button) getView().findViewById(R.id.content_mean);
        btn3 = (Button) getView().findViewById(R.id.volcabulary_choose);
        btn4 = (Button) getView().findViewById(R.id.situation_test);
        manager = getActivity().getSupportFragmentManager();
        transaction = manager.beginTransaction();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = manager.beginTransaction();
                Log.e("eee",""+manager.getFragments());
                ListeningTest newFra=new ListeningTest();
                transaction.replace(R.id.fragment,newFra,"ListeningTest").addToBackStack("ListeningTest");
                transaction.commit();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = manager.beginTransaction();
                Log.e("eee",""+manager.getFragments());
                ContentMeanChooseTest newFra=new ContentMeanChooseTest();
                transaction.replace(R.id.fragment,newFra,"ContentMeanChooseTest").addToBackStack("ContentMeanChooseTest");
                transaction.commit();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = manager.beginTransaction();
                Log.e("eee",""+manager.getFragments());
                VolcabularyChoose newFra=new VolcabularyChoose();
                transaction.replace(R.id.fragment,newFra,"VolcabularyChoose").addToBackStack("VolcabularyChoose");
                transaction.commit();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = manager.beginTransaction();
                Log.e("eee",""+manager.getFragments());
                Situation newFra=new Situation();
                transaction.replace(R.id.fragment,newFra,"Situation").addToBackStack("Situation");
                transaction.commit();
            }
        });
    }
    public TestIndex() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestIndex.
     */
    // TODO: Rename and change types and number of parameters
    public static TestIndex newInstance(String param1, String param2) {
        TestIndex fragment = new TestIndex();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_index, container, false);
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
