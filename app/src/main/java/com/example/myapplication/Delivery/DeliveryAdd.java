package com.example.myapplication.Delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.ShowReview;

public class DeliveryAdd extends AppCompatActivity implements View.OnClickListener{

    TextView t;
    EditText e;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_add);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addReviewBtn2: Go();
                break;
        }
    }

    public void Go(){
        Intent intent = new Intent(this, DeliveryAdd2.class);
        startActivity(intent);
    }
}