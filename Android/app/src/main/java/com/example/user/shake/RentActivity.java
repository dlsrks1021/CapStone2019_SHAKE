package com.example.user.shake;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Map;

public class RentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        String[] info = new String[2];
        info=((MainActivity)MainActivity.mContext).getInfo();

        final Button rentbtn = (Button) findViewById(R.id.rent_button);
        //final TextView registerbtn = (TextView) findViewById(R.id.explain);
        final String bikecode = "7777";
        final String borrower = info[0];

        rentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                System.out.println(jsonResponse);
                                //String userID = jsonResponse.getString("userID");
                                //String userPassword = jsonResponse.getString("userPassword");
                                Intent intent = new Intent(RentActivity.this, RentActivity.class);
                                //intent.putExtra("userID",userID);
                                //intent.putExtra("userPassword",userPassword);
                                RentActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RentActivity.this);
                                builder.setMessage("대여에 실패하였습니다")
                                        .setNegativeButton("다시 시도",null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                RentRequest rentRequest = new RentRequest(bikecode, borrower,"2019-04-03 12:00:01","2019-04-03 15:00:01",0,0, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RentActivity.this);
                queue.add(rentRequest);
            }
        });
    }
}
