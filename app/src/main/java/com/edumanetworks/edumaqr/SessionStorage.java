package com.edumanetworks.edumaqr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

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
