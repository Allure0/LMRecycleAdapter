package com.allure.lmrecycleadapter.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 *
 * Created by luomin on 16/8/3.
 */
public class BaseViewHolderHelper extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private View mConvertView;
    private Context mContext;


    public BaseViewHolderHelper(View itemView) {
        super(itemView);
        this.views = new SparseArray<View>();
    }

    public BaseViewHolderHelper(Context context, View itemView)
    {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        views = new SparseArray<View>();
    }


    public static BaseViewHolderHelper createViewHolder(Context context, View itemView)
    {
        BaseViewHolderHelper holder = new BaseViewHolderHelper(context, itemView);
        return holder;
    }

    public static BaseViewHolderHelper createViewHolder(Context context,
                                                        ViewGroup parent, int layoutId)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        BaseViewHolderHelper holder = new BaseViewHolderHelper(context, itemView);
        return holder;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    public <T extends View> T getView(int viewId) {
        return retrieveView(viewId);
    }

    /**
     * set tthe value of TextView
     * @param viewId  TextView
     * @param value value
     * @return
     */
    public BaseViewHolderHelper setText(int viewId, String value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }

    /**
     * set the image of an ImageView from a resource id.
     *
     * @param viewId    ImageView's id
     * @param imageResId The image resource id.
     * @return
     */
    public BaseViewHolderHelper setImageResource(int viewId, int imageResId) {
        ImageView view = retrieveView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * set background color of a view.
     *
     * @param viewId  View's id
     * @param color  color
     * @return
     */
    public BaseViewHolderHelper setBackgroundColor(int viewId, int color) {
        View view = retrieveView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    /**
     * set background of a view.
     *
     * @param viewId        View's id
     * @param backgroundRes  resource
     * @return
     */
    public BaseViewHolderHelper setBackgroundRes(int viewId, int backgroundRes) {
        View view = retrieveView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * set text color of a TextView.
     *
     * @param viewId    View's id
     * @param textColor The text color
     * @return
     */
    public BaseViewHolderHelper setTextColor(int viewId, int textColor) {
        TextView view = retrieveView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    /**
     * set text color of a TextView.
     *
     * @param viewId        View's id
     * @param textColorRes  text color resource id.
     * @return
     */
    public BaseViewHolderHelper setTextColorRes(int viewId, int textColorRes) {
        TextView view = retrieveView(viewId);
        view.setTextColor(view.getResources().getColor(textColorRes));
        return this;
    }

    /**
     * set the image of an ImageView
     *
     * @param viewId    View's id
     * @param drawable  image drawable.
     * @return
     */
    public BaseViewHolderHelper setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = retrieveView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }
/**
 * Add an action to set the image of an image view. Can be called multiple times.
 */
    /**
     * set the image of an ImageView
     * @param viewId  ImageView's id
     * @param bitmap   bitmap
     * @return
     */
    public BaseViewHolderHelper setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = retrieveView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }


    /**
     * set the alpha of a view
     * @param viewId View‘s id
     * @param value Alpha between 0-1.
     * @return
     */
    public BaseViewHolderHelper setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            retrieveView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            retrieveView(viewId).startAnimation(alpha);
        }
        return this;
    }

    /**
     * set a view visibility
     * @param viewId  View‘s id
     * @param visible  VISIBLE(true), GONE(false)
     * @return
     */
    public BaseViewHolderHelper setVisible(int viewId, boolean visible) {
        View view = retrieveView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * add links into a TextView.
     * @param viewId TextView's id  linkify.
     * @return
     */
    public BaseViewHolderHelper linkify(int viewId) {
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }
    /**
     * Apply the typeface to the given viewId, and enable subpixel rendering.
     */
    public BaseViewHolderHelper setTypeface(int viewId, Typeface typeface) {
        TextView view = retrieveView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    /**
     * Apply the typeface to all the given viewIds, and enable subpixel rendering.
     */
    public BaseViewHolderHelper setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = retrieveView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    /**
     * Sets the progress of a ProgressBar.
     *
     * @param viewId   ProgressBar's id
     * @param progress The progress.
     * @return
     */
    public BaseViewHolderHelper setProgress(int viewId, int progress) {
        ProgressBar view = retrieveView(viewId);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the progress and max of a ProgressBar.
     * @param viewId   ProgressBar's id
     * @param progress The progress.
     * @param max      The max value of a ProgressBar.
     * @return
     */
    public BaseViewHolderHelper setProgress(int viewId, int progress, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the range of a ProgressBar to 0...max.
     *
     * @param viewId ProgressBar's id
     * @param max    The max value of a ProgressBar.
     * @return
     */
    public BaseViewHolderHelper setMax(int viewId, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) of a RatingBar.
     *
     * @param viewId   RatingBar's id
     * @param rating The rating.
     * @return
     */
    public BaseViewHolderHelper setRating(int viewId, float rating) {
        RatingBar view = retrieveView(viewId);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) and max of a RatingBar.
     *
     * @param viewId View'is
     * @param rating rating
     * @param max    The range of the RatingBar to 0...max.
     * @return
     */
    public BaseViewHolderHelper setRating(int viewId, float rating, int max) {
        RatingBar view = retrieveView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param viewId   View's id
     * @param listener The on click listener;
     * @return
     */
    public BaseViewHolderHelper setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = retrieveView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * Sets the on touch listener of the view.
     *
     * @param viewId   View's id
     * @param listener The on touch listener;
     * @return
     */
    public BaseViewHolderHelper setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = retrieveView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     * Sets the on long click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on long click listener;
     * @return
     */
    public BaseViewHolderHelper setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = retrieveView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId View's id
     * @param tag    The tag;
     * @return
     */
    public BaseViewHolderHelper setTag(int viewId, Object tag) {
        View view = retrieveView(viewId);
        view.setTag(tag);
        return this;
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId View's id
     * @param key    The key of tag;
     * @param tag    The tag;
     * @return
     */
    public BaseViewHolderHelper setTag(int viewId, int key, Object tag) {
        View view = retrieveView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * Sets the checked status of a checkable.
     *
     * @param viewId  View's id
     * @param checked The checked status;
     * @return
     */
    public BaseViewHolderHelper setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) retrieveView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * Sets the adapter of a adapter view.
     *
     * @param viewId  View's id
     * @param adapter The adapter;
     * @return
     */
    public BaseViewHolderHelper setAdapter(int viewId, Adapter adapter) {
        AdapterView view = retrieveView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    /**
     *  convertView
     */
    public View getConvertView() {
        return itemView;
    }

    public TextView getTextView(int viewId) {
        return retrieveView(viewId);
    }

    public Button getButton(int viewId) {
        return retrieveView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return retrieveView(viewId);
    }

}
