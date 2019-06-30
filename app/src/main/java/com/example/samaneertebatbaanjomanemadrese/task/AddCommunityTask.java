package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.LoginActivity;
import com.example.samaneertebatbaanjomanemadrese.ManagerAddCommunityActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class AddCommunityTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final static int ERROR, NORMAL, PROCESS, COMPLETE;

    static {
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
    }
    private WeakReference<ManagerAddCommunityActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private ManagerAddCommunityActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private ActionProcessButton addCommunityBtn;


    public AddCommunityTask(ManagerAddCommunityActivity context) {
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
            addCommunityBtn = activity.findViewById(R.id.add_communityـbtn_add);
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
        addCommunityBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();
            addCommunityBtn.setProgress(NORMAL);
            addCommunityBtn.setEnabled(true);
            addCommunityBtn.setClickable(true);
        }, 1500);
    }
    private void unsuccessProcess() {
        addCommunityBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG);
            addCommunityBtn.setProgress(NORMAL);
            addCommunityBtn.setEnabled(true);
            addCommunityBtn.setClickable(true);
        }, 1000);

    }

    private void successProcess() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            addCommunityBtn.setProgress(COMPLETE);
            addCommunityBtn.setProgress(NORMAL);
            addCommunityBtn.setEnabled(true);
            addCommunityBtn.setClickable(true);
            activity.finish();
        }, 1000);
    }
}
