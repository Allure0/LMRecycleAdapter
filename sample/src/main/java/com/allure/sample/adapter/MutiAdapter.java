package com.allure.sample.adapter;

import android.content.Context;

import com.allure.lmrecycleadapter.BaseCleanRecycleAdapter;
import com.allure.lmrecycleadapter.MultiItemTypeSupport;
import com.allure.lmrecycleadapter.viewholder.BaseViewHolderHelper;
import com.allure.sample.R;
import com.allure.sample.bean.MutiBean;

import java.util.List;

/**
 * Created by luomin on 16/8/5.
 */
public class MutiAdapter extends BaseCleanRecycleAdapter<MutiBean> {


    public MutiAdapter(Context context, MultiItemTypeSupport<MutiBean> multiItemTypeSupport, List<MutiBean> data) {
        super(context, multiItemTypeSupport, data);
    }

    @Override
    protected void convert(BaseViewHolderHelper helper, MutiBean item, int position) {
        switch (helper.getItemViewType()) {
            case MutiBean.ITEM_TYPE_CONTENT:
                helper.setText(R.id.text_content,item.getContent());
                break;
            case MutiBean.ITEM_TYPE_PIC:
                helper.setImageResource(R.id.image,item.getResoucre());

                break;

        }
    }
}
