package com.example.samaneertebatbaanjomanemadrese.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.Toast;

import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.SignupActivity;
import com.example.samaneertebatbaanjomanemadrese.adapters.SpinnerAdapter;
import com.example.samaneertebatbaanjomanemadrese.model.School;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import com.example.samaneertebatbaanjomanemadrese.util.SchoolJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class GetNameOfSchoolTask extends AsyncTask<MyHttpManger.RequestData, Void, String> {
    private final WeakReference<SignupActivity> activityReference;
    @SuppressLint("StaticFieldLeak")
    private SignupActivity activity ;
    @SuppressLint("StaticFieldLeak")
    private AppCompatSpinner schoolSPN;


    public GetNameOfSchoolTask(SignupActivity context) {
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
        schoolSPN = activity.findViewById(R.id.signup_school_name_spn);
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
        ArrayList<School> schools = new SchoolJsonParser().schoolParseJson(response);
        String[] name = new String[schools.size()];
        int[] id_school = new int[schools.size()];
        for (int i = 0 ; i < schools.size() ; i++){
            name[i] = schools.get(i).getName();
            id_school[i] = schools.get(i).getId_school();
        }
        SpinnerAdapter schoolAdapter = new SpinnerAdapter(activity , name , id_school);
        schoolSPN.setAdapter(schoolAdapter);
        activity.setSchool_id(schoolAdapter.getId_numeric());
    }

    private void unsuccessProcess() {
        String[]  strings = new String[1];
        strings[0] = activity.getString(R.string.empty_school);
        SpinnerAdapter  cityAdapter = new SpinnerAdapter(activity , strings);
        schoolSPN.setAdapter(cityAdapter);

    }

    private void errorOccurred() {
        Toast.makeText(activity , R.string.error , Toast.LENGTH_LONG).show();
    }
}
