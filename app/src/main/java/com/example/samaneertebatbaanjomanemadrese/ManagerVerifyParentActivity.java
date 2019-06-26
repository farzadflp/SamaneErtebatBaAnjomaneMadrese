package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.samaneertebatbaanjomanemadrese.adapters.ChatAdapter;
import com.example.samaneertebatbaanjomanemadrese.adapters.VerifyAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Message;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.model.User;

import java.util.ArrayList;
import java.util.List;

public class ManagerVerifyParentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Parent> parentList;
    private VerifyAdapter adapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "fa"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_verify_parent);
        init();
        parentList = new ArrayList<>();
        User user = new User(1,"فرزاد","فلاح پور","۱۲۱۲۱۲۱۲",1);
        parentList.add(new Parent(user , "سیسیس" , "۱۲۱۲۱۲۱"));
        showData();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.mvp_rc);
    }

    private void showData() {
        adapter = new VerifyAdapter(parentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public VerifyAdapter getAdapter() {
        return adapter;
    }
}
