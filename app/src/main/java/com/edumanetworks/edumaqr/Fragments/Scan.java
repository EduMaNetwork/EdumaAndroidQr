package com.edumanetworks.edumaqr.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edumanetworks.edumaqr.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.edumanetworks.edumaqr.SessionStorage.PublicAlert;
import static com.edumanetworks.edumaqr.SessionStorage.putPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class Scan extends Fragment {


    View mView;
    ZXingScannerView scannerView; //QR Kütüphanesi
    FrameLayout preview;
    public Scan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_scan, container, false);
        return mView;
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){

        initControls();
    }

    private void initControls() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.maintitle);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        MobileAds.initialize(getContext(), "ca-app-pub-3940256099942544~3347511713");


        preview = (FrameLayout) mView.findViewById(R.id.cameraPreview);

        scannerView = new ZXingScannerView(getContext());

        scannerView.setResultHandler(new ZXingScannerResultHandler());

        preview.addView(scannerView);
        scannerView.startCamera();

    }

    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler{
        @Override
        public void handleResult(Result result) {

            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
           // Bitmap image =  BitmapFactory.decodeByteArray(result.getRawBytes(), 0, result.getRawBytes().length);
            toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 100); // 200 is duration in ms
            String resultCode = result.getText();
            String tip = result.getBarcodeFormat().toString();

            if(resultCode.length() > 1)
            {
                //PublicAlert("Okunan Değer",resultCode.toString(),getContext());
                // okunan.setText(resultCode.toString());
                putPref("result",resultCode,getContext());


                GoResult(result);

            }


        }
    }

    private void GoResult(Result result) {
        Fragment detay = new ResultFragmen();
        Bundle args = new Bundle();


        detay.setArguments(args);
        FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_layout_for_activity_navigation, detay)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPause(){
        super.onPause();
        scannerView.stopCamera();
    }

}
