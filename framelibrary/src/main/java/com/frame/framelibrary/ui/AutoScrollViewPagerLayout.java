package com.frame.framelibrary.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.frame.framelibrary.R;
import com.frame.framelibrary.adapters.ViewPagerAdapter;
import com.frame.framelibrary.ui.autoviewpager.AutoScrollViewPager;
import com.frame.framelibrary.utils.ToastUtil;
import com.frame.framelibrary.utils.image.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动切换的viewpage 已经封装好底部切换小圆点
 * 如果希望自定义 则使用 AutoScrollViewPager即可
 * Created by tangdehao on 2018/10/22.
 */

public class AutoScrollViewPagerLayout extends FrameLayout {
    public AutoScrollViewPagerLayout(@NonNull Context context) {
        this(context, null);
    }

    public AutoScrollViewPagerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollViewPagerLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private AutoScrollViewPager mAutoScrollViewPager;
    private LinearLayout mLayoutIndicator;


    private void init(Context context) {
        View.inflate(context, R.layout.item_banner, this);


        mAutoScrollViewPager = (AutoScrollViewPager) findViewById(R.id.vp_banner);
        mLayoutIndicator = (LinearLayout) findViewById(R.id.layout_indicator);


    }

    public AutoScrollViewPager getAutoScrollViewPager() {
        return mAutoScrollViewPager;
    }


    /**
     * 开始自动切换
     */
    public void onResume() {
        if (mAutoScrollViewPager != null) {
            mAutoScrollViewPager.startAutoScroll();
        }
    }

    /**
     * 暂停自动切换
     */
    public void onPause() {
        if (mAutoScrollViewPager != null) {
            mAutoScrollViewPager.stopAutoScroll();
        }
    }

    /**
     * 准备数据
     *
     * @param context
     * @param items
     */
    public void setup(Context context, List<String> items, final int minHeight, final int maxHeight) {
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        mLayoutIndicator.setGravity(Gravity.BOTTOM);
//        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
//        layoutParams.bottomMargin = DensityUtil.dip2px(5);
//        mLayoutIndicator.setLayoutParams(layoutParams);


        final List<View> views = new ArrayList<>();

        for (final String s : items) {
            FrameLayout frameLayout = new FrameLayout(context);
            final ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            GlideUtils.loadToHttp(context, s, imageView);

//            GlideUtils.loadToHttpOfListener(context, s, new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//
//                    if (resource != null && resource.getHeight() > 0) {
//
//                        if (getLayoutParams() != null) {
//                            int h = resource.getHeight();
//                            if (h < minHeight) {
//                                h = maxHeight;
//                            }else if (h > maxHeight) {
//                                h = maxHeight;
//                            }
//                            LogUtil.e("轮播图高度: "+h);
//                            getLayoutParams().height = h;
//                        }
//                        imageView.setImageBitmap(resource);
////                        setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, resource.getHeight()));
//                    }
//                }
//            });
            if (getLayoutParams() != null) {
                getLayoutParams().height = minHeight;
            }

//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ToastUtil.show(s);
//                }
//            });

            frameLayout.addView(imageView);
            views.add(frameLayout);
        }


        final List<View> indicators = new ArrayList<>();
        if (views.size() >= 2) {
            for (int i = 0; i < views.size(); i++) {
                View point = View.inflate(context, R.layout.view_viewpager_indicator, null);
                View indicator = point.findViewById(R.id.indicator);
                indicators.add(indicator);

                mLayoutIndicator.addView(point);
            }
            if (indicators.size() > 0) {
                indicators.get(0).setBackgroundResource(R.drawable.shape_banner_selected);
            }

            mAutoScrollViewPager.startAutoScroll();
        }


        mAutoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (views.size() >= 2) {
                    for (View in : indicators) {
                        in.setBackgroundResource(R.drawable.shape_banner_nomal);
                    }
                    indicators.get(position).setBackgroundResource(R.drawable.shape_banner_selected);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        ViewPagerAdapter adapter = new ViewPagerAdapter(views);

        mAutoScrollViewPager.setAdapter(adapter);
    }
}
