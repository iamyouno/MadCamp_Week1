package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class Fragment3 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);

//        Fragment importFragment = new Fragment_crawling_1();
//
//        FragmentManager fragmentManager_1 = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager_1.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_3, importFragment);
////                fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

        ImageButton crawling_1 = (ImageButton) view.findViewById(R.id.crawling_1);
        crawling_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //프래그먼트 새로 만들어서 그 프래그먼트 보여주도록
                Fragment importFragment = new Fragment_crawling_1();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_3, importFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton crawling_2 = (ImageButton) view.findViewById(R.id.crawling_2);
        crawling_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //프래그먼트 새로 만들어서 그 프래그먼트 보여주도록
                Fragment importFragment = new Fragment_crawling_2();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_3, importFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ImageButton crawling_3 = (ImageButton) view.findViewById(R.id.crawling_3);
        crawling_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //프래그먼트 새로 만들어서 그 프래그먼트 보여주도록
                Fragment importFragment = new Fragment_crawling_3();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_3, importFragment);
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }



}