package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.Toast;
import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.User;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger.RequestData;
import com.example.samaneertebatbaanjomanemadrese.util.UserJsonParser;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private AppCompatTextView signup;
    private ActionProcessButton loginBtn;
    private AppCompatEditText inputUsername, inputPassword;
    public static final String URL_LOGIN = "http://192.168.1.38:8888/Login.php";
    public final static int ERROR = -1, NORMAL = 0, COMPLETE = 100, PROCESS = 50;
    public final static String FILE_NAME = "myprefs";
    private Context context = LoginActivity.this;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        updateView("fa");
        MyHttpManger.initCooki();
        init();
        loginBtnSetOnClick();


    }
    private void loginBtnSetOnClick() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (isValid(username, password)) {
                    myRequest(username,password);
                }
            }
        });
    }

    private boolean isValid(String username, String password) {
        return username != null && password != null;
    }


    private void init() {
        signup = (AppCompatTextView) findViewById(R.id.login_tv_signup);
        loginBtn = (ActionProcessButton) findViewById(R.id.login_btn_login);
        inputUsername = (AppCompatEditText) findViewById(R.id.login_et_username);
        inputPassword = (AppCompatEditText) findViewById(R.id.login_et_password);
        loginBtn.setMode(ActionProcessButton.Mode.ENDLESS);
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
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add(R.string.chat).setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add(R.string.inbox).setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(LoginActivity.this, InboxActivity.class);
                startActivity(intent);
                return false;
            }
        });
        menu.add(R.string.profile).setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(intent);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
    }

    private void myRequest(String username , String password) {
        LoginTask task = new LoginTask();
        task.execute(getRequestData(username,password));
    }
    private RequestData getRequestData(String username , String password){
        RequestData  requestData = new RequestData();
        requestData.setUri(URL_LOGIN);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        requestData.setParams(params);
        return requestData;
    }


    private class LoginTask extends AsyncTask<RequestData,Void,String> {
        private  User myuser;
        private Context context = LoginActivity.this;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(RequestData... params) {
            RequestData uri = params[0];
            String content = MyHttpManger.getDataHttpURLConnection(uri , MyIntentHelper.getSessionId(context)
                    , MyIntentHelper.getSessionName(context));

            myuser = new UserJsonParser().UserParseJson(content);
            return content;
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    sucessProcess(jsonResponse);

                } else {
                    unsuccessProcess();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        private void unsuccessProcess() {
            loginBtn.setProgress(ERROR);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_LONG);
                    loginBtn.setProgress(NORMAL);
                    loginBtn.setEnabled(true);
                }
            }, 1000);
            loginBtn.setProgress(ERROR);
        }

        private void sucessProcess(JSONObject jsonResponse) throws JSONException {
            MyIntentHelper.writeSession(context
                    ,jsonResponse.getString("sess_id")
                    ,jsonResponse.getString("sess_name"));

            loginBtn.setProgress(COMPLETE);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loginBtn.setProgress(NORMAL);
                    loginBtn.setEnabled(true);
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));

                }
            }, 1500);
        }
    }

}
