package com.example.samaneertebatbaanjomanemadrese.task;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;
import com.example.samaneertebatbaanjomanemadrese.ChatActivity;
import com.example.samaneertebatbaanjomanemadrese.InboxActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.adapters.InboxAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Message;
import com.example.samaneertebatbaanjomanemadrese.util.MessageJsonParser;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetMessageTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private WeakReference<InboxActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private InboxActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private String topicSelected;
    private InboxAdapter adapter;
    private int selectedPosition;
    public GetMessageTask(InboxActivity context) {
        activityReference = new WeakReference<>(context);
    }
    @Override
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
        return MyHttpManger.getDataHttpURLConnection(uri
                , MyIntentHelper.getSessionId(activity)
                , MyIntentHelper.getSessionName(activity));
    }

    @Override
    protected void onPostExecute(String response) {
            activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            adapter = activity.getAdapter();
            topicSelected = adapter.getTopicSelected();
            selectedPosition = adapter.getSelectedPosition();
        if (response == null) {
            errorOccurred();
        } else {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                ArrayList<Message> msgs = new MessageJsonParser().messageParseJson(response);
                if (success) {
                    successProcess(msgs, topicSelected);
                } else {
                    unsuccessProcess();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private void unsuccessProcess() {
        Toast.makeText(activity , R.string.error , Toast.LENGTH_LONG).show();
    }

    private void successProcess(ArrayList<Message> msgs , String topic) {
        Intent intent = new Intent(activity , ChatActivity.class);
        intent.putExtra("msg", msgs);
        intent.putExtra("topic" , topic);
        this.selectedPosition = -1;
        adapter.notifyDataSetChanged();
        activity.startActivity(intent);
    }
    private void errorOccurred() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show(), 1500);
    }
}


