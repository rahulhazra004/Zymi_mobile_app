package com.zymiapp.apps.welcome_screen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zymiapp.apps.R;

public class SlideAdapter extends PagerAdapter {
    private Context mContext;
    WelcomeActivity mWelcomeActivity;
    View mView;
    ImageView imageView;

    SlideAdapter(Context context, WelcomeActivity welcomeActivity) {
        mContext = context;
        mWelcomeActivity = welcomeActivity;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) layoutInflater.inflate(R.layout.slidepager, container, false);
        LinearLayout linearLayout = layout.findViewById(R.id.llSlidingLayout);

        switch (position) {
            case 0:
                mView = View.inflate(mContext, R.layout.first_onboardind_layout, null);
                linearLayout.addView(mView);
                break;
            case 1:
                mView = View.inflate(mContext, R.layout.second_onboardind_layout, null);
                linearLayout.addView(mView);
                break;
            case 2:
                mView = View.inflate(mContext, R.layout.third_onboardind_layout, null);
                /*imageView=mView.findViewById(R.id.profile_image);
                Glide.with(mContext).load(R.drawable.cashback).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });*/
                /*mView.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mWelcomeActivity.skipAndContinue.performClick();
                    }
                });*/
                linearLayout.addView(mView);
                break;
            case 3:
                mView = View.inflate(mContext, R.layout.forth_onboarding_layout, null);
                mView.findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mWelcomeActivity.skipAndContinue.performClick();
                    }
                });
                linearLayout.addView(mView);
                break;
        }
        container.addView(layout);
        return layout;
    }
}
