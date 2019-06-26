package com.example.samaneertebatbaanjomanemadrese.util;

import com.example.samaneertebatbaanjomanemadrese.model.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageJsonParser {
    private static final String KEY_ID_MSg = "id_msg";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID_CONVERSATION = "id_conversation";
    private static final String KEY_MSG = "msg";
    private static final String KEY_DATE_TIME_MSG = "date_time_msg";

    public MessageJsonParser() {
    }

    public ArrayList<Message> messageParseJson(String jsonstring) {
        ArrayList<Message> msgList = new ArrayList<>();
        try {
            JSONObject jsonOBJ = new JSONObject(jsonstring);
            jsonOBJ.remove("success");
            for (int i = 0; i < jsonOBJ.length(); i++) {
                JSONObject  jsonobject = jsonOBJ.getJSONObject(String.valueOf(i));
                Message msg = new Message(jsonobject.getInt(KEY_ID_MSg)
                        , jsonobject.getInt(KEY_ID_USER)
                        ,jsonobject.getInt(KEY_ID_CONVERSATION));
                msg.setMsg(jsonobject.getString(KEY_MSG));
                msg.setUsername(jsonobject.getString(KEY_USERNAME));
                msg.setDateTimeMsg(jsonobject.getString(KEY_DATE_TIME_MSG));

                msgList.add(msg);
            }
            return msgList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
