package com.edumanetworks.edumaqr;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class classAdmob {
    private static final String reklamID = "ca-app-pub-3940256099942544/6300978111";


    public static InterstitialAd getInstance(Context context) {
        InterstitialAd mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(reklamID);
        return mInterstitialAd;
    }
    public static AdRequest getAdRequest() {
        return new AdRequest.Builder()

                .build();
    }
}