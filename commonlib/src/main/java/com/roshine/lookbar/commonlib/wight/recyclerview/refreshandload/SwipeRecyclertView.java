package com.roshine.lookbar.commonlib.wight.recyclerview.refreshandload;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.roshine.lookbar.commonlib.wight.recyclerview.interfaces.AutoLoadMoreListener;
import com.roshine.lookbar.commonlib.wight.recyclerview.interfaces.OnLoadMoreListener;
import com.roshine.lookbar.commonlib.wight.recyclerview.interfaces.OnRefreshListener;


/**
 * @author Roshine
 * @date 2017/8/9 15:35
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class SwipeRecyclertView  extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener, AutoLoadMoreListener {

    private SwipeRefreshLayout mRefreshLayout;
    private LSAutoRecyclertView mRecyclerView;
    private OnRefreshListener refreshListener;
    public static final int LOAD_MORE_SUC = 20;
    public static final int LOAD_MORE_FAIL = 21;
    public static final int LOAD_NO_MORE = 22;

    private boolean canRefresh = true;
    private boolean canLoad = true;
    private OnLoadMoreListener loadMoreListener;


    public SwipeRecyclertView(@NonNull Context context) {
        this(context,null);
    }

    public SwipeRecyclertView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeRecyclertView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupLayout();
    }

    private void setupLayout() {
        View view = LayoutInflater.from(getContext()).inflate(com.roshine.lookbar.commonlib.R.layout.common_swipe_recyclert_view, this);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(com.roshine.lookbar.commonlib.R.id.swip_refresh_layout);
        mRecyclerView = (LSAutoRecyclertView) view.findViewById(com.roshine.lookbar.commonlib.R.id.recyclerview);
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLoadMoreListener(this);
    }

    @Override
    public void onRefresh() {
        if (refreshListener != null && canRefresh && !mRecyclerView.getHasLoadingMore()) {
            mRecyclerView.setHasLoadingMore(true);
            mRecyclerView.setHasMore(true);
            mRecyclerView.setLoadFailed(false);
            refreshListener.onRefresh();
        }
    }
    public void setOnRefreshListener(OnRefreshListener listener){
        canRefresh = true;
        this.refreshListener = listener;
    }

    public void setOnloadMoreListener(OnLoadMoreListener listener){
        canLoad = true;
        this.loadMoreListener = listener;
    }

    public void setRefreshing(boolean flag){
        mRefreshLayout.setRefreshing(flag);
    }
    public void setLoadMoreFinish(int status){
        mRefreshLayout.setEnabled(true);
        if(mRecyclerView.getHasLoadingMore() && canLoad){
            switch (status) {
                case LOAD_MORE_SUC:
                    mRecyclerView.setLoadMoreFinish(LSAutoRecyclertView.LOAD_MORE_SUC);
                    break;
                case LOAD_MORE_FAIL:
                    mRecyclerView.setLoadMoreFinish(LSAutoRecyclertView.LOAD_MORE_FAIL);
                    break;
                case LOAD_NO_MORE:
                    mRecyclerView.setLoadMoreFinish(LSAutoRecyclertView.LOAD_NO_MORE);
                    break;
                default:
                    mRecyclerView.setLoadMoreFinish(LSAutoRecyclertView.LOAD_MORE_SUC);
                    break;
            }
        }
    }

    @Override
    public void onLoadMore() {
        if (loadMoreListener != null && canLoad && !mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setEnabled(false);
            loadMoreListener.onLoadMore();
        }
    }

    @Override
    public void onReLoadMore() {
        if (loadMoreListener != null && canLoad && !mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setEnabled(false);
            loadMoreListener.onReLoadMore();
        }
    }

    public boolean getCanRefresh() {
        return canRefresh;
    }

    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    public boolean getCanLoad() {
        return canLoad;
    }

    public void setCanLoad(boolean canLoad) {
        this.canLoad = canLoad;
    }

    public LSAutoRecyclertView getRecyclertView(){
        if (mRecyclerView != null) {
            return mRecyclerView;
        }
        return null;
    }
    public SwipeRefreshLayout getRefreshLayout(){
        if (mRefreshLayout != null) {
            return mRefreshLayout;
        }
        return null;
    }

    public void setColorSchemeColors(@ColorInt int... colors) {
        if (mRefreshLayout != null) {
            mRefreshLayout.setColorSchemeColors(colors);
        }
    }

    public void setLoadMoreProgressBarDrawbale(Drawable drawable){
        if (mRecyclerView != null) {
            mRecyclerView.setProgressBarTheme(drawable);
        }
    }

    public void setLoadMoreEnable(boolean loadMoreEnable){
        if (mRecyclerView != null) {
            mRecyclerView.setLoadMoreEnable(loadMoreEnable);
        }
    }
}
