package com.allure.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.allure.lmrecycleadapter.interfaces.OnItemClickListener;
import com.allure.lmrecycleadapter.headerfooterwrapper.HeaderAndFooterWrapper;
import com.allure.lmrecycleadapter.interfaces.OnItemLongClickListener;
import com.allure.lmrecycleadapter.loadmore.LMRecycleView;
import com.allure.sample.adapter.TestAdapter;
import com.allure.sample.bean.TestBean;
import com.allure.sample.header_footer_view.FooterLayout;
import com.allure.sample.header_footer_view.HeaderLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luomin on 16/8/5.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private LMRecycleView mRecyclerView;
    private TestAdapter mTestAdapter;

    private List<TestBean> list = new ArrayList<>();


    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private HeaderLayout mHeaderLayout;
    private FooterLayout mFooterLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list= initData();
        mRecyclerView = (LMRecycleView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mTestAdapter = new TestAdapter(this, R.layout.test_recycle_item, list);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mTestAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);

        //you need setAdapter before inintHeaderAndFooter
        initHeaderAndFooter();
        setListener();
    }

    private void setListener() {
        mTestAdapter.setOnItemClickListener(new OnItemClickListener<TestBean>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, TestBean data) {

                Toast.makeText(MainActivity.this, String.valueOf(position)+
                        "---"+data.getName(), 1).show();

            }


        });
        mTestAdapter.setOnItemLongClickListener(new OnItemLongClickListener<TestBean>() {
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, TestBean data) {
                //if you want remove header Or Footer
//                mHeaderAndFooterWrapper.removeHeaderView(mHeaderLayout);
                Toast.makeText(MainActivity.this,
                        "LongClick---"+data.getName(), 1).show();
                return false;
            }
        });

    }

    //add list
    private void addList() {
        List<TestBean> mList=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TestBean testBean = new TestBean();
            testBean.setName("新增Item");
            mList.add(testBean);
        }
        mTestAdapter.setList(mList);
        mHeaderAndFooterWrapper.notifyDataSetChanged();
    }

    //add header and footer
    private void initHeaderAndFooter() {
        mHeaderLayout = new HeaderLayout(this);
        mFooterLayout = new FooterLayout(this);
        mHeaderAndFooterWrapper.addHeaderView(mHeaderLayout);
        mHeaderAndFooterWrapper.addFooterView(mFooterLayout);
//      mHeaderAndFooterWrapper.removeHeaderView(mHeaderLayout);
//      mHeaderAndFooterWrapper.removeFooterView(mFooterLayout);
    }

    private List<TestBean> initData() {

        for (int i = 0; i < 5; i++) {
            TestBean testBean = new TestBean();
            testBean.setName("Item"+String.valueOf(i));
            list.add(testBean);
        }
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {

            case R.id.action_muti:
                startActivity(new Intent(MainActivity.this,MultiItemActivity.class));
                break;
            case R.id.action_loadmore:
                startActivity(new Intent(MainActivity.this,LoadMoreActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
