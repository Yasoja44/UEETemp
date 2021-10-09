package com.example.myapplication.New;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    Button b;
    ImageButton b2;
    DatabaseReference dbRef;
    Review review;
    String pID,uID;
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

        r = findViewById(R.id.yas_ratingBar6);
        LayerDrawable stars = (LayerDrawable) r.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(255, 87, 34), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        t = findViewById(R.id.yas_editTextTextPersonName4);
        b = findViewById(R.id.yas_addReviewBtn);
        b2 = findViewById(R.id.yas_reviewPopupAddCancel);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        pID = extras.getString("PRODUCT_ID");
        uID = extras.getString("USER_ID");

        review = new Review();


        b.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.yas_addReviewBtn: Submit();
                break;
            case R.id.yas_reviewPopupAddCancel: Exit();
                break;
        }
    }

    public void Exit(){

    }

    public void Submit() {



            dbRef = FirebaseDatabase.getInstance().getReference().child("Review");
            try {

                if (TextUtils.isEmpty(t.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Empty Review", Toast.LENGTH_SHORT).show();
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