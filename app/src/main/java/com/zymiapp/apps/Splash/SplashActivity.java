package com.zymiapp.apps.Splash;

import android.content.Intent;

import com.squareup.picasso.Picasso;
import com.zymiapp.apps.Activities.LoginActivity;
import com.zymiapp.apps.Activities.Main_Handler_Activity;
import com.zymiapp.apps.R;
import com.zymiapp.apps.Session.Session_Manager;
import com.zymiapp.apps.network.NetworkCheck;
import com.zymiapp.apps.welcome_screen.WelcomeActivity;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    private Session_Manager session_manager;
    private TextView splsh_text;
    private View splashView;
    private Handler splashHandler;
    private Runnable splashRunnable;
    public Intent intent;
    private String base = "";
    //private ImageView bgimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashView = View.inflate(this, R.layout.activity_splash, null);
        setContentView(splashView);

        intent = getIntent();
        final Bundle extras = intent.getExtras();
        if (extras != null) {
            base = extras.getString("base");
        }

        final Animation anims = AnimationUtils.loadAnimation(this, R.anim.grow_up);
        splsh_text = findViewById(R.id.splash_text);
        splsh_text.startAnimation(anims);
        session_manager = new Session_Manager(getApplicationContext());
        splashHandler = new Handler();

        if (session_manager.isLoggedIn())
            intent = new Intent(this, Main_Handler_Activity.class);
        else
            intent = new Intent(this, WelcomeActivity.class);

        intent.putExtra("id", 0);

        splashRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                finish();
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkCheck.getInstant(this).isConnectingToInternet()) {
            //new DialogUtils(this, "Please check your internet connection.").show();
            return;
        }
        splashHandler.postDelayed(splashRunnable, 2500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!NetworkCheck.getInstant(this).isConnectingToInternet()) {
            Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            return;
        }
        splashHandler.removeCallbacks(splashRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
