package com.frame.framelibrary.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.frame.framelibrary.R;


/**
 * 作者: tangdehao
 * 邮箱: dehao.tang@shiwoface.com
 * 时间: 2017/7/7 11:07
 */
public class MyDialog {

    private Dialog mDialog;

    private View mContentView;

    public MyDialog(Context context) {
        mDialog = new Dialog(context, R.style.CaptainDialogStyle);
//        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }
    public MyDialog(Context context, boolean b) {
        mDialog = new Dialog(context, R.style.CaptainDialogStyle);
//        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(b);
    }
    public Dialog getDialog() {
        return mDialog;
    }

    public void showBottom(View contentView) {

        if (mContentView != null) show();
        setContentView(contentView);
        setGravity(Gravity.BOTTOM);
        setWindowAnimations(R.style.BottomDialogAnim);
        setLayout(contentView.getLayoutParams().width, ViewGroup.LayoutParams.WRAP_CONTENT);
        show();
    }

    public void showLeft(View contentView) {

        if (mContentView != null) show();
        setContentView(contentView);
        setGravity(Gravity.LEFT);
        setWindowAnimations(R.style.LeftDialogAnim);
        setLayout(contentView.getLayoutParams().width, ViewGroup.LayoutParams.WRAP_CONTENT);
        show();
    }

    public void showRight(View contentView) {

        if (mContentView != null) show();
        setContentView(contentView);
        setGravity(Gravity.RIGHT);
        setWindowAnimations(R.style.RightDialogAnim);
        setLayout(contentView.getLayoutParams().width, ViewGroup.LayoutParams.WRAP_CONTENT);
        show();
    }

    public void showTop(View contentView) {

        if (mContentView != null) show();
        setContentView(contentView);
        setGravity(Gravity.TOP);
        setWindowAnimations(R.style.TopDialogAnim);
        setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        show();
    }

    public void showCenter(View contentView) {

        if (mContentView != null) show();
        setContentView(contentView);
        setGravity(Gravity.CENTER);
        setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        show();
    }

    public void show() {
        if (mDialog != null)
            mDialog.show();
    }

    public void setWindowAnimations(int windowAnimations) {
        if (mDialog != null)
            mDialog.getWindow().setWindowAnimations(windowAnimations);
    }

    public void dismiss() {
        if (mDialog != null) {
                mDialog.dismiss();

        }
    }

    private void setContentView(View contentView) {
        if (mDialog != null) {

            this.mContentView = contentView;
            mDialog.getWindow().setContentView(mContentView);
        }
    }

    public void clearBackground() {
        mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void setGravity(int gravity) {
        if (mDialog != null)
            mDialog.getWindow().setGravity(gravity);
    }

    private void setLayout(int width, int height) {
        if (mDialog != null)
            mDialog.getWindow().setLayout(width, height);
    }
}
