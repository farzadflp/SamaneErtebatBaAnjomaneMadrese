package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.samaneertebatbaanjomanemadrese.ManagerVerifyParentActivity;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.adapters.VerifyAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class ManagerVerifyParentTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final WeakReference<ManagerVerifyParentActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private ManagerVerifyParentActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView recyclerView;
    private AppCompatTextView emptyTv;
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
        recyclerView = activity.findViewById(R.id.get_community_rc);
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
                    // unsuccessProcess();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private void successProcess(String response) {
        VerifyAdapter adapter = (VerifyAdapter) recyclerView.getAdapter();
        adapter.getParentList().remove(adapter.getSelectedPosition());
        adapter.notifyDataSetChanged();

    }
    private void unsuccessProcess() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();
        }, 1500);

    }
    private void errorOccurred() {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            Toast.makeText(activity, R.string.error, Toast.LENGTH_LONG).show();

        }, 1500);
    }
}
