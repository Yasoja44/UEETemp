package com.example.myapplication.Delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DeliveryAdd2 extends AppCompatActivity implements OnMapReadyCallback {

    //MapView map;

    GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_add2);

//        map = findViewById(R.id.mapView);
//        map.getMapAsync(this);
//
//        map.onCreate(savedInstanceState);


        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.frag1Map);

        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude+" : " +latLng.longitude);

                gMap.clear();

                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                gMap.addMarker(markerOptions);
            }
        });
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(0, 0))
//                .title("Marker"));
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        map.onStart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        map.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        map.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        map.onStop();
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        map.onDestroy();
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        map.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        map.onLowMemory();
//    }

}