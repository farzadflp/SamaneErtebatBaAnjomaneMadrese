package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.samaneertebatbaanjomanemadrese.adapters.InboxAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Inbox;

import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AppCompatImageView imageView;
    List<Inbox> inboxList;
    InboxAdapter adapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase , "fa"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        recyclerView = (RecyclerView) findViewById(R.id.inbox_recyclerview);
        imageView = (AppCompatImageView) findViewById(R.id.inbox_row_imgv_avatar);
        prepareData();
        showData();

    }

    private void showData() {
        adapter = new InboxAdapter(inboxList);
        recyclerView.setLayoutManager(new LinearLayoutManager(InboxActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

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
}
