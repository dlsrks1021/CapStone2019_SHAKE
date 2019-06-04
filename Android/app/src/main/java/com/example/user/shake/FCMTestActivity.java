package com.example.user.shake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FCMTestActivity extends AppCompatActivity {

    Button test_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcmtest);
        test_button=(Button)findViewById(R.id.button7);
    }

    public void testClicked(View v){
        //MyFirebaseMessagingService.se
    }

}
