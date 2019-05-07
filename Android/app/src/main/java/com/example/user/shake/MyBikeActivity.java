package com.example.user.shake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyBikeActivity extends AppCompatActivity {

    String userId;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bike);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        mTextView = findViewById(R.id.myBikeTextView);

        PhpConnect task = new PhpConnect();
        ArrayList<String> bikeList;

        try {
            String myBike = "";
            bikeList = task.execute("http://13.125.229.179/getMyBikeInfo.php?userId="+userId).get();
            for (int i = 0; i < bikeList.size(); ++i){
                myBike += bikeList.get(i)+"\n";
            }
            mTextView.setText(myBike);
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
