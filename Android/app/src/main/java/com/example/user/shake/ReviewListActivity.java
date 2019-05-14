package com.example.user.shake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {

    private String[] names = {"Charlie","Andrew","Han","Liz","Thomas","Sky","Andy","Lee","Park"};
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<RecyclerItem> mItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        recyclerView = findViewById(R.id.recyclerView);
        setRecyclerView();

    }

    private void setRecyclerView(){
        adapter = new RecyclerAdapter(mItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setData();
    }

    private void setData(){
        mItems.clear();
        for(String name : names){
            mItems.add(new RecyclerItem(name));
        }
    }

}
