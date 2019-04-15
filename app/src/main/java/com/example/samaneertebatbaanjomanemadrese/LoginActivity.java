package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;

import com.dd.processbutton.FlatButton;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

public class LoginActivity extends AppCompatActivity {
    private AppCompatTextView signup;
    private FlatButton login;
    private AppCompatEditText username , password;
    public static final String URL_BASE = "http://192.168.1.35:8888";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase , "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        updateView("fa");
        init();
        //myRequest();
     //   sendpassword();
        //methode check kardan doros boodan password


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , SignupActivity.class );
                startActivity(intent );
            }
        });

    }
/*
    private void myRequest() {
        LoginTask task = new LoginTask();
        task.execute(URL_BASE);
    }
*/

    private void sendpassword() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MyHttpManger.isOnline(LoginActivity.this)){
                    MyIntentHelper.alertDialogIsOffline(LoginActivity.this);
                } else {
                 //   myRequest();
                }
            }
        });
    }

    private void init() {
        signup = (AppCompatTextView) findViewById(R.id.login_tv_signup);
        login = (FlatButton) findViewById(R.id.login_btn_login);
        username = (AppCompatEditText) findViewById(R.id.login_et_username);
        password = (AppCompatEditText) findViewById(R.id.login_et_password);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.signup).setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent  intent = new Intent(LoginActivity.this  , SignupActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add(R.string.chat).setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent  intent = new Intent(LoginActivity.this  , ChatActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add(R.string.inbox).setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent  intent = new Intent(LoginActivity.this  , InboxActivity.class);
                        startActivity(intent);
                        return false;
                    }
                });
        menu.add(R.string.profile).setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent  intent = new Intent(LoginActivity.this  , ProfileActivity.class);
                        startActivity(intent);
                        return false;
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    private void updateView(String lang) {
        Context  context = LocaleHelper.setLocale(this , lang);
    }
/*
    private class LoginTask extends AsyncTask<MyHttpUtils.RequestData,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(MyHttpUtils.RequestData... params) {
            //String uri = params[0];
           // String content = MyHttpManger.getDataHttpURLConnection(uri);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }
*/
}
