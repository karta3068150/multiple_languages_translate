package com.example.sunrise.multiple_languages_translate;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VisionTest.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VisionTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisionTest extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ImageView image = null;
    //RadioButton rb5,rb6,rb7,rb8,radiobutton;
    RadioGroup radiogroup;
    Button reply_btn;//回答按鈕
    private int ans_number = 0 ; //答到第幾題
    private int[] photo = new int [] {R.drawable.apple,R.drawable.lemon,R.drawable.pear,R.drawable.grape,R.drawable.strawberry,R.drawable.star_fruit};//照片

    private String[] response = new String[photo.length],//儲存使用者回答的答案
                    answer = new String[]{"apple","lemon","pear","grape","strawberry","star fruit"},//儲存答案的陣列
                    rand_ans = new String[]{"lemon","apple","kiwifruit","cellphone","water","orange","grape","banana","cabbage","grape","orange","eggplant","pineapple","peach"},
                    rand_ans_question = new String [4];
    public VisionTest() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        createQuestion();

        //RadioGroup 變換答案事件
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                response[ans_number] = ((RadioButton)getView().findViewById(i)).getText().toString();
                Log.e("ans",radioGroup.getCheckedRadioButtonId()+"");
                Log.e("ans_i",i+"");
                Log.e("ans_s",response[ans_number]+"");
            }
        });
        //Reply Button 回答按鈕事件
        reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans_number++;
                if (ans_number == answer.length ){
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    Bundle bundle = new Bundle();

                    bundle.putStringArray("answer_array", answer ) ;
                    bundle.putStringArray("response_array", response );
                    bundle.putIntArray("photo_array", photo);
                    Dictionary_01 next_fragment = new Dictionary_01();
                    next_fragment.setArguments(bundle);
                    transaction.replace(R.id.fragment,next_fragment,"Dictionary_01").addToBackStack("Upload_01").commit();
                }
                createQuestion();
            }
        });
    }
    //創造題目方法
    private void createQuestion (){
        getActivity().setTitle("視覺學習("+(ans_number+1)+"/"+answer.length+")");
        image = getView().findViewById(R.id.imageView2);
        image.setImageResource(photo[ans_number]);
        //答案選擇group
        radiogroup = getView().findViewById(R.id.VisionTest_RadioGroup);
        reply_btn = getView().findViewById(R.id.VisionTest_Reply);
        Random random = new Random();
        for (int i=0 ; i < rand_ans_question.length-1 ; i++){
            rand_ans_question[i] =  rand_ans[random.nextInt(rand_ans.length)];
            for (int j = 0 ; j < i ; j++){
                if (rand_ans_question[i].equals(rand_ans_question[j]) ){
                    i--;
                    break;
                }
            }
        }
        rand_ans_question[ rand_ans_question.length-1 ] = answer[ ans_number ];
        for ( int i = 0 ; i < 10 ; i++ ){
            int x = random.nextInt(4) , y = random.nextInt(4);
            String str = "";
            str = rand_ans_question[x];
            rand_ans_question[x] = rand_ans_question[y];
            rand_ans_question[y] = str;
        }
        for (int i = 0 ; i < rand_ans_question.length ; i++){
            ((RadioButton) radiogroup.getChildAt(i)).setText(rand_ans_question[i]);
        }
        ((RadioButton) radiogroup.getChildAt(0)).setChecked(true);
        //response 儲存使用者答案 ans_number 使用者答到第幾題
        response[ans_number] = ((RadioButton) radiogroup.getChildAt(0)).getText().toString();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VisionTest.
     */
    // TODO: Rename and change types and number of parameters
    public static VisionTest newInstance(String param1, String param2) {
        VisionTest fragment = new VisionTest();
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
        return inflater.inflate(R.layout.fragment_vision_test, container, false);
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
