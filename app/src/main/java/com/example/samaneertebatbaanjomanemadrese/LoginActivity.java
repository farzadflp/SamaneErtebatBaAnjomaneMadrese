package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.TextView;

import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;

public class LoginActivity extends AppCompatActivity {
    private TextView signup;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase , "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        updateView("fa");
        signup = (TextView) findViewById(R.id.login_tv_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , SignupActivity.class );
                startActivity(intent );
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Signup").setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent  intent = new Intent(LoginActivity.this  , SignupActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add("ChatActivity").setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent  intent = new Intent(LoginActivity.this  , ChatActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add("ChatList").setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent  intent = new Intent(LoginActivity.this  , ChatListActivity.class);
                        startActivity(intent);
                        return false;
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    private void updateView(String lang) {
        Context  context = LocaleHelper.setLocale(this , lang);
    }

}
