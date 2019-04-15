package com.example.samaneertebatbaanjomanemadrese;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.adapters.ChatAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AppCompatImageView imageView;
    private AppCompatTextView topicTV;
    private ArrayList<Message> msgArray;
    private List<Message> msgList;
    private ChatAdapter adapter;
    private ActionProcessButton sendBtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String URL_BASE = "http://192.168.1.34:8888/";
    public static final String URL_CHATJSON = "http://192.168.1.34:8888/chat.json";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "fa"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        Bundle extras = getIntent().getExtras();
        msgArray =  getIntent().getParcelableArrayListExtra("msg");
        msgList = msgArray;
        if (extras != null){
            if (extras.containsKey("topic")){
                topicTV.setText(extras.getString("topic"));

            }
        }
        showData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.chat_recyclerview);
        imageView = (AppCompatImageView) findViewById(R.id.msg_row_tv_msg);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.inbox_swrl);
        sendBtn = (ActionProcessButton) findViewById(R.id.chat_btn_send);
        topicTV = (AppCompatTextView) findViewById(R.id.chat_tv_topic);
    }

    private void showData() {
        adapter = new ChatAdapter(msgList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}
