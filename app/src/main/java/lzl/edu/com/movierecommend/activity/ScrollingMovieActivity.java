package lzl.edu.com.movierecommend.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.adapter.ItemMovieAdapter;
import lzl.edu.com.movierecommend.entity.Comments;
import lzl.edu.com.movierecommend.entity.movieentity.Movie;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.http.jsonparse.JsonParseComments;
import lzl.edu.com.movierecommend.http.jsonparse.JsonParseMovieInfo;
import lzl.edu.com.movierecommend.http.volleyutil.VolleyUtil;
import lzl.edu.com.movierecommend.util.HandlerMsgNum;
import lzl.edu.com.movierecommend.util.LogUtil;
import lzl.edu.com.movierecommend.util.ToastUtil;

public class ScrollingMovieActivity extends AppCompatActivity {
    private static final String TAG="ScrollingMovieActivity";

    private CollapsingToolbarLayout toolbar_layout;
    private Intent mIntent;
    private int images[];
    private Toolbar moviesToolbar;
    private LayoutInflater layout;
    private LinearLayout mGallery;
    private RecyclerView recyclerView;
    private ItemMovieAdapter itemMovieAdapter;
    private List<Comments> commentsList = new ArrayList<>();
    private Movie movie = new Movie();
    private ContentLoadingProgressBar progressBar;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HandlerMsgNum.REFRESH_ONE:
                   movie = (Movie) msg.obj;
                    initToolbar();
                    getCommentList(0);
                    progressBar.hide();
                   break;
                case HandlerMsgNum.REFRESH_ZERO:
                    commentsList = (List<Comments>) msg.obj;
                    LogUtil.i(TAG,"comments..."+commentsList.size());
                    initComments();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_movie);
        getMovieInfo();
    }

    private void initComments() {
        recyclerView = (RecyclerView) findViewById(R.id.itemMovieRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //这里先获取第一页的评论内容
        itemMovieAdapter = new ItemMovieAdapter(recyclerView,commentsList);
        setHeader(recyclerView);
        setFooter(recyclerView);
        recyclerView.setAdapter(itemMovieAdapter);
    }

    /**
     * 通过网络获取评论信息.
     * @return
     */
    private void getCommentList(int pageNo){
        String url = URLAddress.getURLPath("FindReplySer?movieId="+getExtras()+"&pageNo="+pageNo);
        VolleyUtil.sendJsonArrayRequest(this, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                JsonParseComments jsonParseComments = new JsonParseComments();
                Message msg = new Message();
                msg.obj =  jsonParseComments.parseJsonByArray(new ArrayList<Comments>(),jsonArray);
                msg.what = HandlerMsgNum.REFRESH_ZERO;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.toast(ScrollingMovieActivity.this,"volleyError=="+volleyError.getMessage());
            }
        });
    }
    private void initToolbar(){
        moviesToolbar = (Toolbar) findViewById(R.id.moviesToolbar);
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        NetworkImageView scroll_movieImageView = (NetworkImageView) findViewById(R.id.scroll_movieImageView);
        scroll_movieImageView.setImageUrl(movie.getUrlImg(),VolleyUtil.getImageLoader(this));
        setSupportActionBar(moviesToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar_layout.setTitle(movie.getMovieName());
        //设置扩展时的字体颜色
        toolbar_layout.setExpandedTitleColor(Color.WHITE);
        toolbar_layout.setExpandedTitleTypeface(Typeface.SERIF);
        //设置收缩时字体颜色
        toolbar_layout.setCollapsedTitleTextColor(Color.WHITE);
        setToolBarClick();
    }
    private void getMovieInfo(){
        //获取电影id，随后加载后台电影的详细信息
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        progressBar.show();

        //获取访问路径..servletName
        String url = URLAddress.getURLPath("FindMovieInfoSer");
        Map<String,String> requestMap = new HashMap<>();
        requestMap.put("movieId",getExtras());
        JSONObject jsonObject = new JSONObject(requestMap);
        VolleyUtil.sendJsonObjectRequest(this, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Movie m = new Movie();
                JsonParseMovieInfo movieInfo = new JsonParseMovieInfo();
              movie =  movieInfo.parseJsonByObject(m,jsonObject);
                Message msg = new Message();
                msg.obj = movie;
                msg.what = HandlerMsgNum.REFRESH_ONE;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.toast(ScrollingMovieActivity.this,"volleyError..."+volleyError.getMessage());
            }
        });
    }
    private void setToolBarClick() {
        moviesToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.scroll_share:
                        Toast.makeText(ScrollingMovieActivity.this,"分享",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.scroll_search:
                        Toast.makeText(ScrollingMovieActivity.this,"搜索",Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        });
    }
    private void setFooter(RecyclerView view){
        LayoutInflater inflater = LayoutInflater.from(this);
        View footer = inflater.inflate(R.layout.item_scrollview_recycler_footer,view,false);
        itemMovieAdapter.setFooterView(footer);
    }
    private void setHeader(RecyclerView view){
        initImages();
        LayoutInflater inflater = LayoutInflater.from(this);
        View header = inflater.inflate(R.layout.item_scrollmovie_recycler_header,view,false);
        setHeaderInfo(header);
        initScrollImage(header);
        itemMovieAdapter.setHeaderView(header);
    }
    private void setHeaderInfo(View header){
        TextView lMovieNameTextView = (TextView) header.findViewById(R.id.lMovieNameTextView);
        RatingBar lRatingBar = (RatingBar) header.findViewById(R.id.lRatingBar);
        TextView lGrades = (TextView) header.findViewById(R.id.lGrades);
        TextView lDirectorTextView = (TextView) header.findViewById(R.id.lDirectorTextView);
        TextView lRoleTextView = (TextView) header.findViewById(R.id.lRoleTextView);
        TextView infoTextView = (TextView) header.findViewById(R.id.infoTextView);

        LogUtil.i(TAG,movie.toString());
        lMovieNameTextView.setText(movie.getMovieName());
        lGrades.setText(movie.getStartNum()+"分");
        lRatingBar.setRating(movie.getStartNum()/2);
        lDirectorTextView.setText(movie.getDirectorName());
        lRoleTextView.setText(movie.getRoleName());
        infoTextView.setText(movie.getDescription());
    }
    private void initScrollImage(View header){
        mGallery = (LinearLayout) header.findViewById(R.id.mGallery);
        for(int i=0;i<images.length;i++){
            View v = LayoutInflater.from(this).inflate(R.layout.item_movie_image_info,mGallery,false);
            ImageView iv = (ImageView) v.findViewById(R.id.itemMovieImageView);
            iv.setImageResource(images[i]);
            TextView tv = (TextView) v.findViewById(R.id.roleNameTextView);
            tv.setText("actor's name");

            mGallery.addView(v);
        }
    }
    private void initImages(){
        images = new int[]{R.mipmap.image1,R.mipmap.image2,R.mipmap.image3,R.mipmap.image4,
                R.mipmap.image1,R.mipmap.image4,R.mipmap.image2,R.mipmap.image3};
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling_movie,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            mIntent = new Intent(this,NavigationDrawerActivity.class);
            startActivity(mIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getExtras() {
        String movieId = (String) getIntent().getExtras().get("movieId");
        if(movieId ==null){
            throw new RuntimeException("movieId can not null");
        }
        return movieId;
    }
}
