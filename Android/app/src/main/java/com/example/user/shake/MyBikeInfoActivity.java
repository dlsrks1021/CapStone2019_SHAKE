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
import com.example.user.shake.Request.GetMyBikeRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class MyBikeInfoActivity extends AppCompatActivity {

    private TextView explain;
    public String json_cost,json_imageurl,json_model_name,json_bike_type,json_addInfo;
    Intent intent_main;

    static String imgUrl = "http://13.125.229.179/JPEG_20190512_201100.jpg";
    String borrower;

    ArrayList<String> cost,imageurl,model_name,bike_type,addInfo;
    ArrayList<String> Title,Context,img_url;

    //Test
    ArrayList<ListVO> list_itemArrayList;
    private ListView listview ;
    private ReportViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bike_info);
        //Toast.makeText(getApplication(),"신고하고자 하는 항목을 선택하세요",Toast.LENGTH_SHORT).show();
        listview=(ListView)findViewById(R.id.listview_mybike);
        list_itemArrayList=new ArrayList<>();
        intent_main = getIntent();
        borrower=intent_main.getStringExtra("userId");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    json_cost=jsonResponse.getString("cost");
                    json_imageurl=jsonResponse.getString("imageurl");
                    json_model_name=jsonResponse.getString("model_name");
                    json_bike_type=jsonResponse.getString("bike_type");
                    json_addInfo=jsonResponse.getString("addInfo");

                    System.out.println(json_cost+"   "+json_imageurl+"   "+json_model_name+"   "+json_bike_type+"   "+json_addInfo);

                    cost=new ArrayList<>(); imageurl=new ArrayList<>(); model_name=new ArrayList<>(); bike_type=new ArrayList<>(); addInfo=new ArrayList<>();

                    img_url=new ArrayList<>();

                    int len = json_cost.split(",").length;
                    for(int i=0;i<len;i++){
                        model_name.add(json_model_name.split("\"")[2*i+1]);
                        bike_type.add(json_bike_type.split("\"")[2*i+1]);
                        list_itemArrayList.add(new ListVO("http://13.125.229.179/JPEG_20190512_201100.jpg",json_model_name.split("\"")[2*i+1],json_bike_type.split("\"")[2*i+1]));
                    }
                    final Intent intent = new Intent(MyBikeInfoActivity.this,ReportMainAcitivity.class);

                    //변수 초기화
                    adapter = new ReportViewAdapter(MyBikeInfoActivity.this,list_itemArrayList);
                    listview = (ListView) findViewById(R.id.listview_mybike);

                    //어뎁터 할당
                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            /*Toast.makeText(MyBikeInfoActivity.this,list_itemArrayList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                            intent.putExtra("img_url","http://13.125.229.179/JPEG_20190512_201100.jpg");
                            intent.putExtra("borrower",intent_main.getStringExtra("userId"));
                            intent.putExtra("bikecode",list_itemArrayList.get(position).getTitle());
                            intent.putExtra("renttime",list_itemArrayList.get(position).getContext());
                            intent.putExtra("requestCode",0);//대여기록 = 0, 공유기록=1*/
                            startActivity(intent);
                            //finish();
                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        GetMyBikeRequest getMyBikeRequest = new GetMyBikeRequest(borrower, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyBikeInfoActivity.this);
        queue.add(getMyBikeRequest);
    }
}
