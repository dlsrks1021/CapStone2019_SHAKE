package com.example.user.shake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReportMainAcitivity extends AppCompatActivity {

    Intent intent;
    String bikecode,img_url,rent_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_main_acitivity);
        intent = getIntent();
        bikecode=intent.getStringExtra("bikecode");
        img_url=intent.getStringExtra("img_url");
        rent_time=intent.getStringExtra("renttime");
        //System.out.println(bikecode+"       "+img_url+"       "+rent_time);
    }
}
