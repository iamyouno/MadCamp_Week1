package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;


public class Fragment1 extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<UserModel> arrayList;

    int image[];
    String name[];
    String phone_number[];


    CustomAdapter adapter;

    String User = "[{'name': 'Noah', 'phone-number': '010-1234-5678', 'image': R.drawable.noah.png},"+
            "{'name': 'Emma', 'phone-number': '010-9012-2243', 'image': R.drawable.emma.png},"+
            "{'name': 'Liam', 'phone-number': '010-2546-4576', 'image': R.drawable.liam.png},"+
            "{'name': 'Isabella', 'phone-number': '010-1446-5986', 'image': R.drawable.isabella.png},"+
            "{'name': 'Sophia', 'phone-number': '010-3876-4887', 'image': R.drawable.sophia.png},"+
            "{'name': 'Sebastian', 'phone-number': '010-2754-3864', 'image': R.drawable.sebastian.png},"+
            "{'name': 'Olivia', 'phone-number': '010-1748-4879', 'image': R.drawable.olivia,obg},"+
            "{'name': 'Ava', 'phone-number': '010-7492-2865', 'image': R.drawable.ava.png},"+
            "{'name': 'Elijah', 'phone-number': '010-1785-4736', 'image': R.drawable.elijah.png},"+
            "{'name': 'Alexander', 'phone-number': '010-8836-4869', 'image': R.drawable.alexander.png}]";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_1, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayList = new ArrayList<>();


        try{
            JSONArray jarray = new JSONArray(User);// JSONARRAY 생성
            
            image = new int[jarray.length()];
            name = new String[jarray.length()];
            phone_number = new String[jarray.length()];

            for(int i=0; i<jarray.length(); i++){
                JSONObject jsonObj = jarray.getJSONObject(i); //JSONObject 추출
                name[i] = jsonObj.getString("name");
                phone_number[i] = jsonObj.getString("phone-number");
                image[i] = jsonObj.getInt("image");

            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }


        for (int i=0; i<phone_number.length; i++){
            UserModel userModel = new UserModel();
            userModel.setName(name[i]);
            userModel.setPhone_number(phone_number[i]);
            userModel.setImage(image[i]);

            arrayList.add(userModel);
        }

        adapter = new CustomAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public class UserModel{
        String name;
        String phone_number;
        int image;
        
        public int getImage(){
            return image;
        }
        
        public String getName(){
            return name;
        }

        public String getPhone_number(){
            return phone_number;
        }
        
        public void setImage(int image){
            this.image = image;
        }
        
        public void setName(String name){
            this.name = name;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }
    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.viewHolder>{
        ArrayList<UserModel> arrayList;

        public CustomAdapter(ArrayList<UserModel> arrayList){
            this.arrayList = arrayList;
        }

        @Override
        public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_list, viewGroup, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(viewHolder viewHolder, int position) {
            viewHolder.name.setText(arrayList.get(position).getName());
            viewHolder.phone_number.setText(arrayList.get(position).getPhone_number());
            viewHolder.image.setImageResource(arrayList.get(position).getImage());
        }

        @Override
        public int getItemCount(){
            return arrayList.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder{
            TextView name;
            TextView phone_number;
            ImageView image;

            public viewHolder(View itemView){
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                phone_number = (TextView) itemView.findViewById(R.id.phone_number);
                image = (ImageView) itemView.findViewById(R.id.image);
            }
        }
    }
}

