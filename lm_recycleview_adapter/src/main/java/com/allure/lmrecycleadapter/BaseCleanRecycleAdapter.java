package com.allure.lmrecycleadapter;

import android.content.Context;

import com.allure.lmrecycleadapter.viewholder.BaseViewHolderHelper;

import java.util.List;


/**
 * It's Clean Adapter
 *  Created by luomin on 16/8/3.
 */
public abstract class BaseCleanRecycleAdapter<T> extends BaseRecycleAdapter<T, BaseViewHolderHelper> {



    public BaseCleanRecycleAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public BaseCleanRecycleAdapter(Context context, int layoutResId, List<T> data) {
        super(context, layoutResId, data);
    }


    protected BaseCleanRecycleAdapter(Context context, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, multiItemTypeSupport);
    }


    protected BaseCleanRecycleAdapter(Context context, MultiItemTypeSupport<T> multiItemTypeSupport, List<T> data) {
        super(context, multiItemTypeSupport, data);
    }

}
