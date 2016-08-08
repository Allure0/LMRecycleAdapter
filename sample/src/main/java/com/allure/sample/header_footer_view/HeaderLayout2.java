package com.allure.sample.header_footer_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.allure.sample.R;

/**
 * Created by luomin on 16/8/8.
 */
public class HeaderLayout2 extends RelativeLayout {

    public HeaderLayout2(Context context) {
        super(context);
        init(context);
    }

    public HeaderLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HeaderLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        inflate(context, R.layout.header_layout2, this);
    }
}
