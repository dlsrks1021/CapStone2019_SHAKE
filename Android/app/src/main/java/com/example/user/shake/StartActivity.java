package com.example.user.shake;

import android.content.Intent;

import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        Button mapBtn = (Button)findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, MapActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(),1000);
    }
    private class splashhandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(),LoginActivity.class));
            StartActivity.this.finish();
        }
    }
    @Override
    public void onBackPressed(){
        // Prevent pushing back button when spalsh is running.

    }
}
