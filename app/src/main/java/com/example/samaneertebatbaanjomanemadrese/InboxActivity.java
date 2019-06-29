package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.example.samaneertebatbaanjomanemadrese.adapters.InboxAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.task.InboxTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger.RequestData;

public class InboxActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AppCompatImageView imageView;
    InboxAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout ;
    public static final String URL_INBOX;

    static {
        URL_INBOX = MyIntentHelper.URL_BASE + "parent/inbox.php";
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        MyHttpManger.initCooki();
        init();
        if (!MyHttpManger.isOnline(InboxActivity.this)){
            MyIntentHelper.alertDialogIsOffline(InboxActivity.this);
        } else {
            InboxRequest();
        }
        swipeRefreshLayoutSetOnRefreshListener();

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
        recyclerView = findViewById(R.id.inbox_recyclerview);
        imageView = findViewById(R.id.inbox_row_imgv_avatar);

    }




    private void swipeRefreshLayoutSetOnRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            finish();
            startActivity(getIntent());
        });
        swipeRefreshLayout.setRefreshing(false);

    }

    public InboxAdapter getAdapter() {
        return adapter;
    }

    private void InboxRequest() {
        InboxTask task = new InboxTask(this);
        task.execute(getConversationRequest());
    }
    private RequestData getConversationRequest(){
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_INBOX);
        requestData.setMethod("POST");
        return requestData;
    }




}















