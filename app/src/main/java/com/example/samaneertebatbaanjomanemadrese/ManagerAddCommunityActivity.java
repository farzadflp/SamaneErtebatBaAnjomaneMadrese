package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Community;
import com.example.samaneertebatbaanjomanemadrese.model.Manager;
import com.example.samaneertebatbaanjomanemadrese.model.User;
import com.example.samaneertebatbaanjomanemadrese.task.AddCommunityTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import java.util.HashMap;
import java.util.Map;

public class ManagerAddCommunityActivity extends AppCompatActivity {
    private Manager manager;
    private Community community;
    private AppCompatEditText inputFirstname, inputLastname, inputPhoneNo, inputTelWork, inputAddressWork, inputUsername, inputPassword , inputDegree , inputCourse , inputPost;
    private ActionProcessButton addCommunityBtn;
    private RadioGroup radioGroup;
    private int gender = 0;
    private final static int ERROR , NORMAL,PROCESS,COMPLETE;
    private static final String URL_ADD_COMMUNITY;

    static {
        URL_ADD_COMMUNITY = MyIntentHelper.URL_BASE + "manager/add_community.php";
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
        setContentView(R.layout.activity_manager_add_community);
        init();
        radioGroupSetOnCheckedChangeListener();
        addCommunityBtnSetOnClickListener();
    }

    private void addCommunityBtnSetOnClickListener() {
        addCommunityBtn.setOnClickListener(v -> {
            String firstname = inputFirstname.getText().toString().trim();
            String lastname = inputLastname.getText().toString().trim();
            String phoneNo = inputPhoneNo.getText().toString().trim();
            String course = inputCourse.getText().toString().trim();
            String degree = inputDegree.getText().toString().trim();
            String username = inputUsername.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            String post = inputPost.getText().toString().trim();
            String telWork = inputTelWork.getText().toString().trim();
            String addressWork = inputAddressWork.getText().toString().trim();
            String role = "c";
            if (isValidInput(firstname, lastname, phoneNo, course, degree, post, telWork , addressWork ,username, password)){
                addCommunityBtn.setProgress(PROCESS);
                addCommunityBtn.setEnabled(false);
                addCommunityBtn.setClickable(false);
                User user = new User(firstname, lastname, phoneNo, username, password, role);
                user.setId_school(manager.getId_school());
                user.setGender(gender);
                initCommunity(user, course, degree, post , telWork , addressWork);
                if (MyHttpManger.isOnline(ManagerAddCommunityActivity.this)) {
                    MyIntentHelper.alertDialogIsOffline(ManagerAddCommunityActivity.this);
                }
                addCommunityRequest();

            }


        });
    }

    private void initCommunity(User user, String course, String degree, String post, String telWork, String addressWork) {
        community = new Community(user,degree,course,post,telWork,addressWork);

    }

    private void addCommunityRequest() {
       AddCommunityTask task = new AddCommunityTask(this);
       task.execute(addCommunityRequestData());

    }

    private MyHttpManger.RequestData addCommunityRequestData() {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_ADD_COMMUNITY);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("firstname", community.getFirstname());
        params.put("lastname", community.getLastname());
        params.put("gender", String.valueOf(community.getGender()));
        params.put("username", community.getUsername());
        params.put("password", community.getPasssword());
        params.put("phone_no", community.getPhoneNo());
        params.put("post" , community.getPost());
        params.put("degree" , community.getDegree());
        params.put("course" , community.getCourse());
        params.put("tel_work" , community.getTelWork());
        params.put("address_work", community.getAddressWork());
        params.put("id_school", String.valueOf(community.getId_school()));
        params.put("role" , community.getRole());
        requestData.setParams(params);
        return requestData;
    }

    private void radioGroupSetOnCheckedChangeListener() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case  R.id.add_community_rb_male:
                    gender = 1 ;
                    break;
                case R.id.add_community_rb_female:
                    gender = 0;
                    break;
            }
        });
    }

    private void init() {
        inputFirstname = findViewById(R.id.add_community_et_firstname);
        inputLastname = findViewById(R.id.add_community_et_lastname);
        inputPhoneNo = findViewById(R.id.add_community_et_phone_no);
        inputUsername = findViewById(R.id.add_community_et_username);
        inputPassword = findViewById(R.id.add_community_et_password);
        inputPost = findViewById(R.id.add_community_et_post);
        inputDegree = findViewById(R.id.add_community_et_degree_of_education);
        inputCourse= findViewById(R.id.add_community_et_course);
        inputTelWork= findViewById(R.id.add_community_et_tel_work);
        inputAddressWork= findViewById(R.id.add_community_et_address_work);
        addCommunityBtn = findViewById(R.id.add_community_btn_add);
        radioGroup = findViewById(R.id.add_community_rg_gender);
        manager = getIntent().getParcelableExtra("manager");
    }
    private boolean isValidInput(String firstname, String lastname, String phoneNo, String course, String degree, String post, String telWork , String addressWork, String username, String password) {

        if (firstname.length() < 3) {
            Toast.makeText(ManagerAddCommunityActivity.this, R.string.warning_fistname_length, Toast.LENGTH_SHORT).show();
            inputFirstname.requestFocus();
            return false;
        } else if (lastname.length() < 3) {
            Toast.makeText(ManagerAddCommunityActivity.this, R.string.warning_lastname_length, Toast.LENGTH_SHORT).show();
            inputLastname.requestFocus();
            return false;
        } else if (phoneNo.length() < 10) {
            Toast.makeText(ManagerAddCommunityActivity.this, R.string.phone_number, Toast.LENGTH_SHORT).show();
            inputPhoneNo.requestFocus();
            return false;
        } else if (course.length() < 3) {
            Toast.makeText(ManagerAddCommunityActivity.this, R.string.course, Toast.LENGTH_SHORT).show();
            inputCourse.requestFocus();
            return false;
        } else if (degree.length() < 3) {
            Toast.makeText(ManagerAddCommunityActivity.this, R.string.degree_of_education, Toast.LENGTH_SHORT).show();
            inputDegree.requestFocus();
            return false;
        } else if (post.length() < 3) {
            Toast.makeText(ManagerAddCommunityActivity.this, R.string.post, Toast.LENGTH_SHORT).show();
            inputPost.requestFocus();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(ManagerAddCommunityActivity.this, R.string.password, Toast.LENGTH_SHORT).show();
            inputPassword.requestFocus();
            return false;
        } else if(username.length() < 4){
            Toast.makeText(ManagerAddCommunityActivity.this, R.string.username, Toast.LENGTH_SHORT).show();
            inputUsername.requestFocus();
            return false;

        }else if(telWork.length() < 10){
            Toast.makeText(ManagerAddCommunityActivity.this, R.string.tel_work, Toast.LENGTH_SHORT).show();
            inputTelWork.requestFocus();
            return false;

        }else if(addressWork.length() < 5){
            Toast.makeText(ManagerAddCommunityActivity.this, R.string.address_work, Toast.LENGTH_SHORT).show();
            inputAddressWork.requestFocus();
            return false;
        }else {
            return true;
        }
    }

}
