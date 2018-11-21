package com.frame.framelibrary.utils;

import android.support.v7.widget.RecyclerView;

/**
 * Created by tangdehao on 2018/10/17.
 */

public class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (recyclerView != null) {

//                    判断是否滑动到底部， recyclerView.canScrollVertically(1);返回false表示不能往上滑动，即代表到底部了；
//                    判断是否滑动到顶部， recyclerView.canScrollVertically(-1);返回false表示不能往下滑动，即代表到顶部了；
            boolean b = recyclerView.canScrollVertically(1);
            if (!b) {
                LogUtil.e( "底部到了.....");
            }
        }
    }
}
