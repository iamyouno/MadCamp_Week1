package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class viewAll extends AppCompatActivity {

    ImageView imageView;
    ImageView chooseImg;
    ToggleButton heart;
    ToggleButton otherheart;
    HorizontalScrollView horizontalScrollView;
    TextView textView;
    int imgid[] = {R.id.imglist1, R.id.imglist2, R.id.imglist3, R.id.imglist4, R.id.imglist5, R.id.imglist6, R.id.imglist7, R.id.imglist8, R.id.imglist9};
    int imgsrc[] = {R.drawable.white, R.drawable.green, R.drawable.sky, R.drawable.mountain, R.drawable.rainbow, R.drawable.clock, R.drawable.red, R.drawable.purple, R.drawable.computer};
    int heartid[] = {R.id.heart1, R.id.heart2, R.id.heart3, R.id.heart4, R.id.heart5, R.id.heart6, R.id.heart7, R.id.heart8, R.id.heart9};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        imageView = findViewById(R.id.viewImg);

        for (int i=0; i<imgid.length; i++){
            int finalI = i;

            heart = findViewById(heartid[finalI]);
            heart.setBackgroundDrawable(getResources().getDrawable(R.drawable.emptyheart));
            heart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    if (heart.isChecked()){
                        heart.setBackgroundDrawable(getResources().getDrawable(R.drawable.fullheart));
                    }
                    else{
                        heart.setBackgroundDrawable(getResources().getDrawable(R.drawable.emptyheart));
                    }
                }
            });

            chooseImg = findViewById(imgid[finalI]);
            chooseImg.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    heart = findViewById(heartid[finalI]);
                    imageView.setImageResource(imgsrc[finalI]);

                    for (int j=0; j<imgid.length; j++){
                            otherheart = findViewById(heartid[j]);
                            otherheart.setVisibility(View.INVISIBLE);
                    }
                    heart.setVisibility(View.VISIBLE);
                }

            });
            heart.setVisibility(View.INVISIBLE);
        }
    }
}

