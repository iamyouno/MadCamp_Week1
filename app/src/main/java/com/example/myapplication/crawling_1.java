package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class crawling_1 extends AppCompatActivity {
//    ArrayList<String> crawling = new ArrayList<>();
//    TextView [] textViews = new TextView[10];
//    int [] textViewsId = {R.id.crawling_text1, R.id.crawling_text2, R.id.crawling_text3, R.id.crawling_text4, R.id.crawling_text5, R.id.crawling_text6, R.id.crawling_text7, R.id.crawling_text8, R.id.crawling_text9, R.id.crawling_text10};
    TextView tv;

    class RealTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                Document document = Jsoup.connect("https://datalab.naver.com/keyword/realtimeList.naver?age=all&entertainment=0&groupingLevel=0&marketing=0&news=0&sports=0").get();
                Elements elements = document.select("ul.ranking_list > li.ranking_item > div.item_box");
                for (Element e : elements) {
                    stringBuilder.append(e.select("span.item_num").text())
                            .append(".")
                            .append(e.select("span.item_title").text())
                            .append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            tv.setText(s);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawling_1);
//
        tv = (TextView)findViewById(R.id.textView5);

        Button button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new RealTask().execute();
            }
        });
    }
}


