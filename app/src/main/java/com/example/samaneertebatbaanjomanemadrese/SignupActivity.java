package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.User;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger.RequestData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.samaneertebatbaanjomanemadrese.R.id.signup_et_st_no_child;

public class SignupActivity extends AppCompatActivity {
    private AppCompatEditText inputFirstname, inputLastname, inputPhoneNo, inputChildName, inputStNoOfChild, inputUsername, inputPassword;
    private ActionProcessButton signupBtn;
    private AppCompatRadioButton maleRB, femaleRB;
    private int gender = 0;
    private User user;
    public static final String URL_REGISTER = "http://192.168.1.38:8888/Register.php";
    public final static int ERROR = -1, NORMAL = 0, COMPLETE = 100, PROCESS = 50;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        signupBtnSetOnClick();

    }

    private void signupBtnSetOnClick() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creat account
                String firstname = inputFirstname.getText().toString().trim();
                String lastname = inputLastname.getText().toString().trim();
                String phoneNo = inputPhoneNo.getText().toString().trim();
                ;
                String childName = inputChildName.getText().toString().trim();
                String stNoOfChild = inputStNoOfChild.getText().toString().trim();
                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                //onRadioButtonClicked(v);
                if (isValidInput(firstname, lastname, phoneNo, childName, stNoOfChild, username, password)) {
                    user = new User(firstname, lastname, username, password, gender);
                    initUser(user, phoneNo, childName, stNoOfChild);
                    if (!MyHttpManger.isOnline(SignupActivity.this)) {
                        MyIntentHelper.alertDialogIsOffline(SignupActivity.this);
                    }
                    myRequest(user);
                }
            }
        });
    }


    private User initUser(User initUser, String phoneNo, String childName, String stNoOfChild) {
        User user = initUser;
        user.setChildName(childName);
        user.setStNoOfChild(stNoOfChild);
        user.setPhoneNo(phoneNo);
        return user;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void sendParamsPost() {
        signupBtn.setEnabled(false);
        signupBtn.setProgress(PROCESS);
    }

    private void onRadioButtonClicked(View view) {
        boolean checked = ((AppCompatRadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.signup_rb_male:
                if (checked)
                    gender = 1;
                break;
            case R.id.signup_rb_female:
                if (checked)
                    gender = 0;
                break;
        }
    }

    private boolean isValidInput(String firstname, String lastname, String phoneNo, String childName, String stNoOfChild, String username, String password) {
        /*
        if ( (firstname == null) && (lastname == null) && (phoneNo == null) && (nidNo == null) && (childName == null) && (stNoOfChild == null) && (username == null) && (password == null) ){
            Toast.makeText(SignupActivity.this, R.string.warning_fistname_length , Toast.LENGTH_SHORT).show();
            //yek fild khalist
            return false;
        }
        */
        if (firstname.length() < 3) {
            Toast.makeText(SignupActivity.this, R.string.warning_fistname_length, Toast.LENGTH_SHORT).show();
            inputFirstname.requestFocus();
            return false;
        } else if (lastname.length() < 3) {
            Toast.makeText(SignupActivity.this, R.string.warning_lastname_length, Toast.LENGTH_SHORT).show();
            inputLastname.requestFocus();
            return false;
        } else if (phoneNo.length() < 3) {
            Toast.makeText(SignupActivity.this, R.string.phone_number, Toast.LENGTH_SHORT).show();
            inputPhoneNo.requestFocus();
            return false;
        } else if (childName.length() < 3) {
            Toast.makeText(SignupActivity.this, R.string.name_Of_child, Toast.LENGTH_SHORT).show();
            inputChildName.requestFocus();
            return false;
        } else if (stNoOfChild.length() < 3) {
            Toast.makeText(SignupActivity.this, R.string.st_no_child, Toast.LENGTH_SHORT).show();
            inputStNoOfChild.requestFocus();
            return false;
        } else if (username.length() < 3) {
            Toast.makeText(SignupActivity.this, R.string.username, Toast.LENGTH_SHORT).show();
            inputUsername.requestFocus();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(SignupActivity.this, R.string.password, Toast.LENGTH_SHORT).show();
            inputPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void init() {
        inputFirstname = (AppCompatEditText) findViewById(R.id.signup_et_firstname);
        inputLastname = (AppCompatEditText) findViewById(R.id.signup_et_lastname);
        inputPhoneNo = (AppCompatEditText) findViewById(R.id.signup_et_phone_no);
        inputUsername = (AppCompatEditText) findViewById(R.id.signup_et_username);
        inputPassword = (AppCompatEditText) findViewById(R.id.signup_et_password);
        inputStNoOfChild = (AppCompatEditText) findViewById(signup_et_st_no_child);
        inputChildName = (AppCompatEditText) findViewById(signup_et_st_no_child);
        signupBtn = (ActionProcessButton) findViewById(R.id.signup_btn_signup);
        maleRB = (AppCompatRadioButton) findViewById(R.id.signup_rb_male);
        femaleRB = (AppCompatRadioButton) findViewById(R.id.signup_rb_female);
        signupBtn.setMode(ActionProcessButton.Mode.ENDLESS);

    }

    private void myRequest(User user) {
        signupBtn.setEnabled(false);
        signupBtn.setProgress(PROCESS);
        SignupTask task = new SignupTask();
        task.execute(getRequestData(user));
    }

    private RequestData getRequestData(User user) {
        RequestData requestData = new RequestData();
        requestData.setUri(URL_REGISTER);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("firstname", user.getFirstname());
        params.put("lastname", user.getLastname());
        params.put("gender", String.valueOf(user.getGender()));
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        params.put("phone_no", user.getPhoneNo());
        params.put("child_name", user.getChildName());
        params.put("st_no_of_child", user.getStNoOfChild());
        requestData.setParams(params);
        return requestData;
    }

    private class SignupTask extends AsyncTask<RequestData, Void, String> {
        private User myuser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(RequestData... params) {
            MyHttpManger.RequestData uri = params[0];
            String content = MyHttpManger.getDataHttpURLConnection(uri , "" , "");
            return content;
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    successProcess();

                } else {
                    unsuccessProcess();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        private void unsuccessProcess() {
            signupBtn.setProgress(ERROR);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SignupActivity.this, "error", Toast.LENGTH_LONG);
                    signupBtn.setProgress(NORMAL);
                    signupBtn.setEnabled(true);
                }
            }, 1000);
            signupBtn.setProgress(ERROR);
        }

        private void successProcess() {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    signupBtn.setProgress(COMPLETE);
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                }
            }, 1000);
        }
    }


}
