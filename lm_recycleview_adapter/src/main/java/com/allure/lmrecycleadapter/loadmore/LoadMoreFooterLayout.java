package com.allure.lmrecycleadapter.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


/**
 * If you want custom about FooterLayout,you can extends this
 *
 * Created by luomin on 16/8/3.
 *
 */
public  abstract class LoadMoreFooterLayout extends RelativeLayout {

    protected State mState = State.Normal;


    public LoadMoreFooterLayout(Context context) {
        super(context);
    }

    public LoadMoreFooterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreFooterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public State getState() {
        return mState;
    }

    public void setState(State status ) {
        setState(status, true);
    }


    /**
     * Set States
     *
     * @param status   FooterLayout State
     * @param showView   showView    Show(true)  Hide(false)
     */
    public void setState(State status, boolean showView) {
        if (mState == status) {
            return;
        }
        mState = status;

        switch (status) {

            case Normal:

                normalView(showView);


                break;
            case Loading:
                loadingView(showView);

                break;
            case TheEnd:
                endView(showView);

                break;

            default:

                break;
        }
    }



    protected abstract void endView(boolean showView);

    protected abstract void loadingView(boolean showView);

    protected abstract void normalView(boolean showView);

    public static enum State {
        Normal/**Norlmal*/,
        TheEnd/**EndComplete*/,
        Loading/**Loading..*/

    }
}
