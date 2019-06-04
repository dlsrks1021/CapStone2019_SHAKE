package com.example.user.shake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ReviewListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private TextView noReviewText;
    private Button backButton;
    private ArrayList<RecyclerItem> mItems = new ArrayList<>();
    private ArrayList<RecyclerItem> copyMItems = new ArrayList<>();
    CheckBox checkMyBike;
    String userId = "", bikecode="";
    int fromRentFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        recyclerView = findViewById(R.id.recyclerView);
        checkMyBike = findViewById(R.id.review_list_checkBox);
        backButton = findViewById(R.id.review_list_back_button);
        noReviewText = findViewById(R.id.review_list_no_review_text);
        setRecyclerView();

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        fromRentFlag = intent.getIntExtra("fromRentFlag", 0);
        bikecode = intent. getStringExtra("bikecode");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (fromRentFlag == 1){
            checkMyBike.setVisibility(View.INVISIBLE);
            copyMItems.clear();

            for (int i = 0; i < mItems.size(); ++i){

                if (mItems.get(i).getBikecode().equals(bikecode)){
                    copyMItems.add(mItems.get(i));
                }

            }
            if (copyMItems.size() == 0){
                noReviewText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }else{
                adapter = new RecyclerAdapter(getApplicationContext(), copyMItems);
                recyclerView.setAdapter(adapter);
            }
        }

        checkMyBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkMyBike.isChecked()){
                    copyMItems.clear();

                    for (int i = 0; i < mItems.size(); ++i){

                        if (mItems.get(i).getOwner().equals(userId)){
                            copyMItems.add(mItems.get(i));
                        }

                    }
                    adapter = new RecyclerAdapter(getApplicationContext(), copyMItems);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    adapter = new RecyclerAdapter(getApplicationContext(), mItems);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

    }

    private void setRecyclerView(){
        adapter = new RecyclerAdapter(getApplicationContext(), mItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setData();
    }


    private void setData(){
        mItems.clear();
        ArrayList<String> reviewList, rentList, bikeList;

        PhpConnect task = new PhpConnect();
        PhpConnect task2 = new PhpConnect();
        PhpConnect task3 = new PhpConnect();

        try {
            String reviewContent="", reviewRating="", rentTime="";
            String bikeOwner="", bikeModel="", reviewOwner="", reviewImage = "", code = "";
            reviewList = task.execute("http://13.125.229.179/getReviewList.php").get();
            rentList = task2.execute("http://13.125.229.179/getRentList.php").get();
            bikeList = task3.execute("http://13.125.229.179/getBikeList.php").get();

            for (int i = 0; i < reviewList.size(); i += 3){
                RecyclerItem recyclerItem = new RecyclerItem();
                reviewContent = reviewList.get(i);
                reviewRating = reviewList.get(i + 1);
                reviewImage = reviewList.get(i + 2);
                recyclerItem.setContent(reviewContent);
                recyclerItem.setRating(Float.parseFloat(reviewRating));
                recyclerItem.setImageUrl(reviewImage);
                mItems.add(recyclerItem);
            }
            for (int i = 0; i < rentList.size(); i += 2){
                rentTime = rentList.get(i);
                reviewOwner = rentList.get(i + 1);
                mItems.get(i / 2).setDate(rentTime);
                mItems.get(i / 2).setName(reviewOwner);

            }
            for (int i = 0; i < bikeList.size(); i += 3){
                bikeOwner = bikeList.get(i);
                bikeModel = bikeList.get(i + 1);
                code = bikeList.get(i + 2);
                mItems.get(i / 3).setOwner(bikeOwner);
                mItems.get(i / 3).setBike(bikeModel);
                mItems.get(i / 3).setBikecode(code);
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
