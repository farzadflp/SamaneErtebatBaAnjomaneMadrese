package com.example.samaneertebatbaanjomanemadrese.util;

import com.example.samaneertebatbaanjomanemadrese.model.User;
import org.json.JSONException;
import org.json.JSONObject;

public class UserJsonParser {

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_PHONE_NO = "phone_no";
    private static final String KEY_CHILDNAME = "child_name";
    private static final String KEY_ST_NO_OF_CHILD = "st_no_of_child";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_ROLE = "role";

    public UserJsonParser() {
    }

    public User UserParseJson(String jsonstring) {
       // ArrayList<User> users = new ArrayList<>();
        try {
            //JSONArray jsonarray = new JSONArray(jsonstring);
            //for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonOBJ = new JSONObject(jsonstring);
                JSONObject  jsonobject = jsonOBJ.getJSONObject("0");

                User user = new User(jsonobject.getString(KEY_FIRSTNAME),
                        jsonobject.getString(KEY_LASTNAME),
                        jsonobject.getString(KEY_USERNAME),
                        "null",
                        jsonobject.getInt(KEY_GENDER));
                user.setRole(jsonobject.getString(KEY_ROLE));
                user.setPhoneNo(jsonobject.getString(KEY_PHONE_NO));
                user.setChildName(jsonobject.getString(KEY_CHILDNAME));
                user.setStNoOfChild(jsonobject.getString(KEY_ST_NO_OF_CHILD));
                user.setId_user(jsonobject.getInt(KEY_ID_USER));
               // users.add(user);

           // }
            return user;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}