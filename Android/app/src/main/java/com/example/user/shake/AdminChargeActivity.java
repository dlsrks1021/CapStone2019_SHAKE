package com.example.user.shake;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.shake.Request.AdminInsertPointRequest;
import com.example.user.shake.Request.AdminPointRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class AdminChargeActivity extends AppCompatActivity {

    private TextView explain;
    public String json_userID,json_time,json_point;
    Intent intent_main;

    static String imgUrl = "http://13.125.229.179/JPEG_20190512_201100.jpg";
    String borrower;

    ArrayList<String> rentnumber,bikecode,rent_time;
    ArrayList<String> Title,Context,img_url;

    //Test
    ArrayList<ListVI> list_itemArrayList;
    private ListView listview ;
    private PointViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_charge);
        list_itemArrayList=new ArrayList<>();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    json_userID=jsonResponse.getString("userID");
                    json_time = jsonResponse.getString("time");
                    json_point = jsonResponse.getString("point");
                    rentnumber=new ArrayList<>(); img_url=new ArrayList<>();
                    int len = json_userID.split(",").length;
                    for(int i=0;i<len;i++){
                        list_itemArrayList.add(new ListVI("http://13.125.229.179/white.png","충전 금액 : "+json_point.split("\"")[2*i+1],json_time.split("\"")[2*i+1],json_userID.split("\"")[2*i+1]+" 님"));
                    }
                    final Intent intent = new Intent(AdminChargeActivity.this,ReportMainAcitivity.class);
                    System.out.println(list_itemArrayList.get(0));
                    //변수 초기화
                    adapter = new PointViewAdapter(AdminChargeActivity.this,list_itemArrayList);
                    listview = (ListView) findViewById(R.id.listview_report);

                    //어뎁터 할당
                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {//list_itemArrayList.get(position).getTitle()
                            AlertDialog.Builder builder = new AlertDialog.Builder(AdminChargeActivity.this);
                            builder.setMessage(list_itemArrayList.get(position).getFinish()+"께 \n"+list_itemArrayList.get(position).getTitle()+" 포인트를 충전합니다")
                                    .setNegativeButton("예", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            int point=Integer.parseInt(list_itemArrayList.get(position).getTitle().replace("충전 금액 : ",""));
                                            String user=list_itemArrayList.get(position).getFinish().replace(" 님","");
                                            System.out.println(point+"   "+user+"TEST");
                                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonResponse = new JSONObject(response);
                                                    }
                                                    catch (Exception e){
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            AdminInsertPointRequest adminInsertPointRequest = new AdminInsertPointRequest(user,point,list_itemArrayList.get(position).getContext(), responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(AdminChargeActivity.this);
                                            queue.add(adminInsertPointRequest);
                                            list_itemArrayList.remove(list_itemArrayList.get(position));
                                            listview.setAdapter(adapter);
                                        }
                                    })
                                    .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ;
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        AdminPointRequest adminPointRequest = new AdminPointRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(AdminChargeActivity.this);
        queue.add(adminPointRequest);
    }
}
