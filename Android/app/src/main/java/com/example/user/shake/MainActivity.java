package com.example.user.shake;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.Button;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ListView listView = null;
    private String userName,userID;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Save User Information
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        Toast.makeText(getApplicationContext(),userName,Toast.LENGTH_SHORT).show();
        mContext=this;

        // Navigation Bar implementation
        Toast.makeText(getApplicationContext(),"화면을 스와이프하시면 메뉴가 보입니다.",Toast.LENGTH_SHORT).show();

        final String[] items = {userID+"님","Rent", "항목2", "항목3", "항목4"} ;
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items) ;

        listView = (ListView) findViewById(R.id.drawer_menulist) ;
        listView.setAdapter(adapter) ;

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                switch (position) {
                    case 0 : //List1
                        Toast.makeText(getApplicationContext(),"Welcome!",Toast.LENGTH_SHORT).show();
                        break ;
                    case 1 : //List2
                        Intent intent = new Intent(MainActivity.this, RentActivity.class);
                        MainActivity.this.startActivity(intent);
                        break ;
                    case 2 : //List3
                        Toast.makeText(getApplicationContext(),"List3 Clicked",Toast.LENGTH_SHORT).show();
                        break ;
                    case 3 : //List4
                        Toast.makeText(getApplicationContext(),"List4 Clicked",Toast.LENGTH_SHORT).show();
                        break ;
                    case 4 : //List5
                        Toast.makeText(getApplicationContext(),"List5 Clicked",Toast.LENGTH_SHORT).show();
                        break ;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer) ;
                drawer.closeDrawer(Gravity.LEFT) ;
            }
        });
    }

    public String[] getInfo(){
        String[] temp = new String[2];
        temp[0]=userID;
        //temp[1]=userName;
        return temp;
    }
}
