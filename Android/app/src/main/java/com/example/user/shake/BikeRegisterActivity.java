package com.example.user.shake;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.shake.Request.ValidtimeRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BikeRegisterActivity extends AppCompatActivity {

    EditText location, model, addInfo, cost, lockId;
    Button buttonRegisterMap, buttonUp, buttonDown, buttonRegister;
    Spinner type;
    ImageView day1,day2,day3,day4,day5,day6,day7,day8,day9,day10,day11,day12;
    ImageView night1,night2,night3,night4,night5,night6,night7,night8,night9,night10,night11,night12;
    CheckBox checkBox;
    int[] day=new int[12];
    int[] night=new int[12];
    int allow=0;

    double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_register);

        for(int i=0;i<12;i++){
            day[i]=0;
            night[i]=0;
        }

        checkBox=(CheckBox)findViewById(R.id.checkBox);

        //ImageView
        day1=findViewById(R.id.day1); day2=findViewById(R.id.day2); day3=findViewById(R.id.day3); day4=findViewById(R.id.day4); day5=findViewById(R.id.day5); day6=findViewById(R.id.day6);
        day7=findViewById(R.id.day7); day8=findViewById(R.id.day8); day9=findViewById(R.id.day9); day10=findViewById(R.id.day10); day11=findViewById(R.id.day11); day12=findViewById(R.id.day12);

        night1=findViewById(R.id.night1); night2=findViewById(R.id.night2); night3=findViewById(R.id.night3); night4=findViewById(R.id.night4); night5=findViewById(R.id.night5); night6=findViewById(R.id.night6);
        night7=findViewById(R.id.night7); night8=findViewById(R.id.night8); night9=findViewById(R.id.night9); night10=findViewById(R.id.night10); night11=findViewById(R.id.night11); night12=findViewById(R.id.night12);


        buttonRegisterMap = findViewById(R.id.buttonRegisterMap);
        buttonUp = findViewById(R.id.buttonCostUp);
        buttonDown = findViewById(R.id.buttonCostDown);
        buttonRegister = findViewById(R.id.buttonRegisterBike);

        location = findViewById(R.id.editTextBikeLocation);
        model = findViewById(R.id.editTextBikeModel);
        addInfo = findViewById(R.id.editTextBikeInfo);
        cost = findViewById(R.id.editTextBikeCost);
        lockId = findViewById(R.id.editTextBikeLockId);

        type = findViewById(R.id.spinnerBikeType);

        day1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[0] == 0 ){
                    day1.setImageResource(R.drawable.clicked);
                    day[0]=1;
                }
                else{
                    day1.setImageResource(R.drawable.notclicked);
                    day[0]=0;
                }

            }
        });

        day2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[1] == 0 ){
                    day2.setImageResource(R.drawable.clicked);
                    day[1]=1;
                }
                else{
                    day2.setImageResource(R.drawable.notclicked);
                    day[1]=0;
                }

            }
        });

        day3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[2] == 0 ){
                    day3.setImageResource(R.drawable.clicked);
                    day[2]=1;
                }
                else{
                    day3.setImageResource(R.drawable.notclicked);
                    day[2]=0;
                }

            }
        });

        day4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[3] == 0 ){
                    day4.setImageResource(R.drawable.clicked);
                    day[3]=1;
                }
                else{
                    day4.setImageResource(R.drawable.notclicked);
                    day[3]=0;
                }

            }
        });

        day5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[4] == 0 ){
                    day5.setImageResource(R.drawable.clicked);
                    day[4]=1;
                }
                else{
                    day5.setImageResource(R.drawable.notclicked);
                    day[4]=0;
                }

            }
        });

        day6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[5] == 0 ){
                    day6.setImageResource(R.drawable.clicked);
                    day[5]=1;
                }
                else{
                    day6.setImageResource(R.drawable.notclicked);
                    day[5]=0;
                }

            }
        });

        day7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[6] == 0 ){
                    day7.setImageResource(R.drawable.clicked);
                    day[6]=1;
                }
                else{
                    day7.setImageResource(R.drawable.notclicked);
                    day[6]=0;
                }

            }
        });

        day8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[7] == 0 ){
                    day8.setImageResource(R.drawable.clicked);
                    day[7]=1;
                }
                else{
                    day8.setImageResource(R.drawable.notclicked);
                    day[7]=0;
                }

            }
        });

        day9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[8] == 0 ){
                    day9.setImageResource(R.drawable.clicked);
                    day[8]=1;
                }
                else{
                    day9.setImageResource(R.drawable.notclicked);
                    day[8]=0;
                }

            }
        });

        day10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[9] == 0 ){
                    day10.setImageResource(R.drawable.clicked);
                    day[9]=1;
                }
                else{
                    day10.setImageResource(R.drawable.notclicked);
                    day[9]=0;
                }

            }
        });

        day11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[10] == 0 ){
                    day11.setImageResource(R.drawable.clicked);
                    day[10]=1;
                }
                else{
                    day11.setImageResource(R.drawable.notclicked);
                    day[10]=0;
                }

            }
        });

        day12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( day[11] == 0 ){
                    day12.setImageResource(R.drawable.clicked);
                    day[11]=1;
                }
                else{
                    day12.setImageResource(R.drawable.notclicked);
                    day[11]=0;
                }

            }
        });

        //
        night1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[0] == 0 ){
                    night1.setImageResource(R.drawable.clicked);
                    night[0]=1;
                }
                else{
                    night1.setImageResource(R.drawable.notclicked);
                    night[0]=0;
                }

            }
        });

        night2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[1] == 0 ){
                    night2.setImageResource(R.drawable.clicked);
                    night[1]=1;
                }
                else{
                    night2.setImageResource(R.drawable.notclicked);
                    night[1]=0;
                }

            }
        });

        night3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[2] == 0 ){
                    night3.setImageResource(R.drawable.clicked);
                    night[2]=1;
                }
                else{
                    night3.setImageResource(R.drawable.notclicked);
                    night[2]=0;
                }

            }
        });

        night4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[3] == 0 ){
                    night4.setImageResource(R.drawable.clicked);
                    night[3]=1;
                }
                else{
                    night4.setImageResource(R.drawable.notclicked);
                    night[3]=0;
                }

            }
        });

        night5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[4] == 0 ){
                    night5.setImageResource(R.drawable.clicked);
                    night[4]=1;
                }
                else{
                    night5.setImageResource(R.drawable.notclicked);
                    night[4]=0;
                }

            }
        });

        night6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[5] == 0 ){
                    night6.setImageResource(R.drawable.clicked);
                    night[5]=1;
                }
                else{
                    night6.setImageResource(R.drawable.notclicked);
                    night[5]=0;
                }

            }
        });

        night7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[6] == 0 ){
                    night7.setImageResource(R.drawable.clicked);
                    night[6]=1;
                }
                else{
                    night7.setImageResource(R.drawable.notclicked);
                    night[6]=0;
                }

            }
        });

        night8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[7] == 0 ){
                    night8.setImageResource(R.drawable.clicked);
                    night[7]=1;
                }
                else{
                    night8.setImageResource(R.drawable.notclicked);
                    night[7]=0;
                }

            }
        });

        night9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[8] == 0 ){
                    night9.setImageResource(R.drawable.clicked);
                    night[8]=1;
                }
                else{
                    night9.setImageResource(R.drawable.notclicked);
                    night[8]=0;
                }

            }
        });

        night10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[9] == 0 ){
                    night10.setImageResource(R.drawable.clicked);
                    night[9]=1;
                }
                else{
                    night10.setImageResource(R.drawable.notclicked);
                    night[9]=0;
                }

            }
        });

        night11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[10] == 0 ){
                    night11.setImageResource(R.drawable.clicked);
                    night[10]=1;
                }
                else{
                    night11.setImageResource(R.drawable.notclicked);
                    night[10]=0;
                }

            }
        });

        night12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if ( night[11] == 0 ){
                    night12.setImageResource(R.drawable.clicked);
                    night[11]=1;
                }
                else{
                    night12.setImageResource(R.drawable.notclicked);
                    night[11]=0;
                }

            }
        });

        buttonRegisterMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BikeRegisterActivity.this, RegisterBikeLocationActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cost.setText(Integer.toString(Integer.parseInt(cost.getText().toString()) + 500));
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(cost.getText().toString()) >= 500)
                    cost.setText(Integer.toString(Integer.parseInt(cost.getText().toString()) - 500));
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int exit_flag=0;
                for(int i=0;i<12;i++){
                    if(day[i]==1||night[i]==1){
                        exit_flag=1;
                    }
                }
                ArrayList<Integer> start_time_day,end_time_day; start_time_day=new ArrayList<>(); end_time_day=new ArrayList<>();
                for(int i=0;i<11;i++){
                    if(day[i]==1){
                        start_time_day.add(i);
                        while(day[i+1]==1){
                            i+=1;
                            if(i==11)break;
                        }
                        end_time_day.add(i+1);
                    }
                }
                if(day[10]==0&&day[11]==1){
                    start_time_day.add(11);
                    end_time_day.add(12);
                }
                ArrayList<Integer> start_time_night,end_time_night; start_time_night=new ArrayList<>(); end_time_night=new ArrayList<>();
                for(int i=0;i<11;i++){
                    if(night[i]==1){
                        start_time_night.add(i);
                        while(night[i+1]==1){
                            i+=1;
                            if(i==11)break;
                        }
                        end_time_night.add(i+1);
                    }
                }
                if(night[10]==0&&night[11]==1){
                    start_time_night.add(11);
                    end_time_night.add(12);
                }
                /*for(int i=0;i<start_time_day.size();i++){
                    System.out.println(start_time_day.get(i));
                    System.out.println(end_time_day.get(i));
                }
                for(int i=0;i<start_time_night.size();i++){
                    System.out.println(start_time_night.get(i));
                    System.out.println(end_time_night.get(i));
                }*/

                Intent intent = getIntent();
                String owner = intent.getStringExtra("userId");
                String sCost = cost.getText().toString();
                String imageurl = "";
                String sLockId = lockId.getText().toString();
                String sModel = model.getText().toString();
                int iType = type.getSelectedItemPosition();
                String sType = "";
                String tType="";

                switch (iType){
                    case 0:
                        sType = "error";
                        Toast.makeText(getApplication(),"자전거 종류를 선택해 주세요.",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        sType = "로드자전거";
                        tType="Road";
                        break;
                    case 2:
                        sType = "전기자전거";
                        tType="Electric";
                        break;
                    case 3:
                        sType = "산악자전거";
                        tType="Mountain";
                        break;
                    case 4:
                        sType = "하이브리드자전거";
                        tType="Hybrid";
                        break;
                    case 5:
                        sType = "미니벨로";
                        tType="Mini";
                        break;
                    case 6:
                        sType = "기타자전거";
                        tType="Other";
                        break;
                    default:
                        sType = "error";
                }
                String sAddInfo = addInfo.getText().toString();
                String bikecode = owner+Double.toString(latitude)+tType;
                imageurl="http://13.125.229.179//"+bikecode+".jpg";

                ArrayList<String> queryResult;
                ArrayList<String> queryResult_smartlock;

                if(exit_flag==1 && !sType.equals("error")) {
                    PhpConnect task = new PhpConnect();
                    PhpConnect task2 = new PhpConnect();
                    try {
                        if(checkBox.isChecked()){
                            allow=0;
                        }
                        else{
                            allow=1;
                        }// allow -> 1: 즉시 예약 X / 0: 즉시 예약 O
                        //Bike Table에 저장
                        task.execute("http://13.125.229.179/insertBikeInfo.php?owner=" + owner + "&bikecode=" + bikecode + "&latitude=" + Double.toString(latitude) + "&longitude=" + Double.toString(longitude)
                                + "&cost=" + sCost + "&url=" + imageurl + "&lockId=" + sLockId + "&model=" + sModel + "&type=" + sType + "&addInfo=" + sAddInfo+"&allow="+allow).get();
                        //Smart Lock Table에 저장
                        task2.execute("http://13.125.229.179/insertSmartlockInfo.php?userId=" + owner + "&bikecode=" + bikecode + "&lockId=" + sLockId).get();
                        Intent intent2 = new Intent();
                        BikeInfo newBike = new BikeInfo(owner, bikecode, latitude, longitude, Integer.parseInt(sCost), imageurl, sLockId, sModel, sType, sAddInfo);
                        intent2.putExtra("newBike", newBike);
                        setResult(RESULT_OK, intent2);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    //double latitude = jsonResponse.getDouble("latitude");
                                    //double longitude = jsonResponse.getDouble("longitude");
                                    if(success){
                                        //Toast.makeText(getApplication(),"TEST ok!",Toast.LENGTH_SHORT).show();
                                    }
                                    else{

                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        //System.out.println("TEST LOG : "+bikecode+"  "+start_time_day.get(0)+"   "+end_time_day.get(0));
                        /*ValidtimeRequest validtimeRequest = new ValidtimeRequest(bikecode,1,5,"day",responseListener);
                        RequestQueue queue = Volley.newRequestQueue(BikeRegisterActivity.this);
                        queue.add(validtimeRequest);*/
                        ValidtimeRequest[] validtimeRequest=new ValidtimeRequest[start_time_day.size()+start_time_night.size()];
                        //validtimeRequest = new ValidtimeRequest(bikecode,1,1,"day", responseListener);
                        RequestQueue queue = Volley.newRequestQueue(BikeRegisterActivity.this);
                        int i=0;
                        for(i=0;i<start_time_day.size();i++){
                            validtimeRequest[i] = new ValidtimeRequest(bikecode,start_time_day.get(i),end_time_day.get(i),"day", responseListener);
                            queue.add(validtimeRequest[i]);
                        }
                        for(i=0;i<start_time_night.size();i++){
                            validtimeRequest[i] = new ValidtimeRequest(bikecode,start_time_night.get(i),end_time_night.get(i),"night", responseListener);
                            queue.add(validtimeRequest[i]);
                        }
                        Intent intent_camera=new Intent(BikeRegisterActivity.this, CameraActivity.class);
                        intent_camera.putExtra("requestCode",0);
                        intent_camera.putExtra("bikecode",bikecode);
                        startActivityForResult(intent_camera,10);
                        finish();
                    } catch (ExecutionException e) {

                    } catch (InterruptedException e) {

                    }
                }
                else {
                    Toast.makeText(getApplication(),"최소 하나 이상의 대여 시간을 설정해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                latitude = data.getDoubleExtra("latitude", 0);
                longitude = data.getDoubleExtra("longitude", 0);
                Geocoder mGeocoder = new Geocoder(getApplicationContext());
                try{
                    List<Address> mResultList = mGeocoder.getFromLocation(latitude, longitude, 1);
                    location.setText(mResultList.get(0).getAddressLine(0));
                }
                catch (IOException e){
                    location.setText("주소정보가 없습니다.");
                }
            }
        }
    }
}
