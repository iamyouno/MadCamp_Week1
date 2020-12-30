package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = findViewById(R.id.viewpager);
        ViewPager_Adapter adapter = new ViewPager_Adapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        //아래부터 tab_layout이랑 viewpager 연동하는 코드
        TabLayout tab = findViewById(R.id.tab_layout);
        tab.setupWithViewPager(vp);

    }


} // ViewPager, ViewPager_Adapter 생성 후 연결