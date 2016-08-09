package com.allure.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.allure.lmrecycleadapter.MultiItemTypeSupport;
import com.allure.lmrecycleadapter.loadmore.DeaultLoadMoreFooter;
import com.allure.lmrecycleadapter.loadmore.LMRecycleView;
import com.allure.lmrecycleadapter.headerfooterwrapper.HeaderAndFooterWrapper;
import com.allure.sample.adapter.MutiAdapter;
import com.allure.sample.bean.MutiBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luomin on 16/8/5.
 */
public class MultiItemActivity extends AppCompatActivity {
    List<MutiBean> list = new ArrayList<>();
    private MutiAdapter mMutiAdapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private  LMRecycleView mRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_muti_layout);

        initData();

        mRecycleView = (LMRecycleView) findViewById(R.id.recycle_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        MultiItemTypeSupport<MutiBean> multiItemTypeSupport = new MultiItemTypeSupport<MutiBean>() {
            @Override
            public int getLayoutId(int viewType) {
                if (viewType == MutiBean.ITEM_TYPE_CONTENT) {
                    return R.layout.multi_item_text;
                } else if (viewType == MutiBean.ITEM_TYPE_PIC) {
                    return R.layout.multi_item_image;
                }

                return viewType;
            }


            @Override
            public int getItemViewType(int position, MutiBean news) {
                return news.getItemType();
            }
        };
        mMutiAdapter=new MutiAdapter(this,multiItemTypeSupport,list);
        headerAndFooterWrapper=new HeaderAndFooterWrapper(mMutiAdapter);
        mRecycleView.setAdapter(headerAndFooterWrapper);

        initLoadMore();

    }


    private void initLoadMore() {
        mRecycleView.setFooterView(new DeaultLoadMoreFooter(this));
        mRecycleView.setCanLoadMore(true);
        mRecycleView.setAllCountSize(22);
        mRecycleView.setOnLoadMore(new LMRecycleView.OnLoadMoreListener() {
            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        loadMoreData();

                    }


                }, 3000);
            }
        });
    }

    private void loadMoreData() {
        List<MutiBean> mutiBeanList = new ArrayList<MutiBean>();
        for (int i = 0; i < 2; i++) {
            MutiBean mutiBeen = new MutiBean();
            mutiBeen.setItemType(MutiBean.ITEM_TYPE_PIC);
            mutiBeen.setResoucre(R.drawable.gyy);
            mutiBeanList.add(mutiBeen);
        }
        mMutiAdapter.setList(mutiBeanList);
        mRecycleView.loadMoreCommplete();
    }
    private void initData() {
        for (int i = 0; i < 5; i++) {
            MutiBean news = new MutiBean();
            if(i%2==0){
                news.setContent("只是内容");
                news.setItemType(MutiBean.ITEM_TYPE_CONTENT);
            }else{
                news.setResoucre(R.drawable.lbb);
                news.setItemType(MutiBean.ITEM_TYPE_PIC);
            }

            list.add(news);
        }

    }






}
