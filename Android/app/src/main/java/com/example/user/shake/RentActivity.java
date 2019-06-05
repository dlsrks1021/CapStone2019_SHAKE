package com.example.user.shake;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.user.shake.Request.CheckRequest;
import com.example.user.shake.Request.GetMyPointRequest;
import com.example.user.shake.Request.GetRentBikeInfo;
import com.example.user.shake.Request.GetRenttimeRequest;
import com.example.user.shake.Request.GetTokenRequest;
import com.example.user.shake.Request.GetValidTimeRequest;
import com.example.user.shake.Request.RentRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RentActivity extends AppCompatActivity {

    TextView type,model,add_info,valid_time,select_time,select_cost,text_insurance;
    ImageView image;
    Button reviewButton;
    int select_renttime=0;
    int insurance=0; int allow=-1;
    int cost,current_point,price_insurance=0;
    TimePicker tp;
    static String day;
    String json_day_start,json_day_end,json_night_start,json_night_end,bikecode;
    ArrayList<Integer> validtime;
    ArrayList<String> gettime;
    sendPost send_post=new sendPost();
    String token;
    String fcm_title,fcm_body,borrower;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        String[] info = new String[2];
        info=((Main2Activity)Main2Activity.mContext).getInfo();
        validtime=new ArrayList<>();
        gettime=new ArrayList<>();
        tp=(TimePicker)findViewById(R.id.tp);
        Calendar tp_time=Calendar.getInstance();
        final Intent intent = getIntent();
        token="";

        bikecode=intent.getStringExtra("bikecode");
        final Button rentbtn = (Button) findViewById(R.id.rent_button);
        checkBox = (CheckBox)findViewById(R.id.checkBow_insurance);
        text_insurance=(TextView)findViewById(R.id.explain8);
        reviewButton = findViewById(R.id.rent_goToReview_button);
        select_time=(TextView)findViewById(R.id.explain10);
        select_cost=(TextView)findViewById(R.id.explain_bike_model2);
        //final Button return_btn = (Button) findViewById(R.id.return_button);
        final TextView explain = (TextView) findViewById(R.id.explain);
        borrower = info[0];
        type=findViewById(R.id.explain_bike_type);
        model=findViewById(R.id.explain_bike_model);
        add_info=findViewById(R.id.explain_bike_explain);
        valid_time=findViewById(R.id.explain_bike_valid_time);
        image=findViewById(R.id.imageView_rent);

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(RentActivity.this, ReviewListActivity.class);
                String userId = intent.getStringExtra("borrower");
                intent2.putExtra("userId", userId);
                intent2.putExtra("bikecode", bikecode);
                intent2.putExtra("fromRentFlag", 1);
                startActivity(intent2);
            }
        });

        //Test
        if(bikecode.equals("not implemented yet")) Glide.with(this).load("http://13.125.229.179/bike.png").into(image);
        else Glide.with(this).load("http://13.125.229.179//"+bikecode+".jpg").into(image);

        Response.Listener<String> responseListener8 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    token=jsonResponse.getString("token");
                    System.out.println("GET TOKEN = "+token);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        GetTokenRequest getTokenRequest = new GetTokenRequest(bikecode,responseListener8);
        RequestQueue queue8 = Volley.newRequestQueue(RentActivity.this);
        queue8.add(getTokenRequest);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        explain.setText("대여 가능");
                    }
                    else{
                        explain.setText("대여 중인 \n자전거가 있습니다");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        CheckRequest checkRequest = new CheckRequest(borrower, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RentActivity.this);
        queue.add(checkRequest);

        Response.Listener<String> responseListener5 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    current_point=jsonResponse.getInt("point");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        GetMyPointRequest getMyPointRequest = new GetMyPointRequest(borrower, responseListener5);
        RequestQueue queue5 = Volley.newRequestQueue(RentActivity.this);
        queue5.add(getMyPointRequest);

        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String biketype=jsonResponse.getString("bike_type");
                    String modelname=jsonResponse.getString("model_name");
                    String addInfo=jsonResponse.getString("addInfo");
                    allow=jsonResponse.getInt("allow");
                    if(allow==1) text_insurance.setText("X");
                    else if(allow==0)text_insurance.setText("O");
                    //System.out.println("ALLOW TEST = "+allow);
                    cost=jsonResponse.getInt("cost");
                    type.setText(biketype);
                    model.setText(modelname);
                    add_info.setText(addInfo);
                    if(success){

                    }
                    else{
                        //explain.setText("대여 중인 \n자전거가 있습니다");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        GetRentBikeInfo getRentBikeInfo = new GetRentBikeInfo(bikecode, responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(RentActivity.this);
        queue1.add(getRentBikeInfo);

        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    json_day_start=jsonResponse.getString("day_start");
                    json_day_end=jsonResponse.getString("day_end");
                    json_night_start=jsonResponse.getString("night_start");
                    json_night_end=jsonResponse.getString("night_end");
                    int len_day = json_day_start.split(",").length;
                    if(json_day_start.equals("[]")) len_day=0;
                    int len_night = json_night_start.split(",").length;
                    if(json_night_start.equals("[]")) len_night=0;
                    String string_day = "<오전>\n"; String string_night = "<오후>\n";
                    for(int i=0;i<len_day;i++){
                        string_day+=json_day_start.split("\"")[2*i+1]+" ~ "+json_day_end.split("\"")[2*i+1]+"시\n";
                        validtime.add(Integer.parseInt(json_day_start.split("\"")[2*i+1]));
                        validtime.add(Integer.parseInt(json_day_end.split("\"")[2*i+1]));
                    }
                    for(int i=0;i<len_night;i++){
                        string_night+=json_night_start.split("\"")[2*i+1]+" ~ "+json_night_end.split("\"")[2*i+1]+"시\n";
                        validtime.add(Integer.parseInt(json_night_start.split("\"")[2*i+1])+12);
                        validtime.add(Integer.parseInt(json_night_end.split("\"")[2*i+1])+12);
                    }

                    if(success){
                        valid_time.setText(string_day+string_night);
                    }
                    else{
                        //explain.setText("대여 중인 \n자전거가 있습니다");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        GetValidTimeRequest getValidTimeRequest = new GetValidTimeRequest(bikecode, responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(RentActivity.this);
        queue2.add(getValidTimeRequest);

        Response.Listener<String> responseListener3 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    String json_renttime=jsonResponse.getString("renttime");
                    String json_returntime=jsonResponse.getString("returntime");
                    int len = json_renttime.split(",").length;
                    if(json_renttime.equals("[]")) len=0;
                    String string_day = "<오전>\n"; String string_night = "<오후>\n";
                    for(int i=0;i<len;i++){
                        gettime.add(json_renttime.split("\"")[2*i+1]);
                        gettime.add(json_returntime.split("\"")[2*i+1]);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        GetRenttimeRequest getRenttimeRequest = new GetRenttimeRequest(bikecode, responseListener3);
        RequestQueue queue3 = Volley.newRequestQueue(RentActivity.this);
        queue2.add(getRenttimeRequest);

        checkBox.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    price_insurance=3000;
                    select_cost.setText(select_renttime*cost+price_insurance+" 원");
                    insurance=1;
                }
                else{
                    price_insurance=0;
                    select_cost.setText(select_renttime*cost+price_insurance+" 원");
                    insurance=0;
                }
            }
        });




        rentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                explain.setText("대여 중인 \n자전거가 있습니다");
                                /*Intent intent = new Intent(RentActivity.this, InfoActivity.class);
                                String rentnumber = jsonResponse.getString("rentnumber");
                                intent.putExtra("rentnumber",rentnumber);
                                finish();*/
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RentActivity.this);
                                builder.setMessage("대여에 실패하였습니다")
                                        .setNegativeButton("다시 시도",null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                if(!explain.getText().equals("대여 중인 \n자전거가 있습니다")) {
                    int hour,min;
                    int execute_flag=0;
                    if(Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.M){
                        hour=tp.getHour();
                        min=tp.getMinute();
                    }else{
                        hour=tp.getCurrentHour();
                        min=tp.getCurrentMinute();
                    }
                    long time = System.currentTimeMillis();
                    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    day = dayTime.format(new Date(time));
                    String save_day=day;
                    day=day.split(" ")[0]+" "+hour+":"+min+":00";
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR,Integer.parseInt(day.split(" ")[0].split("-")[0]));
                    cal.set(Calendar.MONTH,Integer.parseInt(day.split(" ")[0].split("-")[1])-1);
                    cal.set(Calendar.DATE,Integer.parseInt(day.split(" ")[0].split("-")[2]));
                    cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(day.split(" ")[1].split(":")[0]));
                    cal.set(Calendar.MINUTE,Integer.parseInt(day.split(" ")[1].split(":")[1]));
                    cal.set(Calendar.SECOND,Integer.parseInt(day.split(" ")[1].split(":")[2]));

                    cal.add(Calendar.HOUR_OF_DAY, select_renttime);//return 할때 몇시간 빌리는지 넣어주면 됨
                    String return_time = dayTime.format(cal.getTime()); //System.out.println(day+"      "+ return_time);
                    if(hour>Integer.parseInt(save_day.split(" ")[1].split(":")[0])||(hour==Integer.parseInt(save_day.split(" ")[1].split(":")[0])&&min>=Integer.parseInt(save_day.split(" ")[1].split(":")[1]))){
                        execute_flag=1; //현재 시간보다 뒤인지 확인
                    }
                    for(int i=0;i<validtime.size()/2;i++){
                        if(Integer.parseInt(day.split(" ")[1].split(":")[0])>=validtime.get(2*i)-1&&
                                ((Integer.parseInt(return_time.split(" ")[1].split(":")[0])==validtime.get(2*i+1)&&Integer.parseInt(return_time.split(" ")[1].split(":")[1])==0)||Integer.parseInt(return_time.split(" ")[1].split(":")[0])<validtime.get(2*i+1)||
                                        (Integer.parseInt(day.split(" ")[0].split("-")[2])<Integer.parseInt(return_time.split(" ")[0].split("-")[2])&&Integer.parseInt(return_time.split(" ")[1].split("-")[0])<validtime.get(1)&&validtime.get(0)==0)
                        ||(Integer.parseInt(day.split(" ")[0].split("-")[2])<Integer.parseInt(return_time.split(" ")[0].split("-")[2])&&Integer.parseInt(return_time.split(" ")[1].split("-")[0])==validtime.get(1)&&validtime.get(0)==0&&Integer.parseInt(return_time.split(" ")[1].split(":")[1])==0)))
                        {
                            execute_flag=1;
                            break;
                        }
                        else{
                            execute_flag=0;
                        }
                    }
                    if(validtime.size()==0)execute_flag=0;

                    if(execute_flag==1) {
                        if(allow==0) {
                            if (select_renttime != 0) {
                                if (current_point >= select_renttime * cost) {
                                    RentRequest rentRequest = new RentRequest(intent.getStringExtra("bikecode"), intent.getStringExtra("borrower"), day, return_time, allow, insurance, current_point - select_renttime * cost, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(RentActivity.this);
                                    queue.add(rentRequest);
                                    //send_post.execute(20);
                                    Toast.makeText(getApplication(), "정상적으로 대여되었습니다", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplication(), "포인트가 부족합니다", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplication(), "1시간 이상 대여 가능합니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(allow==1){
                            if (select_renttime != 0) {
                                if (current_point >= select_renttime * cost) {
                                    RentRequest rentRequest = new RentRequest(intent.getStringExtra("bikecode"), intent.getStringExtra("borrower"), day, return_time, allow, insurance, current_point - select_renttime * cost, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(RentActivity.this);
                                    queue.add(rentRequest);
                                    send_post.execute(20);
                                    Toast.makeText(getApplication(), "정상적으로 대여 요청 되었습니다", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplication(), "포인트가 부족합니다", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplication(), "1시간 이상 대여 가능합니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else{
                        //System.out.println(return_time);
                        Toast.makeText(getApplication(),"대여 가능시간을 확인 해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplication(),"대여 중인 자전거가 있습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class sendPost extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... integers){
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            fcm_title="대여 요청";
            fcm_body=borrower+" 님의 자전거 대여 요청이 있습니다.";
            RequestBody body = RequestBody.create(mediaType, "{ \"to\" : \""+token+"\",\r\n\"priority\" : \"high\", \r\n\"notification\" : { \r\n\"body\" : \""+fcm_body+"\", \"title\" : \""+fcm_title+"\" }, \"data\" : { \"title\" : \""+fcm_title+"\", \"message\" : \""+fcm_body+"\" } }");
            Request request = new Request.Builder()
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(body)
                    .addHeader("authorization", "key=AAAAte8fpPE:APA91bF-RJ5YY5uL6IIHbOu41KizVrkwMsAotezRUn_JfZyt06-0TGr7_kusw2fomtf3PkuqDktkRY9rpwZNKZpOCyzYV8lEsQZk8LQKLtf2hFQvvgH2dugpBHkMt_LITayTJy_OgSdl")
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "e9e95045-6675-5a29-1cb6-fb3a2ffcf7ee")
                    .build();
            try {
                okhttp3.Response response = client.newCall(request).execute();
            }catch (IOException e){
                ;
            }
            return 0;
        }
        @Override
        protected void onProgressUpdate(Integer... params) {

        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }
    }

    public void plusClicked_rent(View v){
        if(select_renttime<20){
            select_renttime+=1;
            select_time.setText(String.valueOf(select_renttime));
            select_cost.setText(select_renttime*cost+" 원");
        }
        else{
            Toast.makeText(getApplication(),"최대 대여시간은 20시간입니다",Toast.LENGTH_SHORT).show();
        }
    }
    public void minusClicked_rent(View v){
        if(select_renttime>0){
            select_renttime-=1;
            select_time.setText(String.valueOf(select_renttime));
            select_cost.setText(select_renttime*cost+" 원");
        }
        else{
            ;
        }
    }
    public void validClicked(View v){
        show();
    }
    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("대여 가능 시간");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_validtime, null);
        builder.setView(view);
        TextView validtime_dialog=(TextView)view.findViewById(R.id.textView26);
        TextView getTime = (TextView)view.findViewById(R.id.textView28);
        int len_day = json_day_start.split(",").length;
        if(json_day_start.equals("[]")) len_day=0;
        int len_night = json_night_start.split(",").length;
        if(json_night_start.equals("[]")) len_night=0;
        String string_day = "<오전>\n"; String string_night = "<오후>\n";
        for(int i=0;i<len_day;i++){
            string_day+=json_day_start.split("\"")[2*i+1]+" ~ "+json_day_end.split("\"")[2*i+1]+"시\n";
        }
        for(int i=0;i<len_night;i++){
            string_night+=json_night_start.split("\"")[2*i+1]+" ~ "+json_night_end.split("\"")[2*i+1]+"시\n";
        }
        String time="";
        for(int i=0;i<gettime.size()/2;i++){
            time+=gettime.get(2*i)+" ~ "+gettime.get(2*i+1)+"\n";
        }
        validtime_dialog.setText(string_day+string_night);
        getTime.setText(time);
        final AlertDialog dialog = builder.create();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width=370;
        params.height=420;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }
    public void reportCheckClicked(View v){
        Intent intent = new Intent(RentActivity.this,CheckReportActivity.class);
        intent.putExtra("bikecode",bikecode);
        startActivity(intent);
    }
}
