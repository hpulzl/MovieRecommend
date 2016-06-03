package lzl.edu.com.movierecommend.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.ScrollingMovieActivity;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.activity.base.RecyclerViewHolder;
import lzl.edu.com.movierecommend.entity.movieentity.MovieType;

/**
 * Created by admin on 2015/12/30.
 */
public class HotMovieAdapter extends BaseRecyclerAdapter<MovieType> {
    private Intent mIntent = new Intent();
    private List<MovieType> datas;
    public HotMovieAdapter(RecyclerView view, List<MovieType> datas) {
        super(view, datas, R.layout.item_hotmovie);
        this.datas = datas;
    }

    @Override
    public void convert(RecyclerViewHolder holder, MovieType item, int position, boolean isScrolling) {
        holder.setText(R.id.movieMore,"更多");
        //电影类别
        holder.setText(R.id.movieClassifyTextView,item.getMovieType());
        if(item.getMovieTypeList().size()>=3){
            holder.setNetWorkImageUrl(R.id.movieImageView1,item.getMovieTypeList().get(0).getUrlImg(),super.mContext);
            holder.setNetWorkImageUrl(R.id.movieImageView2,item.getMovieTypeList().get(1).getUrlImg(),super.mContext);
            holder.setNetWorkImageUrl(R.id.movieImageView3,item.getMovieTypeList().get(2).getUrlImg(),super.mContext);

            holder.setText(R.id.movieNameTv1,item.getMovieTypeList().get(0).getMovieName());
            holder.setText(R.id.movieNameTv2,item.getMovieTypeList().get(1).getMovieName());
            holder.setText(R.id.movieNameTv3,item.getMovieTypeList().get(2).getMovieName());

            holder.setText(R.id.seenMovieTextView1,item.getMovieTypeList().get(0).getTotalPerson()+"人观看");
            holder.setText(R.id.seenMovieTextView2,item.getMovieTypeList().get(1).getTotalPerson()+"人观看");
            holder.setText(R.id.seenMovieTextView3,item.getMovieTypeList().get(2).getTotalPerson()+"人观看");
        }else if(item.getMovieTypeList().size()==2){
            holder.setNetWorkImageUrl(R.id.movieImageView1,item.getMovieTypeList().get(0).getUrlImg(),super.mContext);
            holder.setNetWorkImageUrl(R.id.movieImageView2,item.getMovieTypeList().get(1).getUrlImg(),super.mContext);

            holder.setText(R.id.movieNameTv1,item.getMovieTypeList().get(0).getMovieName());
            holder.setText(R.id.movieNameTv2,item.getMovieTypeList().get(1).getMovieName());

            holder.setText(R.id.seenMovieTextView1,item.getMovieTypeList().get(0).getTotalPerson()+"人观看");
            holder.setText(R.id.seenMovieTextView2,item.getMovieTypeList().get(1).getTotalPerson()+"人观看");
        }else if(item.getMovieTypeList().size()==1){
            holder.setNetWorkImageUrl(R.id.movieImageView1,item.getMovieTypeList().get(0).getUrlImg(),super.mContext);

            holder.setText(R.id.movieNameTv1,item.getMovieTypeList().get(0).getMovieName());

            holder.setText(R.id.seenMovieTextView1,item.getMovieTypeList().get(0).getTotalPerson()+"人观看");
        }
     //   setOnclick(holder,position);
    }

    private void setOnclick(final RecyclerViewHolder holder,final int position){
        holder.itemView.findViewById(R.id.movieMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Log.i("---------","点击了更多");
                }
        });
        if(datas.get(position).getMovieTypeList().size()>=3){
            holder.itemView.findViewById(R.id.movieImageView1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(holder.itemView.getContext(), ScrollingMovieActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("movieId", datas.get(position).getMovieTypeList().get(0).getMovieId());
                    mIntent.putExtras(mBundle);
                    holder.itemView.getContext().startActivity(mIntent);
                }
            });
            holder.itemView.findViewById(R.id.movieImageView2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(holder.itemView.getContext(), ScrollingMovieActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("movieId",datas.get(position).getMovieTypeList().get(1).getMovieId());
                    mIntent.putExtras(mBundle);
                    holder.itemView.getContext().startActivity(mIntent);
                }
            });
            holder.itemView.findViewById(R.id.movieImageView3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(holder.itemView.getContext(), ScrollingMovieActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("movieId",datas.get(position).getMovieTypeList().get(2).getMovieId());
                    mIntent.putExtras(mBundle);
                    holder.itemView.getContext().startActivity(mIntent);
                }
            });
        }else if(datas.get(position).getMovieTypeList().size()==2){
            holder.itemView.findViewById(R.id.movieImageView1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(holder.itemView.getContext(), ScrollingMovieActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("movieId", datas.get(position).getMovieTypeList().get(0).getMovieId());
                    mIntent.putExtras(mBundle);
                    holder.itemView.getContext().startActivity(mIntent);
                }
            });
            holder.itemView.findViewById(R.id.movieImageView2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(holder.itemView.getContext(), ScrollingMovieActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("movieId",datas.get(position).getMovieTypeList().get(1).getMovieId());
                    mIntent.putExtras(mBundle);
                    holder.itemView.getContext().startActivity(mIntent);
                }
            });
        }else if(datas.get(position).getMovieTypeList().size()==1){
            holder.itemView.findViewById(R.id.movieImageView1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(holder.itemView.getContext(), ScrollingMovieActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("movieId", datas.get(position).getMovieTypeList().get(0).getMovieId());
                    mIntent.putExtras(mBundle);
                    holder.itemView.getContext().startActivity(mIntent);
                }
            });
        }

    }
}
