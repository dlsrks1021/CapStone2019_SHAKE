package com.example.user.shake;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.user.shake.Request.ReportRequest;

import org.json.JSONObject;

public class ReportMainAcitivity extends AppCompatActivity {

    Intent intent;
    String bikecode,img_url,rent_time,borrower,report_content;

    EditText contents;
    TextView textView_bikecode,textView_renttime;
    ImageView image;
    Button report_button;
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_main_acitivity);
        intent = getIntent();
        bikecode=intent.getStringExtra("bikecode");
        borrower=intent.getStringExtra("borrower");
        img_url=intent.getStringExtra("img_url");
        rent_time=intent.getStringExtra("renttime");
        requestCode=intent.getIntExtra("requestCode",5);//Errorcode=5
        System.out.println(bikecode+"   "+borrower+"   "+img_url+"   "+rent_time+"   "+requestCode);
        textView_bikecode=(TextView)findViewById(R.id.textView_report_bikecode_content);
        textView_renttime=(TextView)findViewById(R.id.textView_report_renttime_content);
        image=(ImageView)findViewById(R.id.imageView_report);
        contents=(EditText)findViewById(R.id.edit_report);

        textView_renttime.setText(rent_time);
        textView_bikecode.setText(bikecode);
        Glide.with(this).load(img_url).into(image);
        report_button=(Button)findViewById(R.id.report_button);

        report_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_content=contents.getText().toString();
                if(requestCode==5){
                    Toast.makeText(getApplication(),"잘못된 요청입니다. 다시 시도하세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReportMainAcitivity.this);
                    builder.setMessage("신고 후에는 취소할 수 없습니다.\n신고하시겠습니까?")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try{
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");
                                            }
                                            catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    String url=rent_time.replace(" ","_");
                                    url=url.replaceAll("-","_");
                                    url=url.replaceAll(":","_");
                                    img_url="report_"+url+".jpg";
                                    //System.out.println(img_url+"IMGURL");
                                    Intent intent_test = new Intent(ReportMainAcitivity.this,CameraActivity.class);
                                    intent_test.putExtra("imgurl",img_url);
                                    intent_test.putExtra("requestCode",2);
                                    startActivityForResult(intent_test,10);
                                    ReportRequest reportRequest = new ReportRequest(borrower, bikecode, rent_time, "http://13.125.229.179/"+img_url, report_content, requestCode, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(ReportMainAcitivity.this);
                                    queue.add(reportRequest);
                                    finish();
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ;
                                }
                            })
                            .create()
                            .show();
                }
            }
        });

        //System.out.println(bikecode+"       "+img_url+"       "+rent_time);
    }
}
