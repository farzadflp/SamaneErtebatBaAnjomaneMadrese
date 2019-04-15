package com.example.samaneertebatbaanjomanemadrese;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Inbox;


public class ProfileActivity extends AppCompatActivity {
    MyProfileLayout profileLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase , "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        profileLayout.getSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this , SendMessageActivity.class));
            }
        });
        profileLayout.getSearch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this , SearchActivity.class));
            }
        });
        profileLayout.getChatlist().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this , InboxActivity.class));
            }
        });

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
        profileLayout = (MyProfileLayout) findViewById(R.id.profile_layout);
    }
}
