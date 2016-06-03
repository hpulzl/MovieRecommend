package lzl.edu.com.movierecommend.activity.fragment;

import android.content.Intent;
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
import lzl.edu.com.movierecommend.activity.LoginActivity;
import lzl.edu.com.movierecommend.adapter.FriendsAdapter;
import lzl.edu.com.movierecommend.entity.MUser;
import lzl.edu.com.movierecommend.filter.LoginFilter;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.http.jsonparse.JsonParseFriends;
import lzl.edu.com.movierecommend.http.volleyutil.VolleyUtil;
import lzl.edu.com.movierecommend.util.DialogUtil;
import lzl.edu.com.movierecommend.util.HandlerMsgNum;
import lzl.edu.com.movierecommend.util.ToastUtil;
import shareprefrence.OperateShareprefrence;

public class FriendsFragment extends BaseFragment {
    private AppCompatActivity mAppCompatActivity;
    private Toolbar toolbar;
    private RecyclerView friendsRecyclerView;
    private View footView;
    private LinearLayoutManager layoutManager;
    private FriendsAdapter friendsAdapter;
    private TextView loadMoreTv;
    private ProgressBar loadMorePb;
    private List<MUser> userList = new ArrayList<>();
    private int pageNum;
    private boolean isLoadMore =false;
    private int lastItem;
    private LinearLayout loadMore_linear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.getApplication(this);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_friends;
    }
    private String getUserId(){
       return OperateShareprefrence.loadShareprefrence(mContext).getUserid();
}
    @Override
    public void initDatas() {
        if(!LoginFilter.isLogin(mContext)){
            DialogUtil.DialogUtilInstance().showDialog(mContext,"您还尚未登录",false);
            Intent mIntent = new Intent(mContext, LoginActivity.class);
            startActivityForResult(mIntent,0);
            return;
        }
        String url = URLAddress.getURLPath("FindFriendsSer?pageNo=1&userId="+getUserId());
        VolleyUtil.sendJsonArrayRequest(mContext, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Message msg = new Message();
                msg.obj = new JsonParseFriends().parseJsonByArray(new ArrayList<MUser>(),jsonArray);
                msg.what = HandlerMsgNum.REFRESH_ZERO;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.analysisException(mContext,"解析异常"+volleyError.getMessage());
            }
        });
    }
private Handler mHandler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
       switch (msg.what){
           case HandlerMsgNum.REFRESH_ZERO:
               userList.clear();
               userList = (List<MUser>) msg.obj;
               friendsAdapter.setData(userList);
               friendsAdapter.notifyDataSetChanged();
               loadMore_linear.setVisibility(View.GONE);
               setRefreshState(false);
               pageNum = 1;
               isLoadMore = false;
               break;
           case HandlerMsgNum.REFRESH_ONE: //查看更多数据
               List<MUser> list = (List<MUser>) msg.obj;
               if(list.size()>0){
                   userList.addAll((Collection<? extends MUser>) msg.obj);
                   friendsAdapter.notifyDataSetChanged();
                 loadMore_linear.setVisibility(View.GONE);
               }else{
                   isLoadMore = true;  //不能再加载更多了
                   loadMore_linear.setVisibility(View.VISIBLE);
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
        baseRefeshLayout = (SwipeRefreshLayout) view.findViewById(R.id.friendsReshLayout);
        baseLoadData = (TextView) view.findViewById(R.id.reLoadData_tv);
        friendsRecyclerView = (RecyclerView) view.findViewById(R.id.friendsRecyclerView);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        friendsRecyclerView.setHasFixedSize(true);
        friendsRecyclerView.setLayoutManager(layoutManager);
        footView = LayoutInflater.from(mContext).inflate(R.layout.recycler_foot,friendsRecyclerView,false);
        friendsAdapter = new FriendsAdapter(friendsRecyclerView,userList);
        loadMore_linear = (LinearLayout) footView.findViewById(R.id.loadMore_linear);
        loadMore_linear.setVisibility(View.GONE);
        loadMoreTv = (TextView) footView.findViewById(R.id.loadMore_tv);
        loadMorePb = (ProgressBar) footView.findViewById(R.id.loadMore_pb);
        friendsRecyclerView.setAdapter(friendsAdapter);
        friendsAdapter.setFooterView(footView);
        initToolbar();
        super.setProgressViewOffset();
        super.setSwipeRefreshLayout();
        baseRefeshLayout.setOnRefreshListener(this);
        setRecyclerScrollListener();
    }

    private void setRecyclerScrollListener() {
        friendsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(lastItem+1 == friendsAdapter.getItemCount()&& newState == RecyclerView.SCROLL_STATE_IDLE){ //如果滑动到最后一个item，去加载数据
                    if(!isLoadMore){  //有更多数据
                        pageNum++;
                        getNextPage(pageNum);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }
    private void getNextPage(int pageNo){
        loadMore_linear.setVisibility(View.VISIBLE);
        pageNum = pageNo;
        String url = URLAddress.getURLPath("FindFriendsSer?pageNo="+pageNo+"&userId="+getUserId());
        VolleyUtil.sendJsonArrayRequest(mContext, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Message msg = new Message();
                msg.obj = new JsonParseFriends().parseJsonByArray(new ArrayList<MUser>(),jsonArray);
                msg.what = HandlerMsgNum.REFRESH_ONE;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.analysisException(mContext,"解析异常"+volleyError.getMessage());
            }
        });
    }
    private void initToolbar(){
        mAppCompatActivity = (AppCompatActivity) mActivity;
        //Activity中的元素
        toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.toolBar);
        toolbar.setTitle("好友列表");
        toolbar.setBackgroundColor(mActivity.getResources().getColor(R.color.colorLightGreen));
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
}
