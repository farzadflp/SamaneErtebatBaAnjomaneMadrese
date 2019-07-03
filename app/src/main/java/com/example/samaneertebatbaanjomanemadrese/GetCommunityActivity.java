package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Manager;
import com.example.samaneertebatbaanjomanemadrese.task.GetCommunityDataTask;
import com.example.samaneertebatbaanjomanemadrese.task.GetNotVerfiedParentTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import java.util.HashMap;
import java.util.Map;

public class GetCommunityActivity extends AppCompatActivity {
    private Manager manager;
    private final static String URL_NOT_VERIFIED_PARENT;

    static {
        URL_NOT_VERIFIED_PARENT = MyIntentHelper.URL_BASE + "manager/get_communities.php";
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase , "fa"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_community);
        init();
        GetCommunitiesRequest();

    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        startActivity(getIntent());
    }

    private void GetCommunitiesRequest() {
        GetCommunityDataTask task = new GetCommunityDataTask(this);
        task.execute(GetCommunitiesRequestData());
    }

    private MyHttpManger.RequestData GetCommunitiesRequestData() {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_NOT_VERIFIED_PARENT);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("id_school", String.valueOf(manager.getId_school()));
        requestData.setParams(params);
        return requestData;
    }

    private void init() {
        manager = getIntent().getParcelableExtra("manager");

    }

    public Manager getManager() {
        return manager;
    }
}
