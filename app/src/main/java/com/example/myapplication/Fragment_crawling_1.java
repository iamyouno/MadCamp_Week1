package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fragment_crawling_1 extends Fragment {

    int [] textViewsId = {R.id.crawling_text1, R.id.crawling_text2, R.id.crawling_text3, R.id.crawling_text4, R.id.crawling_text5, R.id.crawling_text6, R.id.crawling_text7, R.id.crawling_text8, R.id.crawling_text9, R.id.crawling_text10,
            R.id.crawling_text11, R.id.crawling_text12, R.id.crawling_text13, R.id.crawling_text14, R.id.crawling_text15, R.id.crawling_text16, R.id.crawling_text17, R.id.crawling_text18, R.id.crawling_text19, R.id.crawling_text20};

    RealTask realTask = new RealTask();
    View view;

    public static Fragment_crawling_1 newInstance(){
        return new Fragment_crawling_1();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_crawling_1, container, false);
        realTask.stop = true;
        realTask.execute();
        return view;
    }

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
                TextView textView = (TextView)view.findViewById(textViewsId[i]);
                textView.setText(crawling[i]);

                Linkify.TransformFilter filter = new Linkify.TransformFilter() {
                    @Override
                    public String transformUrl(Matcher match, String url) {
                        return "";
                    }
                };

                Pattern pattern = Pattern.compile(crawling[i]);
                Linkify.addLinks(textView, pattern, "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query="+crawling[i].substring(i/10+2), null, filter);
                stripUnderlines(textView);
            }
            super.onProgressUpdate();
        }

        @Override
        protected void onCancelled(Boolean s){
            this.stop = !stop;
            super.onCancelled(s);
        }
    }

    // for underline
    private void stripUnderlines(TextView textView) {
        Spannable s = new SpannableString(textView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span: spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }

    public class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }
    // for underline

    @Override
    public void onDestroyView(){
        realTask.stop = false;
        super.onDestroyView();
    }

}