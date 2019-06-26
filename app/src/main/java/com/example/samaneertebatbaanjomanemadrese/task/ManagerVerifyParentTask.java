package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatImageView;
import com.example.samaneertebatbaanjomanemadrese.ManagerVerifyParentActivity;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import java.lang.ref.WeakReference;

public class ManagerVerifyParentTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private WeakReference<ManagerVerifyParentActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private ManagerVerifyParentActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private AppCompatImageView verified , unverified;
    public ManagerVerifyParentTask(ManagerVerifyParentActivity context) {
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
        super.onPostExecute(response);
        activity = activityReference.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }


    }
}
