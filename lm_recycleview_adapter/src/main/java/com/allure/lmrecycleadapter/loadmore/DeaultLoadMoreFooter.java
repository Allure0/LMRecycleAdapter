package com.allure.lmrecycleadapter.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allure.lmrecycleadapter.R;


/**
 *
 * default loadmore
 * Created by luomin on 16/8/3.
 *
 */
public class DeaultLoadMoreFooter extends LoadMoreFooterLayout {


    private View mLoadingView;
    private View mTheEndView;
    private ProgressBar mLoadingProgress;
    private TextView mLoadingText;

    public DeaultLoadMoreFooter(Context context) {
        super(context);
        init(context);
    }

    public DeaultLoadMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DeaultLoadMoreFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.default_loadmore_layout, this);
        setState(State.Normal, true);
    }


    /**
     * If state End,You can do what you show.
     * @param showView
     */
    @Override
    protected void endView(boolean showView) {
        setOnClickListener(null);
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }

        if (mTheEndView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.end_viewstub);
            mTheEndView = viewStub.inflate();
        } else {
            mTheEndView.setVisibility(VISIBLE);
        }

        mTheEndView.setVisibility(showView ? VISIBLE : GONE);
    }

    /**
     * If State Loading,You can do what you show.
     * @param showView
     */
    @Override
    protected void loadingView(boolean showView) {
        setOnClickListener(null);
        if (mTheEndView != null) {
            mTheEndView.setVisibility(GONE);
        }

        if (mLoadingView == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewstub);
            mLoadingView = viewStub.inflate();

            mLoadingProgress = (ProgressBar) mLoadingView.findViewById(R.id.loading_progress);
            mLoadingText = (TextView) mLoadingView.findViewById(R.id.loading_text);
        } else {
            mLoadingView.setVisibility(VISIBLE);
        }

        mLoadingView.setVisibility(showView ? VISIBLE : GONE);

        mLoadingProgress.setVisibility(View.VISIBLE);
        mLoadingText.setText(getResources().getString(R.string.footer_loading));
    }

    /**
     * If State Normal,You can do what you show.
     * @param showView
     */
    @Override
    protected void normalView(boolean showView) {
        setOnClickListener(null);
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }

        if (mTheEndView != null) {
            mTheEndView.setVisibility(GONE);
        }

    }


}