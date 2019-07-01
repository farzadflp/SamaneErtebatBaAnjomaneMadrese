package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.task.VerificationTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import java.util.HashMap;
import java.util.Map;

import static com.example.samaneertebatbaanjomanemadrese.R.id.verification_btn_submit;
import static com.example.samaneertebatbaanjomanemadrese.R.id.verification_et_verfication_code;

public class VerificationActivity extends AppCompatActivity {
    private Parent myParent;
    private ActionProcessButton submitBtn;
    private AppCompatEditText inputVerfication;
    public static final String URL_VERIFICATION, FILE_NAME;
    public final static int ERROR, NORMAL, PROCESS, COMPLETE;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "fa"));
    }
    static {
        URL_VERIFICATION = MyIntentHelper.URL_BASE +"parent/Verification.php";
        ERROR = -1;
        NORMAL = 0;
        PROCESS = 50;
        COMPLETE = 100;
        FILE_NAME = "myprefs";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        init();
        submitClickOnListener();
    }

    private void submitClickOnListener() {
        submitBtn.setOnClickListener(v -> {
            submitBtn.setProgress(PROCESS);
            submitBtn.setEnabled(false);
            submitBtn.setClickable(false);
            String verificationCode = inputVerfication.getText().toString().trim();
            if (!MyHttpManger.isOnline(this)) {
                MyIntentHelper.alertDialogIsOffline(this);
            } else {
                sendVerficationRequest(myParent.getUsername(), verificationCode);
            }

        });
    }

    private void sendVerficationRequest(String username, String verificationCode) {
        VerificationTask task = new VerificationTask(this);
        task.execute( creatRequestData(username,verificationCode));
    }
    private MyHttpManger.RequestData creatRequestData(String username, String verificationCode){
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_VERIFICATION);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("verification_code", verificationCode);
        requestData.setParams(params);
        return requestData;
    }

    private void init() {
        submitBtn = findViewById(verification_btn_submit);
        inputVerfication = findViewById(verification_et_verfication_code);
        submitBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        Bundle extras = getIntent().getExtras();
        myParent =  getIntent().getParcelableExtra("parent");
    }

    public Parent getMyParent() {
        return myParent;
    }
}
