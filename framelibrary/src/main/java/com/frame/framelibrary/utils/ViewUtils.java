package com.frame.framelibrary.utils;

import android.graphics.Point;
import android.view.View;

/**
 * Created by tangdehao on 2018/7/4.
 */

public class ViewUtils {
    public static Point measure(View view) {
        //测量方法
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        int w = view.getMeasuredWidth();
        int h = view.getMeasuredHeight();
        return new Point(w, h);
    }
}
