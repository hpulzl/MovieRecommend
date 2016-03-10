package lzl.edu.com.movierecommend.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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


public class LatestMovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView latestMovieRecycleView;
    private SwipeRefreshLayout latestRefreshLayout;
    private Activity mActivity;
    private MyRecycleAdapter recycleAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View view;
    private List<Movie> lastestMovies = new ArrayList<Movie>();
    private Intent mIntent;
    private Context mContext;
    private final static String TAG="LatestMovieFragment";
    private TextView reLoadData_tv;
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
        getApplication();
        view = inflater.inflate(R.layout.activity_latest_movie, container, false);
        initFindViewById();
        initDatasAndFresh();
        return view;
    }
    private void getApplication(){
        mActivity = this.getActivity();
        mContext = this.getContext();
    }

    private void initFindViewById(){
        latestRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.lastestFreshLayout);
        reLoadData_tv = (TextView) view.findViewById(R.id.reLoadData_tv);
        setSwipeRefreshLayout();
        latestMovieRecycleView = (RecyclerView) view.findViewById
                (R.id.latestMovieRecyclerView);

        latestRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 设置SwipeRefreshLayout的显示属性
     */
    private void setSwipeRefreshLayout(){
        latestRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    /**
     * 第一次加载数据并出现刷新效果
     */
    private void initDatasAndFresh(){
        mHandler.sendEmptyMessage(HandlerMsgNum.REFRESH_ZERO);
    }
    /**
     * 初始化加载数据
     */
    private void initMovieData(){
        mLayoutManager = new GridLayoutManager(mActivity,3);

        //设置RecycleView显示格式为GridLayout格式，一行中显示3个item
        latestMovieRecycleView.setLayoutManager(mLayoutManager);
        //设置每个item之间默认的动画效果
        latestMovieRecycleView.setItemAnimator(new DefaultItemAnimator());
        //获取后台数据库中的电影列表个数
        getListMovie();
        recycleAdapter = new MyRecycleAdapter(latestMovieRecycleView,lastestMovies);
        latestMovieRecycleView.setAdapter(recycleAdapter);
        initAdapterEvent();
    }
    /**
     * 获取list集合
     */
    public void getListMovie(){
        lastestMovies.clear();
        setRefreshState(true);
        reLoadData_tv.setVisibility(View.GONE);
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
     * 点击重新加载数据
     */
    private void reLoadData(){
       reLoadData_tv.setVisibility(View.VISIBLE);
        reLoadData_tv.setText("点击重新加载数据");
        reLoadData_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListMovie();
            }
        });
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
    public void onRefresh() {
        setRefreshState(true);
        mHandler.sendEmptyMessage(HandlerMsgNum.REFRESH_ONE);
    }
    private void setRefreshState(boolean state){
        latestRefreshLayout.setRefreshing(state);
    }
    private void setProgressViewOffset(){
        //这个是解决第一次进入首页时，没有产生下拉刷新的动画
        latestRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                        getResources().getDisplayMetrics()));
    }

}
