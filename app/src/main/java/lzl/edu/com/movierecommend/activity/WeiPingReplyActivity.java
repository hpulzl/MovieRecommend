package lzl.edu.com.movierecommend.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.adapter.CommentReplyAdapter;
import lzl.edu.com.movierecommend.entity.WeipingCustom;
import lzl.edu.com.movierecommend.entity.movieentity.WeiReplyCustom;
import lzl.edu.com.movierecommend.filter.LoginFilter;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.http.jsonparse.JsonParseReply;
import lzl.edu.com.movierecommend.http.volleyutil.VolleyUtil;
import lzl.edu.com.movierecommend.util.DialogUtil;
import lzl.edu.com.movierecommend.util.HandlerMsgNum;
import lzl.edu.com.movierecommend.util.ToastUtil;
import shareprefrence.OperateShareprefrence;

public class WeiPingReplyActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView replyRecyclerView;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private View headView;
    private WeipingCustom custom;
    private CommentReplyAdapter replyAdapter;
    private List<WeiReplyCustom> weiReplylist = new ArrayList<>();
    private int lastItem;
    private  LinearLayoutManager layoutManager;
    private boolean isLoadMore = false;
    private int pageNum = 1;
    private View footView;
    private TextView  loadMoreTv;
    private ProgressBar loadMorePb;
    private LinearLayout commentLinear;
    private EditText commentEdit;
    private Button commentBtn;
    private ProgressBar comment_progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_ping_reply);
        initFindView();
    }
    private void initFindView() {
        //获取微评信息
        custom = (WeipingCustom) getIntent().getSerializableExtra("weipingCustom");
        replyRecyclerView = (RecyclerView) findViewById(R.id.replyRecyclerView);
        commentLinear = (LinearLayout) findViewById(R.id.commentLinear);
        commentEdit = (EditText) findViewById(R.id.commentEdit);
        commentBtn = (Button) findViewById(R.id.commentButton);
        comment_progressBar = (ProgressBar) findViewById(R.id.comment_progressBar);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        replyRecyclerView.setHasFixedSize(true);
        replyRecyclerView.setLayoutManager(layoutManager);
        initReplyDatas();
        headView = LayoutInflater.from(this).inflate(R.layout.item_fragment_hot_comments,replyRecyclerView,false);
        //Activity中的元素
        appBarLayout = (AppBarLayout) headView.findViewById(R.id.comment_toolbar);
        toolbar = (Toolbar) headView.findViewById(R.id.toolBar);
        appBarLayout.setVisibility(View.VISIBLE);
        toolbar.setTitle("评论");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorRed));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHeadViewInfo(custom);
        footView = LayoutInflater.from(this).inflate(R.layout.recycler_foot,replyRecyclerView,false);
        loadMoreTv = (TextView) footView.findViewById(R.id.loadMore_tv);
        loadMorePb = (ProgressBar) footView.findViewById(R.id.loadMore_pb);
        replyAdapter = new CommentReplyAdapter(replyRecyclerView,weiReplylist);
        replyRecyclerView.setAdapter(replyAdapter);
        replyAdapter.setHeaderView(headView);
        replyAdapter.setFooterView(footView);

        setRecyclerScrollListener();
        commentBtn.setOnClickListener(this);
    }

    private void initReplyDatas() {
        String url = URLAddress.getURLPath("ReplySer?articleId="+custom.getArticleID()+"&pageNo=1");
        VolleyUtil.sendJsonArrayRequest(this, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Message msg = new Message();
                msg.obj = new JsonParseReply().parseJsonByArray(new ArrayList<WeiReplyCustom>(),jsonArray);
                msg.what = HandlerMsgNum.REFRESH_ZERO;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.toast(WeiPingReplyActivity.this,"解析异常"+volleyError.getMessage());
            }
        });
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HandlerMsgNum.REFRESH_ZERO:
                    weiReplylist = (List<WeiReplyCustom>) msg.obj;
                    if(weiReplylist.size()>0) {
                        replyAdapter.setData(weiReplylist);
                        replyAdapter.notifyDataSetChanged();
                        loadMorePb.setVisibility(View.GONE);
                        loadMoreTv.setVisibility(View.GONE);
                    }else{
                        isLoadMore = true;  //不能再加载更多了
                        loadMorePb.setVisibility(View.GONE);
                        loadMoreTv.setVisibility(View.VISIBLE);
                        loadMoreTv.setText("暂无评论信息..");
                    }
                    break;
                case HandlerMsgNum.REFRESH_ONE:
                    List<WeiReplyCustom> list = (List<WeiReplyCustom>) msg.obj;
                    if(list.size()>0){
                        weiReplylist.addAll(list);
                        replyAdapter.notifyDataSetChanged();
                        loadMorePb.setVisibility(View.GONE);
                        loadMoreTv.setVisibility(View.GONE);
                    }else{
                        isLoadMore = true;  //不能再加载更多了
                        loadMorePb.setVisibility(View.GONE);
                        loadMoreTv.setVisibility(View.VISIBLE);
                        loadMoreTv.setText("没有更多数据..");
                    }
                    break;
                case HandlerMsgNum.REFRESH_THREE:
                    JSONObject json = (JSONObject) msg.obj;
                    try {
                       if(json.getBoolean("result")){
                           ToastUtil.toast(WeiPingReplyActivity.this,"评论成功！");
                           initReplyDatas();
                           commentEdit.setText("");
                       }else{
                           ToastUtil.toast(WeiPingReplyActivity.this,"评论失败！");
                       }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    comment_progressBar.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private void setRecyclerScrollListener() {
        replyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(lastItem+1 == replyAdapter.getItemCount()&& newState == RecyclerView.SCROLL_STATE_IDLE){ //如果滑动到最后一个item，去加载数据
                    if(!isLoadMore){  //有更多数据
                        pageNum++;
                        getNextPage(pageNum);
                    }else{

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem =  layoutManager.findLastVisibleItemPosition();
            }
        });
    }
    private void getNextPage(int pageNo){
        pageNum = pageNo;
        String url = URLAddress.getURLPath("ReplySer?articleId="+custom.getArticleID()+"&pageNo="+pageNum);
        VolleyUtil.sendJsonArrayRequest(this, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Message msg = new Message();
                msg.obj = new JsonParseReply().parseJsonByArray(new ArrayList<WeiReplyCustom>(),jsonArray);
                msg.what = HandlerMsgNum.REFRESH_ONE;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.toast(WeiPingReplyActivity.this,"解析异常"+volleyError.getMessage());
            }
        });
    }
    private void setHeadViewInfo(WeipingCustom custom){
        NetworkImageView headImg = (NetworkImageView) headView.findViewById(R.id.headImg_Network);
        TextView userNameTv = (TextView) headView.findViewById(R.id.username_tv);
        TextView publishTimeTv = (TextView) headView.findViewById(R.id.publishTime_tv);
        TextView contentTv = (TextView) headView.findViewById(R.id.content_tv);
        NetworkImageView movieImg = (NetworkImageView) headView.findViewById(R.id.movieImg_iv);
        TextView movieNameTv = (TextView) headView.findViewById(R.id.movieName_tv);
        TextView typeTv = (TextView) headView.findViewById(R.id.type_tv);
        TextView timeLengthTv = (TextView) headView.findViewById(R.id.timeLength_tv);
        TextView timeOnTv = (TextView) headView.findViewById(R.id.timeOn_tv);
        TextView directorTv = (TextView) headView.findViewById(R.id.director_tv);
        TextView actorTv = (TextView) headView.findViewById(R.id.actor_tv);
        LinearLayout contentLinear = (LinearLayout) headView.findViewById(R.id.content_linear);
        contentLinear.setVisibility(View.GONE);
        //设置内容
        headImg.setImageUrl(custom.getUser().getHeadImg(), VolleyUtil.getImageLoader(this));
        userNameTv.setText(custom.getUser().getName());
        publishTimeTv.setText(custom.getPublishTime());
        contentTv.setText(custom.getContent());
        movieImg.setImageUrl(custom.getFilm().getImagepath(),VolleyUtil.getImageLoader(this));
        movieNameTv.setText("影片名:"+custom.getFilm().getName());
        typeTv.setText("电影类型"+custom.getFilm().getType());
        timeLengthTv.setText("影片时长:"+custom.getFilm().getTimeLeng());
        timeOnTv.setText("上映时间:"+custom.getFilm().getTimeOn());
        directorTv.setText("导演:"+custom.getFilm().getDirector());
        actorTv.setText("演员:"+custom.getFilm().getActor());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        //点击发送评论
        if(v.getId() == R.id.commentButton){
            if(!LoginFilter.isLogin(this)){
                DialogUtil.DialogUtilInstance().showDialog(this,"您尚未登录",false);
                return;
            }
            if(commentEdit.getText().toString().isEmpty()){
                ToastUtil.toast(this,"内容不能为空");
                return;
            }
            //执行添加评论功能
            comment_progressBar.setVisibility(View.VISIBLE);
            String url = URLAddress.getURLPath("AddWeiReplySer");
            Map<String,String> map = new HashMap<>();
            map.put("articleID",custom.getArticleID());
            map.put("userId", OperateShareprefrence.loadShareprefrence(this).getUserid());
            map.put("wContent",commentEdit.getText().toString());
            JSONObject jsonObject = new JSONObject(map);
            VolleyUtil.sendJsonObjectRequest(this, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Message msg = new Message();
                    msg.what = HandlerMsgNum.REFRESH_THREE;
                    msg.obj = jsonObject;
                    mHandler.sendMessage(msg);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ToastUtil.analysisException(WeiPingReplyActivity.this,"解析异常"+volleyError.getMessage());
                }
            });
        }
    }
}
