package com.example.myapplication.New;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


import com.example.myapplication.DB.Review;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReviewPopupUpdate extends AppCompatActivity implements View.OnClickListener{

    RatingBar r;
    EditText t;
    Button b,b2,b3;
    DatabaseReference dbRef;
    Review review;
    String pID,uID;
    Map<String,Object> updateMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_popup_update);

        ///////////Popup
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        //////////

        r = findViewById(R.id.ratingBarUpdateReview);
        t = findViewById(R.id.reviewUpdateText);
        b = findViewById(R.id.btnUpdateReview);
        b2 = findViewById(R.id.btnDeleteReview);
        b3 = findViewById(R.id.reviewPopupUpdateCancel);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        pID = extras.getString("PRODUCT_ID");
        uID = extras.getString("USER_ID");

        review = new Review();


        b.setOnClickListener(this);
        b2.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdateReview: Update();
                break;
            case R.id.btnDeleteReview: Delete();
                break;
            case R.id.reviewPopupUpdateCancel: Exit();
                break;
        }
    }

    public void Exit(){

    }

    public void Update() {


            dbRef = FirebaseDatabase.getInstance().getReference();
            Query updateQuery = dbRef.child("Review").orderByChild("userID").equalTo(uID);

            updateMap = new HashMap<>();
            updateMap.put("userID",uID);
            updateMap.put("productID",pID);
            updateMap.put("rating",(int) r.getRating());
            updateMap.put("review",t.getText().toString().trim());

            updateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot updateSnapshot : dataSnapshot.getChildren()) {
                        if(updateSnapshot.child("productID").getValue().toString().equals(pID)){

                            updateSnapshot.getRef().updateChildren(updateMap);
                            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

    }

    public void Delete() {

        dbRef = FirebaseDatabase.getInstance().getReference();
        Query deleteQuery = dbRef.child("Review").orderByChild("userID").equalTo(uID);

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                    if(Objects.requireNonNull(deleteSnapshot.child("productID").getValue()).toString().equals(pID)) {
                        deleteSnapshot.getRef().removeValue();
                        Toast.makeText(getApplicationContext(), "Successfully Removed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}