package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProductDummy extends AppCompatActivity {

    TextView t1,t2,t3;
    RatingBar r1,r2,r3;
    EditText t;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dummy);

        t1 = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);

        r1 = findViewById(R.id.ratingBar);
        r2 = findViewById(R.id.ratingBar2);
        r3 = findViewById(R.id.ratingBar3);

        t = findViewById(R.id.editTextTextPersonName3);


        r1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Submit1();
                }
                return true;
            }
        });

        r2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Submit2();
                }
                return true;
            }
        });

        r3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Submit3();
                }
                return true;
            }
        });

    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ratingBar: Submit1();
//                break;
//            case R.id.ratingBar2: Submit2();
//                break;
//            case R.id.ratingBar3: Submit3();
//                break;
//        }
//    }

    public void Submit1(){
        Intent intent = new Intent(this, ShowReview.class);
        extras = new Bundle();
//        extras.putInt("RATING", (int) r1.getRating());
        extras.putString("PRODUCT_ID", "P01");
        extras.putString("USER_ID", t.getText().toString());
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void Submit2(){
        Intent intent = new Intent(this, ShowReview.class);
        extras = new Bundle();
//        extras.putInt("RATING", (int) r2.getRating());
        extras.putString("PRODUCT_ID", "P02");
        extras.putString("USER_ID", t.getText().toString());
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void Submit3(){
        Intent intent = new Intent(this, ShowReview.class);
        extras = new Bundle();
//        extras.putInt("RATING", (int) r3.getRating());
        extras.putString("PRODUCT_ID", "P03");
        extras.putString("USER_ID", t.getText().toString());
        intent.putExtras(extras);
        startActivity(intent);
    }

}