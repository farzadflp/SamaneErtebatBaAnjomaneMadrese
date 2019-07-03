package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.ParentProfileActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.VerificationActivity;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class VerificationTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final static int ERROR, NORMAL, PROCESS, COMPLETE;

    static {
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
    }

    private WeakReference<VerificationActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private VerificationActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private ActionProcessButton submitBtn;


    public VerificationTask(VerificationActivity context) {
        activityReference = new WeakReference<>(context);
    }

    public WeakReference<VerificationActivity> getActivityReference() {
        return activityReference;
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
            submitBtn = activity.findViewById(R.id.verification_btn_submit);
            submitBtn.setProgress(PROCESS);
            Parent parent = activity.getMyParent();
            if (response == null) {
                errorOccurred();
            } else {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    try {
                        MyIntentHelper.writeSession(activity
                                , jsonResponse.getString("sess_id")
                                , jsonResponse.getString("sess_name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    successProcess(parent);
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

    private void successProcess(Parent parent) {
        submitBtn.setProgress(COMPLETE);
        parent.setVerified(1);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            submitBtn.setProgress(NORMAL);
            Intent intent = new Intent(activity, ParentProfileActivity.class);
            intent.putExtra("parent" , parent);
            activity.startActivity(intent);
            activity.finish();
            submitBtn.setEnabled(true);
            submitBtn.setClickable(true);
        }, 1500);

    }

    private void unsuccessProcess() {
        submitBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.wrong_verification_code, Toast.LENGTH_LONG).show();
            submitBtn.setProgress(NORMAL);
            submitBtn.setEnabled(true);
            submitBtn.setClickable(true);
        }, 1500);
        submitBtn.setProgress(ERROR);
    }
    private void errorOccurred() {
        submitBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();
            submitBtn.setProgress(NORMAL);
            submitBtn.setEnabled(true);
            submitBtn.setClickable(true);
        }, 1500);
    }
}
