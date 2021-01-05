package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Stream;


public class Fragment_crawling_2 extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<Fragment_crawling_2.NewsModel> list;
    SwipeRefreshLayout mSwipeRefreshLayout;



    public static Fragment_crawling_2 newInstance(){
        return new Fragment_crawling_2();
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_crawling_2, container, false);

        //SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //여기서 새로고침
                new Fragment_crawling_2.Description().execute();

                //새로고침 아이콘 삭제
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //AsyncTask 작동시키기(파싱)
        new Fragment_crawling_2.Description().execute();

        ImageButton crawling_1 = (ImageButton) view.findViewById(R.id.crawling_1_2);
        crawling_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //프래그먼트 새로 만들어서 그 프래그먼트 보여주도록
                Fragment importFragment = new Fragment_crawling_1();

                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_crawling_2, importFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.e("sdf","sdf");
            }
        });

        ImageButton crawling_3 = (ImageButton) view.findViewById(R.id.crawling_3_2);
        crawling_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //프래그먼트 새로 만들어서 그 프래그먼트 보여주도록
                Fragment importFragment = new Fragment_crawling_3();

                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_crawling_2, importFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.e("sdf","sdf");
            }
        });


        return view;

    }

    private class Description extends AsyncTask<Void, Void, Void> {
//        //진행바 표시
//        private ProgressDialog progressDialog;

//        @Override
//        protected void onPreExecute(){
//            super.onPreExecute();
//
////            //진행 다이얼로그 시작
////            progressDialog = new ProgressDialog(getActivity());
////            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            progressDialog.setMessage("Please Wait");
////            progressDialog.show();
//        }

        @Override
        protected Void doInBackground(Void... params){
            list = new ArrayList<>();
            try{
                Document doc = Jsoup.connect("https://news.naver.com/").get();
                //필요 내용만 지정
                Elements mElementDataSize_img_section = doc.select("div[class=main_content_inner _content_inner] div[class=main_component droppable]");
                //목록 몇개인지 알아내서 그 크기만큼 루프 돌려야함

                for(Element elem_i_s: mElementDataSize_img_section){
                    //뉴스 목록에서 이미지, 섹션 데이터 추출함
                    String news_section = elem_i_s.select("div[class=com_header] h4 a").text();

                    String news_imgUrl = elem_i_s.select("div[class=com_list] dl[class=mtype_img] dt a img").attr("src");

                    List<String> news_title_ = elem_i_s.select("div[class=com_list] div[class=mtype_list_wide] ul[class=mlist2 no_bg] li a").eachText();
                    List<String> news_title_URL = elem_i_s.select("div[class=com_list] div[class=mtype_list_wide] ul[class=mlist2 no_bg] li a").eachAttr("href");

                    String news_imgTitle = elem_i_s.select("div[class=com_list] dl[class=mtype_img] dd a").text();
                    String news_imgTitle_URL = elem_i_s.select("div[class=com_list] dl[class=mtype_img] dd a").attr("href");

                    Fragment_crawling_2.NewsModel nm_i_s = new Fragment_crawling_2.NewsModel();
                    nm_i_s.setNews_section(news_section);

                    nm_i_s.setNews_imgUrl(news_imgUrl);

                    nm_i_s.setNews_title(news_title_);
                    nm_i_s.setNews_title_URL(news_title_URL);

                    nm_i_s.setNews_imgTitle(news_imgTitle);
                    nm_i_s.setNews_imgTitle_URL(news_imgTitle_URL);


                    list.add(nm_i_s);

                }

            }
            catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected  void onPostExecute(Void result){
            //ArrayList adapter랑 연결
            NewsAdapter newsAdapter_img_section = new NewsAdapter(list);
//            NewsAdapter newsAdapter_title = new NewsAdapter(list_title);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(newsAdapter_img_section);
//            recyclerView.setAdapter(newsAdapter_title);

//            progressDialog.dismiss();
        }


    }

    public class NewsModel{
        private String news_section;

        private String news_imgUrl;

        private String news_imgTitle;
        private String news_imgTitle_URL;

        private List<String> news_title;
        private List<String> news_title_URL;


//        Linkify.TransformFilter mTransform = new Linkify.TransformFilter(){
//            @Override
//            public String transformUrl(Matcher match, String url){
//                return "";
//            }
//        };

        public String getNews_section() { return news_section; }
        public void setNews_section(String news_section) { this.news_section = news_section; }

        public String getNews_imgUrl() { return news_imgUrl; }
        public void setNews_imgUrl(String news_imgUrl) { this.news_imgUrl = news_imgUrl; }

        public List<String> getNews_title() { return news_title; }
        public void setNews_title(List<String> news_title_) { this.news_title = news_title_; }

        public List<String> getNews_title_URL() { return news_title_URL; }
        public void setNews_title_URL(List<String> news_title_URL) { this.news_title_URL = news_title_URL; }

        public String getNews_imgTitle() { return news_imgTitle; }
        public void setNews_imgTitle(String news_imgTitle) { this.news_imgTitle = news_imgTitle; }
        public String getNews_imgTitle_URL() { return news_imgTitle_URL; }
        public void setNews_imgTitle_URL(String news_imgTitle_URL) { this.news_imgTitle_URL = news_imgTitle_URL; }
    }


    public class NewsAdapter extends RecyclerView.Adapter<Fragment_crawling_2.NewsAdapter.ViewHolder>{
        //데이터 배열 선언
        private ArrayList<Fragment_crawling_2.NewsModel> list_i_s;
//        private ArrayList<NewsModel> list_t;

        public class ViewHolder extends RecyclerView.ViewHolder{
            private TextView news_section;
            private TextView news_title_1, news_title_2, news_title_3, news_title_4, news_title_5;
            private ImageView news_imgUrl;
            private TextView news_img_title;

            public ViewHolder(View itemView){
                super(itemView);

                news_section = (TextView) itemView.findViewById(R.id.news_section);

                news_imgUrl = (ImageView) itemView.findViewById(R.id.news_img);

                news_title_1 = (TextView) itemView.findViewById(R.id.news_title_1);
                news_title_2 = (TextView) itemView.findViewById(R.id.news_title_2);
                news_title_3 = (TextView) itemView.findViewById(R.id.news_title_3);
                news_title_4 = (TextView) itemView.findViewById(R.id.news_title_4);
                news_title_5 = (TextView) itemView.findViewById(R.id.news_title_5);

                news_img_title = (TextView) itemView.findViewById(R.id.news_img_title);



            }
        };

        //생성자
        public NewsAdapter(ArrayList<Fragment_crawling_2.NewsModel> list){
            this.list_i_s = list;
            //list_i_s에서 첫번재 인덱스 비어있어서 제거함
            list_i_s.remove(0);
        }



        @NonNull
        @Override
        public Fragment_crawling_2.NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crawling_chart, parent, false);
            return new Fragment_crawling_2.NewsAdapter.ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(@NonNull Fragment_crawling_2.NewsAdapter.ViewHolder holder, int position){

            RequestOptions requestOptions = new RequestOptions();
            Stream<String> stringStream_1 = list_i_s.get(position).getNews_title().stream();
            Stream<String> stringStream_2 = list_i_s.get(position).getNews_title().stream();
            Stream<String> stringStream_3 = list_i_s.get(position).getNews_title().stream();
            Stream<String> stringStream_4 = list_i_s.get(position).getNews_title().stream();
            Stream<String> stringStream_5 = list_i_s.get(position).getNews_title().stream();


            stringStream_5.forEachOrdered(item_5 -> holder.news_title_5.setText(item_5));
            stringStream_4.limit(4).forEachOrdered(item_4 -> holder.news_title_4.setText(item_4));
            stringStream_3.limit(3).forEachOrdered(item_3 -> holder.news_title_3.setText(item_3));
            stringStream_2.limit(2).forEachOrdered(item_2 -> holder.news_title_2.setText(item_2));
            stringStream_1.limit(1).forEachOrdered(item_1 -> holder.news_title_1.setText(item_1));

            holder.news_img_title.setText(list_i_s.get(position).getNews_imgTitle());

            holder.news_section.setText(list_i_s.get(position).getNews_section());
            Glide.with(holder.itemView).setDefaultRequestOptions(requestOptions).load(list_i_s.get(position).getNews_imgUrl()).into(holder.news_imgUrl);


            holder.news_img_title.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(list_i_s.get(position).getNews_imgTitle_URL()));
                    startActivity(intent);
                }
            });

            holder.news_title_1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(list_i_s.get(position).getNews_title_URL().get(0)));
                    startActivity(intent);
                }
            });

            holder.news_title_2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(list_i_s.get(position).getNews_title_URL().get(1)));
                    startActivity(intent);
                }
            });

            holder.news_title_3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(list_i_s.get(position).getNews_title_URL().get(2)));
                    startActivity(intent);
                }
            });

            holder.news_title_4.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(list_i_s.get(position).getNews_title_URL().get(3)));
                    startActivity(intent);
                }
            });

            holder.news_title_5.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(list_i_s.get(position).getNews_title_URL().get(4)));
                    startActivity(intent);
                }
            });

        }


        @Override
        public int getItemCount(){
            return list_i_s.size();
        }


    }

}