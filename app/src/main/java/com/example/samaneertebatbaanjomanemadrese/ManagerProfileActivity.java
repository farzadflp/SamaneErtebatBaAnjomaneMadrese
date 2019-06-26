package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Manager;

public class ManagerProfileActivity extends AppCompatActivity {

    private MyProfileLayout profileLayout;
    private Manager manager;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase , "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        profileLayout.getButton1().setText(R.string.add_community);
        profileLayout.getButton2().setText(R.string.verified_parent);
        profileLayout.getButton1().setOnClickListener(v -> startActivity(new Intent(ManagerProfileActivity.this , ManagerAddCommunityActivity.class)));
        profileLayout.getButton2().setOnClickListener(v -> startActivity(new Intent(ManagerProfileActivity.this , ManagerVerifyParentActivity.class)));
        profileLayout.getButton3().setVisibility(View.GONE);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        profileLayout = findViewById(R.id.manager_profile_layout);
        setTexts();
    }

    private void setTexts() {
        Bundle extras = getIntent().getExtras();
        manager =  getIntent().getParcelableExtra("manager");
        String firstname = getString(R.string.first_name)+ ": " + manager.getFirstname()
                , lastname = getString(R.string.last_name)+ ": " + manager.getLastname() ,
                gender = getString(R.string.gender)+ ": "  + manager.getGender() ,
                username = getString(R.string.username)+ ": " + manager.getUsername() ,
                phone_no = getString(R.string.phone_number)+ ": " + manager.getPhoneNo();
        profileLayout.getTv1().setText(firstname);
        profileLayout.getTv2().setText(lastname);
        profileLayout.getTv3().setText(gender);
        profileLayout.getTv4().setText(username);
        profileLayout.getTv5().setText(phone_no);
        //profileLayout.getTv6().setText(child_name);
    }
}
