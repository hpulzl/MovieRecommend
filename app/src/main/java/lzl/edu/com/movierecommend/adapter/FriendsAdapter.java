package lzl.edu.com.movierecommend.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.activity.base.RecyclerViewHolder;
import lzl.edu.com.movierecommend.entity.movieentity.WeiReplyCustom;

/**
 * Created by admin on 2016/1/5.
 */
public class FriendsAdapter extends BaseRecyclerAdapter<WeiReplyCustom>{
    public FriendsAdapter(RecyclerView view, List<WeiReplyCustom> datas) {
        super(view, datas, R.layout.item_weiping_reply);
    }
    @Override
    public void convert(RecyclerViewHolder holder, WeiReplyCustom item, int position, boolean isScrolling) {
        //设置微评用户
        if(item.getUser().getHeadImg().equals("kong")){
            holder.setNetWorkImageUrl(R.id.headImg_Network,R.mipmap.ic_userhead);
        }else {
            holder.setNetWorkImageUrl(R.id.headImg_Network, item.getUser().getHeadImg(), super.mContext);
        }
        holder.setText(R.id.username_tv,item.getUser().getName());
        holder.setText(R.id.publishTime_tv,item.getWtime());
        holder.setText(R.id.reply_tv,item.getWcontent()+""); //评论
    }
}
