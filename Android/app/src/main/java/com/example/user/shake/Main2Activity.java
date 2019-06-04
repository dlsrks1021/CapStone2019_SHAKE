package com.example.user.shake;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.shake.Request.PhpRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;


    ListView listView = null;
    private String userName,userID;
    TextView navTitle;
    TextView navContext;
    public static Context mContext;
    private ArrayList<BikeInfo> bikeList;
    private ArrayList<BikeInfo> rankerList;
    private int markerClickFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String token = FirebaseInstanceId.getInstance().getToken();
        System.out.println("TOKEN = "+token);



        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        //Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_SHORT).show();
        mContext=this;

        navTitle = findViewById(R.id.textNavTitle);
        navContext = findViewById(R.id.textNavContext);

        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Toast.makeText(Main2Activity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/



        //navTitle.setText(userID);
        Toast.makeText(getApplicationContext(),"화면을 스와이프하시면 메뉴가 보입니다.",Toast.LENGTH_SHORT).show();

        //TEST
        String tokenId = token;    //핸드폰별 구분을 위한 아이디값 ?

        String server_key = "AAAAte8fpPE:APA91bF-RJ5YY5uL6IIHbOu41KizVrkwMsAotezRUn_JfZyt06-0TGr7_kusw2fomtf3PkuqDktkRY9rpwZNKZpOCyzYV8lEsQZk8LQKLtf2hFQvvgH2dugpBHkMt_LITayTJy_OgSdl";

        String message = "hello world";

        FCM.send_FCM_Notification( tokenId,server_key,message);

//TEST END

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.mapMain);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*public static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    OkHttpClient mClient = new OkHttpClient();
    public void sendMessage(final JSONArray recipients, final String title, final String body, final String icon, final String message) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);
                    notification.put("icon", icon);

                    JSONObject data = new JSONObject();
                    data.put("message", message);
                    root.put("notification", notification);
                    root.put("data", data);
                    root.put("registration_ids", recipients);

                    String result = postToFCM(root.toString());
                    //Log.d(TAG, "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    Toast.makeText(getApplication(), "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + "AAAAte8fpPE:APA91bF-RJ5YY5uL6IIHbOu41KizVrkwMsAotezRUn_JfZyt06-0TGr7_kusw2fomtf3PkuqDktkRY9rpwZNKZpOCyzYV8lEsQZk8LQKLtf2hFQvvgH2dugpBHkMt_LITayTJy_OgSdl")
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
    */



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        TextView main_title = (TextView)findViewById(R.id.textNavTitle);
        main_title.setText(userID+" 님");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.itemRent) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setMessage("신고할 항목을 선택하세요")
                    .setNegativeButton("대여 기록", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent2 = new Intent(Main2Activity.this, ReportActivity.class);
                            intent2.putExtra("userId", userID);
                            Main2Activity.this.startActivity(intent2);
                        }
                    })
                    .setPositiveButton("공유 기록", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent2 = new Intent(Main2Activity.this, ReportOwnerActivity.class);
                            intent2.putExtra("userId", userID);
                            Main2Activity.this.startActivity(intent2);
                        }
                    })
                    .create()
                    .show();
            //Main2Activity.this.startActivity(intent2);
        } else if (id == R.id.itemRegister) {
            Intent intent2 = new Intent(Main2Activity.this, BikeRegisterActivity.class);
            intent2.putExtra("userId", userID);
            startActivityForResult(intent2, 2);
        }
        else if (id == R.id.itemInfo) {
            Intent intent2 = new Intent(Main2Activity.this, InfoActivity.class);
            intent2.putExtra("userId", userID);
            Main2Activity.this.startActivity(intent2);
        }
        else if (id == R.id.itemcamera) {
            Intent intent2 = new Intent(Main2Activity.this, CameraActivity.class);
            intent2.putExtra("userId", userID);
            Main2Activity.this.startActivity(intent2);
        }
        else if (id == R.id.itemtest) {
            Intent intent2 = new Intent(Main2Activity.this, BluetoothActivity.class);
            intent2.putExtra("userId", userID);
            Main2Activity.this.startActivity(intent2);
        }
        else if(id == R.id.itemReview){
            Intent intent2 = new Intent(Main2Activity.this, ReviewActivity.class);
            intent2.putExtra("userId", userID);
            Main2Activity.this.startActivity(intent2);
        }
        else if (id == R.id.itemReviewList){
            Intent intent2 = new Intent(Main2Activity.this, ReviewListActivity.class);
            intent2.putExtra("userId", userID);
            Main2Activity.this.startActivity(intent2);
        }
        else if (id == R.id.itemPoint){
            Intent intent2 = new Intent(Main2Activity.this, PointActivity.class);
            intent2.putExtra("userId", userID);
            Main2Activity.this.startActivity(intent2);
        }
        else if (id == R.id.itemTerms){
            Intent intent2 = new Intent(Main2Activity.this, TermsActivity.class);
            intent2.putExtra("userId", userID);
            Main2Activity.this.startActivity(intent2);
        }
        else if (id == R.id.itemCategory){
            Intent intent2 = new Intent(Main2Activity.this, CategoryActivity.class);
            intent2.putExtra("userId", userID);
            Main2Activity.this.startActivity(intent2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == 2){
                MarkerOptions markerOptions = new MarkerOptions();
                BikeInfo newBike = (BikeInfo) data.getSerializableExtra("newBike");
                bikeList.add(newBike);
                LatLng bikeLocation = new LatLng(newBike.getBikeLatitude(), newBike.getBikeLongitude());
                simpleAddMarker(mMap, markerOptions, bikeLocation, newBike.getBikeOwner(), "자전거 종류: " + newBike.getBikeType());
            }
        }
    }

    private void findRanker(){

        ArrayList<Float> ratingList = new ArrayList<>();

        for (int i = 0; i < bikeList.size(); ++i){
            ratingList.add(PhpRequest.getBikeRating(bikeList.get(i).getBikeCode()));
        }

        for (int i = 0; i < bikeList.size(); ++i){
            float myRating =  ratingList.get(i);
            int upRatingCount = 0;
            int myReviewCount = bikeList.get(i).getBike_review_count();

            if (myRating == 0 || myReviewCount == 0){
                continue;
            }
            for (int j = 0; j < bikeList.size(); ++j){
                float rating = 0;
                double gapLatitude, gapLongitude, distance;
                int reviewCount = bikeList.get(j).getBike_review_count();

                if (i == j)
                    continue;


                rating = ratingList.get(j);

                gapLatitude = bikeList.get(i).getBikeLatitude() - bikeList.get(j).getBikeLatitude();
                gapLongitude = bikeList.get(i).getBikeLongitude() - bikeList.get(j).getBikeLongitude();
                //위도 경도 km로 단위 변경
                gapLatitude *= 110;
                gapLongitude *= 88.74;
                distance = Math.sqrt(Math.pow(gapLatitude, 2) + Math.pow(gapLongitude, 2));

                if (distance <= 3 && reviewCount >= 1){
                    if (myRating < rating){
                        upRatingCount += 1;
                    }
                }
                if (upRatingCount >= 1)
                    break;
            }
            if (upRatingCount < 1 && myReviewCount >= 1){
                rankerList.add(bikeList.get(i));
            }
        }
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        PhpConnect task = new PhpConnect();
        ArrayList<String> bikeLatLng = new ArrayList<>();
        bikeList = new ArrayList<>();
        MarkerOptions markerOptions = new MarkerOptions();
        int bikeCost = 0, bike_review_count = 0;
        double bikeLatitude = 0, bikeLongitude = 0;
        String bikeOwner = "", bikeType = "", bikeImgUrl = "", bikeCode = "";
        String bikeLockId = "", bikeModelName = "", bikeAddInfo = "";
        float bikeRating = 0;

        mMap = map;

        try {
            bikeLatLng = task.execute("http://13.125.229.179/getBikeInfo.php").get();
        }catch (InterruptedException e){
            //bikeLatLng = "fail connect";
            e.printStackTrace();
        }catch (ExecutionException e){
            //bikeLatLng = "fail connect";
            e.printStackTrace();
        }

        for (int i = 0; i < bikeLatLng.size(); i += 12){
            bikeOwner = bikeLatLng.get(i);
            bikeCode = bikeLatLng.get(i + 1);
            bikeLatitude = Double.parseDouble(bikeLatLng.get(i + 2));
            bikeLongitude = Double.parseDouble(bikeLatLng.get(i + 3));
            bikeCost = Integer.parseInt(bikeLatLng.get(i + 4));
            bikeImgUrl = bikeLatLng.get(i + 5);
            bikeLockId = bikeLatLng.get(i + 6);
            bikeModelName = bikeLatLng.get(i + 7);
            bikeType = bikeLatLng.get(i + 8);
            bikeAddInfo = bikeLatLng.get(i + 9);
            bikeRating = Float.parseFloat(bikeLatLng.get(i + 10));
            bike_review_count = Integer.parseInt(bikeLatLng.get(i + 11));

            BikeInfo bike = new BikeInfo(bikeOwner, bikeCode, bikeLatitude, bikeLongitude, bikeCost, bikeImgUrl, bikeLockId, bikeModelName, bikeType, bikeAddInfo);
            bike.setBikeRating(bikeRating);
            bike.setBikeReviewCount(bike_review_count);
            bikeList.add(bike);
        }
        rankerList = new ArrayList<>();
        findRanker();

        for (int i = 0; i < bikeList.size(); ++i){
            LatLng bikeLocation = new LatLng(bikeList.get(i).getBikeLatitude(), bikeList.get(i).getBikeLongitude());
            int rankerFlag = -1;
            for (int j = 0; j < rankerList.size(); ++j) {
                if (bikeList.get(i).getBikeCode().equals(rankerList.get(j).getBikeCode())){
                    rankerFlag = j;
                    break;
                }
            }
            if (rankerFlag == -1){
                simpleAddMarker(map, markerOptions, bikeLocation, bikeList.get(i).getBikeOwner(), "자전거 종류: " + bikeList.get(i).getBikeType());
            }else{
                rankerAddMarker(map, markerOptions, bikeLocation, rankerList.get(rankerFlag).getBikeOwner(), "자전거 종류: " + rankerList.get(rankerFlag).getBikeType());
            }
        }

        GpsInfo gpsInfo = new GpsInfo(getApplicationContext());

        LatLng myLocation = new LatLng(gpsInfo.getLatitude(), gpsInfo.getLongitude());
        userAddMarker(map, myLocation);

        LatLng SEOUL = new LatLng(37.506, 126.958);
        if (myLocation.latitude > 30 && myLocation.latitude < 50 && myLocation.longitude < 150 && myLocation.longitude > 100)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        else
            mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));


        mMap.setOnMarkerClickListener(this);
    }

    private void simpleAddMarker(final GoogleMap map, MarkerOptions markerOptions, LatLng pos, String title, String context) {
        markerOptions.position(pos);
        markerOptions.title(title);
        markerOptions.snippet(context);
        map.addMarker(markerOptions);
    }

    private void rankerAddMarker(final GoogleMap map, MarkerOptions markerOptions, LatLng pos, String title, String context){
        markerOptions.position(pos);
        markerOptions.title(title);
        markerOptions.snippet(context);

        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.ranker_star);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

        map.addMarker(markerOptions);
    }

    private void userAddMarker(final GoogleMap map, LatLng pos){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(pos);

        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.person_icon);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

        map.addMarker(markerOptions);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String mOwner = marker.getTitle();
        for (int i = 0; i < bikeList.size(); ++i){
            if ((bikeList.get(i).getBikeOwner().equals(mOwner)) && (marker.getPosition().latitude == bikeList.get(i).getBikeLatitude())){
                if (markerClickFlag == i){
                    Intent intent = new Intent(Main2Activity.this, RentActivity.class);
                    intent.putExtra("borrower", userID);
                    intent.putExtra("bikecode", bikeList.get(i).getBikeCode());
                    startActivity(intent);
                }else{
                    markerClickFlag = i;
                }
                break;
            }
        }
        return false;
    }
    public String[] getInfo(){
        String[] temp = new String[2];
        temp[0]=userID;
        temp[1]=userName;
        return temp;
    }
}
