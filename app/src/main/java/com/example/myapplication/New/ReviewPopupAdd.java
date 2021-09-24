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

public class ReviewPopupAdd extends AppCompatActivity implements View.OnClickListener{

    RatingBar r;
    EditText t;
    Button b,b2;
    DatabaseReference dbRef;
    Review review;
    String pID,uID;
    Boolean exists = false;
    Map<String,Object> updateMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_popup_add);

        ///////////Popup
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        //////////

        r = findViewById(R.id.ratingBar6);
        t = findViewById(R.id.editTextTextPersonName4);
        b = findViewById(R.id.button5);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        pID = extras.getString("PRODUCT_ID");
        uID = extras.getString("USER_ID");

        review = new Review();
        exists = isHere();

        b.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button5: Submit();
                break;
            case R.id.button3: Delete();
                break;
        }
    }

    public void Submit() {

        if (exists) {

            Toast.makeText(getApplicationContext(), "If", Toast.LENGTH_SHORT).show();

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
        } else {
            Toast.makeText(getApplicationContext(), "Else", Toast.LENGTH_SHORT).show();


            dbRef = FirebaseDatabase.getInstance().getReference().child("Review");
            try {

                if (TextUtils.isEmpty(t.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Empty Name", Toast.LENGTH_SHORT).show();
                else {

                    review.setUserID(uID.trim());
                    review.setProductID(pID.trim());
                    review.setRating((int) r.getRating());
                    review.setReview(t.getText().toString().trim());

                    dbRef.push().setValue(review);
                    Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show();


                }
            } catch (NumberFormatException nfe) {
                Toast.makeText(getApplicationContext(), "Invalid Contact No or ID", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Boolean isHere(){
        dbRef = FirebaseDatabase.getInstance().getReference();
        Query checkQuery = dbRef.child("Review").orderByChild("userID").equalTo(uID);

        checkQuery.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot checkSnapshot : dataSnapshot.getChildren()) {
                    if(Objects.requireNonNull(checkSnapshot.child("productID").getValue()).toString().equals(pID)){
                        exists = true;

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return exists;
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