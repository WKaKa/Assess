package aohuan.com.assess.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public void cleanList() {
        mDatas.clear();
    }

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        // return mDatas.size();

        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        // return mDatas.get(position);
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();

    }

    public void jiaNotif(){
        Log.e("commonadapter", "----jiaNotif--");
        if(mContext!=null){
            System.out.println("----aa--");
        }else {
            System.out.println("----bb--");
        }
        if(mDatas!=null){
            Log.i("--aaa", mDatas + "");
        }
        notifyDataSetChanged();
    }

    public abstract void convert(ViewHolder helper, T item, int position);
    private ViewHolder getViewHolder(int position, View convertView,ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,position);
    }
}
