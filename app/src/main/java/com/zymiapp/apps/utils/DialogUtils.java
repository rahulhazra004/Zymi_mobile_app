package com.zymiapp.apps.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zymiapp.apps.R;

public class DialogUtils extends AppCompatDialog implements View.OnClickListener {
    public DialogUtils(@NonNull Context context, String string) {
        super(context);
        AlertText=string;
    }
    public DialogUtils(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogUtils(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private View mView;
    private Button btnOk;
    TextView tvAlertText;
    private String AlertText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mView = View.inflate(getContext(), R.layout.popup_dialog, null);
        setContentView(mView);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        //wmlp.windowAnimations = R.style.DialogAnimation;
        initView(mView);
        tvAlertText.setText(AlertText);
    }

    private void initView(View mView) {
        tvAlertText = mView.findViewById(R.id.tvAlertText);
        btnOk = mView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnOk) {
            dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}
