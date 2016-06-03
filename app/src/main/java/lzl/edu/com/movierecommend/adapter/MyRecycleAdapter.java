package lzl.edu.com.movierecommend.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.activity.base.RecyclerViewHolder;
import lzl.edu.com.movierecommend.entity.movieentity.Movie;

/**
 * Created by admin on 2015/12/26.
 */
public class MyRecycleAdapter extends BaseRecyclerAdapter<Movie> {
    private static final String TAG="MyRecycleAdapter";
    public MyRecycleAdapter(RecyclerView view, List<Movie> datas) {
        super(view, datas, R.layout.item_latestmovie);
    }
    @Override
    public void convert(RecyclerViewHolder holder, Movie item, int position, boolean isScrolling) {
        //设置电影名称
        holder.setText(R.id.movieNameTextView,item.getMovieName());
        holder.setRateBar(R.id.gradeRatingBar,item.getStartNum());
        holder.setText(R.id.lGrades,item.getStartNum()+"分");
        holder.setNetWorkImageUrl(R.id.movieImageView,item.getUrlImg(),super.mContext);
    }
}
