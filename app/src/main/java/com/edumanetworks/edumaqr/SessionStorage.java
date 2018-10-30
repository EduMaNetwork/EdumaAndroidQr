package com.edumanetworks.edumaqr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SessionStorage {
    public static void putPref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void putListHistoryPref(String key, List<HistoryModel> liste,Context context){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(liste);

        editor.putString(key, jsonFavorites);

        editor.commit();
    }
    public static ArrayList<HistoryModel> getListHistoryPref(String key,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String listPref = preferences.getString(key, null);
        Gson gson = new Gson();
        HistoryModel[] scanItems = gson.fromJson(listPref,
                HistoryModel[].class);
        List<HistoryModel> favorites;

        favorites = Arrays.asList(scanItems);
        favorites = new ArrayList<HistoryModel>(favorites);

        return (ArrayList<HistoryModel>) favorites;
    }


    public static void PublicAlert(String title,String alert, Context context) {

        AlertDialog.Builder abl = new AlertDialog.Builder(context);
        abl.setTitle(title);
        abl.setMessage(alert);
        abl.setIcon(android.R.drawable.ic_dialog_info);
        abl.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        abl.show();


    }


}
