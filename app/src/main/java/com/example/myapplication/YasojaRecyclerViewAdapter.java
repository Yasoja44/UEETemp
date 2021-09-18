package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class YasojaRecyclerViewAdapter extends RecyclerView.Adapter<YasojaRecyclerViewAdapter.ViewHolder>  {

    private static final String TAG = "test.sliit.recyclerview.RecyclerViewAdapter";
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();
    private ArrayList<String> mImageNames2 = new ArrayList<>();

    private Context mContext;
    public YasojaRecyclerViewAdapter(ArrayList<String> mImageNames,ArrayList<String> mImageNames2, ArrayList<String>
            mImage, Context mContext) {
        this.mImageNames = mImageNames;
        this.mImageNames2 = mImageNames2;
        this.mImage = mImage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_yas,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Glide.with(mContext)
                .asBitmap().load(mImage.get(position))
                .into(holder.image);
        holder.imageName.setText(mImageNames.get(position));
        holder.imageName2.setText(mImageNames2.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on"+mImageNames.get(position));

                Toast.makeText(mContext,mImageNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mImageNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView imageName;
        TextView imageName2;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.person_photo);
            imageName = itemView.findViewById(R.id.person_name);
            imageName2 = itemView.findViewById(R.id.person_name2);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}

