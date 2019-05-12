package com.example.user.shake;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
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

public class ReportActivity extends AppCompatActivity {

    private TextView explain;
    private String rentnumber,bikecode,rent_time;

    ImageView imView;
    String imgUrl = "http://13.125.229.179/JPEG_20190512_201100.jpg";
    Bitmap bmImg;
    back task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toast.makeText(getApplication(),"신고하고자 하는 항목을 선택하세요",Toast.LENGTH_SHORT).show();
        explain=(TextView) findViewById(R.id.textView_report_explain);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    //boolean success = jsonResponse.getBoolean("success");
                    rentnumber=jsonResponse.getString("rentnumber");
                    bikecode = jsonResponse.getString("bikecode");
                    rent_time = jsonResponse.getString("rent_time");
                    System.out.println(rentnumber+"    "+bikecode+"     "+rent_time);
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
