#LMRecycleViewAdapter（[Enlish README](https://github.com/Allure0/LMRecycleAdapter/blob/master/README.md)）
-------------
简易的,多功能的,支持多个特性的的RecycleViewAdapter.特性如下。

##特性

- 简化代码,不在书写ViewHolder
- 支持Item的基本点击、长按事件,支持子控件事件监听
- 一行代码实现控件的绑定设值事件监听
- 支持随时添加（删除）头部，尾部，并支持多个header footer
- 支持上拉加载更多,可自定义加载更多布局（有的同学可能疑惑怎么不加上下拉刷新，个人认为下拉刷新不是同一级功能.若有需要请联合使用系统提供的SwipeRefreshLayout或者[android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh)）实现下拉刷新,上拉加载
- 支持多ItemType布局

###[APK 下载](https://raw.githubusercontent.com/Allure0/LMRecycleAdapter/master/demo/sample-debug.apk)
![](https://raw.githubusercontent.com/Allure0/LMRecycleAdapter/master/demo/adapter_gif.gif)

### Gradle引与使用
Gradle:  
```
dependencies {
 compile 'com.allure0:LMRecycleViewAdapter:1.0.0'
}
```
###普通Adapter示例显示：

```
       mTestAdapter = new TestAdapter(this, R.layout.test_recycle_item, list);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mTestAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
  
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

```
###Item点击、长按事件
```

   mTestAdapter.setOnItemClickListener（）;
   mTestAdapter.setOnItemLongClickListener（）;

```
###Item内子控件点击、长按事件
```

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

```

###LoadMore加载

![](https://raw.githubusercontent.com/Allure0/LMRecycleAdapter/master/demo/loadmore.png)

```
       
  LMRecycleView  mRecyclerView = (LMRecycleView) findViewById(R.id.recycle_view);

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
                            testBean.setName("新增" + name[i]);
                            testBean.setDesc("大家好,我是新增:" + name[i]);
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
```
###多Header Footer添加与移除(可添加多个)

![](https://raw.githubusercontent.com/Allure0/LMRecycleAdapter/master/demo/normal.png)

```

        mHeaderAndFooterWrapper.addHeaderView(mHeaderLayout);
        mHeaderAndFooterWrapper.addFooterView(mFooterLayout);
//      mHeaderAndFooterWrapper.removeHeaderView(mHeaderLayout);
//      mHeaderAndFooterWrapper.removeFooterView(mFooterLayout);
```
###多ItemType布局

![](https://raw.githubusercontent.com/Allure0/LMRecycleAdapter/master/demo/muti.png)

```
<------------Activity Or Fragment------------------>

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
        
<------------Adapter------------------>
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
        
```
###注意事项
在使用了HeaderAndFooterWrapper装饰器后,若有数据变化,请在原有Adapter设置变化后，在调用一次mHeaderAndFooterWrapper.notifyDataSetChanged(）更新数据。否则数据会显示不正常。

###TODO
若有BUG或者疑问,请提交Issues。或者QQ群:[482906631]()
或者emal:[qq395118726@gmail.com]()

###感谢
[base-adapter-helper](https://github.com/JoanZapata/base-adapter-helper)


## License
Copyright 2016 Allure

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
