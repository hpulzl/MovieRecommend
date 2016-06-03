package lzl.edu.com.movierecommend.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.ScrollingMovieActivity;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.adapter.MyRecycleAdapter;
import lzl.edu.com.movierecommend.entity.movieentity.Movie;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.http.jsonparse.JsonParseMovie;
import lzl.edu.com.movierecommend.http.volleyutil.VolleyUtil;
import lzl.edu.com.movierecommend.util.HandlerMsgNum;


public class LatestMovieFragment extends BaseFragment{
    private MyRecycleAdapter recycleAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Movie> lastestMovies = new ArrayList<Movie>();
    private Intent mIntent;
    private RecyclerView lastestRecyclerView;
    private final static String TAG="LatestMovieFragment";
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case HandlerMsgNum.REFRESH_ZERO:
                   //当触发下拉刷新时，就调用刷新方法，显示刷新。
                   setProgressViewOffset();
                   initMovieData();
                   break;
               case HandlerMsgNum.REFRESH_ONE:
                   updateDatasByRefresh();
                   break;
           }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.getApplication(this);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    protected int getFragmentId() {
        return R.layout.activity_latest_movie;
    }

    @Override
    public void initFindViewById() {
        baseRefeshLayout = (SwipeRefreshLayout) view.findViewById(R.id.lastestFreshLayout);
        baseLoadData = (TextView) view.findViewById(R.id.reLoadData_tv);
        super.setProgressViewOffset();
        super.setSwipeRefreshLayout();
        baseRefeshLayout.setOnRefreshListener(this);
    }

    /**
     * 初始化加载数据
     */
    private void initMovieData(){
        lastestRecyclerView = (RecyclerView) view.findViewById(R.id.latestMovieRecyclerView);
        mLayoutManager = new GridLayoutManager(mActivity,3);

        //设置RecycleView显示格式为GridLayout格式，一行中显示3个item
        lastestRecyclerView.setLayoutManager(mLayoutManager);
        //设置每个item之间默认的动画效果
        lastestRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //获取后台数据库中的电影列表个数
        getListMovie();
        recycleAdapter = new MyRecycleAdapter(lastestRecyclerView,lastestMovies);
        lastestRecyclerView.setAdapter(recycleAdapter);
        initAdapterEvent();
    }
    /**
     * 获取list集合
     */
    public void getListMovie(){
        lastestMovies.clear();
        setRefreshState(true);
        baseLoadData.setVisibility(View.GONE);
        String url = URLAddress.getURLPath("LatestMovieSer");
        VolleyUtil.sendJsonArrayRequest(mContext, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                JsonParseMovie listMovie = new JsonParseMovie();
                listMovie.parseJsonByArray(lastestMovies,jsonArray);
                recycleAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                reLoadData();
            }
        });
        setRefreshState(false);
    }
    /**
     * 更新数据
     */
    private void updateDatasByRefresh(){
        getListMovie();
        recycleAdapter.setData(lastestMovies);
        recycleAdapter.notifyDataSetChanged();
    }
    private void initAdapterEvent(){
        recycleAdapter.setOnItemOnclickListener(new BaseRecyclerAdapter.OnItemOnclickListener() {
            @Override
            public void onItemClick(View view, Object obj, int position) {
                String movieId = lastestMovies.get(position).getMovieId();
                Bundle mBundle = new Bundle();
                mIntent = new Intent(mActivity, ScrollingMovieActivity.class);
                mBundle.putString("movieId",movieId);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
    }
    @Override
    public void initDatas() {
        mHandler.sendEmptyMessage(HandlerMsgNum.REFRESH_ZERO);
    }
}
