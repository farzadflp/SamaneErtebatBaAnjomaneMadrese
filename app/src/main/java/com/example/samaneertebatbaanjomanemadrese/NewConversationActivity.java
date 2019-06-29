package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.dd.processbutton.iml.ActionProcessButton;
import com.example.samaneertebatbaanjomanemadrese.adapters.GetCommunityAdapter;
import com.example.samaneertebatbaanjomanemadrese.adapters.SpinnerAdapter;
import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.helper.MyIntentHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Conversation;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.task.GetCommunityTask;
import com.example.samaneertebatbaanjomanemadrese.task.NewConversationTask;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;
import java.util.HashMap;
import java.util.Map;

public class NewConversationActivity extends AppCompatActivity {
    private AppCompatSpinner communitySPN,visibilitySPN , categorySPN;
    AppCompatEditText inputMsg , inputTopic;
    private Conversation conversation = new Conversation();
    private Parent parent;
    private String username_two;
    String[]  visibilityItem , categoryItem;
    private int currentCommunity = 0 , currentVisibility = 0 ,currentCategory =0;
    private static final String URL_NEW_CONV ,URL_GET_COMMUNITY ;
    private ActionProcessButton newConvBtn;
    static {
        URL_NEW_CONV = MyIntentHelper.URL_BASE +"parent/new_conversation.php";
        URL_GET_COMMUNITY = MyIntentHelper.URL_BASE +"parent/get_community.php";

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase , "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_conversation);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        getCommunityRequest();
        communitySPNSetOnItemSelectedListener();
        visibilitySPNSetOnItemSelectedListener();
        categorySPNsetOnItemSelectedListener();
        newConvBtnSetOnClickListener();


    }

    private void newConvBtnSetOnClickListener() {
        newConvBtn.setOnClickListener(v -> {
            String msg = inputMsg.getText().toString().trim();
            String topic = inputTopic.getText().toString().trim();
            if (!topic.isEmpty() && !msg.isEmpty() && currentCategory != 0 && currentCommunity != 0 && currentVisibility !=0){
                newConvBtn.setClickable(false);
                newConvBtn.setEnabled(false);
                conversation.setMsg(msg);
                conversation.setTopic(topic);
                if (!MyHttpManger.isOnline(NewConversationActivity.this)){
                    MyIntentHelper.alertDialogIsOffline(NewConversationActivity.this);
                }else {
                newConversationRequest();
                }
            }
        });
    }

    private void categorySPNsetOnItemSelectedListener() {
        categorySPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentCategory != position){
                    currentCategory = position;
                    conversation.setCategory((String) parent.getSelectedItem());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void visibilitySPNSetOnItemSelectedListener() {
        visibilitySPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentVisibility != position){
                    currentVisibility = position;
                    String str = (String) parent.getSelectedItem();
                    if (str.equals(getString(R.string.public_word))){
                        conversation.setAccessibility("pub");
                    } else if (str.equals(getString(R.string.private_word))){
                        conversation.setAccessibility("pv");
                    }

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void communitySPNSetOnItemSelectedListener() {
        communitySPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentCommunity!= position){
                    currentCommunity = position;
                    GetCommunityAdapter adapter = (GetCommunityAdapter) parent.getAdapter();
                    username_two  = (String) adapter.getItem(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getCommunityRequest() {
        GetCommunityTask task = new GetCommunityTask(this);
        task.execute(getCommunityRequestData());
    }

    private MyHttpManger.RequestData getCommunityRequestData() {
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_GET_COMMUNITY);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("id_school" , String.valueOf(parent.getId_school()));
        requestData.setParams(params);
        return requestData;
    }

    private void init() {
        communitySPN = findViewById(R.id.new_conv_spn_send_to);
        visibilitySPN = findViewById(R.id.new_conv_spn_visibility);
        categorySPN = findViewById(R.id.new_conv_spn_category);
        newConvBtn = findViewById(R.id.new_conv_btn_send);
        inputMsg = findViewById(R.id.new_conv_et_msg);
        inputTopic = findViewById(R.id.new_conv_et_topic);
        newConvBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        parent =  getIntent().getParcelableExtra("parent");
        visibilityItem = getResources().getStringArray(R.array.visibilityArray);
        categoryItem =getResources().getStringArray(R.array.categoryArray);
        SpinnerAdapter visibilityAdapter = new SpinnerAdapter(this,visibilityItem);
        SpinnerAdapter categoryAdapter = new SpinnerAdapter(this,categoryItem);
        visibilitySPN.setAdapter(visibilityAdapter);
        categorySPN.setAdapter(categoryAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void newConversationRequest() {
       NewConversationTask task = new NewConversationTask(this);
        task.execute(newConversationRequestData(conversation));
    }
    private MyHttpManger.RequestData newConversationRequestData(Conversation conversation){
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_NEW_CONV);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("category", conversation.getCategory());
        params.put("accessibility", conversation.getAccessibility());
        params.put("topic", conversation.getTopic());
        params.put("msg", conversation.getMsg());
        params.put("username_two" , username_two);
        requestData.setParams(params);
        return requestData;
    }

    public Parent getMyParent() {
        return parent;
    }
}
