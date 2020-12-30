package com.example.myapplication;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class FavAdapter extends RecyclerView.Adapter<FavAdapter.CustomViewHolder> {
    private ArrayList<favImg> mList;
    ImageView setImage;
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;

        public CustomViewHolder(View view){
            super(view);
            this.image = (ImageView) view.findViewById(R.id.fav_listitem);

            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    int pos = getAdapterPosition();
//
//                    setImage = findViewById(R.id.viewFav);
//                    setImage.setImageResource(R.drawable.sky);
                }
            });
        }

    }

    public FavAdapter(ArrayList<favImg> list){
        this.mList = list;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_img, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.image.setImageResource(mList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
