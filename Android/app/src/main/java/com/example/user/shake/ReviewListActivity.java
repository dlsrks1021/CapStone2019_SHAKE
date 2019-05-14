package com.example.user.shake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

        PhpConnect task = new PhpConnect();
        try {
            task.execute("http://13.125.229.179/insertReview.php?havetoimplement").get();

        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
