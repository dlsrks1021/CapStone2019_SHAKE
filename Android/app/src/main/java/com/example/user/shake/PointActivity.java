package com.example.user.shake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.shake.Request.GetPointRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class PointActivity extends AppCompatActivity {

    ArrayList<ListVI> list_itemArrayList;
    private PointViewAdapter adapter;
    public String jsonPoint,json_time,json_finish;
    ArrayList<String> userID,time,array_finish;
    public int userPoint;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        list_itemArrayList=new ArrayList<>();
        intent=getIntent();
        String user= intent.getStringExtra("userId");
        final TextView point=(TextView)findViewById(R.id.textView17);
        final ListView listView=(ListView)findViewById(R.id.listView_point);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    jsonPoint=jsonResponse.getString("userID");
                    json_time = jsonResponse.getString("time");
                    json_finish = jsonResponse.getString("finish");
                    userPoint=jsonResponse.getInt("userPoint");
                    point.setText(userPoint+" P");
                    //System.out.println(jsonPoint.split("\"")[1]+"      "+json_time.split("\"")[1]+"    "+json_finish.split("\"")[1]);
                    userID=new ArrayList<>(); time=new ArrayList<>(); array_finish=new ArrayList<>();
                    String finish_string;
                    int len = jsonPoint.split(",").length;
                    if(len==0) list_itemArrayList.add(new ListVI("http://13.125.229.179/white.png","포인트 신청 내역이 없습니다","",""));
                    for(int i=0;i<len;i++){
                        userID.add(jsonPoint.split("\"")[2*i+1]);
                        if(json_finish.split("\"")[2*i+1].equals("1")) {
                            finish_string="충전 완료";
                        }
                        else {
                            finish_string="충전 대기 중";
                        }
                        System.out.println(jsonPoint.split("\"")[1]+"      "+json_time.split("\"")[1]+"    "+finish_string);
                        list_itemArrayList.add(new ListVI("http://13.125.229.179/white.png","충전 금액 : "+jsonPoint.split("\"")[2*i+1]+" P",json_time.split("\"")[2*i+1],finish_string));
                    }
                    final Intent intent = new Intent(PointActivity.this,ReportMainAcitivity.class);
                    System.out.println(list_itemArrayList.get(0));
                    //변수 초기화
                    adapter = new PointViewAdapter(PointActivity.this,list_itemArrayList);

                    //어뎁터 할당
                    listView.setAdapter(adapter);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        GetPointRequest getPointRequest = new GetPointRequest(user, responseListener);
        RequestQueue queue = Volley.newRequestQueue(PointActivity.this);
        queue.add(getPointRequest);
    }
    public void chargeButtonClicked(View v){
        Intent intent_point=new Intent(PointActivity.this,PointMainActivity.class);
        intent_point.putExtra("userID",intent.getStringExtra("userId"));
        intent_point.putExtra("userPoint",userPoint);
        startActivity(intent_point);
        finish();
    }
}
