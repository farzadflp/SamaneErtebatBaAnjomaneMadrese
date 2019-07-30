package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Community;
import com.example.samaneertebatbaanjomanemadrese.model.Manager;

public class CommunityProfileActivity extends AppCompatActivity {
    private MyProfileLayout profileLayout;
    private Community community;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        profileLayout.getButton1().setVisibility(View.GONE);
        profileLayout.getButton2().setVisibility(View.GONE);
        profileLayout.getButton3().setOnClickListener(v -> {
            Intent intent = new Intent(CommunityProfileActivity.this , InboxActivity.class);
            startActivity(intent);
        });
    }

    private void init() {
        profileLayout = findViewById(R.id.community_profile_layout);
        community = getIntent().getParcelableExtra("community");
        setTexts();
    }

    private void setTexts() {
        String genderStr = "";
        switch (community.getGender()){
            case 0 :
                genderStr = getString(R.string.female);
                break;
            case 1 :
                genderStr = getString(R.string.male);
        }

        String firstname = getString(R.string.first_name) + ": " + community.getFirstname(), lastname = getString(R.string.last_name) + ": " + community.getLastname(),
                gender = getString(R.string.gender) + ": " + genderStr,
                username = getString(R.string.username) + ": " + community.getUsername(),
                phone_no = getString(R.string.phone_number) + ": " + community.getPhoneNo();
        profileLayout.getTv1().setText(firstname);
        profileLayout.getTv2().setText(lastname);
        profileLayout.getTv3().setText(gender);
        profileLayout.getTv4().setText(username);
        profileLayout.getTv5().setText(phone_no);
        profileLayout.getTv6().setVisibility(View.INVISIBLE);
    }
}
