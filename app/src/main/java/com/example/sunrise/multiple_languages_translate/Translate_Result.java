package com.example.sunrise.multiple_languages_translate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Translate_Result extends Fragment {
    private OnFragmentInteractionListener mListener;
    private String translate_word = "";
    public Translate_Result() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
        TextView tv = (TextView) getView().findViewById(R.id.Translate_result_word)
                ,pinyin = (TextView) getView().findViewById(R.id.Translate_result_pinyin)
                ,customer_language = (TextView) getView().findViewById(R.id.Translate_result_custom_language)
                ,sentence_origin = (TextView) getView().findViewById(R.id.Translate_ressult_sentence_origin)
                ,sentence_custom = (TextView) getView().findViewById(R.id.Translate_ressult_sentence_customer);
        tv.setText(translate_word);
        switch (translate_word){
            case "開心":
                pinyin.setText("kai  xin");
                customer_language.setText("happy");
                sentence_origin.setText("心情愉快舒暢");
                sentence_custom.setText("Feeling or showing pleasure or contentment.");
                break;
            case "同學":
                pinyin.setText("tong  xue");
                customer_language.setText("classmate");
                sentence_origin.setText("在同一所學校學習的人");
                sentence_custom.setText("A fellow member of a class at school, college, or university.");
                break;
            case "咖啡":
                pinyin.setText("ka  fei");
                customer_language.setText("coffee");
                sentence_origin.setText("常綠 小喬木或灌木，葉子長卵形，先端尖，花白色，有香味，結漿果，深紅色，內有兩顆種子。種子炒熟制成粉，可以作飲料。產在熱帶和亞熱帶地區");
                sentence_custom.setText("Coffee is a brewed drink prepared from roasted coffee beans, which are the seeds of berries from the Coffea plant");
                break;
        }

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
