package com.example.user.shake;

import android.Manifest;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng SEOUL = new LatLng(37.56, 126.97);
        LatLng BIKE = new LatLng(37.56, 126.98);

        MarkerOptions markerOptions = new MarkerOptions();

        simpleAddMarker(map, markerOptions, SEOUL, "공유자: 박기철", "로드자전거\nroad01");
        simpleAddMarker(map, markerOptions, BIKE, "공유자: 김규리", "마운틴자전거\nmountain01");

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    private void simpleAddMarker(final GoogleMap map, MarkerOptions markerOptions, LatLng pos, String title, String context){
        markerOptions.position(pos);
        markerOptions.title(title);
        markerOptions.snippet(context);
        map.addMarker(markerOptions);
    }
}