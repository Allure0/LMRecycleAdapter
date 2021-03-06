package com.allure.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allure.lmrecycleadapter.headerfooterwrapper.HeaderAndFooterWrapper;
import com.allure.lmrecycleadapter.interfaces.OnItemClickListener;
import com.allure.lmrecycleadapter.loadmore.DeaultLoadMoreFooter;
import com.allure.lmrecycleadapter.loadmore.LMRecycleView;
import com.allure.sample.adapter.TestAdapter;
import com.allure.sample.bean.TestBean;
import com.allure.sample.header_footer_view.HeaderLayout;
import com.allure.sample.header_footer_view.HeaderLayout2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luomin on 16/8/5.
 */
public class LoadMoreActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LMRecycleView mRecyclerView;
    private TestAdapter mTestAdapter;

    private List<TestBean> list = new ArrayList<>();

    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private HeaderLayout mHeaderLayout;
    private HeaderLayout2 mHeaderLayout2;
    private RelativeLayout emptyView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadmore_layout);
        list.addAll( initData());
        emptyView= (RelativeLayout) findViewById(R.id.empty);

        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mRecyclerView = (LMRecycleView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mTestAdapter = new TestAdapter(this, R.layout.test_recycle_item, list);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mTestAdapter);
        //you need setAdapter first
        initHeaderAndFooter();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTestAdapter.clear();
                            mTestAdapter.setList(initData());
                            mHeaderAndFooterWrapper.notifyDataSetChanged();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    },2000);
                }


        });
        initLoadMore();
        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
        mRecyclerView.setEmptyView(emptyView);
        setListener();
    }

    private void initLoadMore() {
        //you can use  custom loadmore view,DefaultLoadMore just a default view
        mRecyclerView.setFooterView(new DeaultLoadMoreFooter(this));
        //loadMore(true)
        mRecyclerView.setCanLoadMore(true);
        //Max Count
        mRecyclerView.setAllCountSize(20);
        //LoadMore CallBack
        mRecyclerView.setOnLoadMore(new LMRecycleView.OnLoadMoreListener() {
            @Override
            public void onLoadmore() {
               mSwipeRefreshLayout.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<TestBean> mlTestBeen = new ArrayList<TestBean>();
                        for (int i = 0; i < 2; i++) {
                            TestBean testBean = new TestBean();
                            testBean.setName("新增Item" );
                            mlTestBeen.add(testBean);
                        }
                        mTestAdapter.setList(mlTestBeen);
                        mRecyclerView.loadMoreCommplete();
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }, 3000);
            }
        });
    }

    private void setListener() {
        mTestAdapter.setOnItemClickListener(new OnItemClickListener<TestBean>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, TestBean data) {
                //if you want remove header Or Footer
//                mHeaderAndFooterWrapper.removeHeaderView(mHeaderLayout);
//                mHeaderAndFooterWrapper.removeHeaderView(mHeaderLayout);
                mHeaderAndFooterWrapper.removeHeaderView(mHeaderLayout2);
                mHeaderAndFooterWrapper.removeHeaderView(mHeaderLayout);
                mTestAdapter.clear();
                mHeaderAndFooterWrapper.notifyDataSetChanged();
                Toast.makeText(LoadMoreActivity.this, String.valueOf(data.getName()), 1).show();
            }


        });

    }


    //add header and footer
    private void initHeaderAndFooter() {
        mHeaderLayout = new HeaderLayout(this);
        mHeaderLayout2 = new HeaderLayout2(this);
        mHeaderAndFooterWrapper.addHeaderView(mHeaderLayout);
        mHeaderAndFooterWrapper.addHeaderView(mHeaderLayout2);
    }

    private List<TestBean> initData() {
        List<TestBean> list1=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TestBean testBean = new TestBean();
            testBean.setName("Item"+String.valueOf(i));
            list.add(testBean);
        }
        return list1;
    }


}
