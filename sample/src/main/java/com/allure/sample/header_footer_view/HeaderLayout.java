package com.allure.sample.header_footer_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.allure.sample.R;

/**
 * Created by luomin on 16/8/5.
 */
public class HeaderLayout extends RelativeLayout {

    public HeaderLayout(Context context) {
        super(context);
        init(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        inflate(context, R.layout.header_layout, this);
    }
}
