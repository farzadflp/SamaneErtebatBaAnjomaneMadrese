package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dd.processbutton.FlatButton;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.User;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.samaneertebatbaanjomanemadrese.R.id.signup_et_st_no_child;

public class SignupActivity extends AppCompatActivity {
    private AppCompatEditText inputFirstname, inputLastname, inputPhoneNo, inputNIDNo, inputChildName, inputStNoOfChild, inputUsername, inputPassword;
    private FlatButton signupBtn;
    private AppCompatRadioButton maleRB, femaleRB;
    private boolean gender = false;
    private User user;
    public static final String URL_REGISTER = "http://192.168.1.35:8888/Register.php";
    private RequestQueue requestQueue;

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
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = inputFirstname.getText().toString().trim();
                String lastname = inputLastname.getText().toString().trim();
                String phoneNo = inputPhoneNo.getText().toString().trim();
                String NIDNo = inputNIDNo.getText().toString().trim();
                String childName = inputChildName.getText().toString().trim();
                String stNoOfChild = inputStNoOfChild.getText().toString().trim();
                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                //onRadioButtonClicked(v);
                if (isValidInput(firstname, lastname, phoneNo, NIDNo, childName, stNoOfChild, username, password)) {
                    user = initUser(new User(firstname, lastname, phoneNo, username, password, gender), NIDNo, childName, stNoOfChild);
                    if (!MyHttpManger.isOnline(SignupActivity.this)){
                        MyIntentHelper.alertDialogIsOffline(SignupActivity.this);
                    }
                    sendParamsPost();
                }
            }
        });
    }


    private User initUser(User initUser, String NIDNo, String childName, String stNoOfChild) {
        User user = initUser;
        user.setNIDNo(NIDNo);
        user.setChildName(childName);
        user.setStNoOfChild(stNoOfChild);
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
        requestQueue = Volley.newRequestQueue(SignupActivity.this);
        StringRequest request = new StringRequest(
                Request.Method.POST
                , URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(SignupActivity.this, "error", Toast.LENGTH_LONG);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map< String , String >  params = new HashMap<>();
                try {
                params.put("firstname", user.getFirstname());
                params.put("lastname", user.getLastname());
                params.put("gender", String.valueOf(user.isGender()));
                params.put("username", user.getUsername());
                params.put("password", user.getPassword());
                params.put("phone_no", user.getPhoneNo());
                params.put("nid_no", user.getNIDNo());
                params.put("child_name", user.getChildName());
                params.put("st_no_of_child", user.getStNoOfChild());
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                return params;
            }
        };
        requestQueue.add(request);

    }

    private void onRadioButtonClicked(View view) {
        boolean checked = ((AppCompatRadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.signup_rb_male:
                if (checked)
                    gender = true;
                break;
            case R.id.signup_rb_female:
                if (checked)
                    gender = false;
                break;
        }
    }

    private boolean isValidInput(String firstname, String lastname, String phoneNo, String nidNo, String childName, String stNoOfChild, String username, String password) {
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
        } else if (nidNo.length() < 3) {
            Toast.makeText(SignupActivity.this, R.string.nid_no, Toast.LENGTH_SHORT).show();
            inputNIDNo.requestFocus();
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
        inputNIDNo = (AppCompatEditText) findViewById(R.id.signup_et_nid_no);
        inputChildName = (AppCompatEditText) findViewById(signup_et_st_no_child);
        signupBtn = (FlatButton) findViewById(R.id.signup_btn_signup);
        maleRB = (AppCompatRadioButton) findViewById(R.id.signup_rb_male);
        femaleRB = (AppCompatRadioButton) findViewById(R.id.signup_rb_female);
    }
}
