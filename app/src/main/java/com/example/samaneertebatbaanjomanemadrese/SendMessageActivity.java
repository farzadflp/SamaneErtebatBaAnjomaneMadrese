package com.example.samaneertebatbaanjomanemadrese;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.example.samaneertebatbaanjomanemadrese.helper.LocaleHelper;

public class SendMessageActivity extends AppCompatActivity {
    private AppCompatSpinner sendToSPN,visibilitySPN , categorySPN;
    String[] sendToItem , visibilityItem , categoryItem;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase , "fa"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();


    }

    private void init() {
        sendToSPN = (AppCompatSpinner) findViewById(R.id.send_msg_spn_send_to);
        visibilitySPN = (AppCompatSpinner) findViewById(R.id.send_msg_spn_visibility);
        categorySPN = (AppCompatSpinner) findViewById(R.id.send_msg_spn_category);
        sendToItem = getResources().getStringArray(R.array.sendToArray);
        visibilityItem = getResources().getStringArray(R.array.visibilityArray);
        categoryItem =getResources().getStringArray(R.array.categoryArray);
        ArrayAdapter<String> sendToAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, sendToItem);
        ArrayAdapter<String> visibilityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, visibilityItem);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, categoryItem);
        sendToSPN.setAdapter(sendToAdapter);
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
}
