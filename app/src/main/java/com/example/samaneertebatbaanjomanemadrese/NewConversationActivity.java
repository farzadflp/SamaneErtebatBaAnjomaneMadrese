package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;
import com.example.samaneertebatbaanjomanemadrese.model.Conversation;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

import java.util.HashMap;
import java.util.Map;

public class NewConversationActivity extends AppCompatActivity {
    private AppCompatSpinner sendToSPN,visibilitySPN , categorySPN;
    String[] sendToItem , visibilityItem , categoryItem;
    public static final String URL_NEW_CONV = "http://192.168.1.35:8888/new_conversation.php";
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



    }

    private void init() {
        sendToSPN = findViewById(R.id.send_msg_spn_send_to);
        visibilitySPN = findViewById(R.id.send_msg_spn_visibility);
        categorySPN = findViewById(R.id.send_msg_spn_category);
        sendToItem = getResources().getStringArray(R.array.sendToArray);
        visibilityItem = getResources().getStringArray(R.array.visibilityArray);
        categoryItem =getResources().getStringArray(R.array.categoryArray);
        //SpinnerAdapter adapter = new SpinnerAdapter(NewConversationActivity.this , sendToItem);
        ArrayAdapter<String> sendToAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item ,sendToItem);
        ArrayAdapter<String> visibilityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, visibilityItem);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, categoryItem);
        sendToSPN.setAdapter(sendToAdapter);
      //  sendToSPN.setAdapter(adapter);
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
    private void myRequest() {
       // newConversationTask task = new newConversationTask();
       // task.execute(getRequestData(new Conversation("","")));
    }
    private MyHttpManger.RequestData getRequestData(Conversation conversation){
        MyHttpManger.RequestData requestData = new MyHttpManger.RequestData();
        requestData.setUri(URL_NEW_CONV);
        requestData.setMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("category", conversation.getCategory());
        params.put("accessibility", conversation.getAccessibility());
        params.put("topic", conversation.getTopic());
        params.put("msg", conversation.getMsg());
        return requestData;
    }
    private class newConversationTask extends AsyncTask<MyHttpManger.RequestData,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(MyHttpManger.RequestData... requestData) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
