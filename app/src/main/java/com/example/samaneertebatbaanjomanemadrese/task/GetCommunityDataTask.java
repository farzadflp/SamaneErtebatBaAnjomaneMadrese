package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.samaneertebatbaanjomanemadrese.GetCommunityActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.adapters.CommunityAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Community;
import com.example.samaneertebatbaanjomanemadrese.util.CommunityJsonParser;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetCommunityDataTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final WeakReference<GetCommunityActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private GetCommunityActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerView;
    private AppCompatTextView emptyTv;


    public GetCommunityDataTask(GetCommunityActivity context) {
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
        recyclerView = activity.findViewById(R.id.get_community_rc);
        emptyTv = activity.findViewById(R.id.get_community_tv);
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
        ArrayList<Community> communities = new CommunityJsonParser().CommunintiesDataParseJson(response);
        CommunityAdapter adapter = new CommunityAdapter(communities);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }
    private void unsuccessProcess() {
        recyclerView.setVisibility(View.GONE);
        emptyTv = activity.findViewById(R.id.get_community_tv);
        emptyTv.setVisibility(View.VISIBLE);
    }

    private void errorOccurred() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();

        }, 1500);
    }
}
