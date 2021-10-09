package com.example.myapplication.Delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DeliveryAdd2 extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    GoogleMap gMap;
    String lat;


    Geocoder geocoder;
    List<Address> addresses;

    String address;
    String city;
    String state;
    String country;
    String postalCode;
    String knownName;

    String text;

    EditText t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_add2);


        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.yas_frag1Map);

        supportMapFragment.getMapAsync(this);

        t = findViewById(R.id.yas_editTextTextPersonName2);

        lat = "";
        geocoder = new Geocoder(this,Locale.getDefault());

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

                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    country = addresses.get(0).getCountryName();
                    postalCode = addresses.get(0).getPostalCode();
                    knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                    text = address +" , "+state +" , "+knownName ;

                    t.setText(text);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.yas_addReviewBtn2: Go();
                break;
        }
    }

    public void Go(){


        if (TextUtils.isEmpty(t.getText().toString()))
            Toast.makeText(getApplicationContext(), "Empty Address", Toast.LENGTH_SHORT).show();
        else {
//            Intent intent = new Intent(this, DeliveryAdd2.class);
//            startActivity(intent);
            Toast.makeText(this, "Order Successful", Toast.LENGTH_SHORT).show();
        }
    }
}