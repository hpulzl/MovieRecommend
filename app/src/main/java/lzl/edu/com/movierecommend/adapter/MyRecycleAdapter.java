package lzl.edu.com.movierecommend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.entity.Movie;

/**
 * Created by admin on 2015/12/26.
 */
public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder> {

    private List<Movie> list;
    private LayoutInflater inflater;
    private Context mContext;
    public MyRecycleAdapter(Context context, List<Movie> movieList){
        mContext = context;
        list = movieList;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 设置一个接口，监听点击事件的发生。
     */
    public interface OnItemClickListener{
        //item点击
        public void onItemClick(View view,int position);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(inflater.
                inflate(R.layout.item_latestmovie,parent,false));

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Movie m = list.get(position);
            holder.movieNameTextView.setText(m.getMovieName());
            holder.movieImageView.setImageResource(m.getUrlImage());
            holder.gradeRatingBar.setRating(m.getStartNum());
        //给图片设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置接口的回调
                onItemClickListener.onItemClick(holder.itemView,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView movieImageView;
        TextView movieNameTextView;
        RatingBar gradeRatingBar;
    public MyViewHolder(View itemView) {
        super(itemView);

        movieImageView = (ImageView) itemView.findViewById(R.id.movieImageView);
        movieNameTextView = (TextView) itemView.findViewById(R.id.movieNameTextView);
        gradeRatingBar = (RatingBar) itemView.findViewById(R.id.gradeRatingBar);
    }
}
}
