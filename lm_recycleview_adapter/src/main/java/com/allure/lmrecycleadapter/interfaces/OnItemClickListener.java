package com.allure.lmrecycleadapter.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by luomin on 16/8/4.
 */
public interface OnItemClickListener<T> {
    void onItemClick(View view, RecyclerView.ViewHolder holder, int position,T data);

}
