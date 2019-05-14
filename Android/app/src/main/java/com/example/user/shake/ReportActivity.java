package com.example.user.shake;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.user.shake.Request.CheckRequest;
import com.example.user.shake.Request.RentInfoRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private TextView explain;
    public String json_rentnumber,json_bikecode,json_rent_time;
    Intent intent_main;

    //static ImageView imView;
    static String imgUrl = "http://13.125.229.179/JPEG_20190512_201100.jpg";
    //static Bitmap bmImg;
    //static back task;

    ArrayList<String> rentnumber,bikecode,rent_time;
    ArrayList<String> Title,Context,img_url;
    ArrayList<Integer> img;

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

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    json_rentnumber=jsonResponse.getString("rentnumber");
                    json_bikecode = jsonResponse.getString("bikecode");
                    json_rent_time = jsonResponse.getString("rent_time");
                    //System.out.println(json_rentnumber.split("\"")[1]+"      "+json_rentnumber.split("\"")[3]);
                    rentnumber=new ArrayList<>(); img_url=new ArrayList<>();
                    // bikecode=new ArrayList<>(); rent_time=new ArrayList<>();
                    //Title=new ArrayList<>(); Context=new ArrayList<>(); img=new ArrayList<>();

                    int len = json_rentnumber.split(",").length;
                    for(int i=0;i<len;i++){
                        rentnumber.add(json_rentnumber.split("\"")[2*i+1]);
                        list_itemArrayList.add(new ListVO("http://13.125.229.179/JPEG_20190512_201100.jpg",json_bikecode.split("\"")[2*i+1],json_rent_time.split("\"")[2*i+1]));
                    }
                    final Intent intent = new Intent(ReportActivity.this,ReportMainAcitivity.class);

                    //변수 초기화
                    adapter = new ReportViewAdapter(ReportActivity.this,list_itemArrayList);
                    listview = (ListView) findViewById(R.id.listview_report);

                    //어뎁터 할당
                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(ReportActivity.this,list_itemArrayList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                            intent.putExtra("img_url","http://13.125.229.179/JPEG_20190512_201100.jpg");
                            intent.putExtra("borrower",intent_main.getStringExtra("userId"));
                            intent.putExtra("bikecode",list_itemArrayList.get(position).getTitle());
                            intent.putExtra("renttime",list_itemArrayList.get(position).getContext());
                            startActivity(intent);
                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        RentInfoRequest rentInfoRequest = new RentInfoRequest("test", responseListener);
        RequestQueue queue = Volley.newRequestQueue(ReportActivity.this);
        queue.add(rentInfoRequest);
    }
}
