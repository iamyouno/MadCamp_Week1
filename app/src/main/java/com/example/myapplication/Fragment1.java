package com.example.myapplication;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.icu.lang.UScript;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import android.text.util.Linkify;


public class Fragment1 extends Fragment {
    View view;
    RecyclerView recyclerView;
    static ArrayList<UserModel> arrayList = new ArrayList<>();
    static int num = 0;

    EditText searchview;

    String phone;

    //int image[] = {R.drawable.noah, R.drawable.noah, R.drawable.noah, R.drawable.noah, R.drawable.noah, R.drawable.noah, R.drawable.noah, R.drawable.noah, R.drawable.noah, R.drawable.noah};
    String name[];
    String phone_number[];

    CustomAdapter adapter;

    String User = "[{'name': 'Noah', 'phone-number': '010-1234-5678'},"+
            "{'name': 'Emma', 'phone-number': '010-9012-2243'},"+
            "{'name': 'Liam', 'phone-number': '010-2546-4576'},"+
            "{'name': 'Isabella', 'phone-number': '010-1446-5986'},"+
            "{'name': 'Sophia', 'phone-number': '010-3876-4887'},"+
            "{'name': 'Sebastian', 'phone-number': '010-2754-3864'},"+
            "{'name': 'Olivia', 'phone-number': '010-1748-4879'},"+
            "{'name': 'Ava', 'phone-number': '010-7492-2865'},"+
            "{'name': 'Elijah', 'phone-number': '010-1785-4736'},"+
            "{'name': 'Alexander', 'phone-number': '010-8836-4869'}]";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_1, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        searchview = (EditText) view.findViewById(R.id.searchView);
        searchview.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num += 1;


