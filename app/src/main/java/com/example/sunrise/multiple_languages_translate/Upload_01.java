package com.example.sunrise.multiple_languages_translate;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


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
    private ProgressDialog dialog; // 下載中對話窗
    private OnFragmentInteractionListener mListener;
    private String[] language = new String[] {"zh","en"}; // 位置 0 : 翻譯前語言  , 1 : 翻譯後語言
    private String[] word_data,exam_data,word_trans_data,word_ex_data,pinyin;
    String select_type = "" ;//選擇類型
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        btn = (Button) getView().findViewById(R.id.Upload_01_btn);
        manager = getActivity().getSupportFragmentManager();
        transaction = manager.beginTransaction();
        getActivity().setTitle("語言翻轉");


        // /事件處理中圖示
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch(msg.what)
                {
                    case 0:
                        dialog.dismiss();
                        break;
                }
            }
        };
        //按鈕事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView tv = (TextView) getView().findViewById(R.id.Upload_01_website);

                dialog = ProgressDialog.show(getActivity(),
                        "讀取中", "請等待檔案下載完畢...", true);
                RadioGroup rg = getView().findViewById(R.id.Upload_01_RadioGroup);
                final RadioButton rb = getView().findViewById(rg.getCheckedRadioButtonId());
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions,112);

                final Bundle transfer_data = new Bundle(); // Fragment之間傳輸資料

                //下載詞彙發音、教材詞彙與題目
                Thread download_sound =new Thread() {
                    @Override
                    public void run() {
                        //下載教材詞彙與題目
                        URL url;
                        HttpURLConnection conn = null;
                        try {
                            // 與資料庫做連結
                            url = new URL("http://120.105.160.7/104b/get_data.php");
                            //判斷選取種類
                            switch ( rb.getTag().toString() ){
                                case "1":
                                    select_type = "music";
                                    break;
                                case "2":
                                    select_type = "movie";
                                    break;
                                case "3":
                                    select_type = "e-book";
                                    break;
                            }
                            conn = (HttpURLConnection) url.openConnection();
                            //POST傳輸
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            //  OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter( conn.getOutputStream() , "UTF-8"));
                            writer.write("type="+select_type+"&");
                            writer.write("name="+tv.getText().toString());
                            writer.flush();
                            writer.close();
                            //  out.close();
                            conn.connect();
                            //Get Response  ,  取得輸入流，並使用Reader讀取
                            InputStream is = conn.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            StringBuilder response = new StringBuilder();
                            String line,re;
                            //     send_data = "";
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                                response.append('\r');
                            }
                            reader.close();
                            re = response.toString();
                            Log.e("get_data",re);
                            JSONObject jsonObject = new JSONObject(re);
                            // 讀取所有詞彙
                            //所有詞彙
                            JSONArray jsonArray = jsonObject.getJSONArray("word");
                            word_data = new String[jsonArray.length()-1];
                            for (int i=0 ; i < jsonArray.length()-1 ; i++){
                                word_data[i] = jsonArray.getString(i);
                            }
                            //翻譯後的文字
                            jsonArray = jsonObject.getJSONArray("word_tran");
                            word_trans_data = new String [jsonArray.length()-1];
                            for (int i=0 ; i < jsonArray.length()-1 ; i++){
                                word_trans_data[i] = jsonArray.getString(i);
                            }
                            //題庫
                            jsonArray = jsonObject.getJSONArray("exam");
                            exam_data = new String [jsonArray.length()-1];
                            for (int i=0 ; i < jsonArray.length()-1 ; i++){
                                exam_data[i] = jsonArray.getString(i);
                            }
                            //拼音
                            jsonArray = jsonObject.getJSONArray("pinyin");
                            pinyin = new String [jsonArray.length()-1];
                            for (int i=0 ; i < jsonArray.length()-1 ; i++){
                                pinyin[i] = jsonArray.getString(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (conn != null) {
                                conn.disconnect();  //取到資料後斷線
                            }
                            //宣告資料庫操作指標
                            SQLite sqlite = new SQLite(getActivity());

                            Cursor c = null;
                            SQLiteDatabase db = sqlite.getWritableDatabase();
                            //儲存所有詞彙的table
                            db.execSQL("CREATE TABLE IF NOT EXISTS all_word"+
                                    "(word VARCHAR NOT NULL,"+"language VARCHAR NOT NULL,"+"word_trans VARCHAR NOT NULL," //+"word_explain VARCHAR NOT NULL,"
                                    +"pinyin VARCHAR NOT NULL);");
                            //儲存該章節詞彙的table
                            db.execSQL("CREATE TABLE IF NOT EXISTS "+ tv.getText() +
                                    "(word VARCHAR NOT NULL PRIMARY KEY,"+"language VARCHAR NOT NULL,"+"word_trans VARCHAR NOT NULL," //+"word_explain VARCHAR NOT NULL,"
                                    +"pinyin VARCHAR NOT NULL);");
                            //儲存已使用教材
                            db.execSQL("CREATE TABLE IF NOT EXISTS all_chapter" +
                                    "(name VARCHAR NOT NULL PRIMARY KEY,"+"language VARCHAR NOT NULL,type VARCHAR NOT NULL);");

                            //將本次製作教材名稱加入至all_chapter表格內
                            c=db.rawQuery("SELECT * FROM all_chapter WHERE name='"+tv.getText().toString()+"'", null);
                            if (c.getCount()==0) {
                                db.execSQL("INSERT INTO  all_chapter" +
                                        " (name,language,type) VALUES " +
                                        "('" + tv.getText() + "','english','" + select_type + "');");
                            }
                            //儲存教材內容之題目(預定)
                            db.execSQL("CREATE TABLE IF NOT EXISTS question" +
                                    "(name VARCHAR NOT NULL PRIMARY KEY);");

                            //將不重複的詞彙加入
                            for (int i = 0 ; i < word_data.length ; i++){
                                //c=db.rawQuery("SELECT * FROM all_word WHERE word=?", new String[]{word_data[i]});
                                c=db.rawQuery("SELECT * FROM all_word WHERE word='"+word_data[i]+"'", null);
                                if (c.getCount()>0) continue;
                                db.execSQL("INSERT INTO "+ tv.getText() +
                                        " (word,language,word_trans,pinyin) VALUES "+
                                        "('"+word_data[i]+"','english','"+word_trans_data[i]+"','"+pinyin[i]+"');");
                                db.execSQL("INSERT INTO  all_word" +
                                        " (word,language,word_trans,pinyin) VALUES "+
                                        "('"+word_data[i]+"','english','"+word_trans_data[i]+"','"+pinyin[i]+"');");
                            }
                            //將不重複之題目加入
                            for (int i = 0 ; i < exam_data.length ; i++){
                                //          c=db.rawQuery("SELECT * FROM question WHERE name=? ", new String[]{exam_data[i]} );
                                c=db.rawQuery("SELECT * FROM question WHERE name='"+exam_data[i]+"' ", null);
                                if (c.getCount()>0) continue;
                                db.execSQL("INSERT INTO  question"+
                                        " ( name ) VALUES "+
                                        "('" +exam_data[i]+ "');");
                            }
                            db.close();
                            sqlite.close();
                        }

                        //下載詞彙發音
                        try {
                            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.example.sunrise.test");
                            //判断文件夹是否存在，如果不存在就创建，否则不创建
                            if (!file.exists()) {
                                //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
                                file.mkdirs();
                            }
                            //下載聲音
                            for (int i = 0 ; i < 2 ; i++) {
                                for (int q = 0; q < word_data.length; q++) {
                                    String file_url = "" , file_name = "";
                                    if ( i==0 ){
                                        file_url = "https://fanyi.baidu.com/gettts?lan="+language[i]+"&text=" + word_data[q] + "&spd=5&source=web";
                                        file_name = word_data[q];
                                    } else{
                                        file_url = "https://fanyi.baidu.com/gettts?lan="+language[i]+"&text=" + word_trans_data[q] + "&spd=5&source=web";
                                        file_name = word_trans_data[q];
                                    }
                                    file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.example.sunrise.test/" + file_name);
                                    //判断文件夹是否存在，如果不存在就创建，否则不创建
                                    if (file.exists()) continue;
                                    url = new URL(file_url);
                                    URLConnection conection = url.openConnection();
                                    conection.connect();
                                    // download the file 下載檔案 打開url的串流連接
                                    InputStream input = new BufferedInputStream(url.openStream());
                                    // 檢測資料夾有無存在

                                    // Output stream
                                    OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.example.sunrise.test/" + file_name + ".mp3");
                                    byte data[] = new byte[1024];

                                    long total = 0;
                                    int count = 0;
                                    while ((count = input.read(data)) != -1) {
                                        // writing data to file  (byte陣列,起始位置,大小)
                                        output.write(data, 0, count);
                                    }

                                    // flushing output
                                    output.flush();

                                    // closing streams
                                    output.close();
                                    input.close();
                                }
                            }
                            sleep(2000);
                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                        }finally {
                            handler.sendEmptyMessage(0);
                            //------與網站連接 end
                            transfer_data.putString("translate_chapter",String.valueOf( tv.getText() ) ) ;
                            Dictionary_01 next_fragment = new Dictionary_01();
                            next_fragment.setArguments(transfer_data);
                            transaction.replace(R.id.fragment,next_fragment,"Dictionary_01").addToBackStack("Upload_01").commit();
                        }
                    }
                };

                download_sound.start();

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
