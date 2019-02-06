package com.example.samaneertebatbaanjomanemadrese;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;


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
        profileLayout = (MyProfileLayout) findViewById(R.id.profile_layout);
        init();

    }

    private void init() {
        profileLayout.getSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this , "send" , Toast.LENGTH_LONG).show();
            }
        });
        profileLayout.getSearch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this , "search" , Toast.LENGTH_LONG).show();
            }
        });
        profileLayout.getChatlist().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this , "chatlist" , Toast.LENGTH_LONG).show();
            }
        });

    }
}
