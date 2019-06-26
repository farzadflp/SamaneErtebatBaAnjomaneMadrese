package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.adapters.SpinnerAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.model.School;
import com.example.samaneertebatbaanjomanemadrese.model.User;
import com.example.samaneertebatbaanjomanemadrese.task.GetCityOfSchoolTask;
import com.example.samaneertebatbaanjomanemadrese.task.GetNameOfSchool;
import com.example.samaneertebatbaanjomanemadrese.task.SignupTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger.RequestData;

import java.util.HashMap;
import java.util.Map;

import static com.example.samaneertebatbaanjomanemadrese.R.id.signup_et_name_of_child;
import static com.example.samaneertebatbaanjomanemadrese.R.id.signup_et_st_no_child;

public class SignupActivity extends AppCompatActivity {
    private AppCompatEditText inputFirstname, inputLastname, inputPhoneNo, inputChildName, inputStNoOfChild, inputUsername, inputPassword;
    private ActionProcessButton signupBtn;
    private AppCompatSpinner stateSPN , gradeSPN ,  citySPN , zoneSPN , schoolSPN;
    private RadioGroup radioGroup;
    private int gender = 0;
    private Parent parent;
    private School school = new School("");
    private int currentState = 0 , currentGrade = 0 ,currentCity =0 ,currentZone =0 , currentSchool =0;
    private static final String URL_REGISTER , URL_GET_CITY, URL_GET_SCHOOL;
    public final static int ERROR,NORMAL,PROCESS,COMPLETE;
    private String[] stateArray , gradeArray , cityArray , zoneArray , schoolArray;
    private int[] school_id;

