package com.edumanetworks.edumaqr.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.edumanetworks.edumaqr.HistoryModel;
import com.edumanetworks.edumaqr.R;
import com.edumanetworks.edumaqr.SharedPreference;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.edumanetworks.edumaqr.SessionStorage.getPref;
import static com.edumanetworks.edumaqr.SessionStorage.putListHistoryPref;
import static com.edumanetworks.edumaqr.SessionStorage.putPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragmen extends Fragment {

    Button btnShare,btnWeb,btnSearch,btnEmail,btnCall;
    ImageView imgResult,imgResultType;
    TextView resultTime,resultText,resultType;
    Bitmap image;
    String tarihs,description,tarih;
    HistoryModel model;
    SharedPreference sharedPreference;
    final List<HistoryModel> historys = new ArrayList<>();
    Activity activity;

    String tip;

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

        Date date;
        final String result = getPref("result",getContext());

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        tarihs = df.format(c);




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
        resultTime.setText(tarihs);


        if(result.startsWith("http") || result.startsWith("www")) //Okuanan qr web sayfası ise
        {
            btnWeb.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);
            btnShare.setVisibility(View.VISIBLE);
            btnEmail.setVisibility(View.GONE);
            btnCall.setVisibility(View.GONE);
            resultType.setText("Web İçeriği");
            description = tarihs + " - Web İçeriği";
            imgResultType.setImageResource(R.drawable.ic_web_white);
            tip = "W";
        }
        else if(result.contains("@"))
        {
            btnWeb.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            btnShare.setVisibility(View.VISIBLE);
            btnEmail.setVisibility(View.VISIBLE);
            btnCall.setVisibility(View.GONE);
            resultType.setText("E-Mail Adresi");
            description = tarihs + " - EMail Adresi";
            imgResultType.setImageResource(R.drawable.ic_send_white);
            tip = "E";
        }
        else if((result.startsWith("+") || result.startsWith("0")) || (result.length() > 9 && result.length() < 12) || result.startsWith("tel")){
            btnWeb.setVisibility(View.GONE);
            btnSearch.setVisibility(View.VISIBLE);
            btnShare.setVisibility(View.VISIBLE);
            btnEmail.setVisibility(View.GONE);
            btnCall.setVisibility(View.VISIBLE);
            resultType.setText("Telefon No");
            description = tarihs + " - Telefon No";
            imgResultType.setImageResource(R.drawable.ic_phone_white);
            tip = "T";
        }
        else
        {
            btnWeb.setVisibility(View.GONE);
            btnSearch.setVisibility(View.VISIBLE);
            btnShare.setVisibility(View.VISIBLE);
            btnEmail.setVisibility(View.GONE);
            btnCall.setVisibility(View.GONE);
            resultType.setText("Düz Metin");
            description = tarihs + " - Düz Metin";
            imgResultType.setImageResource(R.drawable.ic_text_result_white);
            tip = "M";
        }

         HistoryModel model = new HistoryModel();
            model.setScanResult(result);
            model.setScanDescription(description);
            model.setScanType(tip);
            historys.add(model);
         putListHistoryPref("historyList",historys,getContext());

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Eduma Qr Tarama Sonucu Paylaş");

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, result);
                startActivity(Intent.createChooser(sharingIntent, "Eduma QR"));

            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(result));
                startActivity(intent);

            }
        });
        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(result));
                startActivity(intent);

            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String escapedQuery = null;
                try {
                    escapedQuery = URLEncoder.encode(result.toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Uri uri = Uri.parse("http://www.google.com/#q=" + escapedQuery);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { result });
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "mail body");
                startActivity(Intent.createChooser(intent, ""));


            }
        });

    }
}
