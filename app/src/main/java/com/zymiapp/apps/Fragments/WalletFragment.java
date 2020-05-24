package com.zymiapp.apps.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zymiapp.apps.Activities.My_Wallet;
import com.zymiapp.apps.Adapters.Wallet_Adapter;
import com.zymiapp.apps.Applications.AppController;
import com.zymiapp.apps.Model.Wallet;
import com.zymiapp.apps.R;
import com.zymiapp.apps.Session.Customer_Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletFragment extends Fragment implements View.OnClickListener {
    public WalletFragment() {
        // Required empty public constructor
    }


    private View rootview;
    ImageView ivBacks;
    private static final String WALLET_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/selectWallet/API-KEY/123456";
    private static final String KEY_RES_ID = "res_id";
    private Customer_Session customer_session;
    private TextView mwb;
    private ProgressBar p_bar;
    private LinearLayout lin;
    private List<Wallet> transactions = new ArrayList<>();
    private RecyclerView recyclerView;
    private Wallet_Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_wallet, container, false);
        customer_session = new Customer_Session(getActivity());

        initView(rootview);
        getWallet();
        return rootview;
    }

    private void initView(View rootview) {
        rootview.findViewById(R.id.ivBacks).setOnClickListener(this);
        mwb = rootview.findViewById(R.id.mwb);
        lin = rootview.findViewById(R.id.wall_lin);
        p_bar = rootview.findViewById(R.id.p_bar);
        recyclerView = rootview.findViewById(R.id.recycler_v);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        onBackPressed();

    }

    @Override
    public void onClick(View v) {
        exitFromCurrentFragment();
    }

    public void onBackPressed() {
        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    exitFromCurrentFragment();
                    return true;
                }
                return false;
            }
        });
    }

    public void exitFromCurrentFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
        More_Fragment.llAllSectionContainer.setVisibility(View.VISIBLE);
    }

    void getWallet() {

        String tag_json_obj = "json_obj_req";

        Map<String, String> postParam = new HashMap<>();
        postParam.put(KEY_RES_ID, customer_session.getCustomerID());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                WALLET_URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            String status = response.getString("status");

                            if (status.equals("200")) {

                                JSONArray jsonArray = response.getJSONArray("wallet_data");

                                if (jsonArray.length() > 1) {
                                    recyclerView.setVisibility(View.VISIBLE);
                                }

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    String trans_id = jsonObject1.getString("transaction_id");
                                    String trans_type = jsonObject1.getString("transaction_details");
                                    String trans_amount = jsonObject1.getString("transaction_amount");
                                    String trans_status = jsonObject1.getString("transaction_type");

                                    Wallet wallet = new Wallet(trans_id, trans_type, trans_amount, trans_status);
                                    transactions.add(wallet);
                                }

                                mAdapter = new Wallet_Adapter(getActivity(), transactions);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(mAdapter);

                                p_bar.setVisibility(View.GONE);
                                mwb.setText("₹" + response.getString("total_wallet_amount"));
                                lin.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);

                            } else if (status.equals("400")) {
                                p_bar.setVisibility(View.GONE);
                                mwb.setText("₹0");
                                lin.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getContext(), "Error, low internet connection", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error, low internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error, low internet connection", Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }

}
