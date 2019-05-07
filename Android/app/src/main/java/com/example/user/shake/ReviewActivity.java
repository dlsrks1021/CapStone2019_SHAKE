package com.example.user.shake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class ReviewActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText review;
    Button submitButton;

    float rating;
    String userId;
    int rentnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        rentnumber = intent.getIntExtra("rentnumber", -1);

        ratingBar = findViewById(R.id.reviewRatingBar);
        review = findViewById(R.id.reviewEdit);
        submitButton = findViewById(R.id.reviewSubmitButton);

        rating = (float) 3.5;

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating_val, boolean b) {
                rating = rating_val;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rentnumber == -1){
                    Toast.makeText(getApplicationContext(),"rentnumber error!",Toast.LENGTH_SHORT).show();
                }
                if (review.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요!",Toast.LENGTH_SHORT).show();
                }
                else{
                    PhpConnect task = new PhpConnect();
                    try {
                        task.execute("http://13.125.229.179/insertReview.php?rentnumber="+rentnumber+"&contents="+review.getText().toString()+"&imageUrl=").get();
                        Toast.makeText(getApplicationContext(),"리뷰가 등록되었습니다!",Toast.LENGTH_SHORT).show();
                        finish();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }
        });

    }
}
