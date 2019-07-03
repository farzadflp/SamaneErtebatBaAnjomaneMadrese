package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.Toast;

import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.SignupActivity;
import com.example.samaneertebatbaanjomanemadrese.adapters.SpinnerAdapter;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import com.example.samaneertebatbaanjomanemadrese.util.SchoolJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class GetCityOfSchoolTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final WeakReference<SignupActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private SignupActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private AppCompatSpinner citySPN ;

    public GetCityOfSchoolTask(SignupActivity context) {
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
        activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return;
        citySPN = activity.findViewById(R.id.signup_city_spn);
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
        SpinnerAdapter  cityAdapter = new SpinnerAdapter(activity , new SchoolJsonParser().cityOfSchoolParseJson(response));
        citySPN.setAdapter(cityAdapter);
    }

    private void unsuccessProcess() {
        String[]  strings = new String[1];
        strings[0] = activity.getString(R.string.empty_city);
        SpinnerAdapter  cityAdapter = new SpinnerAdapter(activity , strings);
        citySPN.setAdapter(cityAdapter);

    }

    private void errorOccurred() {
        Toast.makeText(activity , R.string.error , Toast.LENGTH_LONG).show();
    }
}
