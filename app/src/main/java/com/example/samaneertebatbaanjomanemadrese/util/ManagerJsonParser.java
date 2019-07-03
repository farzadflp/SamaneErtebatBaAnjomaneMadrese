package com.example.samaneertebatbaanjomanemadrese.util;

import com.example.samaneertebatbaanjomanemadrese.model.Manager;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManagerJsonParser {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_PHONE_NO = "phone_no";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_ROLE = "role";
    private static final String KEY_ID_SCHOOL = "id_school";
    private static final String KEY_DEGREE = "degree";
    private static final String KEY_COURSE = "course";
    private static final String KEY_VERIFIED = "verified";


    public ManagerJsonParser() {
    }

    //parse manager data for login
    public Manager managerParseJson(String jsonstring) {
        try {
            JSONObject jsonOBJ = new JSONObject(jsonstring);
            JSONObject  jsonobject = jsonOBJ.getJSONObject("0");
            User  user = new User(jsonobject.getString(KEY_FIRSTNAME)
                    ,jsonobject.getString(KEY_LASTNAME)
                    ,jsonobject.getString(KEY_USERNAME)
                    ,jsonobject.getInt(KEY_GENDER)
                    ,jsonobject.getInt(KEY_ID_USER)
                    ,jsonobject.getInt(KEY_ID_SCHOOL)
            );
            user.setVerified(jsonobject.getInt(KEY_VERIFIED));
            user.setPhoneNo(jsonobject.getString(KEY_PHONE_NO));

            return new Manager(user
                        ,jsonobject.getString(KEY_DEGREE)
                        ,jsonobject.getString(KEY_COURSE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
