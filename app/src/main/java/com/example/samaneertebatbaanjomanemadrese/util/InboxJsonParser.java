package com.example.samaneertebatbaanjomanemadrese.util;

import com.example.samaneertebatbaanjomanemadrese.model.Inbox;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class InboxJsonParser {
    private static final String KEY_ID_TWO = "id_two";
    private static final String KEY_USERNAME_TWO = "username_two";
    private static final String KEY_ID_CONVERSATION = "id_conversation";
    private static final String KEY_TOPIC = "topic";
    private static final String KEY_DATE_TIME_CONV = "date_time_conv";
    private static final String KEY_CATEGORY = "category";

    public InboxJsonParser() {
    }

    public ArrayList<Inbox> InboxParseJson(String jsonstring) {
        ArrayList<Inbox> inboxes = new ArrayList<>();
        try {
            JSONObject jsonOBJ = new JSONObject(jsonstring);
            jsonOBJ.remove("success");
            for (int i = 0; i < jsonOBJ.length(); i++) {
                JSONObject  jsonobject = jsonOBJ.getJSONObject(String.valueOf(i));
                Inbox inbox = new Inbox(jsonobject.getInt(KEY_ID_CONVERSATION),jsonobject.getInt(KEY_ID_TWO));
                inbox.setTopic(jsonobject.getString(KEY_TOPIC));
                inbox.setUsername_two(jsonobject.getString(KEY_USERNAME_TWO));
                inbox.setDate_time_conv(jsonobject.getString(KEY_DATE_TIME_CONV));
                inbox.setCategory(jsonobject.getString(KEY_CATEGORY));
                inboxes.add(inbox);
            }
            return inboxes;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
