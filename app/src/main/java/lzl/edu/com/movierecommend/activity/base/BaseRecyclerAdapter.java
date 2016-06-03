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
    private static final String TAG="BaseRecyclerAdapter";
    protected List<T> dataList;
    protected Context mContext;
    protected int itemLayoutId;
    protected boolean isScrolling;
    //headView
    private static final int TYPE_HEADER = 0;
    //正常的View
    private static final int TYPE_NORMAL = 1;
    //判断footer
    private static final int TYPE_FOOTER = 2;
    private View headerView;
    private View footerView;
    private int totalList;
    private OnItemOnclickListener onItemOnclickListener;
    public interface OnItemOnclickListener{
        void onItemClick(View view,Object obj,int position);
    }
    public void setFooterView(View footerView){
        this.footerView = footerView;
        notifyDataSetChanged();
    }
    /**
     * 在第一行item中插入HeaderView
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }
    public void setData(List<T> list){
        dataList = list;
    }
    private int getRealPosition(int position){
        return headerView ==null ? position : position-1;
    }
    /**
     * 通过当前position来判断每个item的类型
     * 如果postion=0并且HeaderView不为空，则是TYPE_HEADER类型
     * 如果position=size+1且FooterView不为空，则为TYPE_FOOTER类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(position==0 && headerView !=null){
            return TYPE_HEADER;
        }else if(position+1==getItemCount() && footerView!=null){
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }
    //是否在滑动
    public BaseRecyclerAdapter(RecyclerView view, List<T> datas,int itemLayoutId){
        if(datas==null){
            this.dataList = new ArrayList<T>();
        }else{
            this.dataList = datas;
        }
        this.itemLayoutId = itemLayoutId;
        this.mContext = view.getContext();

        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //滑动，RecyclerView.SCROLL_STATE_IDLE表示滑动停止
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
        if(headerView!=null && viewType == TYPE_HEADER) {
            return new RecyclerViewHolder(headerView);
        }else if(footerView!=null && viewType == TYPE_FOOTER){
            return new RecyclerViewHolder(footerView);
        }

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View root = inflater.inflate(itemLayoutId,parent,false);
        return new RecyclerViewHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if(getItemViewType(position)!=TYPE_NORMAL) {
           return;
        }
        int p = getRealPosition(position);
        convert(holder, dataList.get(p), p, isScrolling);
        //设置点击事件
        holder.itemView.setOnClickListener(getOnClickListener(position));
    }
   @Override
   public int getItemCount() {
       if(dataList == null){
           return 0;
       }
       if(headerView!=null && footerView!=null){
           //头尾都不为空
           totalList =  dataList.size()+2;
       }else if(headerView ==null && footerView ==null){
           //头尾都为空
           totalList = dataList.size();
       }else{
           //头尾有一个不为空
           totalList = dataList.size()+1;
       }
       return totalList;
   }
    public void setOnItemOnclickListener(OnItemOnclickListener l){
        onItemOnclickListener = l;
    }

    public View.OnClickListener getOnClickListener(final int position){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemOnclickListener != null && v != null){
                    onItemOnclickListener.onItemClick(v,dataList.get(getRealPosition(position)),getRealPosition(position));
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
    public void notifyLoadMore(){
        this.notifyItemRemoved(getItemCount());
    }
}
