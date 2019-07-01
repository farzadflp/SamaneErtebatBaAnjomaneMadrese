package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.samaneertebatbaanjomanemadrese.InboxActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.adapters.InboxAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Inbox;
import com.example.samaneertebatbaanjomanemadrese.util.InboxJsonParser;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import java.lang.ref.WeakReference;
import java.util.List;

public class  InboxTask extends AsyncTask<MyHttpManger.RequestData,Void,String> {
    private WeakReference<InboxActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private InboxActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerView;

    public InboxTask(InboxActivity context) {
        activityReference = new WeakReference<>(context);
    }
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(MyHttpManger.RequestData... params) {
        MyHttpManger.RequestData uri = params[0];
        activity = activityReference.get();
        if (activity == null || activity.isFinishing()) {
            return null;
        }
        return MyHttpManger.getDataHttpURLConnection(uri , MyIntentHelper.getSessionId(activity), MyIntentHelper.getSessionName(activity));
    }

    @Override
    protected void onPostExecute(String response) {
        activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return;
        recyclerView = activity.findViewById(R.id.inbox_recyclerview);
        List<Inbox> inboxList = new InboxJsonParser().InboxParseJson(response);
        showData(activity, inboxList);
    }
    private void showData(Context context, List<Inbox> inboxList) {
        InboxAdapter adapter = new InboxAdapter(inboxList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
