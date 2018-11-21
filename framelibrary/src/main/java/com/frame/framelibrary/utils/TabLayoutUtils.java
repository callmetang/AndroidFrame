package com.frame.framelibrary.utils;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.framelibrary.core.App;

import java.lang.reflect.Field;

/**
 * Created by a on 2018/10/31.
 */

public class TabLayoutUtils {

    public static void measuredTabWidth(final TabLayout mTabLayout) {
        if (mTabLayout == null) return;
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = mTabLayout.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);

                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(mTabLayout);

                    int dp10 = DensityUtil.dip2px(8);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;//+ dp10 * 2;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 根据tab 个数来测量下划线宽度
     * @param mTabLayout
     * @param tabSize
     */
    public static void measuredTabWidth(final TabLayout mTabLayout, final int tabSize) {
        if (mTabLayout == null) return;
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = mTabLayout.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);

                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(mTabLayout);


                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;//+ dp10 * 2;
                        params.leftMargin = (ScreenUtils.getScreenWidth(App.getInstance()) / tabSize - width) / 2;
                        params.rightMargin = (ScreenUtils.getScreenWidth(App.getInstance()) / tabSize - width) / 2;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setIndicator(final TabLayout tabs, final int leftDip, final int rightDip) {

        try {
            tabs.post(new Runnable() {
                @Override
                public void run() {
                    Class<?> tabLayout = tabs.getClass();
                    Field tabStrip = null;
                    try {
                        tabStrip = tabLayout.getDeclaredField("mTabStrip");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    tabStrip.setAccessible(true);
                    LinearLayout llTab = null;
                    try {
                        llTab = (LinearLayout) tabStrip.get(tabs);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
                    int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
                    for (int i = 0; i < llTab.getChildCount(); i++) {
                        View child = llTab.getChildAt(i);
                        child.setPadding(0, 0, 0, 0);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                        params.leftMargin = left;
                        params.rightMargin = right;
                        child.setLayoutParams(params);
                        child.invalidate();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
