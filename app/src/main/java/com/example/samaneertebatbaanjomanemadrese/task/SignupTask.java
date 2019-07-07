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
    private final static int ERROR, NORMAL, PROCESS, COMPLETE , DELAY;

    static {
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
        DELAY = 2000;
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
        return MyHttpManger.getDataHttpURLConnection(uri , "" , "");
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
                unsuccessProcess(response);
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
        }, DELAY);
    }
    private void unsuccessProcess(String response) {
        signupBtn.setProgress(ERROR);
        JSONObject jsonResponse = null;
        try {
            jsonResponse = new JSONObject(response);
            boolean userExist = jsonResponse.getBoolean("user_exist");
            boolean phoneNoExist = jsonResponse.getBoolean("phone_no_exist");
            if (userExist){
                Toast.makeText(activity, R.string.warning_user_exist, Toast.LENGTH_LONG).show();
            }else if (phoneNoExist){
                Toast.makeText(activity, R.string.warning_user_phone_no, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            signupBtn.setProgress(NORMAL);
            signupBtn.setEnabled(true);
            signupBtn.setClickable(true);
        }, DELAY);

    }

    private void successProcess() {

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            signupBtn.setProgress(COMPLETE);
            activity.startActivity(new Intent(activity, LoginActivity.class));
            activity.finish();
        }, DELAY);
    }
}