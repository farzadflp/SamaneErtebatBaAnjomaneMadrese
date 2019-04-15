package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
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
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Inbox;
import com.example.samaneertebatbaanjomanemadrese.util.InboxJsonParser;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger.RequestData;

import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AppCompatImageView imageView;
    List<Inbox> inboxList;
    InboxAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    public static final String URL_INBOX = "http://192.168.1.38:8888/inbox.php";
    public Context inboxContext = InboxActivity.this;
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
        //sendParamsPost();
        myRequest();
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

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.inbox_recyclerview);
        imageView = (AppCompatImageView) findViewById(R.id.inbox_row_imgv_avatar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.inbox_swrl);

    }




    private void updatePage() {
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
    private void myRequest() {
        InboxTask task = new InboxTask();
        task.execute(getRequestData());
    }
    private RequestData getRequestData(){
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_INBOX);
        requestData.setMethod("POST");
        return requestData;
    }


    private class InboxTask extends AsyncTask<MyHttpManger.RequestData,Void,String> {
        private Context context = InboxActivity.this;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(MyHttpManger.RequestData... params) {
            MyHttpManger.RequestData uri = params[0];
            String content = MyHttpManger.getDataHttpURLConnection(uri , MyIntentHelper.getSessionId(context), MyIntentHelper.getSessionName(context));

            /*
            for (Inbox inboxItem :
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
            */
            return content;
        }

        @Override
        protected void onPostExecute(String response) {
            inboxList = new InboxJsonParser().InboxParseJson(response);
            showData(InboxActivity.this, inboxList);
        }
    }



}















