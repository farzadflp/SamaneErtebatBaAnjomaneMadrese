package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.ChatActivity;
import com.example.samaneertebatbaanjomanemadrese.LoginActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.adapters.ChatAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Message;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class AddMessageToConversationTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final static int ERROR, NORMAL,COMPLETE;

    static {
        ERROR = -1;
        NORMAL = 0;
        COMPLETE = 100;
    }
    private WeakReference<ChatActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private ChatActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private ActionProcessButton sendBtn;
    @SuppressLint("StaticFieldLeak")
    private AppCompatEditText inputMsg;
    private ChatAdapter adapter;
    private Message newMessage;
    public AddMessageToConversationTask(ChatActivity context) {
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
        return MyHttpManger.getDataHttpURLConnection(uri, MyIntentHelper.getSessionId(activity)
                , MyIntentHelper.getSessionName(activity));
    }

    @Override
    protected void onPostExecute(String response) {
        try {
            activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            sendBtn = activity.findViewById(R.id.chat_btn_send);
            inputMsg = activity.findViewById(R.id.chat_et_msg);
            adapter = activity.getAdapter();
            newMessage = activity.getNewMessage();
            if (response == null) {
                errorOccurred();
            } else {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    successProcess();
                } else {
                    unsuccessProcess();
                }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void successProcess() {
        sendBtn.setProgress(COMPLETE);
        List<Message> msgList = adapter.getMsgList();
        msgList.add(newMessage);
        adapter.notifyItemInserted(adapter.getItemCount()-1);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            inputMsg.getText().clear();
            sendBtn.setProgress(NORMAL);
            sendBtn.setEnabled(true);
            sendBtn.setClickable(true);
        }, 1500);
    }

    private void unsuccessProcess() {
        Toast.makeText(activity , R.string.error_login , Toast.LENGTH_LONG).show();
        MyIntentHelper.clearSession(activity);
        inputMsg.getText().clear();
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }, 1500);

    }

    private void errorOccurred() {
        sendBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();
            sendBtn.setProgress(NORMAL);
            sendBtn.setEnabled(true);
            sendBtn.setClickable(true);
        }, 1500);
    }
}
