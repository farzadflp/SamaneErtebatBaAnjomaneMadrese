package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.EditCommunityActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class EditCommunityTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final static int ERROR, NORMAL, PROCESS, COMPLETE;

    static {
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
    }
    private WeakReference<EditCommunityActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private EditCommunityActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private ActionProcessButton EditCommunityBtn;


    public EditCommunityTask(EditCommunityActivity context) {
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
            EditCommunityBtn = activity.findViewById(R.id.edit_community_btn_add);
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
        EditCommunityBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();
            EditCommunityBtn.setProgress(NORMAL);
            EditCommunityBtn.setEnabled(true);
            EditCommunityBtn.setClickable(true);
        }, 1500);
    }
    private void unsuccessProcess() {
        EditCommunityBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG);
            EditCommunityBtn.setProgress(NORMAL);
            EditCommunityBtn.setEnabled(true);
            EditCommunityBtn.setClickable(true);
        }, 1000);

    }

    private void successProcess() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            EditCommunityBtn.setProgress(COMPLETE);
            EditCommunityBtn.setProgress(NORMAL);
            EditCommunityBtn.setEnabled(true);
            EditCommunityBtn.setClickable(true);
            activity.finish();
        }, 1000);
    }
}
