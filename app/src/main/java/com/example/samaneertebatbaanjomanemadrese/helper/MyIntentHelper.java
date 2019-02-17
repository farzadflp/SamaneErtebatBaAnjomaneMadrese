package com.example.samaneertebatbaanjomanemadrese.helper;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.example.samaneertebatbaanjomanemadrese.R;
import com.example.samaneertebatbaanjomanemadrese.util.MyHttpManger;

public class MyIntentHelper {


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
                .setPositiveButton(R.string.turn_on_wifi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openWifiSettingsScreen(context);
                    }
                })
                .setNegativeButton(R.string.turn_on_mobile_data, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openDataUsageScreen(context);
                    }
                })
                .setNeutralButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!MyHttpManger.isOnline(context)){
                            alertDialogIsOffline(context);
                        }

                    }
                })
                .show();

    }



}
