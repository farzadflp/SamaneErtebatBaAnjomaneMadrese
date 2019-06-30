package com.example.samaneertebatbaanjomanemadrese;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.adapters.ChatAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Message;
import com.example.samaneertebatbaanjomanemadrese.task.AddMessageToConversationTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AppCompatTextView topicTV;
    AppCompatEditText inputMsg;
    private List<Message> msgList;
    private Message newMessage ;
    private ChatAdapter adapter;
    private ActionProcessButton sendBtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String URL_ADD_MESSAGE;
    private final static int ERROR, NORMAL, PROCESS, COMPLETE;

    static {
        URL_ADD_MESSAGE = MyIntentHelper.URL_BASE + "parent/add_msg.php";
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
    }

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
        showData();
        sendBtnSetOnClickListener();
    }

    private void sendBtnSetOnClickListener() {
        sendBtn.setOnClickListener(v -> {
            String msg = inputMsg.getText().toString().trim();
            if (!msg.isEmpty()){
                sendBtn.setProgress(PROCESS);
                sendBtn.setEnabled(false);
                sendBtn.setClickable(false);
                newMessage = new Message(msg ,msgList.get(0).getIdConversation() );
                addMessageRequest();
            }

        });
    }

    private void addMessageRequest() {
        AddMessageToConversationTask task = new AddMessageToConversationTask(this);
        task.execute(addMessageRequestData());
    }

    private MyHttpManger.RequestData addMessageRequestData() {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_ADD_MESSAGE);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("id_conversation", String.valueOf(newMessage.getIdConversation()));
        params.put("msg", newMessage.getMsg() );
        requestData.setParams(params);
        return requestData;
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
        recyclerView = findViewById(R.id.chat_recyclerview);
        swipeRefreshLayout = findViewById(R.id.inbox_swrl);
        sendBtn = findViewById(R.id.chat_btn_send);
        topicTV = findViewById(R.id.chat_tv_topic);
        inputMsg = findViewById(R.id.chat_et_msg);
        sendBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        Bundle extras = getIntent().getExtras();
        ArrayList<Message> msgArray =  getIntent().getParcelableArrayListExtra("msg");
        msgList = msgArray;
        if (extras != null){
            if (extras.containsKey("topic")){
                topicTV.setText(extras.getString("topic"));
            }
        }
    }

    private void showData() {
        adapter = new ChatAdapter(msgList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    public ChatAdapter getAdapter() {
        return adapter;
    }

    public Message getNewMessage() {
        return newMessage;
    }

}
