package lzl.edu.com.movierecommend.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.WeiPingReplyActivity;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.activity.base.RecyclerViewHolder;
import lzl.edu.com.movierecommend.entity.WeipingCustom;
import lzl.edu.com.movierecommend.util.ToastUtil;

/**
 * Created by admin on 2016/1/5.
 */
public class HotCommentAdapter extends BaseRecyclerAdapter<WeipingCustom>{
    public HotCommentAdapter(RecyclerView view, List<WeipingCustom> datas) {
        super(view, datas, R.layout.item_fragment_hot_comments);
    }
    @Override
    public void convert(RecyclerViewHolder holder, WeipingCustom item, int position, boolean isScrolling) {
        //设置微评用户
        holder.setNetWorkImageUrl(R.id.headImg_Network,item.getUser().getHeadImg(),super.mContext);
        holder.setText(R.id.username_tv,item.getUser().getName());
        //设置电影信息
        holder.setNetWorkImageUrl(R.id.movieImg_iv,item.getFilm().getImagepath(),super.mContext);
        holder.setText(R.id.movieName_tv,"影片名:"+item.getFilm().getName());
        holder.setText(R.id.type_tv,"类型:"+item.getFilm().getType());
        holder.setText(R.id.timeLength_tv,"时长:"+item.getFilm().getTimeLeng()+"分钟");
        holder.setText(R.id.timeOn_tv,"上映时间:"+item.getFilm().getTimeOn());
        holder.setText(R.id.director_tv,"导演:"+item.getFilm().getDirector());
        holder.setText(R.id.actor_tv,"主演:"+item.getFilm().getActor());
        //微评信息
        holder.setText(R.id.publishTime_tv,item.getPublishTime());
        holder.setText(R.id.content_tv,item.getContent());
        holder.setText(R.id.zan_tv,item.getGoodNum()+""); //点赞
        holder.setText(R.id.comment_tv,item.getFeedBackNum()+""); //评论
        holder.setText(R.id.share_tv,item.getForwardNum()+""); //转发

        setOnclick(holder,item);
    }

    private void setOnclick(final RecyclerViewHolder holder, final WeipingCustom item) {
        holder.itemView.findViewById(R.id.zan_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setText(R.id.zan_tv,""+(item.getGoodNum()+1));
                //进行网络请求，点赞。这里暂时不写...
            }
        });
        holder.itemView.findViewById(R.id.comment_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //评论
                //跳转至评论页面
                Intent mIntent = new Intent(mContext, WeiPingReplyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("weipingCustom",item);
                mIntent.putExtras(bundle);
                mContext.startActivity(mIntent);
            }
        });
        holder.itemView.findViewById(R.id.share_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.toast(mContext,"暂没有开通转发功能!");
            }
        });
    }
}
