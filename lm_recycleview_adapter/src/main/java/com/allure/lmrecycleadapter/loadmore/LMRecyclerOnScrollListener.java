package com.allure.lmrecycleadapter.loadmore;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by luomin on 16/5/13.
 *
 */
public class LMRecyclerOnScrollListener extends RecyclerView.OnScrollListener implements OnLoadListener {

    /**
     * Recycle ManagerType
     */
    protected ManagerType managerType;
    /**
     * The lastVisiableItemPosition
     */
    private int lastVisibleItemPosition;
    /**
     * the last position
     */
    private int[] lastPositions;

    /**
     * scroll State
     */
    private int currentScrollState = 0;

    public LMRecyclerOnScrollListener() {
        super();
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (managerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                managerType = ManagerType.LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                managerType = ManagerType.GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                managerType = ManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (managerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = getMaxPosition(lastPositions);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        switch (currentScrollState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                if (visibleItemCount > 0 && (lastVisibleItemPosition) >= totalItemCount - 1) {
                    onLoadMore(recyclerView);
                }
                break;

            case RecyclerView.SCROLL_STATE_SETTLING:
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
//                if (visibleItemCount > 0 && (lastVisibleItemPosition) >= totalItemCount - 1) {
//                    onLoadMore(recyclerView);
//                }
                break;
        }

    }

    private int getMaxPosition(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    @Override
    public void onLoadMore(View view) {

    }


    public static enum ManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }
}