        if (num == 1){
            try{
                JSONArray jarray = new JSONArray(User);// JSONARRAY 생성

                //image = new int[jarray.length()];
                name = new String[jarray.length()];
                phone_number = new String[jarray.length()];

                for(int i=0; i<jarray.length(); i++){
                    JSONObject jsonObj = jarray.getJSONObject(i); //JSONObject 추출
                    //image[i] = jsonObj.getInt("image");
                    //image[i] = jsonObj.getInt("image");
                    //final int resourceid = getResources().getIdentifier(image[i], "drawable", getContext().getPackageName());
                    //Drawable drawable = getResources().getDrawable(resourceid);
                    //Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), resourceid);
                    //image[i] = Base64.encodeToString(bitmap, Base64.DEFAULT);
                    name[i] = jsonObj.getString("name");
                    phone_number[i] = jsonObj.getString("phone-number");

                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }


            for (int i=0; i<phone_number.length; i++){
                UserModel userModel = new UserModel();
                //userModel.setImage(image[i]);
                userModel.setName(name[i]);
                userModel.setPhone_number(phone_number[i]);

                arrayList.add(userModel);
            }
        }

        //새로운 전화번호 추가
        ImageButton add_button;
        add_button = view.findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view_ = LayoutInflater.from(getActivity()).inflate(R.layout.edit_box, null, false);
                builder.setView(view_);

                final Button Button_submit = (Button) view_.findViewById(R.id.button_submit);
                final EditText editText_name = (EditText) view_.findViewById(R.id.edittext_name);
                final EditText editText_phone = (EditText) view_.findViewById(R.id.edittext_phone);

                Button_submit.setText("추가");
                final AlertDialog dialog = builder.create();

                Button_submit.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        String strName = editText_name.getText().toString();
                        String strPhone = editText_phone.getText().toString();

                        UserModel um_ = new UserModel();
                        um_.setName(strName);
                        um_.setPhone_number(strPhone);

                        arrayList.add(um_);

                        adapter.notifyDataSetChanged();

                        Collections.sort(arrayList, new Comparator<UserModel>(){
                            @Override
                            public int compare(UserModel rhs, UserModel lhs){
                                return rhs.getName().compareTo(lhs.getName());
                            }
                        });

                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });




        //이름순 정렬
        Collections.sort(arrayList, new Comparator<UserModel>(){
            @Override
            public int compare(UserModel rhs, UserModel lhs){
                return rhs.getName().compareTo(lhs.getName());
            }
        });

        //수정함_adapter = new CustomAdapter(arrayList);
        adapter = new CustomAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
        return view;

    }



    public class UserModel {
        //int image;
        String name;
        String phone_number;

        //public int getImage() { return image; }

        public String getName(){
            return name;
        }

        public String getPhone_number(){
            return phone_number;
        }

        //public void setImage(int image) { this.image = image; }

        public void setName(String name){
            this.name = name;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.viewHolder> implements Filterable  {
        ArrayList<UserModel> arrayList;

        ArrayList<UserModel> filtered_arrayList;

        Context mcontext;


        public CustomAdapter(Context context, ArrayList<UserModel> arrayList){
            this.arrayList = arrayList;
            this.filtered_arrayList = arrayList;
            this.mcontext = context;
        }

//        public CustomAdapter(ArrayList<UserModel> list){
//            this.arrayList = arrayList;
//            this.filtered_arrayList = arrayList;
//        }

        @Override
        public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_list, viewGroup, false);

            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(viewHolder viewHolder, int position) {
            viewHolder.name.setText(filtered_arrayList.get(position).getName());
            viewHolder.phone_number.setText(filtered_arrayList.get(position).getPhone_number());


            // 전화번호 편집 및 삭제
            viewHolder.edit_delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().openContextMenu(v);
//
//                    arrayList.remove(position);
//                    notifyDataSetChanged();

                }
            });



            //전화걸기
            viewHolder.phone_number.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View V){
                    phone = arrayList.get(position).getPhone_number();
                    String tel = "tel:" + phone;
                    startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                }
            });

        }

        @Override
        public int getItemCount(){
            return filtered_arrayList.size();
        }

        //전화번호 검색
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint){
                    String charString = constraint.toString();
                    if(charString.isEmpty()){
                        filtered_arrayList = arrayList;
                    }
                    else{
                        ArrayList<UserModel> filtering_arrayList = new ArrayList<>();
                        for (int i=0; i<arrayList.size(); i++){
                            if(arrayList.get(i).getName().toLowerCase().contains(charString.toLowerCase())){
                                filtering_arrayList.add(arrayList.get(i));
                            }
                        }
                        filtered_arrayList = filtering_arrayList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filtered_arrayList;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results){
                    filtered_arrayList = (ArrayList<UserModel>)results.values;
                    notifyDataSetChanged();
                }
            };
        }

        //implements~~ : 컨텍스트 메뉴 사용 위함 --> contextmenu로 edit, delete 구현
        public class viewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            TextView name;
            TextView phone_number;
            ImageButton edit_delete_button;

            public viewHolder(View itemView){
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                phone_number = (TextView) itemView.findViewById(R.id.phone_number);
                edit_delete_button = (ImageButton) itemView.findViewById(R.id.edit_delete_button);

                view.setOnCreateContextMenuListener(this); // 현재 클래스에서 oncreatecontextmenulistener 구현

            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) { // contextmenu 생성, 항목 선택시 호출되는 리스너 등록
                MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "EDIT");
                MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "DELETE");
                Edit.setOnMenuItemClickListener(onEditMenu);
                Delete.setOnMenuItemClickListener(onEditMenu);
            }

            //항목 클릭시 동작설정
            private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case 1001: //EDIT 선택
                            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                            // edit_box 보여줌
                            View view = LayoutInflater.from(mcontext).inflate(R.layout.edit_box, null, false);
                            builder.setView(view);
                            final Button Button_submit = (Button) view.findViewById(R.id.button_submit);
                            final EditText edittext_name = (EditText) view.findViewById(R.id.edittext_name);
                            final EditText edittext_phone = (EditText) view.findViewById(R.id.edittext_phone);

                            // 입력되어있던 데이터 보여줌
                            edittext_name.setText(arrayList.get(getAdapterPosition()).getName());
                            edittext_phone.setText(arrayList.get(getAdapterPosition()).getPhone_number());

                            final AlertDialog dialog = builder.create();
                            Button_submit.setOnClickListener(new View.OnClickListener(){
                                // 추가 눌렀을 때 현재 UI에 입력되어있는 내용으로
                                public void onClick(View v){
                                    String strName = edittext_name.getText().toString();
                                    String strPhone = edittext_phone.getText().toString();

                                    // arrayList 데이터 변경
                                    UserModel um = new UserModel();
                                    um.setName(strName);
                                    um.setPhone_number(strPhone);
                                    arrayList.add(getAdapterPosition(), um);

                                    notifyItemChanged(getAdapterPosition());

                                    dialog.dismiss();
                                }
                            });

                            dialog.show();

                            break;

                        case 1002: //DELETE 선택
                            arrayList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), arrayList.size());

                            break;

                    }
                    return true;
                }
            };

        }

    }


}
