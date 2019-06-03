package com.example.user.shake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
    }

    public void adminChargeClicked(View v){
        Intent intent=new Intent(AdminMainActivity.this,AdminChargeActivity.class);
        startActivity(intent);
    }

    public void adminReportClicked(View v){
        Intent intent=new Intent(AdminMainActivity.this,AdminReportActivity.class);
        startActivity(intent);
    }
}
