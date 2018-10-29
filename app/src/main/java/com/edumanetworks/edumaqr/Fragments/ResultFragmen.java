package com.edumanetworks.edumaqr.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.edumanetworks.edumaqr.R;

import java.util.Calendar;
import java.util.Date;

import static com.edumanetworks.edumaqr.SessionStorage.getPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragmen extends Fragment {

   Button btnShare,btnWeb,btnSearch,btnEmail,btnCall;
   ImageView imgResult,imgResultType;
   TextView resultTime,resultText,resultType;
   Bitmap image;


    View mView;
    public ResultFragmen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       mView =inflater.inflate(R.layout.fragment_result, container, false);
       return mView;
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){

        initControls();
    }

    private void initControls() {
        String result = getPref("result",getContext());
        Date currentTime = Calendar.getInstance().getTime();

        //Result sayfası buttonları
        btnShare = (Button) mView.findViewById(R.id.btnShare);
        btnCall = (Button) mView.findViewById(R.id.btnPhone);
        btnWeb = (Button) mView.findViewById(R.id.btnWebSearch);
        btnSearch = (Button) mView.findViewById(R.id.googleSearch);
        btnEmail = (Button) mView.findViewById(R.id.btnEmail);

        //Result Sayfası Image Tasıyıcıları
        imgResult = (ImageView) mView.findViewById(R.id.imgResult);
        imgResultType = (ImageView) mView.findViewById(R.id.imgResulType);

        //Result Sayfası TextViewler
        resultTime = (TextView) mView.findViewById(R.id.txtResultTime);
        resultText = (TextView) mView.findViewById(R.id.txtResult);
        resultType = (TextView) mView.findViewById(R.id.txtResultType);

        resultText.setText(result);
        resultTime.setText(String.valueOf(currentTime));


        if(result.startsWith("http") || result.startsWith("www")) //Okuanan qr web sayfası ise
        {
            btnWeb.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);
            btnShare.setVisibility(View.VISIBLE);
            btnEmail.setVisibility(View.GONE);
            btnCall.setVisibility(View.GONE);
            resultType.setText("Web İçeriği");
            imgResultType.setImageResource(R.drawable.ic_web_white);
        }
        else if(result.contains("@"))
        {
            btnWeb.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            btnShare.setVisibility(View.VISIBLE);
            btnEmail.setVisibility(View.VISIBLE);
            btnCall.setVisibility(View.GONE);
            resultType.setText("E-Mail Adresi");
            imgResultType.setImageResource(R.drawable.ic_send_white);
        }
        else if((result.startsWith("+") || result.startsWith("0")) || (result.length() > 9 && result.length() < 12)){
            btnWeb.setVisibility(View.GONE);
            btnSearch.setVisibility(View.VISIBLE);
            btnShare.setVisibility(View.VISIBLE);
            btnEmail.setVisibility(View.GONE);
            btnCall.setVisibility(View.VISIBLE);
            resultType.setText("Telefon No");
            imgResultType.setImageResource(R.drawable.ic_phone_white);
        }
        else
        {
            btnWeb.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            btnShare.setVisibility(View.GONE);
            btnEmail.setVisibility(View.GONE);
            btnCall.setVisibility(View.GONE);
            resultType.setText("Düz Metin");
            imgResultType.setImageResource(R.drawable.ic_text_result_white);
        }

    }
}
