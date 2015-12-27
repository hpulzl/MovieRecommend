package lzl.edu.com.movierecommend.activity.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.ScrollingMovieActivity;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.adapter.MyRecycleAdapter;
import lzl.edu.com.movierecommend.entity.Movie;


public class LatestMovieFragment extends Fragment {
    private RecyclerView latestMovieRecycleView;
    private Activity mActivity;
    private MyRecycleAdapter recycleAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View view;
    private List<Movie> list = new ArrayList<Movie>();
    private Intent mIntent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = this.getActivity();
        view = inflater.inflate(R.layout.activity_latest_movie, container, false);
        initView();
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        latestMovieRecycleView = (RecyclerView) view.findViewById
                (R.id.latestMovieRecyclerView);
        Random random = new Random();
      for(int i=0;i<10;i++){
          Movie m = new Movie();
          m.setStartNum(i+10);
          m.setMovieName("叶问"+i);
          int a = random.nextInt(2)==1 ? R.mipmap.image2 : R.mipmap.image1;
          m.setUrlImage(a);
          list.add(m);
      }

        mLayoutManager = new GridLayoutManager(mActivity,3);

        //设置RecycleView显示格式为GridLayout格式，一行中显示3个item
        latestMovieRecycleView.setLayoutManager(mLayoutManager);
        //设置每个item之间默认的动画效果
        latestMovieRecycleView.setItemAnimator(new DefaultItemAnimator());
        recycleAdapter = new MyRecycleAdapter(latestMovieRecycleView, list);

        latestMovieRecycleView.setAdapter(recycleAdapter);

        initAdapterEvent();
    }

    private void initView(){

    }
    private void initAdapterEvent(){
        recycleAdapter.setOnItemOnclickListener(new BaseRecyclerAdapter.OnItemOnclickListener() {
            @Override
            public void onItemClick(View view, Object obj, int position) {
                Toast.makeText(mActivity,"您点击了"+position+"个按钮",Toast.LENGTH_SHORT).show();
                mIntent = new Intent(mActivity, ScrollingMovieActivity.class);
                startActivity(mIntent);
            }
        });
    }
}
