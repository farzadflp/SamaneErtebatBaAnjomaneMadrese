package com.example.samaneertebatbaanjomanemadrese.util;

import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParentJsonParser {

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
    private static final String KEY_ID_SCHOOL = "id_school";
    private static final String KEY_VERIFIED_BY_M = "verified_by_m";
    private static final String KEY_VERIFIED = "verified";

    public ParentJsonParser() {
    }
//parse parent data for login
    public Parent parentParseJson(String jsonstring) {
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
                Parent parent = new Parent(user
                        , jsonobject.getString(KEY_CHILDNAME)
                        ,jsonobject.getString(KEY_ST_NO_OF_CHILD)
                );
                parent.setRole(jsonobject.getString(KEY_ROLE));
                parent.setVerified_by_m(jsonobject.getString(KEY_VERIFIED_BY_M));
            return parent;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}