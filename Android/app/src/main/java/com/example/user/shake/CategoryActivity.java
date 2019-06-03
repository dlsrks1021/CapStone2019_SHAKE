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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView categoryView;
    private ArrayList<CategoryItem> mItems = new ArrayList<>();
    private ArrayList<CategoryItem> copyMItems = new ArrayList<>();
    private Spinner spinner_1, spinner_2;

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
                        copyMItems.clear();
                        for (int i = 0; i < mItems.size(); ++i){copyMItems.add(mItems.get(i));}
                        break;
                    case 1://가격
                        Collections.sort(copyMItems, new Comparator<CategoryItem>() {
                            @Override
                            public int compare(CategoryItem c1, CategoryItem c2) {
                                return Integer.compare(c1.getPrice(), c2.getPrice());
                            }
                        });
                        adapter = new CategoryAdapter(getApplicationContext(), copyMItems);
                        categoryView.setAdapter(adapter);
                        break;
                    case 2://거리
                        Collections.sort(copyMItems, new Comparator<CategoryItem>() {
                            @Override
                            public int compare(CategoryItem c1, CategoryItem c2) {
                                return Double.compare(c1.getDistance(), c2.getDistance());
                            }
                        });
                        adapter = new CategoryAdapter(getApplicationContext(), copyMItems);
                        categoryView.setAdapter(adapter);
                        break;
                        default:
                            break;
                }
                switch (spinner_2.getSelectedItemPosition()){
                    case 0://all
                        bikeSorting("all");
                        break;
                    case 1://로드
                        bikeSorting("로드자전거");
                        break;
                    case 2://전기
                        bikeSorting("전기자전거");
                        break;
                    case 3://산악
                        bikeSorting("산악자전거");
                        break;
                    case 4://하이브리드
                        bikeSorting("하이브리드자전거");
                        break;
                    case 5://미니벨로
                        bikeSorting("미니벨로");
                        break;
                    case 6://기타
                        bikeSorting("기타자전거");
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
                    case 0://all
                        bikeSorting("all");
                        break;
                    case 1://로드
                        bikeSorting("로드자전거");
                        break;
                    case 2://전기
                        bikeSorting("전기자전거");
                        break;
                    case 3://산악
                        bikeSorting("산악자전거");
                        break;
                    case 4://하이브리드
                        bikeSorting("하이브리드자전거");
                        break;
                    case 5://미니벨로
                        bikeSorting("미니벨로");
                        break;
                    case 6://기타
                        bikeSorting("기타자전거");
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

    public void bikeSorting(String bikeType){
        ArrayList<CategoryItem> bikeTypeSortingList = new ArrayList<>();
        for (int i = 0; i < copyMItems.size(); ++i){
            if(copyMItems.get(i).getType().equals(bikeType)){
                bikeTypeSortingList.add(copyMItems.get(i));
            }
        }
        if (bikeType.equals("all"))
            adapter = new CategoryAdapter(getApplicationContext(), copyMItems);
        else
            adapter = new CategoryAdapter(getApplicationContext(), bikeTypeSortingList);
        categoryView.setAdapter(adapter);
    }

}
