package com.example.user.shake;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.shake.Request.InsertPointRequest;

import org.json.JSONObject;

import java.util.Date;

public class PointMainActivity extends AppCompatActivity {

    int chargePoint=0;
    TextView currentPoint;
    TextView addPoint;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_main);
        intent = getIntent();
        int userPoint=intent.getIntExtra("userPoint",0);
        currentPoint=(TextView)findViewById(R.id.textView17);
        currentPoint.setText(userPoint+" P");
        addPoint=(TextView)findViewById(R.id.textView20);
    }

    public void plusClicked(View v){
        if(chargePoint+1000<=100000){
            chargePoint+=1000;
            addPoint.setText(chargePoint+" P");
        }
        else{
            Toast.makeText(getApplication(),"포인트는 한번에 10만 포인트까지 충전 가능합니다",Toast.LENGTH_SHORT).show();
        }
    }
    public void minusClicked(View v){
        if(chargePoint-1000>=0){
            chargePoint-=1000;
            addPoint.setText(chargePoint+" P");
        }
        else{
            ;
        }
    }
    public void chargeClicked(View v){
        if(chargePoint>0){
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonResponse = new JSONObject(response);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            long time = System.currentTimeMillis();
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String day = dayTime.format(new Date(time));
            InsertPointRequest insertPointRequest = new InsertPointRequest(intent.getStringExtra("userID"),chargePoint,day, responseListener);
            RequestQueue queue = Volley.newRequestQueue(PointMainActivity.this);
            queue.add(insertPointRequest);
            System.out.println(intent.getStringExtra("userID")+"     "+chargePoint+day+"TESTPOINT");
            Intent back = new Intent(PointMainActivity.this,PointActivity.class);
            back.putExtra("userId",intent.getStringExtra("userID"));
            startActivity(back);
            finish();
        }
        else{
            Toast.makeText(getApplication(),"1000 포인트 이상부터 충전 가능합니다",Toast.LENGTH_SHORT).show();
        }
    }
}
