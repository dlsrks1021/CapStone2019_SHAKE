package com.example.user.shake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.user.shake.Request.PhpRequest;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView categoryView;
    private ArrayList<CategoryItem> mItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryView = findViewById(R.id.categoryView);
        setRecyclerView();
    }

    private void setRecyclerView(){
        adapter = new CategoryAdapter(getApplicationContext(), mItems);
        categoryView.setAdapter(adapter);
        categoryView.setLayoutManager(new LinearLayoutManager(this));
        setData();
    }

    private void setData(){
        mItems.clear();
        mItems = PhpRequest.getCategoryItem(getApplicationContext());
    }

}
