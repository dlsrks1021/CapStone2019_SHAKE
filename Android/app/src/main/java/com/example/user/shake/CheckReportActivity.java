package com.example.user.shake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.shake.Request.CheckReportRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class CheckReportActivity extends AppCompatActivity {

    Intent intent;
    String json_borrower,json_renttime,json_imagerul,json_content;
    ArrayList<ListVI> list_itemArrayList;
    private ListView listview ;
    private PointViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report);
        intent = getIntent();
        String bikecode = intent.getStringExtra("bikecode");
        listview=(ListView)findViewById(R.id.listview_report);
        list_itemArrayList=new ArrayList<>();
        Response.Listener<String> responseListener5 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    json_borrower=jsonResponse.getString("borrower");
                    json_renttime=jsonResponse.getString("renttime");
                    json_imagerul=jsonResponse.getString("imageurl");
                    json_content=jsonResponse.getString("content");
                    int len = json_borrower.split(",").length;
                    if(json_borrower.equals("[]")) {
                        len=0;
                        list_itemArrayList.add(new ListVI("http://13.125.229.179/white.png","신고 내역이 없습니다","",""));
                    }
                    for(int i=0;i<len;i++){
                        String url=json_imagerul.split("\"")[2*i+1].replaceAll("\\\\","");
                        list_itemArrayList.add(new ListVI(url,json_content.split("\"")[2*i+1],json_renttime.split("\"")[2*i+1],json_borrower.split("\"")[2*i+1]+" 님"));
                        //System.out.println(url);
                    }
                    final Intent intent = new Intent(CheckReportActivity.this,ReportMainAcitivity.class);
                    adapter = new PointViewAdapter(CheckReportActivity.this,list_itemArrayList);
                    listview = (ListView) findViewById(R.id.listview_report);
                    listview.setAdapter(adapter);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        CheckReportRequest checkReportRequest = new CheckReportRequest(bikecode, responseListener5);
        RequestQueue queue5 = Volley.newRequestQueue(CheckReportActivity.this);
        queue5.add(checkReportRequest);
    }
}
