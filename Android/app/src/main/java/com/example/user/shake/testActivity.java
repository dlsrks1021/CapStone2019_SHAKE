package com.example.user.shake;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class testActivity extends AppCompatActivity implements View.OnClickListener{

    private Button UploadBn, ChooseBn;
    private EditText NAME;
    private ImageView imgView;
    private final int IMG_REQUEST=1;
    private Bitmap bitmap;
    private String UploadUrl = "http://13.125.229.179/updateinfo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        UploadBn = (Button)findViewById(R.id.test_upload);
        ChooseBn=(Button)findViewById(R.id.test_choose);
        NAME=(EditText)findViewById(R.id.name);
        imgView = (ImageView)findViewById(R.id.test_image);
        ChooseBn.setOnClickListener(this);
        UploadBn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.test_choose:
                selectImage();
                break;
            case R.id.test_upload:
                uploadImage();
                break;
        }
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST&&resultCode==RESULT_OK&&data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImage(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UploadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Toast.makeText(testActivity.this,Response,Toast.LENGTH_SHORT).show();
                    //imgView.setImageResource(0);
                    //imgView.setVisibility(View.GONE);
                    //NAME.setText("");
                    //NAME.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",NAME.getText().toString().trim());
                params.put("image",imageToString(bitmap));

                return params;
            }
        };
        MySingleton.getInstance(testActivity.this).addToRequestQue(stringRequest);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
}
