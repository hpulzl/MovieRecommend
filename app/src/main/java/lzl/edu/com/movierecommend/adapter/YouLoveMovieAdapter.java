package lzl.edu.com.movierecommend.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.activity.base.RecyclerViewHolder;
import lzl.edu.com.movierecommend.entity.movieentity.Movie;

/**
 * Created by admin on 2016/1/5.
 */
public class YouLoveMovieAdapter extends BaseRecyclerAdapter<Movie> {

    public YouLoveMovieAdapter(RecyclerView view, List<Movie> datas) {
        super(view, datas, R.layout.item_you_love_movie);
    }

    @Override
    public void convert(RecyclerViewHolder holder, Movie item, int position, boolean isScrolling) {
        holder.setImageResource(R.id.mLoveMovieImageView,item.getUrlImage());
        holder.setText(R.id.directorTextView,item.getDirectorName());
        holder.setText(R.id.movieTextView,item.getMovieName());
        holder.setText(R.id.roleTextView,item.getRoleName());
//        holder.setText(R.id.personNumTextView,item.getCollectionPersonNum()+"");
        holder.setImageResource(R.id.collectionMovie,R.drawable.select_collection);

        setClick(holder);
    }
    private void setClick(RecyclerViewHolder holder){
        holder.itemView.findViewById(R.id.collectionMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag()==true){
                    v.setSelected(false);
                    v.setTag(false);
                }else {
                    v.setSelected(true);
                    v.setTag(true);
                }
            }
        });
    }
}
