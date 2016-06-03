package lzl.edu.com.movierecommend.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.adapter.HotCommentAdapter;
import lzl.edu.com.movierecommend.entity.WeipingCustom;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.http.jsonparse.JsonParseWeipingComment;
import lzl.edu.com.movierecommend.http.volleyutil.VolleyUtil;
import lzl.edu.com.movierecommend.util.HandlerMsgNum;
import lzl.edu.com.movierecommend.util.ToastUtil;

/**
 * Created by admin on 2015/12/28.
 */
public class HotCommentsFragment extends BaseFragment{
    private RecyclerView hotCommentRecyclerView;
    private HotCommentAdapter hotCommentAdapter;
    private List<WeipingCustom> listWeipingCustom;
    private LinearLayoutManager linearLayoutManager;
    private AppCompatActivity mAppCompatActivity;
    private Toolbar toolbar;
    private int lastItem;
    private int pageNum = 1;
    private TextView loadMoreTv;
    private ProgressBar loadMorePb;
    private LinearLayout linearLayout;
    private View footView;
    private boolean isLoadMore =false;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.getApplication(this);
        initToolbar();
        return super.onCreateView(inflater,container,savedInstanceState);
    }
    private void initToolbar(){
        mAppCompatActivity = (AppCompatActivity) mActivity;
        //Activity中的元素
        toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.toolBar);
        toolbar.setTitle("热门微评");
        toolbar.setBackgroundColor(mActivity.getResources().getColor(R.color.colorRed));
        mAppCompatActivity.setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) mAppCompatActivity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                mActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            this.initToolbar();
        }
    }
    @Override
    protected int getFragmentId() {
        return R.layout.fragment_hot_comments;
    }
    /**
     * 加载服务器端数据
     */
    @Override
    public void initDatas() {
        setRefreshState(true);
        String url = URLAddress.getURLPath("FindWeiPingSer?pageNo=1");
        VolleyUtil.sendJsonArrayRequest(mContext, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                JsonParseWeipingComment jsonParse = new JsonParseWeipingComment();
                Message msg = new Message();
                msg.obj = jsonParse.parseJsonByArray(new ArrayList<WeipingCustom>(),jsonArray);
                msg.what = HandlerMsgNum.REFRESH_ZERO;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.analysisException(mContext,"微评数据解析错误"+volleyError.getMessage());
            }
        });
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HandlerMsgNum.REFRESH_ZERO:  //第一次加载数据
                    listWeipingCustom.clear();
                    listWeipingCustom = (List<WeipingCustom>) msg.obj;
                    hotCommentAdapter.setData(listWeipingCustom);
                    hotCommentAdapter.notifyDataSetChanged();
                    pageNum = 1;
                    isLoadMore = false;
                    break;
                case HandlerMsgNum.REFRESH_ONE: //查看更多数据
                    List<WeipingCustom> list = (List<WeipingCustom>) msg.obj;
                    if(list.size()>0){
                        listWeipingCustom.addAll((Collection<? extends WeipingCustom>) msg.obj);
                        hotCommentAdapter.notifyDataSetChanged();
                        linearLayout.setVisibility(View.GONE);
                    }else{
                        isLoadMore = true;  //不能再加载更多了
                        loadMorePb.setVisibility(View.GONE);
                        loadMoreTv.setVisibility(View.VISIBLE);
                        loadMoreTv.setText("没有更多数据..");
                    }
                    break;
            }
        }
    };
    @Override
    public void initFindViewById() {
        //初始化进度条
        baseRefeshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hotCommentFreshLayout);
        baseLoadData = (TextView) view.findViewById(R.id.reLoadData_tv);
        super.setProgressViewOffset();
        super.setSwipeRefreshLayout();
        baseRefeshLayout.setOnRefreshListener(this);
        //初始化RecyclerView
        hotCommentRecyclerView = (RecyclerView) view.findViewById(R.id.hotCommentRecyclerView);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hotCommentRecyclerView.setHasFixedSize(true);
        hotCommentRecyclerView.setLayoutManager(linearLayoutManager);
        hotCommentAdapter = new HotCommentAdapter(hotCommentRecyclerView,listWeipingCustom);
        footView = LayoutInflater.from(mContext).inflate(R.layout.recycler_foot,hotCommentRecyclerView,false);
        linearLayout = (LinearLayout) footView.findViewById(R.id.loadMore_linear);
        loadMoreTv = (TextView) footView.findViewById(R.id.loadMore_tv);
        loadMorePb = (ProgressBar) footView.findViewById(R.id.loadMore_pb);
        linearLayout.setVisibility(View.GONE);
        hotCommentRecyclerView.setAdapter(hotCommentAdapter);
        hotCommentAdapter.setFooterView(footView);
        setRefreshState(false);
        //想底部滑动，加载更多数据
        setRecyclerScrollListener();
    }

    private void setRecyclerScrollListener() {
        hotCommentRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(lastItem+1 == hotCommentAdapter.getItemCount()&& newState == RecyclerView.SCROLL_STATE_IDLE){ //如果滑动到最后一个item，去加载数据
                    if(!isLoadMore){  //没有更多数据
                        pageNum++;
                     getNextPage(pageNum);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 获取下一页的内容
     * @param pageNo
     */
    private void getNextPage(int pageNo){
        linearLayout.setVisibility(View.VISIBLE);
        pageNum = pageNo;
        String url = URLAddress.getURLPath("FindWeiPingSer?pageNo="+pageNum);
        Log.i("nextPagePath",url);
        VolleyUtil.sendJsonArrayRequest(mContext, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                JsonParseWeipingComment jsonParse = new JsonParseWeipingComment();
                Message msg = new Message();
                msg.obj = jsonParse.parseJsonByArray(new ArrayList<WeipingCustom>(),jsonArray);
                msg.what = HandlerMsgNum.REFRESH_ONE;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.analysisException(mContext,"微评数据解析错误"+volleyError.getMessage());
            }
        });
    }
}
