package com.example.whereto;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class postDialog extends Dialog implements View.OnClickListener{

    private TextView tv_tittle,tv_message,tv_cancel,tv_confirm;

    private String title,message,cancel,confirm;

    private IOnCancelListener cancelListener;

    private IOnConfirmListener confirmListener;

    public postDialog(@NonNull Context context) {
        super(context);
    }

    public postDialog(@NonNull Context context, int themeId) {
        super(context);
    }

    public postDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public postDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public postDialog setCancel(String cancel, IOnCancelListener listener) {
        this.cancel = cancel;
        this.cancelListener = listener;
        return this;
    }

    public postDialog setConfirm(String confirm, IOnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener = listener;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog);

        //set Width:


        tv_tittle = findViewById(R.id.tv_title);
        tv_message = findViewById(R.id.tv_message);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_confirm = findViewById(R.id.tv_confirm);

        if(!TextUtils.isEmpty(title)){
            tv_tittle.setText(title);

        }
        if(!TextUtils.isEmpty(message)){
            tv_message.setText(message);
        }
        if(!TextUtils.isEmpty(cancel)){
            tv_cancel.setText(cancel);
        }
        if(!TextUtils.isEmpty(confirm)){
            tv_confirm.setText(confirm);
        }

        tv_cancel.setOnClickListener(this::onClick);
        tv_confirm.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                if(cancelListener != null){
                    cancelListener.onCancel(this);
                }
                dismiss();
                break;
            case R.id.tv_confirm:
                if(confirmListener != null){
                    confirmListener.onConfirm(this);
                }
                dismiss();
                break;
        }
    }

    public interface IOnCancelListener{
        void onCancel(postDialog dialog);
    }

    public interface IOnConfirmListener{
        void onConfirm(postDialog dialog);
    }
}
