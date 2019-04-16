package com.example.user.shake;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONObject;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    private String rentnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        String[] info = new String[2];
        info=((MainActivity)MainActivity.mContext).getInfo();
        final String borrower = info[0];
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(InfoActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(InfoActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();

        TextView title = (TextView)findViewById(R.id.title_info);
        final TextView content = (TextView)findViewById(R.id.content_info);
        Button return_button = (Button) findViewById(R.id.return_button_info);
//
        try {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();
            System.out.println("위치정보 : " + provider + "\n" + "위도 : " + longitude + "\n" + "경도 : " + latitude + "\n" + "고도  : " + altitude);
        }
        catch (SecurityException e){
            System.out.println("FUCK");
            e.printStackTrace();
        }
//
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        content.setText("대여 중인 자전거가 없습니다");
                    }
                    else{
                        content.setText("대여 중인 \n자전거가 있습니다");
                        rentnumber=jsonResponse.getString("rentnumber");
                        //System.out.println(rentnumber);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        CheckRequest checkRequest = new CheckRequest(borrower, responseListener);
        RequestQueue queue = Volley.newRequestQueue(InfoActivity.this);
        queue.add(checkRequest);

        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("rent_success");
                            if(success){
                                content.setText("대여 중인 자전거가 없습니다");
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                                builder.setMessage("반납에 실패하였습니다")
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
                if(!content.getText().equals("대여 중인 자전거가 없습니다")) {
                    ReturnRequest returnRequest = new ReturnRequest(borrower, Integer.parseInt(rentnumber),"2019-04-03 12:00:01", "http://13.125.229.179/testimage.php", 123, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(InfoActivity.this);
                    queue.add(returnRequest);
                }
                else{
                    Toast.makeText(getApplicationContext(),"대여 중인 자전거가 없습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

            Toast.makeText(getApplication(),"위치정보 : " + provider + "\n" +"위도 : " + longitude + "\n" +"경도 : " + latitude + "\n" +"고도  : " + altitude,Toast.LENGTH_SHORT).show();

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };


}
