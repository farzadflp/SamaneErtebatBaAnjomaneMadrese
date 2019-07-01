package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.InboxActivity;
import com.example.samaneertebatbaanjomanemadrese.LoginActivity;
import com.example.samaneertebatbaanjomanemadrese.NewConversationActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.adapters.InboxAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class NewConversationTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private WeakReference<NewConversationActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private NewConversationActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private ActionProcessButton newConvBtn;
    private final static int ERROR,NORMAL,PROCESS,COMPLETE;
    static {
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
    }


    public NewConversationTask(NewConversationActivity context) {
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
            newConvBtn = activity.findViewById(R.id.new_conv_btn_send);
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            if (jsonResponse.length() == 0){
                errorOccurred();
            }else if (success) {
                successProcess();

            } else {
                unsuccessProcess();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void errorOccurred() {
        newConvBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();
            newConvBtn.setProgress(NORMAL);
            newConvBtn.setEnabled(true);
            newConvBtn.setClickable(true);
        }, 1500);
    }
    private void unsuccessProcess() {
        newConvBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG);
            newConvBtn.setProgress(NORMAL);
            newConvBtn.setEnabled(true);
            newConvBtn.setClickable(true);
            Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
        }, 1000);

    }

    private void successProcess() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            newConvBtn.setProgress(COMPLETE);
            Intent  intent = new Intent(activity , InboxActivity.class);
            intent.putExtra("parent", activity.getMyParent() );
            activity.finish();
            activity.startActivity(intent);
        }, 1000);
    }
}

