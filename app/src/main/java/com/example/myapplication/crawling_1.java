package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;


public class crawling_1 extends AppCompatActivity {

    int [] textViewsId = {R.id.crawling_text1, R.id.crawling_text2, R.id.crawling_text3, R.id.crawling_text4, R.id.crawling_text5, R.id.crawling_text6, R.id.crawling_text7, R.id.crawling_text8, R.id.crawling_text9, R.id.crawling_text10,
                R.id.crawling_text11, R.id.crawling_text12, R.id.crawling_text13, R.id.crawling_text14, R.id.crawling_text15, R.id.crawling_text16, R.id.crawling_text17, R.id.crawling_text18, R.id.crawling_text19, R.id.crawling_text20};

    RealTask realTask = new RealTask();

    class RealTask extends AsyncTask<Void, Void, Boolean> {
        String[] crawling = new String[20];
        boolean stop = true;

        @Override
        protected Boolean doInBackground(Void... voids) {
            while (stop) {
                try {
                    Document document = Jsoup.connect("https://datalab.naver.com/keyword/realtimeList.naver?age=all&entertainment=0&groupingLevel=0&marketing=0&news=0&sports=0").get();
                    Elements elements = document.select("ul.ranking_list > li.ranking_item > div.item_box");
                    int i = 0;
                    for (Element e : elements) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(e.select("span.item_num").text())
                                .append(".")
                                .append(e.select("span.item_title").text());
                        crawling[i] = stringBuilder.toString();
                        i++;
                    }
//                    Thread.sleep(100);
                    publishProgress();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Void... voids){
            for (int i=0; i<20; i++){
                TextView textView = (TextView)findViewById(textViewsId[i]);
                textView.setText(crawling[i]);
            }
            super.onProgressUpdate();
        }

        @Override
        protected void onCancelled(Boolean s){
            this.stop = !stop;
            super.onCancelled(s);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawling_1);
        realTask.stop = true;
        realTask.execute();

    }

    @Override
    protected void onDestroy(){
        realTask.stop = false;
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }
}


