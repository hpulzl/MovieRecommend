package lzl.edu.com.movierecommend.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.adapter.ItemMovieHeaderAdapter;
import lzl.edu.com.movierecommend.entity.Comments;
import lzl.edu.com.movierecommend.entity.User;

public class ScrollingMovieActivity extends AppCompatActivity {
    private static final String TAG="ScrollingMovieActivity";

    private CollapsingToolbarLayout toolbar_layout;
    private Intent mIntent;
    private int images[];
    private Toolbar moviesToolbar;
    private LayoutInflater layout;
    private LinearLayout mGallery;
    private ItemMovieHeaderAdapter itemMovieAdapter;
    private RecyclerView recyclerView;
    private List<Comments> commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_movie);
        initToolbar();
        initComments();
    }

    private void initComments() {
        recyclerView = (RecyclerView) findViewById(R.id.itemMovieRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        itemMovieAdapter = new ItemMovieHeaderAdapter(getCommentsList());
       //设置头
        setHeader(recyclerView);
        setFooter(recyclerView);
        recyclerView.setAdapter(itemMovieAdapter);
    }
    private List<Comments> getCommentsList(){
        commentsList = new ArrayList<>();
        for(int i=0;i<10;i++){
            Comments comments = new Comments();
            comments.setContents("你是傻逼吧，怎么那么笨那。去电影院看看那不就知道了吗？");
            comments.setTime("2015-01-10");
            User u = new User();
            u.setNickImage(R.mipmap.image1);
            u.setNickName("小黑");
            u.setUserMember("黄金会员");
            comments.setUser(u);

            commentsList.add(comments);
        }
        return commentsList;
    }
    private void initToolbar(){
         moviesToolbar = (Toolbar) findViewById(R.id.moviesToolbar);
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(moviesToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar_layout.setTitle("叶问");
        //设置扩展时的字体颜色
        toolbar_layout.setExpandedTitleColor(Color.GREEN);
        //设置收缩时字体颜色
        toolbar_layout.setCollapsedTitleTextColor(Color.WHITE);
        setToolBarClick();
    }

    private void setToolBarClick() {
        moviesToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.scroll_share:
                        Toast.makeText(ScrollingMovieActivity.this,"分享",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.scroll_collection:
                        Toast.makeText(ScrollingMovieActivity.this,"收藏",Toast.LENGTH_LONG).show();
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
        initScrollImage(header);
        itemMovieAdapter.setHeaderView(header);
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
}
