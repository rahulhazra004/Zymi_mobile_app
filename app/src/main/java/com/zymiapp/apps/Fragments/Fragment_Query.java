package com.zymiapp.apps.Fragments;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.zymiapp.apps.Activities.Main_Handler_Activity;
import com.zymiapp.apps.R;
import com.zymiapp.apps.Session.Customer_Session;

public class Fragment_Query extends Fragment {

    private View rootview;
    private Button btn;
    String wp_no="";
    String wp_text="";
    String wp_b_no="";
    String wp_b_text="";
    ImageView sharebtn;
    private Customer_Session customer_session;

    public Fragment_Query() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootview =inflater.inflate(R.layout.fragment_fragment__query, container, false);
        customer_session = new Customer_Session(getContext());
        sharebtn=rootview.findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareBody = "Hey, There earn a referral , when you login using the link. "+ "https://play.google.com/store/apps/details?id=com.zymiapp.apps&referrer="+customer_session.getCustomerID();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Refer & Earn");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share Using:"));
            }
        });



        return rootview;
    }





}