    static {
        URL_REGISTER = MyIntentHelper.URL_BASE + "Register.php";
        URL_GET_CITY = MyIntentHelper.URL_BASE + "get_city.php";
        URL_GET_SCHOOL = MyIntentHelper.URL_BASE + "get_school.php";
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
    }



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
        radioGroupSetOnCheckedChangeListener();
        gradeSPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentGrade != position){
                    currentGrade = position;
                    school.setGrade((String) parent.getSelectedItem());
                    Toast.makeText(SignupActivity.this , school.getState() , Toast.LENGTH_LONG).show();
                    currentState = 0;
                    currentCity = 0 ;
                    currentZone = 0;
                    currentSchool = 0;
                    SpinnerAdapter stateAdapter = new SpinnerAdapter(SignupActivity.this , stateArray);
                    stateSPN.setAdapter(stateAdapter);
                    citySPN.setAdapter(null);
                    zoneSPN.setAdapter(null);
                    schoolSPN.setAdapter(null);
                    school.setCity(null);
                    school.setZone(-1);
                    school.setName(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stateSPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentState != position){
                    currentState = position;
                    school.setState((String) parent.getSelectedItem());
                    currentCity = 0 ;
                    currentZone = 0;
                    currentSchool = 0;
                    if (!MyHttpManger.isOnline(SignupActivity.this)) {
                        MyIntentHelper.alertDialogIsOffline(SignupActivity.this);
                    }
                    Toast.makeText(SignupActivity.this , school.getState() , Toast.LENGTH_LONG).show();
                    getCityOfSchoolRequest(school);
                    zoneSPN.setAdapter(null);
                    schoolSPN.setAdapter(null);
                    school.setZone(-1);
                    school.setName(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        citySPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentCity != position){
                    currentCity = position;
                    school.setCity((String) parent.getSelectedItem());
                    currentZone = 0;
                    currentSchool = 0;
                    Toast.makeText(SignupActivity.this , school.getState() , Toast.LENGTH_LONG).show();
                    SpinnerAdapter  zoneAdapter = new SpinnerAdapter(SignupActivity.this , zoneArray);
                    zoneSPN.setAdapter(zoneAdapter);
                    schoolSPN.setAdapter(null);
                    school.setName(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        zoneSPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentZone != position){
                    currentZone = position;
                    schoolSPN.setAdapter(null);
                    currentSchool = 0;
                    school.setZone(Integer.parseInt((String) parent.getSelectedItem()));
                    if (!MyHttpManger.isOnline(SignupActivity.this)) {
                        MyIntentHelper.alertDialogIsOffline(SignupActivity.this);
                    }
                    getNameOfSchoolRequest(school);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        schoolSPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentSchool != position){
                    currentSchool = position;
                    school.setName((String) parent.getSelectedItem());
                    school.setId_school(school_id[position]);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signupBtnSetOnClick();

    }

    private void radioGroupSetOnCheckedChangeListener() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.signup_rb_male){
                gender = 1;
            }else if(checkedId == R.id.signup_rb_female){
                gender = 0;
            }
        });
    }

    private void signupBtnSetOnClick() {
        signupBtn.setOnClickListener(v -> {
            //create account
            String firstname = inputFirstname.getText().toString().trim();
            String lastname = inputLastname.getText().toString().trim();
            String phoneNo = inputPhoneNo.getText().toString().trim();
            String childName = inputChildName.getText().toString().trim();
            String stNoOfChild = inputStNoOfChild.getText().toString().trim();
            String username = inputUsername.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            String role = "p";
            if (isValidInput(firstname, lastname, phoneNo, childName, stNoOfChild, username, password)) {
                if (currentCity != 0 && currentZone != 0 && currentState != 0 && currentGrade != 0 && currentSchool !=0 ) {
                    signupBtn.setProgress(PROCESS);
                    signupBtn.setEnabled(false);
                    signupBtn.setClickable(false);
                    User user = new User(firstname, lastname, phoneNo, username, password, role);
                    initParent(user, phoneNo, childName, stNoOfChild);
                    if (!MyHttpManger.isOnline(SignupActivity.this)) {
                        MyIntentHelper.alertDialogIsOffline(SignupActivity.this);
                    }
                    signupRequest(parent);
                }
            }
        });
    }


    private void initParent(User initUser, String phoneNo, String childName, String stNoOfChild) {
        parent = new Parent(initUser , childName , stNoOfChild);
        parent.setChildName(childName);
        parent.setStNoOfChild(stNoOfChild);
        parent.setPhoneNo(phoneNo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isValidInput(String firstname, String lastname, String phoneNo, String childName, String stNoOfChild, String username, String password) {

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
        inputFirstname = findViewById(R.id.signup_et_firstname);
        inputLastname = findViewById(R.id.signup_et_lastname);
        inputPhoneNo = findViewById(R.id.signup_et_phone_no);
        inputUsername = findViewById(R.id.signup_et_username);
        inputPassword = findViewById(R.id.signup_et_password);
        inputStNoOfChild = findViewById(signup_et_st_no_child);
        inputChildName = findViewById(signup_et_name_of_child);
        signupBtn = findViewById(R.id.signup_btn_signup);
        radioGroup = findViewById(R.id.signup_rg_gender);
        gradeSPN = findViewById(R.id.signup_grade_spn);
        stateSPN = findViewById(R.id.signup_state_spn);
        citySPN = findViewById(R.id.signup_city_spn);
        zoneSPN = findViewById(R.id.signup_zone_spn);
        schoolSPN = findViewById(R.id.signup_school_name_spn);
        stateArray =getResources().getStringArray(R.array.state);
        gradeArray =getResources().getStringArray(R.array.grade);
        zoneArray =getResources().getStringArray(R.array.zone);
        SpinnerAdapter  gradeAdapter = new SpinnerAdapter(this , gradeArray);
        gradeSPN.setAdapter(gradeAdapter);


        
        signupBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        inputFirstname.requestFocus();

    }
    private void getNameOfSchoolRequest(School school) {
        GetNameOfSchool task = new GetNameOfSchool(this);
        task.execute(getNameOfSchoolRequestData(school));
    }

    private RequestData getNameOfSchoolRequestData(School school) {
        RequestData requestData = new RequestData();
        requestData.setUri(URL_GET_SCHOOL);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("grade", school.getGrade());
        params.put("state", school.getState());
        params.put("city", school.getCity());
        params.put("zone", String.valueOf(school.getZone()));
        requestData.setParams(params);
        return requestData;

    }

    private void getCityOfSchoolRequest(School school) {
        GetCityOfSchoolTask task = new GetCityOfSchoolTask(this);
        task.execute(getCityRequestData(school));
    }

    private RequestData getCityRequestData(School school) {
        RequestData requestData = new RequestData();
        requestData.setUri(URL_GET_CITY);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("grade", school.getGrade());
        params.put("state", school.getState());
        requestData.setParams(params);
        return requestData;

    }

    private void signupRequest(Parent parent) {
        signupBtn.setEnabled(false);
        signupBtn.setProgress(PROCESS);
        SignupTask task = new SignupTask(this);
        task.execute(getSignupRequestData(parent));
    }

    private RequestData getSignupRequestData(Parent parent) {
        RequestData requestData = new RequestData();
        requestData.setUri(URL_REGISTER);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("firstname", parent.getFirstname());
        params.put("lastname", parent.getLastname());
        params.put("gender", String.valueOf(parent.getGender()));
        params.put("username", parent.getUsername());
        params.put("password", parent.getPasssword());
        params.put("phone_no", parent.getPhoneNo());
        params.put("child_name", parent.getChildName());
        params.put("st_no_of_child", parent.getStNoOfChild());
        params.put("id_school", String.valueOf(school.getId_school()));
        requestData.setParams(params);
       return requestData;

    }

    public int[] getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int[] school_id) {
        this.school_id = school_id;
    }

    public String[] getZoneArray() {
        return zoneArray;
    }


}
