package com.example.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_crawling_3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_crawling_3 extends Fragment {

    ArrayList<Fragment_crawling_3.GameResult> gameResults;
    RecyclerView recyclerView;
    DatePickerDialog.OnDateSetListener listener;

    String url;
    int yearf=2021;
    int monthf=0;
    int dayf=1;
    View view;


    public static Fragment_crawling_3 newInstance(String param1, String param2) {
        return new Fragment_crawling_3();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_crawling_3, container, false);

        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recyclerView_sport);
        Button button = view.findViewById(R.id.datePicker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                TextView textView = (TextView)view.findViewById(R.id.noResult);
                textView.setText("");
                selectDate();
            }
        });

        ImageButton crawling_2 = (ImageButton) view.findViewById(R.id.crawling_2_3);
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

        ImageButton crawling_1 = (ImageButton) view.findViewById(R.id.crawling_1_3);
        crawling_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //프래그먼트 새로 만들어서 그 프래그먼트 보여주도록
                Fragment importFragment = new Fragment_crawling_3();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_3, importFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        return view;
    }

    public void selectDate(){
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String yearS = Integer.toString(year);
                String monthS = Integer.toString(month+1);
                String dayS = Integer.toString(dayOfMonth);
                url = "https://search.naver.com/search.naver?where=nexearch&sm=tab_etc&mra=bjA5&query=" + yearS + "년%20" + monthS + "월%20" + dayS + "일%20해외축구%20경기일정";
                yearf = year;
                monthf = month;
                dayf = dayOfMonth;
                SportJsoup sportJsoup = new SportJsoup();
                sportJsoup.execute();
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), listener, yearf, monthf, dayf);
        dialog.show();
    }

    public class GameResult {
        private String team1;
        private String team2;
        private String score;
        private String logo1;
        private String logo2;
        public GameResult(){
        }

        public void setTeam1(String team1){
            this.team1 = team1;
        }

        public void setTeam2(String team2){
            this.team2 = team2;
        }

        public void setScore(String score){
            this.score = score;
        }

        public void setLogo1(String logo1){
            this.logo1 = logo1;
        }

        public void setLogo2(String logo2){
            this.logo2 = logo2;
        }

        public String getTeam1(){
            return team1;

        }

        public String getTeam2(){
            return team2;
        }

        public String getScore(){
            return score;
        }

        public String getLogo1(){
            return logo1;
        }

        public String getLogo2(){
            return logo2;
        }
    }


    public class RecyclerAdapter extends RecyclerView.Adapter<Fragment_crawling_3.RecyclerAdapter.ViewHolder> {
        private ArrayList<Fragment_crawling_3.GameResult> grs;

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView team1, team2, score;
            ImageView logo1, logo2;
            ViewHolder(View view){
                super(view);

                team1 = view.findViewById(R.id.team1);
                team2 = view.findViewById(R.id.team2);
                score = view.findViewById(R.id.score);
                logo1 = view.findViewById(R.id.logo1);
                logo2 = view.findViewById(R.id.logo2);
            }
        }

        public RecyclerAdapter(ArrayList<Fragment_crawling_3.GameResult> list){
            this.grs = list;
            if (list.isEmpty()){
                noResult();
            }
            else{
                Clean();
            }
        }

        @Override
        public Fragment_crawling_3.RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crawilng_3_recycler, parent, false);
            return new Fragment_crawling_3.RecyclerAdapter.ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(Fragment_crawling_3.RecyclerAdapter.ViewHolder holder, int position) {
            RequestOptions requestOptions = new RequestOptions();

            GameResult gr = grs.get(position);
            if (gameResults.isEmpty()){

            }
            else {
                holder.team1.setText(gr.getTeam1());
                holder.team2.setText(gr.getTeam2());
                holder.score.setText(gr.getScore());
                Glide.with(holder.itemView).setDefaultRequestOptions(requestOptions).load(gr.getLogo1()).into(holder.logo1);
                Glide.with(holder.itemView).setDefaultRequestOptions(requestOptions).load(gr.getLogo2()).into(holder.logo2);
            }
        }

        @Override
        public int getItemCount() {
            return grs.size();
        }
    }

    public void noResult(){
        TextView noResult = (TextView)view.findViewById(R.id.noResult);
        noResult.setText("No Match");
    }

    public void Clean(){
        TextView noResult = (TextView)view.findViewById(R.id.noResult);
        noResult.setText("");
    }

    public class SportJsoup extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select("tbody._scroll_content > tr");
                gameResults = new ArrayList<>();
                for (Element e : elements ){
                    GameResult gameResult = new GameResult();

                    gameResult.setTeam1(e.select("td.l_team > span[class=txt_name txt_pit]").text());
                    gameResult.setTeam2(e.select("td.r_team > span[class=txt_name txt_pit]").text());
                    gameResult.setScore(e.select("td.score").text());
                    gameResult.setLogo1(e.select("td.l_team > a > img").attr("src"));
                    gameResult.setLogo2(e.select("td.r_team > a > img").attr("src"));

                    gameResults.add(gameResult);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids){
            RecyclerAdapter adapter = new RecyclerAdapter(gameResults);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}