package com.zymiapp.apps.welcome_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zymiapp.apps.Activities.LoginActivity;
import com.zymiapp.apps.R;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private View mView;
    private ViewPager welcomeViewPager;
    private TextView mVersionText;
    Button skipAndContinue;
    private CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView= View.inflate(this, R.layout.activity_welcome, null);
        setContentView(mView);
        initView(mView);
        setAdapter();
    }

    private void initView(View mView) {
        welcomeViewPager=mView.findViewById(R.id.welcomeViewPager);
        circleIndicator=mView.findViewById(R.id.circleIndicator);
        skipAndContinue=mView.findViewById(R.id.skipAndContinue);
        skipAndContinue.setOnClickListener(this);
    }

    private void setAdapter() {
        SlideAdapter slideAdapter=new SlideAdapter(this, this);
        welcomeViewPager.setAdapter(slideAdapter);
        circleIndicator.setViewPager(welcomeViewPager);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.skipAndContinue){
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            finish();
        }
    }
}
