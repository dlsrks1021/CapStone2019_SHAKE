package com.example.user.shake;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FCMTestActivity extends AppCompatActivity {

    Button test_button;
    sendPost1 send_post=new sendPost1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcmtest);
        test_button=(Button)findViewById(R.id.button7);

    }

    public void testClicked(View v){
        send_post.execute(20);
    }

    public class sendPost1 extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... integers){
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{ \"to\" : \"ePfqy4aXOxQ:APA91bHGKU0oYEkIsPgsvGFcOZ9s_tcwlcOy2mbLmv8hy23ETAKva272do0fixt5tZILiEug8VlOIWeQDT9nEOhNm6K_Vv6UjT1zC98GhxUNdmifBocmlrhOO-OVna-8yy1N7V0CcHfd\",\r\n\"priority\" : \"high\", \r\n\"notification\" : { \r\n\"body\" : \"백그라운드다잉\", \"title\" : \"테스트다잉\" }, \"data\" : { \"title\" : \"테스트다잉\", \"message\" : \"포그라운드다잉\" } }");
            Request request = new Request.Builder()
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(body)
                    .addHeader("authorization", "key=AAAAte8fpPE:APA91bF-RJ5YY5uL6IIHbOu41KizVrkwMsAotezRUn_JfZyt06-0TGr7_kusw2fomtf3PkuqDktkRY9rpwZNKZpOCyzYV8lEsQZk8LQKLtf2hFQvvgH2dugpBHkMt_LITayTJy_OgSdl")
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "e9e95045-6675-5a29-1cb6-fb3a2ffcf7ee")
                    .build();
            try {
                Response response = client.newCall(request).execute();
            }catch (IOException e){
                ;
            }
            return 0;
        }
        @Override
        protected void onProgressUpdate(Integer... params) {

        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }
    }


}
