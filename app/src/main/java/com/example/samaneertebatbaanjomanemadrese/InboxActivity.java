package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.samaneertebatbaanjomanemadrese.adapters.InboxAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Inbox;
import com.example.samaneertebatbaanjomanemadrese.util.InboxJsonParser;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AppCompatImageView imageView;
    List<Inbox> inboxList;
    InboxAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    public static final String URL_BASE = "http://192.168.43.246:8888/";
    public static final String URL_INBOXJSON = "http://192.168.43.246:8888/inbox.json";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase , "fa"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        //  prepareData();
        InboxTask  task = new InboxTask();
        task.execute(URL_INBOXJSON);
        updatePageLister();




    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.inbox_recyclerview);
        imageView = (AppCompatImageView) findViewById(R.id.inbox_row_imgv_avatar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.inbox_swrl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updatePageLister() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                finish();
                startActivity(getIntent());
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }


    private void showData(Context context, List<Inbox> inboxList) {
        adapter = new InboxAdapter(inboxList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
    private void showData(ArrayList<Inbox> inboxes) {
        adapter = new InboxAdapter(inboxList);
        recyclerView.setLayoutManager(new LinearLayoutManager(InboxActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private class InboxTask extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String uri = params[0];
            String content = MyHttpManger.getDataHttpClient(uri);
            inboxList = new InboxJsonParser().parseJson(content);
            for (Inbox inboxItem:
                 inboxList) {
                try {
                    URL url = new URL(inboxItem.getAvatarPath());
                    InputStream instream = (InputStream) url.getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(instream);
                    inboxItem.setBitmap(bitmap);
                    instream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }

            return content;
        }

        @Override
        protected void onPostExecute(String result) {
           // inboxList = new InboxJsonParser().parseJson(result);
            showData(InboxActivity.this , inboxList);
        }
    }

}














/*
    private void prepareData() {
        if (inboxList == null) {
            inboxList = new ArrayList<>();
        } else {
            inboxList.clear();
        }
        inboxList.add(new Inbox( "فرزاد فلاح پور", imageView));
        inboxList.add(new Inbox( "پارسا محمودی", imageView));
        inboxList.add(new Inbox( "آریا روحانی", imageView));
        inboxList.add(new Inbox( "شایان شایگانی", imageView));
        inboxList.add(new Inbox( "امیر خیری", imageView));
        inboxList.add(new Inbox( "فرزاد فلاح پور", imageView));
        inboxList.add(new Inbox( "پارسا محمودی", imageView));
        inboxList.add(new Inbox( "آریا روحانی", imageView));
        inboxList.add(new Inbox( "شایان شایگانی", imageView));
        inboxList.add(new Inbox( "امیر خیری", imageView));
        inboxList.add(new Inbox( "فرزاد فلاح پور", imageView));
        inboxList.add(new Inbox( "پارسا محمودی", imageView));
        inboxList.add(new Inbox( "آریا روحانی", imageView));
        inboxList.add(new Inbox( "شایان شایگانی", imageView));
        inboxList.add(new Inbox( "امیر خیری", imageView));
        inboxList.add(new Inbox( "فرزاد فلاح پور", imageView));
        inboxList.add(new Inbox( "پارسا محمودی", imageView));
        inboxList.add(new Inbox( "آریا روحانی", imageView));
        inboxList.add(new Inbox( "شایان شایگانی", imageView));
        inboxList.add(new Inbox( "امیر خیری", imageView));
        inboxList.add(new Inbox( "فرزاد فلاح پور", imageView));
        inboxList.add(new Inbox( "پارسا محمودی", imageView));
        inboxList.add(new Inbox( "آریا روحانی", imageView));
        inboxList.add(new Inbox( "شایان شایگانی", imageView));
        inboxList.add(new Inbox( "امیر خیری", imageView));
    }
    */
