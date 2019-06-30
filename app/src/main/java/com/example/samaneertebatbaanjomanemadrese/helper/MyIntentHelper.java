package com.example.samaneertebatbaanjomanemadrese.helper;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.model.Parent;
import com.example.samaneertebatbaanjomanemadrese.model.User;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

public class MyIntentHelper {
    private static SharedPreferences pref  ;
    private final static String FILE_NAME;
    public  final static String URL_BASE;

    static {
        FILE_NAME = "myprefs";
        URL_BASE = "http://192.168.1.40:8888/";
    }



    public static void writeSession(Context context , String sessId ,String sessName){
        pref = context.getSharedPreferences(FILE_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("sess_id",sessId);
        editor.putString("sess_name",sessName);
        editor.apply();
    }
    public static String getSessionName(Context context ) {
        pref = context.getSharedPreferences(FILE_NAME , Context.MODE_PRIVATE);
        return pref.getString("sess_name" , "not-found");
    }

    public static String getSessionId(Context context) {
        pref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return pref.getString("sess_id" , "not-found");
    }
    public static void clearSession(Context context){
        pref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public static void openWifiSettingsScreen(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

    public static void openDataUsageScreen(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.settings",
                "com.android.settings.Settings$DataUsageSummaryActivity"));
        context.startActivity(intent);
    }
    public static void alertDialogIsOffline(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.no_internet_access)
                .setCancelable(false)
                .setMessage(R.string.you_are_offline)
                .setPositiveButton(R.string.turn_on_wifi, (dialog, which) -> openWifiSettingsScreen(context))
                .setNegativeButton(R.string.turn_on_mobile_data, (dialog, which) -> openDataUsageScreen(context))
                .setNeutralButton(R.string.retry, (dialog, which) -> {
                    if(!MyHttpManger.isOnline(context)){
                        alertDialogIsOffline(context);
                    }

                })
                .show();

    }



}
