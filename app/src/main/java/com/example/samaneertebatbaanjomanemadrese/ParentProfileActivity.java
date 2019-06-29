package com.example.samaneertebatbaanjomanemadrese;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;


public class ParentProfileActivity extends AppCompatActivity {
    private MyProfileLayout profileLayout;
    private Parent parent;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase , "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        profileLayout.getButton1().setOnClickListener(v -> {
            Intent  intent = new Intent(ParentProfileActivity.this , NewConversationActivity.class);
            intent.putExtra("parent" , parent);
            startActivity(intent);
        });
        profileLayout.getButton2().setVisibility(View.GONE);
        profileLayout.getButton3().setOnClickListener(v -> startActivity(new Intent(ParentProfileActivity.this , InboxActivity.class)));

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
        profileLayout = findViewById(R.id.profile_layout);
        setTexts();
    }

    private void setTexts() {
        parent =  getIntent().getParcelableExtra("parent");
        String firstname = getString(R.string.first_name)+ ": " + parent.getFirstname()
                , lastname = getString(R.string.last_name)+ ": " + parent.getLastname() ,
                gender = getString(R.string.gender)+ ": "  + parent.getGender() ,
                username = getString(R.string.username)+ ": " + parent.getUsername() ,
                phone_no = getString(R.string.phone_number)+ ": " + parent.getPhoneNo(),
                child_name = getString(R.string.name_Of_child)+ ": " + parent.getChildName();
        profileLayout.getTv1().setText(firstname);
        profileLayout.getTv2().setText(lastname);
        profileLayout.getTv3().setText(gender);
        profileLayout.getTv4().setText(username);
        profileLayout.getTv5().setText(phone_no);
        profileLayout.getTv6().setText(child_name);
    }

}
