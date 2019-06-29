package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;

import com.example.samaneertebatbaanjomanemadrese.adapters.VerifyAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Manager;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.task.GetNotVerfiedParentTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerVerifyParentActivity extends AppCompatActivity {
   // private RecyclerView recyclerView;
    private AppCompatTextView emptyTv;
    private List<Parent> parentList;
    private VerifyAdapter adapter;
    private RecyclerView recyclerView;
    private Manager manager;
    private final static  String URL_NOT_VERIFIED_PARENT;
    static {
        URL_NOT_VERIFIED_PARENT = MyIntentHelper.URL_BASE + "manager/get_parents.php";
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "fa"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_verify_parent);
        init();
        notVerifiedParentRequest();
    }

    private void notVerifiedParentRequest() {
        GetNotVerfiedParentTask task = new GetNotVerfiedParentTask(this);
        task.execute(notVerifiedParentRequestData());
    }

    private MyHttpManger.RequestData notVerifiedParentRequestData() {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_NOT_VERIFIED_PARENT);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("id_school" , String.valueOf(manager.getId_school()));
        requestData.setParams(params);
        return requestData;
    }

    private void init() {
        recyclerView = findViewById(R.id.mvp_rc);
        emptyTv = findViewById(R.id.mvp_tv);
        Bundle extras = getIntent().getExtras();
        manager =  getIntent().getParcelableExtra("manager");
    }

    public void setParentList(List<Parent> parentList) {
        this.parentList = parentList;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
