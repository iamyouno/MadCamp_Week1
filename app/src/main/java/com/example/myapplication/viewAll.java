package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import static com.example.myapplication.Fragment2.favImgs;


public class viewAll extends AppCompatActivity {

    ImageView imageView;
    ImageView chooseImg;
    ImageButton backward;
    ToggleButton heart;
    ToggleButton other_heart;
    ToggleButton saved_heart;
    boolean[] heart_toggled = new boolean[10];
    static int check;

    int[] img_id = {R.id.imglist1, R.id.imglist2, R.id.imglist3, R.id.imglist4, R.id.imglist5, R.id.imglist6, R.id.imglist7, R.id.imglist8, R.id.imglist9, R.id.imglist10};
    int[] img_src = {R.drawable.white, R.drawable.green, R.drawable.sky, R.drawable.mountain, R.drawable.rainbow, R.drawable.clock, R.drawable.red, R.drawable.purple, R.drawable.computer, R.drawable.orange};
    int[] heart_id = {R.id.heart1, R.id.heart2, R.id.heart3, R.id.heart4, R.id.heart5, R.id.heart6, R.id.heart7, R.id.heart8, R.id.heart9, R.id.heart10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        imageView = findViewById(R.id.viewImg);

        final int[] delete = new int[img_id.length];
        for (int i=0; i<img_id.length; i++){
            int finalI = i;


            heart = findViewById(heart_id[finalI]);
            heart.setBackgroundDrawable(getResources().getDrawable(R.drawable.emptyheart));
            heart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    if (heart.isChecked()){
                        heart.setBackgroundDrawable(getResources().getDrawable(R.drawable.fullheart));
                        view_favorite vf = new view_favorite();
                        delete[finalI] = favImgs.size();
                        vf.addfavImgs(new favImg(img_src[finalI]));

                    }
                    else{
                        heart.setBackgroundDrawable(getResources().getDrawable(R.drawable.emptyheart));
                        view_favorite vf = new view_favorite();

                        favImgs.remove(delete[finalI]);
                    }
                }
            });

            chooseImg = findViewById(img_id[finalI]);
            chooseImg.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    heart = findViewById(heart_id[finalI]);
                    imageView.setImageResource(img_src[finalI]);

                    for (int j=0; j<img_id.length; j++){
                        other_heart = findViewById(heart_id[j]);
                        other_heart.setVisibility(View.INVISIBLE);
                    }
                    heart.setVisibility(View.VISIBLE);
                }
            });
            heart.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle save){
        super.onSaveInstanceState(save);
        save.putBooleanArray("heart_toggled", heart_toggled);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        heart_toggled = savedInstanceState.getBooleanArray("heart_toggled");
    }
}

