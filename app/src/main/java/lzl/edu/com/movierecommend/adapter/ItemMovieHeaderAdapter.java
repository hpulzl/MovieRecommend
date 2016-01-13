package lzl.edu.com.movierecommend.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.entity.Comments;
import lzl.edu.com.movierecommend.entity.User;

/**
 * Created by admin on 2016/1/11.
 */
public class ItemMovieHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ItemMovieHeaderAdapter";
    //headView
   private static final int TYPE_HEADER = 0;
    //正常的View
   private static final int TYPE_NORMAL = 1;
    //判断footer
    private static final int TYPE_FOOTER = 2;
    private View headerView;
    private OnItemClickListener mListener;
    private List<Comments> commentsList;
    private View footerView;
    private int dataSize;

    interface OnItemClickListener{
        public void clickListener(int position,Comments com);
    }

    public void setOnItemClickListener(OnItemClickListener l){
        this.mListener = l;
    }

    public ItemMovieHeaderAdapter(List<Comments> datas){
        if(datas==null){
            commentsList = new ArrayList<>();
        }else{
            commentsList = datas;
        }
    }

    public void setFooterView(View footerView){
        this.footerView = footerView;
        notifyDataSetChanged();
    }
    public View getHeaderView() {
        return headerView;
    }
    /**
     * 在第一行item中插入HeaderView
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }
    private int getRealPostion(int position){
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
       }else if(position==commentsList.size()+1 && footerView!=null){
           return TYPE_FOOTER;
       }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(headerView!=null && viewType == TYPE_HEADER) {
            return new Holder(headerView);
        }else if(footerView!=null && viewType == TYPE_FOOTER){
            return new Holder(footerView);
        }

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_comments,parent,false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(headerView != null && getItemViewType(position) == TYPE_HEADER ){
            return;
        }
        if(position == dataSize-1 && footerView !=null){
            return;
        }
        int p = getRealPostion(position);
        final Comments com = commentsList.get(p);
       if(holder instanceof Holder){
        User u = com.getUser();
           ((Holder) holder).nickIv.setImageResource(u.getNickImage());
           ((Holder) holder).commentsTv.setText(com.getContents());
           ((Holder) holder).nickNameTv.setText(u.getNickName());
           ((Holder) holder).timeTv.setText(com.getTime());
           ((Holder) holder).userMemberTv.setText(u.getUserMember());
       }
        if(mListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.clickListener(position,com);
                }
            });
        }
        setHolderClick((Holder) holder);
    }

    private void setHolderClick(Holder holder) {
        holder.nickIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"点击了头像");
            }
        });
    }


    @Override
    public int getItemCount() {
         dataSize = commentsList.size();
        if(headerView!=null && footerView!=null){
            dataSize = dataSize+2;

            return dataSize;
        }else if(headerView !=null ||footerView !=null){
            dataSize = dataSize+1;

            return dataSize;
        }
        return dataSize;
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView nickNameTv;
        TextView userMemberTv;
        TextView commentsTv;
        TextView timeTv;
        ImageView nickIv;
        public Holder(View itemView) {
            super(itemView);
            if(itemView == headerView) return;
            nickNameTv = (TextView) itemView.findViewById(R.id.nickNameTextView);
            userMemberTv = (TextView) itemView.findViewById(R.id.userMemberTextView);
            timeTv = (TextView) itemView.findViewById(R.id.timeTextView);
            timeTv = (TextView) itemView.findViewById(R.id.timeTextView);
            commentsTv = (TextView) itemView.findViewById(R.id.commentsTextView);
            nickIv = (ImageView) itemView.findViewById(R.id.nickImageView);
        }
    }
}
