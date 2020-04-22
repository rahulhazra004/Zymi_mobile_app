package com.zymiapp.apps.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.zymiapp.apps.Adapters.MyAdapter;
import com.zymiapp.apps.Applications.AppController;
import com.zymiapp.apps.Model.Slider;
import com.zymiapp.apps.R;
import com.zymiapp.apps.Session.Session_Manager;

import me.relex.circleindicator.CircleIndicator;


public class Verification extends AppCompatActivity {

    private static final String KEY_OTP = "c_otp";
    private static final String KEY_REF_EMAIL = "c_mobile";
    private static final String VERIFICATION_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/verifyOTP/API-KEY/123456";
    private static final String RESEND_OTP_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/resendOTP/API-KEY/123456";
    private ProgressDialog mProgressDialog;
    private String mobile;
    private Session_Manager session_manager;
    private EditText otp;
    private Button btn;
    //private TextView resend_otp;
    private int seconds=30;
    private ViewPager mPager;
    private MyAdapter myAdapter;
    private List<Slider> sliders = new ArrayList<>();
    private static final String IMG_URL = "https://www.resellingapp.com/apiv2/zymi/rest_server/getHomeSlider/API-KEY/123456";
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras!=null){
            mobile = extras.getString("mobile");
        }

        session_manager = new Session_Manager(getApplicationContext());

        otp = (EditText)findViewById(R.id.email);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTP();
                showProgressDialog();
            }
        });

        //resend_otp = (TextView) findViewById(R.id.otp_link);

/*        //Declare the timer
        final Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        resend_otp.setText(String.valueOf("00")+":"+String.valueOf(seconds));
                        seconds -= 1;
                        if(seconds == 0)
                        {
                            resend_otp.setText("Didn't receive the code? Send Again");
                            t.cancel();
                        }
                    }
                });
            }

        }, 0, 1000);

        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seconds==0){
                    showProgressDialog();
                    resendOTP();
                }
            }
        });*/


        mPager = (ViewPager) findViewById(R.id.pager);
        getImages();
        mPager.setAdapter(myAdapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        NUM_PAGES = sliders.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPager.getParent().requestDisallowInterceptTouchEvent(true);
            }
        });

    }



    private void verifyOTP(){

        Map<String, String> postParam= new HashMap<>();
        postParam.put(KEY_REF_EMAIL,mobile);
        postParam.put(KEY_OTP,otp.getText().toString().trim());

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                VERIFICATION_URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String status = response.getString("status");

                            hideProgressDialog();

                            if(status.equals("201")){
                                Toast.makeText(Verification.this, "Welcome to ZyMi", Toast.LENGTH_SHORT).show();
                                LoginActivity.getInstance().finish();
                                session_manager.setLogin(true);

                                Intent intent = new Intent(getApplicationContext(),Main_Handler_Activity.class);
                                startActivity(intent);

                                finish();

                            }else {
                                Toast.makeText(Verification.this, "Failed...Call 7909029245 for Assistant", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                Toast.makeText(Verification.this,"Connection Error",Toast.LENGTH_LONG).show();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }

    private void resendOTP(){

        Map<String, String> postParam= new HashMap<>();
        postParam.put(KEY_REF_EMAIL,mobile);

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                RESEND_OTP_URL, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String status = response.getString("status");

                            mProgressDialog.hide();

                            if(status.equals("200")){
                                Toast.makeText(Verification.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(Verification.this, "Connection Error", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                Toast.makeText(Verification.this,"Connection Error",Toast.LENGTH_LONG).show();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);

    }



    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    void getImages(){

        String tag_json_obj = "json_obj_req";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                IMG_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        sliders.clear();
                        try {
                            String status = response.getString("status");

                            if (status.equals("200")){
                                JSONArray jsonArray = response.getJSONArray("userData");
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                               // wp_no = jsonObject1.getString("gen_whatsapp_number");
                               // wp_no_b = jsonObject1.getString("biz_whatsapp_number");
                               // wp_text = jsonObject1.getString("gen_whatsapp_text");
                               // wp_b_text = jsonObject1.getString("biz_whatsapp_text");



                                for (int i=0;i<jsonArray.length();i++){

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String img_url = jsonObject.getString("img_dest");
                                    String base_activity = jsonObject.getString("base_activity");
                                    String cat_id = jsonObject.getString("cat_id");

                                    sliders.add(new Slider(img_url,base_activity,cat_id));
                                }

                                myAdapter = new MyAdapter(Verification.this, sliders,null);
                                mPager.setAdapter(myAdapter);

                            }else {
                                Toast.makeText(Verification.this, "Connection Error", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Verification.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

}
