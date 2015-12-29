package lzl.edu.com.movierecommend.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import lzl.edu.com.movierecommend.R;

public class ScrollingMovieActivity extends AppCompatActivity {
    private Toolbar moviesToolbar;
    private CollapsingToolbarLayout toolbar_layout;
    private Intent mItent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_movie);
        initToolbar();
    }
    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.moviesToolbar);
        toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar_layout.setTitle("叶问");
        //设置扩展时的字体颜色
        toolbar_layout.setExpandedTitleColor(Color.GREEN);
        //设置收缩时字体颜色
        toolbar_layout.setCollapsedTitleTextColor(Color.WHITE);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling_movie,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            mItent = new Intent(this,NavigationDrawerActivity.class);
            startActivity(mItent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
