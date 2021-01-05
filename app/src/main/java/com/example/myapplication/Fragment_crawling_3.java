package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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

    ArrayList<GameResult> gameResults;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recyclerView_sport);
        Button button = view.findViewById(R.id.datePicker);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                TextView textView = (TextView)view.findViewById(R.id.noResult);
                textView.setText("");
                selectDate();
            }
        });
        return inflater.inflate(R.layout.activity_crawling_3, container, false);
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

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        private ArrayList<GameResult> grs;

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

        public RecyclerAdapter(ArrayList<GameResult> list){
            this.grs = list;
            if (list.isEmpty()){
                noResult();
            }
            else{
                Clean();
            }
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crawilng_3_recycler, parent, false);
            return new RecyclerAdapter.ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
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