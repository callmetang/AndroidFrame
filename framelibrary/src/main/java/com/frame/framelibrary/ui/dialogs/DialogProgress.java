package com.frame.framelibrary.ui.dialogs;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.frame.framelibrary.R;
import com.wang.avi.AVLoadingIndicatorView;


/**
 * Created by tangdehao on 2018/4/13.
 */

public class DialogProgress {

    private Context context;

    public DialogProgress(Context context) {
        this.context = context;
        myDialog = new MyDialog(context);
        myDialog.clearBackground();
        viewDialog = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        textView = (TextView) viewDialog.findViewById(R.id.fg_base_loading_msg);
        mAvi = (AVLoadingIndicatorView) viewDialog.findViewById(R.id.avi);

    }

    private View viewDialog;
    private MyDialog myDialog;
    private TextView textView;
    private AVLoadingIndicatorView mAvi;


    public void setText(String info) {

        if (!TextUtils.isEmpty(info))
            this.textView.setText(info);
    }

    public void showProgress(boolean show) {

        if (show) {
            mAvi.show();
            mAvi.setVisibility(View.VISIBLE);
            myDialog.showCenter(viewDialog);
        } else {
            myDialog.dismiss();
            mAvi.hide();
        }

    }
}
