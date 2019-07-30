package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Community;
import com.example.samaneertebatbaanjomanemadrese.model.Manager;
import com.example.samaneertebatbaanjomanemadrese.model.User;
import com.example.samaneertebatbaanjomanemadrese.task.EditCommunityTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import java.util.HashMap;
import java.util.Map;

public class EditCommunityActivity extends AppCompatActivity {
    private Manager manager;
    private Community community;
    private AppCompatEditText inputFirstname, inputLastname, inputPhoneNo, inputTelWork, inputAddressWork, inputDegree, inputCourse, inputPost;
    private ActionProcessButton editCommunityBtn;
    private RadioGroup radioGroup;
    private int gender = 0;
    private final static int ERROR, NORMAL, PROCESS, COMPLETE;
    private static final String URL_EDIT_COMMUNITY;

    static {
        URL_EDIT_COMMUNITY = MyIntentHelper.URL_BASE + "manager/edit_community.php";
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
        setContentView(R.layout.activity_edit_community);
        init();
        editCommunityBtnSetOnClickListener();
        radioGroupSetOnCheckedChangeListener();
    }

    private void init() {
        manager = getIntent().getParcelableExtra("manager");
        community = getIntent().getParcelableExtra("community");
        inputFirstname = findViewById(R.id.edit_community_et_firstname);
        inputLastname = findViewById(R.id.edit_community_et_lastname);
        inputPhoneNo = findViewById(R.id.edit_community_et_phone_no);
        inputPost = findViewById(R.id.edit_community_et_post);
        inputDegree = findViewById(R.id.edit_community_et_degree_of_education);
        inputCourse = findViewById(R.id.edit_community_et_course);
        inputTelWork = findViewById(R.id.edit_community_et_tel_work);
        inputAddressWork = findViewById(R.id.edit_community_et_address_work);
        editCommunityBtn = findViewById(R.id.edit_community_btn_add);
        radioGroup = findViewById(R.id.edit_community_rg_gender);
        setEditTexts();
    }

    private void setEditTexts() {
        inputFirstname.setText(community.getFirstname());
        inputLastname.setText(community.getLastname());
        inputPhoneNo.setText(community.getPhoneNo());
        inputTelWork.setText(community.getTelWork());
        inputAddressWork.setText(community.getAddressWork());
        inputDegree.setText(community.getDegree());
        inputCourse.setText(community.getCourse());
        inputPost.setText(community.getPost());
    }

    private void radioGroupSetOnCheckedChangeListener() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.edit_community_rb_male:
                    gender = 1;
                    break;
                case R.id.edit_community_rb_female:
                    gender = 0;
                    break;
            }
        });
    }

    private void editCommunityBtnSetOnClickListener() {
        editCommunityBtn.setOnClickListener(v -> {
            String firstname = inputFirstname.getText().toString().trim();
            String lastname = inputLastname.getText().toString().trim();
            String phoneNo = inputPhoneNo.getText().toString().trim();
            String course = inputCourse.getText().toString().trim();
            String degree = inputDegree.getText().toString().trim();
            String post = inputPost.getText().toString().trim();
            String telWork = inputTelWork.getText().toString().trim();
            String addressWork = inputAddressWork.getText().toString().trim();
            String role = "c";
            if (isValidInput(firstname, lastname, phoneNo, course, degree, post, telWork, addressWork)) {
                editCommunityBtn.setProgress(PROCESS);
                editCommunityBtn.setEnabled(false);
                editCommunityBtn.setClickable(false);
                User user = new User(firstname, lastname,gender );
                user.setId_school(manager.getId_school());
                user.setRole(role);
                user.setPhoneNo(phoneNo);
                Community editedCommunity = new Community(user, course, degree, post, telWork, addressWork);
                if (MyHttpManger.isOnline(EditCommunityActivity.this)) {
                    MyIntentHelper.alertDialogIsOffline(EditCommunityActivity.this);
                }
                editCommunityRequest(editedCommunity);

            }


        });
    }

    private MyHttpManger.RequestData editCommunityRequestData(Community editedCommunity) {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_EDIT_COMMUNITY);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("id_user", String.valueOf(community.getId_user()));
        params.put("firstname", editedCommunity.getFirstname());
        params.put("lastname", editedCommunity.getLastname());
        params.put("gender", String.valueOf(editedCommunity.getGender()));
        params.put("phone_no", editedCommunity.getPhoneNo());
        params.put("post" , editedCommunity.getPost());
        params.put("degree" , editedCommunity.getDegree());
        params.put("course" , editedCommunity.getCourse());
        params.put("tel_work" , editedCommunity.getTelWork());
        params.put("address_work", editedCommunity.getAddressWork());
        requestData.setParams(params);
        return requestData;
    }

    private void editCommunityRequest(Community editedCommunity) {
        EditCommunityTask task = new EditCommunityTask(this);
        task.execute(editCommunityRequestData(editedCommunity));
    }


    private boolean isValidInput(String firstname, String lastname, String phoneNo, String course, String degree, String post, String telWork , String addressWork) {

        if (firstname.length() < 3) {
            Toast.makeText(EditCommunityActivity.this, R.string.warning_fistname_length, Toast.LENGTH_SHORT).show();
            inputFirstname.requestFocus();
            return false;
        } else if (lastname.length() < 3) {
            Toast.makeText(EditCommunityActivity.this, R.string.warning_lastname_length, Toast.LENGTH_SHORT).show();
            inputLastname.requestFocus();
            return false;
        } else if (phoneNo.length() < 10) {
            Toast.makeText(EditCommunityActivity.this, R.string.warning_phone_no_length, Toast.LENGTH_SHORT).show();
            inputPhoneNo.requestFocus();
            return false;
        } else if (course.length() < 3) {
            Toast.makeText(EditCommunityActivity.this, R.string.warning_course_length, Toast.LENGTH_SHORT).show();
            inputCourse.requestFocus();
            return false;
        } else if (degree.length() < 3) {
            Toast.makeText(EditCommunityActivity.this, R.string.warning_degree_length, Toast.LENGTH_SHORT).show();
            inputDegree.requestFocus();
            return false;
        } else if (post.length() < 3) {
            Toast.makeText(EditCommunityActivity.this, R.string.warning_post_length, Toast.LENGTH_SHORT).show();
            inputPost.requestFocus();
            return false;
        } else if(telWork.length() < 10){
            Toast.makeText(EditCommunityActivity.this, R.string.warning_tel_work_length, Toast.LENGTH_SHORT).show();
            inputTelWork.requestFocus();
            return false;

        }else if(addressWork.length() < 5){
            Toast.makeText(EditCommunityActivity.this, R.string.warning_address_work_length, Toast.LENGTH_SHORT).show();
            inputAddressWork.requestFocus();
            return false;
        }else {
            return true;
        }
    }
}
