package com.example.user.shake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.shake.Request.AdminReportRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class AdminReportActivity extends AppCompatActivity {

    private TextView explain;
    public String json_borrower,json_rent_time,json_bikecode,json_imageurl,json_report_content;
    String requestCode;
    Intent intent_main;

    static String imgUrl = "http://13.125.229.179/JPEG_20190512_201100.jpg";
    String borrower;

    ArrayList<String> rentnumber,bikecode,rent_time;
    ArrayList<String> Title,Context,img_url;

    //Test
    ArrayList<ListVT> list_itemArrayList;
    private ListView listview ;
    private AdminReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);
        listview=(ListView)findViewById(R.id.listview_report);
        list_itemArrayList=new ArrayList<>();
        intent_main = getIntent();
        borrower=intent_main.getStringExtra("userId");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    json_borrower=jsonResponse.getString("borrower");
                    json_bikecode = jsonResponse.getString("bikecode");
                    json_rent_time = jsonResponse.getString("renttime");
                    json_imageurl = jsonResponse.getString("imageurl");
                    json_report_content = jsonResponse.getString("content");
                    requestCode=jsonResponse.getString("requestCode");
                    String requestString="";
                    int len = json_borrower.split(",").length;
                    for(int i=0;i<len;i++){
                        if(requestCode.split("\"")[2*i+1].equals("0")){
                            requestString="대여자";
                        }
                        else{
                            requestString="공유자";
                        }
                        list_itemArrayList.add(new ListVT("http://13.125.229.179/JPEG_20190512_201100.jpg",json_borrower.split("\"")[2*i+1]+" 님",json_report_content.split("\"")[2*i+1],requestString,json_rent_time.split("\"")[2*i+1],json_bikecode.split("\"")[2*i+1]));
                        System.out.println(json_borrower.split("\"")[2*i+1]+" 님"+json_report_content.split("\"")[2*i+1]+requestString+json_rent_time.split("\"")[2*i+1]+json_bikecode.split("\"")[2*i+1]);
                    }
                    final Intent intent = new Intent(AdminReportActivity.this,ReportMainAcitivity.class);
                    System.out.println(list_itemArrayList.get(0));
                    //변수 초기화
                    adapter = new AdminReportAdapter(AdminReportActivity.this,list_itemArrayList);
                    listview = (ListView) findViewById(R.id.listview_report);

                    //어뎁터 할당
                    listview.setAdapter(adapter);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        AdminReportRequest adminReportRequest = new AdminReportRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(AdminReportActivity.this);
        queue.add(adminReportRequest);
    }
}
