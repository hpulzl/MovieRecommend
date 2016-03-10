package lzl.edu.com.movierecommend.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.activity.base.RecyclerViewHolder;
import lzl.edu.com.movierecommend.entity.movieentity.Movie;

/**
 * Created by admin on 2015/12/30.
 */
public class HotMovieAdapter extends BaseRecyclerAdapter<Movie> {

    public HotMovieAdapter(RecyclerView view, List<Movie> datas) {
        super(view, datas, R.layout.item_hotmovie);
    }

    @Override
    public void convert(RecyclerViewHolder holder, Movie item, int position, boolean isScrolling) {
        holder.setText(R.id.movieMore,"更多");
        //电影类别
      /*  holder.setText(R.id.movieClassifyTextView,item.getMovieClassify());
        //获取电影类别图片集
        holder.setImageResource(R.id.movieImageView1,item.getMoviesImage().get(0));
        holder.setImageResource(R.id.movieImageView2,item.getMoviesImage().get(1));
        holder.setImageResource(R.id.movieImageView3,item.getMoviesImage().get(2));

        holder.setText(R.id.seenMovieTextView1,item.getSeenPerson().get(0)+"");
        holder.setText(R.id.seenMovieTextView2,item.getSeenPerson().get(1)+"");
        holder.setText(R.id.seenMovieTextView3,item.getSeenPerson().get(2)+"");*/
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        setOnclick(holder);
    }
    private void setOnclick(RecyclerViewHolder holder){
        holder.itemView.findViewById(R.id.movieMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Log.i("---------","点击了更多");
                }
        });
        holder.itemView.findViewById(R.id.movieImageView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Log.i("---------","点击了图以");
                }
        });
        holder.itemView.findViewById(R.id.movieImageView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Log.i("---------","点击了图二");
                }
        });
        holder.itemView.findViewById(R.id.movieImageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Log.i("---------","点击了图三");
                }
        });
    }
}
