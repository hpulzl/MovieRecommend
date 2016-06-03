package lzl.edu.com.movierecommend.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.activity.base.RecyclerViewHolder;
import lzl.edu.com.movierecommend.entity.Comments;
import lzl.edu.com.movierecommend.entity.User;

/**
 * Created by admin on 2016/1/17.
 */
public class ItemMovieAdapter extends BaseRecyclerAdapter<Comments> {
    public ItemMovieAdapter(RecyclerView view, List<Comments> datas) {
        super(view, datas, R.layout.item_movie_comments);
    }

    @Override
    public void convert(RecyclerViewHolder holder, Comments item, int position, boolean isScrolling) {
        User u = item.getUser();
        holder.setText(R.id.nickNameTextView,u.getNickName());
        holder.setNetWorkImageUrl(R.id.nickImageView,u.getHeadImg(),super.mContext);
        holder.setText(R.id.userMemberTextView,u.getProvince());
        holder.setText(R.id.timeTextView,item.getTime());
        holder.setText(R.id.commentsTextView,item.getContents());
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
