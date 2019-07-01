package com.example.samaneertebatbaanjomanemadrese.util;

import com.example.samaneertebatbaanjomanemadrese.model.Community;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommunityJsonParser {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_PHONE_NO = "phone_no";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_ID_SCHOOL = "id_school";
    private static final String KEY_POST = "post";
    private static final String KEY_TEL_WORK = "tel_work";
    private static final String KEY_ADDRESS_WORK = "address_work";
    private static final String KEY_DEGREE = "degree";
    private static final String KEY_COURSE = "course";


    //parse community data for login
    public Community CommunintyParseJson(String jsonstring) {
        try {
            JSONObject jsonOBJ = new JSONObject(jsonstring);
            JSONObject jsonobject = jsonOBJ.getJSONObject("0");
            User user = new User(jsonobject.getString(KEY_FIRSTNAME)
                    , jsonobject.getString(KEY_LASTNAME)
                    , jsonobject.getString(KEY_USERNAME)
                    , jsonobject.getInt(KEY_GENDER)
                    , jsonobject.getInt(KEY_ID_USER)
                    , jsonobject.getInt(KEY_ID_SCHOOL)
            );
            user.setPhoneNo(jsonobject.getString(KEY_PHONE_NO));
            Community community = new Community(user,
                    jsonobject.getString(KEY_POST)
                    , jsonobject.getString(KEY_DEGREE)
                    , jsonobject.getString(KEY_COURSE)
            );
            community.setAddressWork(jsonobject.getString(KEY_ADDRESS_WORK));
            community.setTelWork(jsonobject.getString(KEY_TEL_WORK));
            return community;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Community> CommunintiesDataParseJson(String jsonstring) {
        try {
            ArrayList<Community> communities = new ArrayList<>();
            JSONObject jsonOBJ = new JSONObject(jsonstring);
            jsonOBJ.remove("success");
            for (int i = 0; i < jsonOBJ.length(); i++) {
                JSONObject jsonobject = jsonOBJ.getJSONObject(String.valueOf(i));
                User user = new User(jsonobject.getString(KEY_FIRSTNAME)
                        , jsonobject.getString(KEY_LASTNAME)
                        , jsonobject.getInt(KEY_GENDER)
                );
                user.setPhoneNo(jsonobject.getString(KEY_PHONE_NO));
                user.setRole("c");
                user.setId_user(jsonobject.getInt(KEY_ID_USER));
                Community community = new Community(user,
                        jsonobject.getString(KEY_POST)
                        , jsonobject.getString(KEY_DEGREE)
                        , jsonobject.getString(KEY_COURSE)
                );
                community.setAddressWork(jsonobject.getString(KEY_ADDRESS_WORK));
                community.setTelWork(jsonobject.getString(KEY_TEL_WORK));
                communities.add(community);


            }
            return communities;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Community> CommunintiesParseJson(String jsonstring) {
        try {
            ArrayList<Community> communities = new ArrayList<>();
            JSONObject jsonOBJ = new JSONObject(jsonstring);
            jsonOBJ.remove("success");
            for (int i = 0; i < jsonOBJ.length(); i++) {
                JSONObject jsonobject = jsonOBJ.getJSONObject(String.valueOf(i));
                User user = new User(jsonobject.getString(KEY_FIRSTNAME)
                        , jsonobject.getString(KEY_LASTNAME)
                        , jsonobject.getString(KEY_USERNAME)
                );
                Community community = new Community(user, jsonobject.getString(KEY_POST));
                communities.add(community);
            }
            return communities;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
