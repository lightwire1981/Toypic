package com.example.tpictest.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.tpictest.R;

public class CustomDialog extends Dialog {

    public enum DIALOG_CATEGORY {
        LOGIN,
        PASSWORD
    }
    private final DIALOG_CATEGORY dialog_category;

    public interface DialogResponseListener {
        void getResponse(boolean response, Object data);
    }

    private DialogResponseListener dialogResponseListener;

    public CustomDialog(@NonNull Context context, DIALOG_CATEGORY dialog_category) {
        super(context);
        this.dialog_category = dialog_category;
    }

    public CustomDialog(Context context, DIALOG_CATEGORY dialog_category, DialogResponseListener dialogResponseListener) {
        super(context);
        this.dialog_category = dialog_category;
        this.dialogResponseListener = dialogResponseListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (dialog_category) {
            case LOGIN:
                setContentView(R.layout.dialog_login_confirm);
                findViewById(R.id.btnDlgLoginNo).setOnClickListener(onClickListener);
                findViewById(R.id.btnDlgLoginYes).setOnClickListener(onClickListener);
                break;
            case PASSWORD:
                break;
            default:
                break;
        }
        setLayoutParameter();
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.btnDlgLoginNo:
                dialogResponseListener.getResponse(false, null);
                dismiss();
                break;
            case R.id.btnDlgLoginYes:
                dialogResponseListener.getResponse(true, null);
                dismiss();
                break;
            default:
                break;
        }
    };

    private void setLayoutParameter() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND|WindowManager.LayoutParams.FLAG_FULLSCREEN;
        layoutParams.dimAmount = 0.8f;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        this.setCanceledOnTouchOutside(false);

        switch (dialog_category) {
            case LOGIN:
                layoutParams.height = displayMetrics.heightPixels;
                layoutParams.width = (int)(displayMetrics.widthPixels*0.9);
                break;
            case PASSWORD:
                break;
            default:
                break;
        }

        getWindow().setAttributes(layoutParams);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}