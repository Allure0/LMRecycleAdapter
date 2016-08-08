package com.allure.lmrecycleadapter.headerfooterwrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.allure.lmrecycleadapter.viewholder.BaseViewHolderHelper;


/**
 * Created by luomin on 16/8/4.
 */
public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            BaseViewHolderHelper holder = BaseViewHolderHelper.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
            return holder;

        } else if (mFootViews.get(viewType) != null) {
            BaseViewHolderHelper holder = BaseViewHolderHelper.createViewHolder(parent.getContext(), mFootViews.get(viewType));
            return holder;
        }else{
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isHeader(position)) {
                        return gridManager.getSpanCount();
                    } else if (isFooter(position)) {
                        return gridManager.getSpanCount();

                    } else {
                        return 1;
                    }

                }
            });
        }

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && (holder.getLayoutPosition() < mHeaderViews.size() || holder.getLayoutPosition() >= getItemCount() - mFootViews.size())) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }

    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }


    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFooterView(View view) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }




    /**
     * isHeader
     *
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return getHeadersCount() > 0 && position < mHeaderViews.size();
    }

    /**
     * isFooter
     *
     * @param position
     * @return
     */
    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - mFootViews.size();
        return getFootersCount() > 0 && position >= lastPosition;
    }


    /**
     * remove header instance of position
     *
     * @param position
     */
    public void removeHeaderView(int position) {
        mHeaderViews.delete(position + BASE_ITEM_TYPE_HEADER);
        notifyDataSetChanged();

    }

    /**
     * remove header instance of view
     * @param view
     */
    public void removeHeaderView(View view) {
        removeHeaderView(mHeaderViews.indexOfValue(view) );
    }

    /**
     * remove footer instance of position
     *
     * @param position
     */
    public void removeFooterView(int position) {
        mFootViews.delete(position + BASE_ITEM_TYPE_FOOTER);
        notifyDataSetChanged();
    }

    /**
     * remove footer instance of view
     * @param view
     */
    public void removeFooterView(View view) {
        removeFooterView(mFootViews.indexOfValue(view) );

    }

    /**
     * remove loadmore footer
     */
    public void removeLoadFooterView() {

       clearAllFooters();
    }

    /**
     * clear all headers
     */
    public void clearAllHeaders() {

        mHeaderViews.clear();
        notifyDataSetChanged();
    }

    /**
     * clear all footers
     */
    public void clearAllFooters() {

        mFootViews.clear();
        notifyDataSetChanged();
    }


}
