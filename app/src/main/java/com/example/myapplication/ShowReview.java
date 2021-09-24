package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.New.ReviewPopupAdd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowReview extends AppCompatActivity implements View.OnClickListener{

    DatabaseReference dbRef;
    TextView t;
    RatingBar r;
    String pID,uID;
    int total=0,count=0;
    Button b;
    Bundle extras;

    FloatingActionButton fb;

    private static final String TAG = "Show";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mNames2 = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_review);

        //t = findViewById(R.id.textView4);
        r = findViewById(R.id.ratingBar5);
        //b = findViewById(R.id.button2);

        fb = findViewById(R.id.popButton);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        pID = extras.getString("PRODUCT_ID");
        uID = extras.getString("USER_ID");




        dbRef = FirebaseDatabase.getInstance().getReference();
        Query showQuery = dbRef.child("Review").orderByChild("productID").equalTo(pID);

        showQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot showSnapshot : dataSnapshot.getChildren()) {
                    //t.append("Review: " + showSnapshot.child("review").getValue().toString() + "\n" +
                    //        "Rating: " + showSnapshot.child("rating").getValue().toString() + "\n" +
                    //        "User ID: " + showSnapshot.child("userID").getValue().toString() + "\n" +
                     //       "Product ID: " + showSnapshot.child("productID").getValue().toString() + "\n\n"
                    //);


                    int temp= Integer.parseInt(showSnapshot.child("rating").getValue().toString());
                    Rate(temp);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        initImageBitmaps();




    }

    private void initImageBitmaps() {
        dbRef = FirebaseDatabase.getInstance().getReference();
        Query showQuery = dbRef.child("Review").orderByChild("productID").equalTo(pID);

        showQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot showSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, "initImageBitmaps: started");
                    mImageUrls.add("https://www.howtogeek.com/wp-content/uploads/2020/03/delivery-food.jpg.pagespeed.ce.8e-lIOJeD5.jpg");
                    mNames.add("Review: " + showSnapshot.child("review").getValue().toString() + "\n" +
                            "Rating: " + showSnapshot.child("rating").getValue().toString() + "\n" +
                            "User ID: " + showSnapshot.child("userID").getValue().toString() + "\n" +
                            "Product ID: " + showSnapshot.child("productID").getValue().toString() + "\n\n"
                    );
                    initRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: started");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        YasojaRecyclerViewAdapter adapter = new YasojaRecyclerViewAdapter(mNames,mNames2,mImageUrls,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void Rate(int tot) {


        count += 1;
        total += tot;

        if(count == 0){
            count = 1;
        }

        System.out.println("Count:" + count);
        System.out.println("Total:" + total);

        r.setRating(total/count);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.popButton: Submit2();
                break;
            //case R.id.button2: Submit();
                //break;

        }
    }

//    public void Submit() {
//        Intent intent = new Intent(this, AddReview.class);
//        extras = new Bundle();
//        extras.putString("PRODUCT_ID", pID);
//        extras.putString("USER_ID", uID);
//        intent.putExtras(extras);
//        startActivity(intent);
//    }

    public void Submit2() {
        Intent intent = new Intent(this, ReviewPopupAdd.class);
        extras = new Bundle();
        extras.putString("PRODUCT_ID", pID);
        extras.putString("USER_ID", uID);
        intent.putExtras(extras);
        Toast toast = Toast.makeText(getApplicationContext(),
                "This is a message displayed in a Toast",
                Toast.LENGTH_SHORT);

        toast.show();
        startActivity(intent);
    }
}