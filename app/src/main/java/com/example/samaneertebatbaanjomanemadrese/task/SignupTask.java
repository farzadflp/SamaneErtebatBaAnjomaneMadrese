package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.LoginActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.SignupActivity;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class SignupTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final static int ERROR, NORMAL, PROCESS, COMPLETE;

    static {
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
    }

    private WeakReference<SignupActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private SignupActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private ActionProcessButton signupBtn;


    public SignupTask(SignupActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(MyHttpManger.RequestData... params) {
        MyHttpManger.RequestData uri = params[0];
        String content = MyHttpManger.getDataHttpURLConnection(uri , "" , "");
        return content;
    }

    @Override
    protected void onPostExecute(String response) {
        try {
            activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            signupBtn = activity.findViewById(R.id.signup_btn_signup);
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
        signupBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();
            signupBtn.setProgress(NORMAL);
            signupBtn.setEnabled(true);
            signupBtn.setClickable(true);
        }, 1500);
    }
    private void unsuccessProcess() {
        signupBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG);
            signupBtn.setProgress(NORMAL);
            signupBtn.setEnabled(true);
            signupBtn.setClickable(true);
        }, 1000);

    }

    private void successProcess() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            signupBtn.setProgress(COMPLETE);
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
        }, 1000);
    }
}