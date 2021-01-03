package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.ArrayList;

import static com.example.myapplication.Fragment2.favImgs;

public class view_favorite extends AppCompatActivity {
    RecyclerView recyclerView;
    Context mContext;
    ImageView imageView;
    FavAdapter favAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_favorite);

        mContext = getApplicationContext();

        recyclerView = findViewById(R.id.listFav);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        favAdapter = new FavAdapter(favImgs);
        recyclerView.setAdapter(favAdapter);
        imageView = findViewById(R.id.viewFav);

    }

    public class FavAdapter extends RecyclerView.Adapter<FavAdapter.CustomViewHolder> {
        private ArrayList<favImg> mList;

        public void delfavImgs(int i) {
            favImgs.remove(new favImg(i));
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected ImageView image;

            public CustomViewHolder(View view){
                super(view);
                this.image = (ImageView) view.findViewById(R.id.fav_listitem);

                view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v){
                        int pos = getAdapterPosition();
                        imageView.setImageResource(mList.get(pos).getImage());
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
        public void onBindViewHolder(@NonNull FavAdapter.CustomViewHolder holder, int position) {
            holder.image.setImageResource(mList.get(position).getImage());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
        
    }

    public void addfavImgs(favImg fi) {
        favImgs.add(fi);
    }
}