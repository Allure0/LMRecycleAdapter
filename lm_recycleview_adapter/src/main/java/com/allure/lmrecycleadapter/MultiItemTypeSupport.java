package com.allure.lmrecycleadapter;

/**
 * interface  about Different classes of Item
 * Created by luomin on 16/8/2.
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);

}
