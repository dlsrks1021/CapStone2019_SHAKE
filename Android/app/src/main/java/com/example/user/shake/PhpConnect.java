package com.example.user.shake;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PhpConnect extends AsyncTask<String, String, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(String... params) {
        ArrayList<String> output = new ArrayList<String>();
        try {
            //연결 url 설정
            URL url = new URL(params[0]);

            //커넥션 객체 생성
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //연결되었으면
            if(conn != null){
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);

                //연결된 코드가 리턴되면
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    for(int i = 0; true; ++i){
                        //웹상에 보이는 텍스트를 라인단위로 읽어 저장
                        String line = br.readLine();
                        if(line == null) {
                            break;
                        }
                        output.add(line);
                    }
                    br.close();
                }
                conn.disconnect();
            }else{
                //fail to connect
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return output;
    }
}
