package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.CommunityProfileActivity;
import com.example.samaneertebatbaanjomanemadrese.LoginActivity;
import com.example.samaneertebatbaanjomanemadrese.ManagerProfileActivity;
import com.example.samaneertebatbaanjomanemadrese.ParentProfileActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.VerificationActivity;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Community;
import com.example.samaneertebatbaanjomanemadrese.model.Manager;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.util.CommunityJsonParser;
import com.example.samaneertebatbaanjomanemadrese.util.ManagerJsonParser;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import com.example.samaneertebatbaanjomanemadrese.util.ParentJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


public class LoginTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final static int ERROR, NORMAL, PROCESS, COMPLETE;

    static {
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
    }

    private WeakReference<LoginActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private LoginActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private ActionProcessButton loginBtn;


        public LoginTask(LoginActivity context) {
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
            loginBtn = activity.findViewById(R.id.login_btn_login);
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
                    switch (activity.getRole()) {
                        case "p":
                            LoginParentProcess(response);
                            break;
                        case "c":
                            LoginCommunityProcess(response);
                            break;
                        case "m":
                            LoginManagerProcess(response);
                            break;
                    }
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

    private void LoginManagerProcess(String response) {
        Manager manager = new ManagerJsonParser().managerParseJson(response);
        loginBtn.setProgress(COMPLETE);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            loginBtn.setProgress(NORMAL);
            Intent  intent = new Intent(activity, ManagerProfileActivity.class);
            intent.putExtra("manager" , manager);
            activity.startActivity(intent);
            activity.finish();
            loginBtn.setEnabled(true);
            loginBtn.setClickable(true);
        }, 1500);
    }

    private void  LoginCommunityProcess(String response) {
        Community community = new CommunityJsonParser().CommunintyParseJson(response);
        loginBtn.setProgress(COMPLETE);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            loginBtn.setProgress(NORMAL);
            Intent  intent = new Intent(activity, CommunityProfileActivity.class);
            intent.putExtra("community" , community);
            activity.startActivity(intent);
            activity.finish();
            loginBtn.setEnabled(true);
            loginBtn.setClickable(true);
        }, 1500);
    }



    private void LoginParentProcess(String response) {
        Parent parent = new ParentJsonParser().parentParseJson(response);
        loginBtn.setProgress(COMPLETE);
        switch (parent.getVerified_by_m()) {
            case "n":
                notVerifiedByManager();
                return;
            case "u":
                unVerifiedByManager();
                break;
            case "v":
                //parent verified
                if (parent.getVerified() == 1) {
                    parentCanLogin(parent);
                } else {
                    unVerifiedPhoneNo(parent);
                }
                break;
        }
    }


    private void parentCanLogin(Parent parent) {

        loginBtn.setProgress(COMPLETE);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            loginBtn.setProgress(NORMAL);
            Intent  intent = new Intent(activity, ParentProfileActivity.class);
            intent.putExtra("parent" , parent);
            activity.startActivity(intent);
            activity.finish();
            loginBtn.setEnabled(true);
            loginBtn.setClickable(true);
        }, 1500);
    }

    private void unVerifiedPhoneNo(Parent parent) {
        loginBtn.setProgress(COMPLETE);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            loginBtn.setProgress(NORMAL);
            Intent  intent = new Intent(activity, VerificationActivity.class);
            intent.putExtra("parent" , parent);
            activity.startActivity(intent);
            activity.finish();
            loginBtn.setEnabled(true);
            loginBtn.setClickable(true);
        }, 1500);
    }

    private void unVerifiedByManager() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.unverified_information)
                .setCancelable(false)
                .setIcon(R.drawable.forbbiden)
                .setMessage(R.string.unverified_information_by_manager)
                .setNeutralButton(R.string.ok, (dialog, which) -> {
                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        loginBtn.setProgress(NORMAL);
                        loginBtn.setEnabled(true);
                        loginBtn.setClickable(true);
                    }, 1500);
                }).show();
    }

    private void notVerifiedByManager() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.not_verified)
                .setCancelable(false)
                .setIcon(R.drawable.warning)
                .setMessage(R.string.not_verified_by_manager)
                .setNeutralButton(R.string.ok, (dialog, which) -> {

                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        loginBtn.setProgress(NORMAL);
                        loginBtn.setEnabled(true);
                        loginBtn.setClickable(true);
                    }, 1500);
                }).show();
    }

    private void unsuccessProcess() {
        loginBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error_login, Toast.LENGTH_LONG).show();
            loginBtn.setProgress(NORMAL);
            loginBtn.setEnabled(true);
            loginBtn.setClickable(true);
        }, 1500);
        loginBtn.setProgress(ERROR);
    }

    private void errorOccurred() {
        loginBtn.setProgress(ERROR);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();
            loginBtn.setProgress(NORMAL);
            loginBtn.setEnabled(true);
            loginBtn.setClickable(true);
        }, 1500);
    }


}