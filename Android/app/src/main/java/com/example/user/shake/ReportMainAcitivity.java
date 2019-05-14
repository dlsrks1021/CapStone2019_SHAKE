package com.example.user.shake;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.user.shake.Request.RentRequest;
import com.example.user.shake.Request.ReportRequest;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class ReportMainAcitivity extends AppCompatActivity {

    Intent intent;
    String bikecode,img_url,rent_time,borrower,report_content;

    EditText contents;
    TextView textView_bikecode,textView_renttime;
    ImageView image;
    Button report_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_main_acitivity);
        intent = getIntent();
        bikecode=intent.getStringExtra("bikecode");
        borrower=intent.getStringExtra("userId");
        img_url=intent.getStringExtra("img_url");
        rent_time=intent.getStringExtra("renttime");
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
                report_content=contents.getText().toString();
                ReportRequest reportRequest = new ReportRequest(borrower,bikecode,rent_time,img_url, report_content,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReportMainAcitivity.this);
                queue.add(reportRequest);
            }
        });

        //System.out.println(bikecode+"       "+img_url+"       "+rent_time);
    }
}
