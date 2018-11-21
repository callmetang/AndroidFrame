package com.frame.framelibrary.ui;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * 介绍：PopupWindow
 * 作者: tangdehao
 * 邮箱: dehao.tang@shiwoface.com
 * 时间: 2017/8/4 10:03
 */
public class PopupWindowHelper {


    /**
     * 显示 PopupWindow
     *
     * @param anchorView  锚点
     * @param contentView 显示的view
     * @return
     */
    public static PopupWindow showPopup(View anchorView, View contentView) {

        PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(anchorView);

        return popupWindow;

    }

    /**
     * 显示 PopupWindow
     *
     * @param anchorView  锚点
     * @param contentView 显示的view
     * @return
     */
    public static PopupWindow showPopup(View anchorView, View contentView, int width, int height) {

        PopupWindow popupWindow = new PopupWindow(contentView,
                width, height);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(anchorView);

        return popupWindow;

    }


}
