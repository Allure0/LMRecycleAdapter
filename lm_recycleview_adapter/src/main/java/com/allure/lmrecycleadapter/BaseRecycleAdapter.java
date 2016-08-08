package com.allure.lmrecycleadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allure.lmrecycleadapter.interfaces.OnItemClickListener;
import com.allure.lmrecycleadapter.interfaces.OnItemLongClickListener;
import com.allure.lmrecycleadapter.viewholder.BaseViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luomin on 16/8/3.
 */
public abstract class BaseRecycleAdapter<T, VH extends BaseViewHolderHelper> extends RecyclerView.Adapter<BaseViewHolderHelper>

{

    protected static final String TAG = BaseRecycleAdapter.class.getSimpleName();

    protected final Context mContext;

    protected int layoutResId;

    protected List<T> mDatas;

    private OnItemClickListener mOnItemClickListener = null;

    private OnItemLongClickListener mOnItemLongClickListener;

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;


    protected BaseRecycleAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }


    protected BaseRecycleAdapter(Context context, int layoutResId, List<T> data) {
        this.mDatas = data == null ? new ArrayList<T>() : data;
        this.mContext = context;
        this.layoutResId = layoutResId;
    }

    protected BaseRecycleAdapter(Context context, MultiItemTypeSupport<T> multiItemTypeSupport) {
        this(context, multiItemTypeSupport, null);
    }

    protected BaseRecycleAdapter(Context context, MultiItemTypeSupport<T> multiItemTypeSupport, List<T> data) {
        this.mContext = context;
        this.mDatas = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.mMultiItemTypeSupport = multiItemTypeSupport;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public T getItem(int position) {
        if (position >= mDatas.size()) return null;
        return mDatas.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null) {
            return mMultiItemTypeSupport.getItemViewType(position, getItem(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolderHelper onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        if (mMultiItemTypeSupport != null) {
            int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
            view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, viewGroup, false);
        }
        BaseViewHolderHelper holder = new BaseViewHolderHelper(view);
        setListener(viewGroup, holder, viewType);
        return holder;
    }


    @Override
    public void onBindViewHolder(BaseViewHolderHelper helper, int position) {
        helper.itemView.setTag(position);
        T item = getItem(position);
        convert((VH) helper, item, position);
    }


    private void setListener(ViewGroup viewGroup, final BaseViewHolderHelper viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, viewHolder, (int) v.getTag(), mDatas.get((int) v.getTag()));

                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    return mOnItemLongClickListener.onItemLongClick(v, viewHolder, (int) v.getTag(), mDatas.get((int) v.getTag()));
                }
                return false;
            }
        });

    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected abstract void convert(VH helper, T item, int position);


    /**
     * get real position
     *
     * @param viewHolder
     * @return
     */
    protected int getPosition(RecyclerView.ViewHolder viewHolder) {

        return viewHolder.getAdapterPosition();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }


    /**
     * set a data replace oldData
     *
     * @param oldData
     * @param newData
     */
    public void set(T oldData, T newData) {
        set(mDatas.indexOf(oldData), newData);
    }

    /**
     * set a data index of position
     *
     * @param position
     * @param data
     */
    public void set(int position, T data) {
        mDatas.set(position, data);
        notifyDataSetChanged();
    }

    /**
     * get all datas
     *
     * @return
     */
    public List<T> getLists() {
        return mDatas;
    }

    /**
     * add  a  data index of position
     *
     * @param position
     * @param t
     */
    public void addOne(int position, T t) {
        mDatas.add(position, t);
        notifyItemInserted(position);
    }

    /**
     * add  a set of data
     *
     * @param list
     */
    public void setList(List<T> list) {
        int size = mDatas.size();
        mDatas.addAll(list);
        notifyItemRangeInserted(size, list.size());
    }


    /**
     * remove one data index of positon
     *
     * @param position
     */
    public void removeOne(int position) {
        if (!mDatas.isEmpty()) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }

    }



    /**
     * @param data
     * @return
     */
    public boolean contains(T data) {
        return mDatas.contains(data);
    }


    /**
     * clear all datas
     */
    public void clear() {
        if (!mDatas.isEmpty()) {
            mDatas.clear();
            this.notifyDataSetChanged();
        }
    }
}
