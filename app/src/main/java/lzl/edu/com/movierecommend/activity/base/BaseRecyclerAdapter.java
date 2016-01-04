package lzl.edu.com.movierecommend.activity.base;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/27.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected List<T> dataList;
    protected Context mContext;
    protected int itemLayoutId;
    protected boolean isScrolling;
    private OnItemOnclickListener onItemOnclickListener;

    public interface OnItemOnclickListener{
        void onItemClick(View view,Object obj,int position );
    }
    //是否在滑动
    public BaseRecyclerAdapter(RecyclerView view, List<T> datas,int itemLayoutId){
        if(datas==null){
            dataList = new ArrayList<T>();
        }else{
            dataList = datas;
        }
        this.itemLayoutId = itemLayoutId;
        this.mContext = view.getContext();

        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //滑动
                isScrolling = !(newState == RecyclerView.SCROLL_STATE_IDLE);
                //没有滑动时开始加载数据
                if(!isScrolling){
                    notifyDataSetChanged();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     * Recycler适配器填充方法
     * @param holder viewHolder
     * @param item   javaBean
     * @param position
     * @param isScrolling 是否在滚动
     */
    public abstract void convert(RecyclerViewHolder holder,T item,int position,boolean isScrolling);

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View root = inflater.inflate(itemLayoutId,parent,false);
        return new RecyclerViewHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        convert(holder,dataList.get(position),position,isScrolling);
        //设置点击事件
        holder.itemView.setOnClickListener(getOnClickListener(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOnItemOnclickListener(OnItemOnclickListener l){
        onItemOnclickListener = l;
    }

    public View.OnClickListener getOnClickListener(final int position){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemOnclickListener != null && v != null){
                    onItemOnclickListener.onItemClick(v,dataList.get(position),position);
                }
            }
        };
    }
    public BaseRecyclerAdapter<T> refresh(List<T> datas){
        if(datas==null){
            dataList = new ArrayList<T>();
        }else{
            dataList = datas;
        }
        return this;
    }
}
