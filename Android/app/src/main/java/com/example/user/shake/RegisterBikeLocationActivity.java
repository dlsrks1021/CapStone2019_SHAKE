package com.example.user.shake;

import android.app.FragmentManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class RegisterBikeLocationActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    LatLng location_center;
    Button buttonBikeRegisterLocation, searchButton;
    EditText searchText;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bike_location);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.mapRegister);
        mapFragment.getMapAsync(this);

        buttonBikeRegisterLocation = findViewById(R.id.buttonRegisterBikeLocation);
        searchButton = findViewById(R.id.register_bike_search_button);
        searchText = findViewById(R.id.register_bike_search_text);
        searchText.bringToFront();

        buttonBikeRegisterLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("latitude", location_center.latitude);
                intent.putExtra("longitude", location_center.longitude);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder mGeocoder = new Geocoder(getApplicationContext());
                try{
                    List<Address> mResultLocation = mGeocoder.getFromLocationName(searchText.getText().toString(), 1);
                    double latitude = mResultLocation.get(0).getLatitude();
                    double longitude = mResultLocation.get(0).getLongitude();
                    LatLng pos = new LatLng(latitude, longitude);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                } catch (IOException e){

                }
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap map){
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition arg0) {
                location_center = arg0.target;
            }
        });

        LatLng SEOUL = new LatLng(37.56, 126.97);
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(11));
        mMap = map;
    }

}
