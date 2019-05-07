package com.example.user.shake;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.user.shake.Request.CheckRequest;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class InfoActivity extends AppCompatActivity {

    private String rentnumber;
    String userId;
    TextView idView;
    TextView emailView, rentBikeView;
    TextView pointView, modelNameView;
    EditText passwordEdit;
    EditText checkPasswordEdit;
    Button modifyButton;

    //Test
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    // GPSTracker class
    private GpsInfo gps;
    private Button btnShowLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        String[] info = new String[2];
        info=((Main2Activity)Main2Activity.mContext).getInfo();
        final String borrower = info[0];
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        idView = findViewById(R.id.infoIdView);
        emailView = findViewById(R.id.infoEmailView);
        pointView = findViewById(R.id.infoPointView);
        passwordEdit = findViewById(R.id.infoPasswordEdit);
        checkPasswordEdit = findViewById(R.id.infoCheckPasswordEdit);
        modifyButton = findViewById(R.id.infoModifyButton);
        //rentBikeView = findViewById(R.id.infoRentBikeView);
        //modelNameView = findViewById(R.id.infoModelnameView);

        idView.setText(intent.getStringExtra("userId"));
        pointView.setText("p");

        PhpConnect task = new PhpConnect();
        ArrayList<String> userEmail;

        try {
            userEmail = task.execute("http://13.125.229.179/getEmailInfo.php?userId="+userId).get();
            emailView.setText(userEmail.get(0));
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordEdit.getText().toString().equals(checkPasswordEdit.getText().toString())){
                    PhpConnect task2 = new PhpConnect();
                    try {
                        task2.execute("http://13.125.229.179/getEmailInfo.php?userId="+userId+"&password="+passwordEdit.getText().toString()).get();
                        Toast.makeText(getApplicationContext(),"비밀번호가 변경되었습니다!",Toast.LENGTH_SHORT).show();
                        finish();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다!",Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        //TEST
        callPermission();  // 권한 요청을 해야 함
        //Test end

        TextView title = (TextView)findViewById(R.id.title_info);
        final TextView content = (TextView)findViewById(R.id.content_info);
        Button return_button = (Button) findViewById(R.id.return_button_info);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        content.setText("X");
                    }
                    else{
                        content.setText("O");
                        rentnumber=jsonResponse.getString("rentnumber");
                        rentBikeView.setVisibility(View.VISIBLE);
                        PhpConnect task = new PhpConnect();
                        try {
                            ArrayList<String> result = task.execute("http://13.125.229.179/getModelnameByRentnumber.php?rentnumber="+rentnumber).get();
                            modelNameView.setText(result.get(0));
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }catch (ExecutionException e) {
                            e.printStackTrace();
                        }
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
                if (!isPermission) {
                    callPermission();
                    return;
                }

                gps = new GpsInfo(InfoActivity.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    Log.v("gps","gps OK");
                    /*Toast.makeText(getApplication(),String.valueOf(latitude+"   "+longitude),Toast.LENGTH_SHORT).show();
                    Toast.makeText(
                            getApplicationContext(),
                            "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                            Toast.LENGTH_LONG).show();*/
                } else {
                    // GPS 를 사용할수 없으므로
                    Log.v("gps", "gps Fail");
                    gps.showSettingsAlert();
                }
                if(!content.getText().equals("대여 중인 자전거가 없습니다")) {
                    /*if(if문으로 위치 받아서 반납장소랑 현재 위치랑 비슷한지 비교해서 맞으면 실행){
                        long time = System.currentTimeMillis();
                        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String day = dayTime.format(new Date(time));
                        ReturnRequest returnRequest = new ReturnRequest(getIntent().getStringExtra("userId"), Integer.parseInt(rentnumber),day, "http://13.125.229.179/testimage.php", 123, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(InfoActivity.this);
                        queue.add(returnRequest);
                    }*/
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

    //Test Start
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    public void smart_key_clicked(View v){
        Intent intent2 = new Intent(InfoActivity.this, BluetoothActivity.class);
        InfoActivity.this.startActivity(intent2);
    }

//Test end


}
