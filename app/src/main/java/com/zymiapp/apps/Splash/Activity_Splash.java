package com.zymiapp.apps.Splash;

import android.content.Intent;

import com.squareup.picasso.Picasso;
import com.zymiapp.apps.Activities.Login_Actvity;
import com.zymiapp.apps.Activities.Main_Handler_Activity;
import com.zymiapp.apps.R;
import com.zymiapp.apps.Session.Session_Manager;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Splash extends AppCompatActivity {

    private Session_Manager session_manager;
    private String base="";
    private TextView splsh_text;
    private ImageView bgimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Intent intent = getIntent();
        final Bundle extras = intent.getExtras();
        if (extras!=null){
            base = extras.getString("base");
        }
        final Animation anims = AnimationUtils.loadAnimation(this, R.anim.grow_up);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/LobsterTwo_Regular.ttf");

        splsh_text = (TextView) findViewById(R.id.splash_text);
        bgimg = (ImageView) findViewById(R.id.bgimg);
        splsh_text.setTypeface(tf2);

        Picasso.with(this).load(R.drawable.bg).fit().into(bgimg);

        splsh_text.startAnimation(anims);
        session_manager = new Session_Manager(getApplicationContext());

        Thread shivThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (session_manager.isLoggedIn()){

                    try{
                        if (base.equals("cat_details")){
                            Intent intent = new Intent(getApplicationContext(), Main_Handler_Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("id",1);
                            intent.putExtra("cat",extras.getString("cat"));
                            startActivity(intent);
                            overridePendingTransition (0, 0);
                            finish();
                        }else {
                            Intent intent = new Intent(getApplicationContext(), Main_Handler_Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("id",0);
                            intent.putExtra("cat","");
                            startActivity(intent);
                            overridePendingTransition (0, 0);
                            finish();
                        }

                    }catch(NullPointerException e){
                        Intent intent = new Intent(getApplicationContext(), Main_Handler_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("id",0);
                        intent.putExtra("cat","");
                        startActivity(intent);
                        overridePendingTransition (0, 0);
                        finish();
                    }

                }else {
                    Intent intent = new Intent(getApplicationContext(), Login_Actvity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition (0, 0);
                    finish();
                }


            }
        });

        shivThread.start();
    }
}
