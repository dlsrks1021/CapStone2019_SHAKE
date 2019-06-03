package com.example.user.shake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
    private Spinner spinner_1, spinner_2;
    private ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryView = findViewById(R.id.categoryView);
        setRecyclerView();

        spinner_1 = findViewById(R.id.category_spinner);
        spinner_2 = findViewById(R.id.category_spinner2);

        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        //do nothing
                        break;
                    case 1:
                        spinner_2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        spinner_2.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        spinner_2.setVisibility(View.INVISIBLE);
                        break;
                        default:
                            break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0://로드
                        break;
                    case 1://전기
                        break;
                    case 2://산악
                        break;
                    case 3://하이브리드
                        break;
                    case 4://미니벨로
                        break;
                    case 5://기타
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });




    }

    private void setRecyclerView(){
        adapter = new CategoryAdapter(getApplicationContext(), mItems);
        categoryView.setAdapter(adapter);
        categoryView.setLayoutManager(new LinearLayoutManager(this));
        setData();
    }

    private void setData(){
        mItems.clear();
        ArrayList<CategoryItem> categoryList = PhpRequest.getCategoryItem(getApplicationContext());
        for (int i = 0; i < categoryList.size(); ++i){
            mItems.add(categoryList.get(i));
        }
    }

}
