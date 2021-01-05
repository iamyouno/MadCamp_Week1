package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class Fragment3 extends Fragment {


    FragmentActivity fragmentActivity;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof Activity){
            fragmentActivity = (FragmentActivity) context;
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        Fragment importFragment = new Fragment_crawling_2();

        FragmentManager fragmentManager_1 = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction_1 = fragmentManager_1.beginTransaction();
        fragmentTransaction_1.replace(R.id.fragment_3, importFragment);
        fragmentTransaction_1.addToBackStack(null);
        fragmentTransaction_1.commit();

        ImageButton crawling_1 = (ImageButton) view.findViewById(R.id.crawling_1);
        crawling_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //프래그먼트 새로 만들어서 그 프래그먼트 보여주도록
                Fragment importFragment = new Fragment_crawling_1();

                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_3, importFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.e("sdf","sdf");

            }
        });

        ImageButton crawling_2 = (ImageButton) view.findViewById(R.id.crawling_2);
        crawling_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //프래그먼트 새로 만들어서 그 프래그먼트 보여주도록
                Fragment importFragment = new Fragment_crawling_2();

                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_3, importFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.e("sdf","sdf");
            }
        });

        ImageButton crawling_3 = (ImageButton) view.findViewById(R.id.crawling_3);
        crawling_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //프래그먼트 새로 만들어서 그 프래그먼트 보여주도록
                Fragment importFragment = new Fragment_crawling_3();

                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_3, importFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.e("sdf","sdf");
            }
        });


        return view;
    }



}