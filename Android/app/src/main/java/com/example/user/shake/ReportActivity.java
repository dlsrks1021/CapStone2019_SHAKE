package com.example.user.shake;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.shake.Request.CheckRequest;
import com.example.user.shake.Request.RentInfoRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    private TextView explain;
    public String json_rentnumber,json_bikecode,json_rent_time;

    ImageView imView;
    String imgUrl = "http://13.125.229.179/JPEG_20190512_201100.jpg";
    Bitmap bmImg;
    back task;
    ArrayList<String> rentnumber,bikecode,rent_time;

    private ListView listview ;
    private ReportViewAdapter adapter;
    private int[] img = {R.drawable.common_google_signin_btn_icon_dark,R.drawable.common_google_signin_btn_icon_dark,R.drawable.common_google_signin_btn_icon_dark};
    private String[] Title = {"정준일 바램","윤종신 좋니","러블리즈 아츄"};
    private String[] Context = {"정준일 명곡","윤종신 히트곡","러블리즈 히트곡"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toast.makeText(getApplication(),"신고하고자 하는 항목을 선택하세요",Toast.LENGTH_SHORT).show();
        //explain=(TextView) findViewById(R.id.textView_report_explain);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    json_rentnumber=jsonResponse.getString("rentnumber");
                    json_bikecode = jsonResponse.getString("bikecode");
                    json_rent_time = jsonResponse.getString("rent_time");
                    //System.out.println(json_rentnumber.split("\"")[1]+"      "+json_rentnumber.split("\"")[3]);
                    rentnumber=new ArrayList<>(); bikecode=new ArrayList<>(); rent_time=new ArrayList<>();

                    int len = json_rentnumber.split(",").length;
                    for(int i=0;i<len;i++){
                        //System.out.println(2*i+1+"    "+json_bikecode.split("\"")[2*i+1]);
                        rentnumber.add(json_rentnumber.split("\"")[2*i+1]);
                        bikecode.add(json_bikecode.split("\"")[2*i+1]);
                        rent_time.add(json_rent_time.split("\"")[2*i+1]);
                    }

                    //변수 초기화
                    adapter = new ReportViewAdapter();
                    listview = (ListView) findViewById(R.id.listview_report);

                    //어뎁터 할당
                    listview.setAdapter(adapter);

                    //adapter를 통한 값 전달
                    for(int i=0; i<img.length;i++){
                        adapter.addVO(ContextCompat.getDrawable(ReportActivity.this,img[i]),Title[i],Context[i]);
                    }
                    //explain.setText(rentnumber.get(1)+"   "+bikecode.get(1)+"     "+rent_time.get(1));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        RentInfoRequest rentInfoRequest = new RentInfoRequest("test", responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReportActivity.this);
        queue.add(rentInfoRequest);

        task = new back();
        imView = (ImageView) findViewById(R.id.imageView_report);
        task.execute(imgUrl);

    }
    //image upload
    private class back extends AsyncTask<String, Integer,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);

            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }
        protected void onPostExecute(Bitmap img){
            imView.setImageBitmap(bmImg);
        }
    }
}
