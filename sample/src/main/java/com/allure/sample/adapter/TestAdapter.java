package com.allure.sample.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.allure.lmrecycleadapter.BaseCleanRecycleAdapter;
import com.allure.lmrecycleadapter.viewholder.BaseViewHolderHelper;
import com.allure.sample.R;
import com.allure.sample.bean.TestBean;

import java.util.List;

/**
 * Created by luomin on 16/8/5.
 */
public class TestAdapter extends BaseCleanRecycleAdapter<TestBean> {


    public TestAdapter(Context context, int layoutResId, List<TestBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolderHelper helper, TestBean item, final int position) {
        helper.setText(R.id.name, item.getName())
                .setText(R.id.desc, item.getDesc());
        //Onclick
        helper.setOnClickListener(R.id.name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Position",position+"");
            }
        });
       /* //OnLongClick
        helper.setOnLongClickListener();
        //OnTouch
        helper.setOnTouchListener();*/
    }
}
