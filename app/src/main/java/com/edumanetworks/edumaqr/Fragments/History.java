package com.edumanetworks.edumaqr.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.edumanetworks.edumaqr.HistoryAdapter;
import com.edumanetworks.edumaqr.HistoryModel;
import com.edumanetworks.edumaqr.R;
import com.edumanetworks.edumaqr.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import static com.edumanetworks.edumaqr.SessionStorage.getListHistoryPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    Activity activity;
    ListView historysListView;
    List<HistoryModel> historys = new ArrayList<>();
    HistoryAdapter historyListAdapter;

    SharedPreference sharedPreference;
    public History() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sharedPreference = new SharedPreference();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        historys = getListHistoryPref("historyList",getContext());
        View mView = inflater.inflate(R.layout.fragment_history, container, false);

        if (historys == null) {
            showAlert(getResources().getString(R.string.no_historys_items),
                    getResources().getString(R.string.no_historys_msg));
        } else {

            if (historys.size() == 0) {
                showAlert(
                        getResources().getString(R.string.no_historys_items),
                        getResources().getString(R.string.no_historys_msg));
            }

            historysListView = (ListView) mView.findViewById(R.id.list_product);
            if (historys != null){
                historyListAdapter = new HistoryAdapter(activity, historys);
                historysListView.setAdapter(historyListAdapter);

            }

        }
        return mView;

    }
    public void showAlert (String title, String message){
        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Tamam",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // activity.finish();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }
}
