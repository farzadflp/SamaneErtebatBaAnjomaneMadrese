package com.example.samaneertebatbaanjomanemadrese.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleHelper {
    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language" ;

    public static Context onAttach(Context context){
        String lang = getPersistedData(context , Locale.getDefault().getLanguage());
        return setLocale(context , lang);
    }

    public static Context onAttach(Context context , String defaultLanguage){
        String lang = getPersistedData(context , defaultLanguage);
        return setLocale(context , lang);
    }

    public static Context setLocale(Context context, String lang) {
        persist( context , lang );
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return updateResourses(context , lang);
        }
        return updateResoursesLegacy(context , lang);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResoursesLegacy(Context context, String lang) {
        Locale  locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLayoutDirection(locale);
        }
        resources.updateConfiguration(config , resources.getDisplayMetrics());

        return context;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResourses(Context context, String lang) {
        Locale  locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        config.setLocale(locale);
        config.setLayoutDirection(locale);

        return context.createConfigurationContext(config);
    }

    private static void persist(Context context, String lang) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SELECTED_LANGUAGE, lang);
        editor.apply();
    }

    private static String getPersistedData(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE , language);
    }

}
