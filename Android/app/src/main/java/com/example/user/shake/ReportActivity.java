package com.example.user.shake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.shake.Request.RentInfoRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    private TextView explain;
    public String json_rentnumber,json_bikecode,json_rent_time,json_url;
    Intent intent_main;

    static String imgUrl = "http://13.125.229.179/JPEG_20190512_201100.jpg";
    String borrower,temp;

    ArrayList<String> rentnumber,bikecode,rent_time;
    ArrayList<String> Title,Context,img_url;

    //Test
    ArrayList<ListVO> list_itemArrayList;
    private ListView listview ;
    private ReportViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toast.makeText(getApplication(),"신고하고자 하는 항목을 선택하세요",Toast.LENGTH_SHORT).show();
        listview=(ListView)findViewById(R.id.listview_report);
        list_itemArrayList=new ArrayList<>();
        intent_main = getIntent();
        borrower=intent_main.getStringExtra("userId");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    json_rentnumber=jsonResponse.getString("rentnumber");
                    json_bikecode = jsonResponse.getString("bikecode");
                    json_rent_time = jsonResponse.getString("rent_time");
                    json_url = jsonResponse.getString("imageurl");
                    //System.out.println(json_rentnumber);
                    rentnumber=new ArrayList<>(); img_url=new ArrayList<>();
                    // bikecode=new ArrayList<>(); rent_time=new ArrayList<>();
                    //Title=new ArrayList<>(); Context=new ArrayList<>(); img=new ArrayList<>();
                    int len = json_rentnumber.split(",").length;
                    if(json_rentnumber.split(",").equals("[]")){
                        len=0;
                        list_itemArrayList.add(new ListVO("http://13.125.229.179/white.png","대여 기록이 없습니다.",""));
                    }
                    for(int i=0;i<len;i++){
                        temp="";
                        if(json_url.split("\"")[2*i+1].equals("not implementtest    1ed yet")){
                            temp="http://13.125.229.179/JPEG_20190512_201100.jpg";
                        }else{
                            temp=json_url.split("\"")[2*i+1];
                            temp=temp.replaceAll("\\\\","");
                        }
                        //System.out.println(temp);
                        list_itemArrayList.add(new ListVO(temp,json_bikecode.split("\"")[2*i+1],json_rent_time.split("\"")[2*i+1]));
                    }
                    final Intent intent = new Intent(ReportActivity.this,ReportMainAcitivity.class);
                    System.out.println(list_itemArrayList.get(0));
                    //변수 초기화
                    adapter = new ReportViewAdapter(ReportActivity.this,list_itemArrayList);
                    listview = (ListView) findViewById(R.id.listview_report);

                    //어뎁터 할당
                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(ReportActivity.this,list_itemArrayList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                            intent.putExtra("img_url",temp);
                            intent.putExtra("borrower",intent_main.getStringExtra("userId"));
                            intent.putExtra("bikecode",list_itemArrayList.get(position).getTitle());
                            intent.putExtra("renttime",list_itemArrayList.get(position).getContext());
                            intent.putExtra("requestCode",0);//대여기록 = 0, 공유기록=1
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        RentInfoRequest rentInfoRequest = new RentInfoRequest(borrower, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReportActivity.this);
        queue.add(rentInfoRequest);
    }
}
