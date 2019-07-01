package com.example.samaneertebatbaanjomanemadrese.util;

import com.example.samaneertebatbaanjomanemadrese.model.School;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SchoolJsonParser {
    private static final String KEY_CITY = "city";

    public String[] cityOfSchoolParseJson(String jsonstring) {
        try {

            JSONObject jsonOBJ = new JSONObject(jsonstring);
            jsonOBJ.remove("success");
            String[] cityArray = new String[jsonOBJ.length()+1];
            cityArray[0] = "-------------";
            for (int i = 1; i < jsonOBJ.length()+1; i++) {
                JSONObject jsonobject = jsonOBJ.getJSONObject(String.valueOf(i-1));
                cityArray[i]=jsonobject.getString(KEY_CITY);
            }
            return cityArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<School> schoolParseJson(String jsonstring) {
        try {

            JSONObject jsonOBJ = new JSONObject(jsonstring);
            jsonOBJ.remove("success");
            ArrayList<School>  schools = new ArrayList<>();
            schools.add(new School("-------------" , -2));
            for (int i = 1; i < jsonOBJ.length()+1; i++) {
                JSONObject jsonobject = jsonOBJ.getJSONObject(String.valueOf(i-1));
                schools.add(new School(jsonobject.getString("name"),jsonobject.getInt("id_school")));
            }
            return schools;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
