package com.example.samaneertebatbaanjomanemadrese.util;

import com.example.samaneertebatbaanjomanemadrese.InboxActivity;
import com.example.samaneertebatbaanjomanemadrese.model.Inbox;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InboxJsonParser {

    private static final String KEY_USERNAME = "username";
    private static final String KEY_AVATAR	= "avatar";

    public ArrayList<Inbox> parseJson(String jsonstring){
        ArrayList<Inbox> inboxes = new ArrayList<>();
        try {
            JSONArray jsonarray = new JSONArray(jsonstring);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Inbox  inbox = new Inbox();
                inbox.setUsername(jsonobject.getString(KEY_USERNAME));
                inbox.setAvatarPath(InboxActivity.URL_BASE +jsonobject.getString(KEY_AVATAR));
                inboxes.add(inbox);
            }
            return inboxes;
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return null;
    }


}
