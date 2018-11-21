package com.frame.framelibrary.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.frame.framelibrary.R;
import com.frame.framelibrary.beans.EventBean;
import com.frame.framelibrary.utils.LogUtil;
import com.frame.framelibrary.utils.ScreenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;

    protected LayoutInflater mInflater;

    protected View getView(int id) {
        View view = null;
        if (mInflater != null) {
            view = mInflater.inflate(id, null);
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

//    private View view;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        this.mInflater = inflater;
//        view = inflater.inflate(R.layout.fragment_base, container, false);
//        return view;
//    }

    private FrameLayout layout;
    private boolean isInit;
    protected boolean isLoad;//是否已经加载过了 加载过了不会再次加载
    private View contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);


        View v = null;

        if (
                getUserVisibleHint() &&
                        !isInit && !isLoad) {

            v = onCreateView(savedInstanceState);


        } else if (contentView != null) {

            v = contentView;

        } else {

            layout = new FrameLayout(getContext());
            layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            v = layout;
        }


        contentView = v;

        return v;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && !isInit && contentView != null && contentView.getParent() != null && layout != null && !isLoad) {

            View v = onCreateView(null);

            layout.removeAllViews();
            layout.addView(v);

        }

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private LinearLayout mRoot;


    protected LinearLayout mActionBar;
    protected FrameLayout mContainer;

    protected LinearLayout mLayoutNoNet;
    protected LinearLayout mLayoutLoading;
    protected LinearLayout mLayoutLoadError;

    protected SmartRefreshLayout mRefreshLayout;
    protected View mStatusBar;



    private View onCreateView(Bundle savedInstanceState) {

        mInflater = LayoutInflater.from(getActivity());
        final View view = mInflater.inflate(R.layout.fragment_base, null);
        Object layoutId = initLayout();

        mStatusBar = (View) view.findViewById(R.id.status_bar);
        if (mStatusBar.getLayoutParams() != null) {
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(mActivity);
        }
        showStatusBar(false);

        mRoot = (LinearLayout) view.findViewById(R.id.root);
        mActionBar = (LinearLayout) view.findViewById(R.id.action_bar);
        mContainer = (FrameLayout) view.findViewById(R.id.container);

        mRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refresh_layout);

        mLayoutNoNet = (LinearLayout) view.findViewById(R.id.layout_no_net);
        mLayoutLoading = (LinearLayout) view.findViewById(R.id.layout_loading);
        mLayoutLoadError = (LinearLayout) view.findViewById(R.id.layout_load_error);


        showNoNetView(false);
        showLoadingView(false);
        showLoadErrorView(false);


        if (layoutId != null) {
            if (layoutId instanceof Integer) {
                View layout = mInflater.inflate((int) layoutId, null);
                mRoot.removeAllViews();
                mRoot.addView(layout);
            } else if (layoutId instanceof View) {
                mContainer.removeAllViews();
                mContainer.addView((View) layoutId);
            }
        }

        initRefresh();

        initViews(view, savedInstanceState);
        initEvents();
        initDatas();

        isLoad = true;
        return view;
    }


    /**
     * 设置无网络布局,不设置则使用默认布局
     *
     * @param view
     */
    protected void setNoNetView(View view) {
        if (view != null && mLayoutNoNet != null) {
            mLayoutNoNet.removeAllViews();
            mLayoutNoNet.addView(view);
        }
    }

    /**
     * 设置加载中布局,不设置则使用默认布局
     *
     * @param view
     */
    protected void setLoadingView(View view) {
        if (view != null && mLayoutLoading != null) {
            mLayoutLoading.removeAllViews();
            mLayoutLoading.addView(view);
        }
    }

    /**
     * 设置加载失败布局,不设置则使用默认布局
     *
     * @param view
     */
    protected void setLoadErrorView(View view) {
        if (view != null && mLayoutLoadError != null) {
            mLayoutLoadError.removeAllViews();
            mLayoutLoadError.addView(view);
        }
    }

    /**
     * 刷新模式下 打开界面自动下拉刷新
     */
    protected void autoRefresh() {
        if (mRefreshLayout != null) {
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {

                    mRefreshLayout.autoRefresh();
                }
            });
        }
    }


    private void initRefresh() {
        //默认设置样式 可自行修改
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));


//        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                beginRefresh();
//            }
//        });
//        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                beginLoadMore();
//            }
//        });

    }


    /**
     * 刷新模式<br/>
     * 1: 包含下拉刷新,上拉加载<br/>
     * 2: 不显示下拉刷新,上拉加载<br/>
     * 3: 只有下拉刷新<br/>
     * 4: 只有上拉加载<br/>
     *
     * @param mode
     */
    protected void setRefreshMode(int mode) {
        switch (mode) {
            case 1:
                mRefreshLayout.setEnableRefresh(true);
                mRefreshLayout.setEnableLoadMore(true);
                break;
            case 2:
                mRefreshLayout.setEnableRefresh(false);
                mRefreshLayout.setEnableLoadMore(false);
                break;
            case 3:
                mRefreshLayout.setEnableRefresh(true);
                mRefreshLayout.setEnableLoadMore(false);
                break;
            case 4:
                mRefreshLayout.setEnableRefresh(false);
                mRefreshLayout.setEnableLoadMore(true);
                break;
        }
    }
    /**
     * 显示状态栏 默认不显示
     *
     * @param show 是否显示
     */
    protected void showStatusBar(boolean show) {
        mStatusBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    /**
     * 显示状态栏颜色
     *
     * @param color
     */
    protected void setStatusBarColor(int color) {
        if (mStatusBar!=null)
        {
            mStatusBar.setBackgroundColor(color);
        }
    }


    /**
     * 显示标题栏
     *
     * @param show 是否显示
     */
    protected void showActionBar(boolean show) {
        mActionBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    /**
     * 显示主内容
     *
     * @param show 是否显示
     */
    protected void showContentView(boolean show) {
        mContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            showNoNetView(false);
            showLoadingView(false);
            showLoadErrorView(false);
        }
    }

    /**
     * 显示没网络布局
     *
     * @param show 是否显示
     */
    protected void showNoNetView(boolean show) {
        mLayoutNoNet.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示加载中布局
     *
     * @param show 是否显示
     */
    protected void showLoadingView(boolean show) {
        mLayoutLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示加载失败布局
     *
     * @param show 是否显示
     */
    protected void showLoadErrorView(boolean show) {
        mLayoutLoadError.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    /**
     * 支持id,view
     * id 情况下没有刷新功能 标题栏还在(原始状态)
     *
     * @return
     */
    protected abstract Object initLayout();

    protected abstract void initViews(View view, Bundle savedInstanceState);

    protected abstract void initEvents();

    protected abstract void initDatas();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBean eventBean) {
        LogUtil.e("BaseFragment : " + eventBean.toString());
    }

}
