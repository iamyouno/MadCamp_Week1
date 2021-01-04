package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class crawling_3 extends AppCompatActivity {

//    ArrayList<GameResult> gameResults = new ArrayList<>();
    ArrayList<GameResult> gameResults;
    RecyclerView recyclerView;

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawling_3);

        recyclerView = findViewById(R.id.recyclerView_sport);
        EditText editText = (EditText)findViewById(R.id.date);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch(actionId){
                    case EditorInfo.IME_ACTION_SEARCH:
                        break;
                    default:
                        String date = editText.getText().toString();
                        url = "https://search.daum.net/search?w=tot&q="+date.substring(0, 4)+"년%2"+date.substring(4, 6)+"월%2"+date.substring(6)+"일%20해외축구%20일정&DA=Z9T&rtmaxcoll=";
                        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        SportJsoup sportJsoup = new SportJsoup();
                        sportJsoup.execute();

                        return false;
                }
                return true;
            }
        });

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
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crawilng_3_recycler, parent, false);
            return new ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RequestOptions requestOptions = new RequestOptions();
            GameResult gr = grs.get(position);
            holder.team1.setText(gr.getTeam1());
            holder.team2.setText(gr.getTeam2());
            holder.score.setText(gr.getScore());
            Glide.with(holder.itemView).setDefaultRequestOptions(requestOptions).load(gr.getLogo1()).into(holder.logo1);
            Glide.with(holder.itemView).setDefaultRequestOptions(requestOptions).load(gr.getLogo2()).into(holder.logo2);

        }

        @Override
        public int getItemCount() {
            return grs.size();
        }
    }

    public class SportJsoup extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select("ul.list_game > li > div.wrap_game > div.team_cont > a.link_sports");
                gameResults = new ArrayList<>();
                for (Element e : elements ){

                    List<String> teams = e.select("div.team_item > div.info_item > div.team_name").eachText();
                    List<String> scores = e.select("div.team_item > div.score_info").eachText();
                    List<String> logos = e.select("div.team_item > div.info_item > div.team_logo > img[src $= png]").eachText();
                    GameResult gameResult = new GameResult();
                    gameResult.setScore(scores.get(0).substring(3, 4)+":"+scores.get(1).substring(3, 4));
                    gameResult.setTeam1(teams.get(0));
                    gameResult.setTeam2(teams.get(1));
                    gameResult.setLogo1(logos.get(0));
                    gameResult.setLogo2(logos.get(1));
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
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

}

