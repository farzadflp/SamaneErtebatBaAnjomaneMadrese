package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.task.LoginTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger.RequestData;
import java.util.HashMap;
import java.util.Map;

import static com.example.samaneertebatbaanjomanemadrese.R.string.verify;


public class LoginActivity extends AppCompatActivity {
    private AppCompatTextView signupTv;
    private ActionProcessButton loginBtn;
    private AppCompatEditText inputUsername, inputPassword;
    private RadioGroup radioGroup;
    private String role = "p";
    public static final String URL_LOGIN,FILE_NAME;
    public final static int ERROR,NORMAL,PROCESS,COMPLETE;

    static {
        URL_LOGIN = MyIntentHelper.URL_BASE + "Login.php";
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
        FILE_NAME = "myprefs";
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        updateView("fa");
        init();
        radioGroupSetOnCheckedChangeListener();
        loginBtnSetOnClick();


    }

    private void loginBtnSetOnClick() {
        loginBtn.setOnClickListener(v -> {
            loginBtn.setProgress(PROCESS);
            loginBtn.setEnabled(false);
            loginBtn.setClickable(false);
            String username = inputUsername.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            if (isValid(username, password)) {
                if (!MyHttpManger.isOnline(LoginActivity.this)){
                    MyIntentHelper.alertDialogIsOffline(LoginActivity.this);
                } else {
                    loginRequest(username, password);
                }
            }
        });
    }

    private void radioGroupSetOnCheckedChangeListener() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.login_rb_parent) {
                role = "p";
            } else if (checkedId == R.id.login_rb_community) {
                role = "c";
            } else if (checkedId == R.id.login_rb_manager) {
                role = "m";

            }
        });
    }

    private boolean isValid(String username, String password) {
        if (username.isEmpty()) {
            Toast.makeText(LoginActivity.this, R.string.warning_field_empty, Toast.LENGTH_SHORT).show();
            inputUsername.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this, R.string.warning_field_empty, Toast.LENGTH_SHORT).show();
            inputPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }


    private void init() {
        signupTv = findViewById(R.id.login_tv_signup);
        loginBtn = findViewById(R.id.login_btn_login);
        inputUsername = findViewById(R.id.login_et_username);
        inputPassword = findViewById(R.id.login_et_password);
        radioGroup = findViewById(R.id.login_rg_role);
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
        menu.add(R.string.signup).setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            return false;
        });
        menu.add(R.string.chat).setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
            startActivity(intent);
            return false;
        });
        menu.add(R.string.inbox).setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(LoginActivity.this, InboxActivity.class);
            startActivity(intent);
            return false;
        });
        menu.add(R.string.profile).setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(LoginActivity.this, ParentProfileActivity.class);
            startActivity(intent);
            return false;
        });
        menu.add(verify).setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(LoginActivity.this, ManagerVerifyParentActivity.class);
            startActivity(intent);
            return false;
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
    }

    private void loginRequest(String username, String password) {
        LoginTask task = new LoginTask(LoginActivity.this);
        task.execute(getRequestData(username, password));
    }

    private RequestData getRequestData(String username, String password) {
        RequestData requestData = new RequestData();
        requestData.setUri(URL_LOGIN);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("role", role);
        requestData.setParams(params);
        return requestData;
    }

    public String getRole() {
        return role;
    }




}
