package com.allure.lmrecycleadapter.loadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.allure.lmrecycleadapter.headerfooterwrapper.HeaderAndFooterWrapper;


/**
 * Created by luomin on 16/5/13.
 * This RecycleView can LoadMore
 */
public class LMRecycleView extends RecyclerView {


    private Context mContext;
    private OnLoadMoreListener onLoadListener;
    private LoadMoreFooterLayout footerView;
    private boolean canLoadMore = true;
    private int mCountNumber = 0;
    private int realCountNumber = 0;
    private  LoadMoreFooterLayout.State state;


    public LMRecycleView(Context context) {
        super(context);
        init(context);

    }


    public LMRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public LMRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {

        addOnScrollListener(allureRecyclerOnScrollListener);
    }



    private LMRecyclerOnScrollListener allureRecyclerOnScrollListener = new LMRecyclerOnScrollListener() {
        @Override
        public void onLoadMore(View view) {
            super.onLoadMore(view);
            Adapter adapter = getAdapter();
            if (getAdapter() instanceof HeaderAndFooterWrapper) {
                 state = footerView.getState();
                if (state == LoadMoreFooterLayout.State.Loading) {
                    Log.d("@BOOM", " isLoading");
                    return;
                }
                Log.d("all count contain  ", getAdapter().getItemCount() + "");
                Log.d("header count  ", ((HeaderAndFooterWrapper) adapter).getHeadersCount() + "");
                Log.d("footer count  ", ((HeaderAndFooterWrapper) adapter).getFootersCount() + "");
                realCountNumber = getAdapter().getItemCount() - ((HeaderAndFooterWrapper) adapter).getHeadersCount() - ((HeaderAndFooterWrapper) adapter).getFootersCount();
                Log.d("RealItem Count", realCountNumber + "");
                if (mCountNumber > 0) {
                    if (realCountNumber < mCountNumber) {

                        if (onLoadListener != null && canLoadMore == true) {
                            footerView.setState(LoadMoreFooterLayout.State.Loading);
                            ((HeaderAndFooterWrapper) getAdapter()).addFooterView(footerView);
//                            scrollToPosition(getAdapter().getItemCount() - 1);
                            getAdapter().notifyDataSetChanged();
                            onLoadListener.onLoadmore();
                        }
                    } else {
                        changeLoadMoreEndSate();
                    }
                } else {
                    if (onLoadListener != null && canLoadMore == true) {
                        footerView.setState(LoadMoreFooterLayout.State.Loading);
                        ((HeaderAndFooterWrapper) getAdapter()).addFooterView(footerView);
                        scrollToPosition(getAdapter().getItemCount() - 1);
                        getAdapter().notifyDataSetChanged();
                        onLoadListener.onLoadmore();
                    }
                }

            }

        }
    };


    /**
     *  The LoadMore Layout
     * @param view
     */
    public void setFooterView(View view) {
        this.footerView = (LoadMoreFooterLayout) view;
        footerView.setState(LoadMoreFooterLayout.State.Normal);
    }


    /**
     * Loadmore call back
     *
     * @param onloadMore
     */
    public void setOnLoadMore(OnLoadMoreListener onloadMore) {
        onLoadListener = onloadMore;

    }

    /**
     * Set maximum number
     *
     * @param number
     */
    public void setAllCountSize(int number) {
        if (number > 0) {
            mCountNumber = number;
        }

    }

    /**
     * If you get datas, change state Normal,user method
     *
     */
    public void loadMoreCommplete() {
        if (footerView.getState() == LoadMoreFooterLayout.State.Loading) {
            footerView.setState(LoadMoreFooterLayout.State.Normal);
            ((HeaderAndFooterWrapper) getAdapter()).removeLoadFooterView();
            getAdapter().notifyDataSetChanged();
        }

    }

    /**
     * If you get all the data, change state End
     *
     */
    public void changeLoadMoreEndSate() {

        if (footerView.getState() != LoadMoreFooterLayout.State.TheEnd) {
            ((HeaderAndFooterWrapper) getAdapter()).addFooterView(footerView);
            getAdapter().notifyDataSetChanged();
            footerView.setState(LoadMoreFooterLayout.State.TheEnd);
        }
    }

    /**
     * get Footer's State
     * @return
     */
    public LoadMoreFooterLayout.State getFooterState(){
        return footerView.getState();
    }

    /**
     * Whether you can load more  。。。Yes(true)  No(false)
     *
     * @param canLoadMore
     */
    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }


    public interface OnLoadMoreListener {
        void onLoadmore();
    }


}
