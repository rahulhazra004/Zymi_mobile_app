package com.zymiapp.apps.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zymiapp.apps.R;

public class More_Fragment extends Fragment implements View.OnClickListener {
    public More_Fragment() {
        // Required empty public constructor
    }


    private View rootview;
    public static LinearLayout llAllSectionContainer;
    private FrameLayout containerFragment;
    boolean isRoot = false;
    private FragmentTransaction transaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        rootview = inflater.inflate(R.layout.fragment_more, container, false);
        initView(rootview);
        return rootview;
    }


    private void initView(View rootview) {
        llAllSectionContainer= rootview.findViewById(R.id.llAllSectionContainer);
        rootview.findViewById(R.id.cart_layout).setOnClickListener(this);
        rootview.findViewById(R.id.wallet_layout).setOnClickListener(this);
        rootview.findViewById(R.id.my_orders_layout).setOnClickListener(this);
        rootview.findViewById(R.id.partner_layout).setOnClickListener(this);
        rootview.findViewById(R.id.payment_details_layout).setOnClickListener(this);
        rootview.findViewById(R.id.help_layout).setOnClickListener(this);
        rootview.findViewById(R.id.help_layouthelp_layout).setOnClickListener(this);
        rootview.findViewById(R.id.about_layout).setOnClickListener(this);
        rootview.findViewById(R.id.social_layout).setOnClickListener(this);
        rootview.findViewById(R.id.ranking_layout).setOnClickListener(this);
        containerFragment=rootview.findViewById(R.id.containerFragment);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cart_layout:
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                llAllSectionContainer.setVisibility(View.GONE);
                transaction.replace(R.id.containerFragment, new Fragment_Query());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.wallet_layout:
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                llAllSectionContainer.setVisibility(View.GONE);
                transaction.replace(R.id.containerFragment, new WalletFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.my_orders_layout:
                break;
            case R.id.partner_layout:
                break;
            case R.id.payment_details_layout:
                break;
            case R.id.help_layout:
                break;
            case R.id.help_layouthelp_layout:
                break;
            case R.id.about_layout:
                break;
            case R.id.social_layout:
                break;
            case R.id.ranking_layout:
                break;
            //case R.id.wallet_layout:

        }
    }

    @Override
    public void onResume() {

        super.onResume();
        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().getSupportFragmentManager().popBackStack();
                    llAllSectionContainer.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
    }



}
