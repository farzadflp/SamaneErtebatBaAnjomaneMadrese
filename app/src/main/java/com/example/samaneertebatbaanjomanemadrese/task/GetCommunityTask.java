package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.Toast;

import com.example.samaneertebatbaanjomanemadrese.NewConversationActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.adapters.GetCommunityAdapter;
import com.example.samaneertebatbaanjomanemadrese.adapters.SpinnerAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Community;
import com.example.samaneertebatbaanjomanemadrese.util.CommunityJsonParser;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetCommunityTask  extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final WeakReference<NewConversationActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private NewConversationActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private AppCompatSpinner communitySPN;


    public GetCommunityTask(NewConversationActivity context) {
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
        activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return;
        communitySPN = activity.findViewById(R.id.new_conv_spn_send_to);
        if (response == null) {
            errorOccurred();
        } else {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = false;
                success = jsonResponse.getBoolean("success");
                if (success) {
                    successProcess(response);
                } else {
                    unsuccessProcess();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void successProcess(String response) {
        ArrayList<Community> communities = new CommunityJsonParser().CommunintiesParseJson(response);
        String[] firstname = new String[communities.size()+1];
        String[] lastname = new String[communities.size()+1];
        String[] post = new String[communities.size()+1];
        String[] username = new String[communities.size()+1];
        firstname[0] = "-------------";
        lastname[0] = "-------------";
        post[0] = "-------------";
        username[0] = "-------------";

        for (int i = 1 ; i < communities.size()+1; i++){
            firstname[i] = communities.get(i-1).getFirstname();
            lastname[i] = communities.get(i-1).getLastname();
            post[i] = communities.get(i-1).getPost();
            username[i] = communities.get(i-1).getUsername();
        }
        GetCommunityAdapter adapter = new GetCommunityAdapter(activity , firstname , lastname , post , username);
        communitySPN.setAdapter(adapter);

    }
    private void unsuccessProcess() {
        String[]  strings = new String[1];
        strings[0] = activity.getString(R.string.empty_city);
        SpinnerAdapter cityAdapter = new SpinnerAdapter(activity , strings);
        communitySPN.setAdapter(cityAdapter);

    }

    private void errorOccurred() {
        Toast.makeText(activity , R.string.error , Toast.LENGTH_LONG).show();
    }
}
