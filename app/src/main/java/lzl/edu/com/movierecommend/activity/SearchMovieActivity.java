package lzl.edu.com.movierecommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import lzl.edu.com.movierecommend.R;

public class SearchMovieActivity extends AppCompatActivity {
    private static final String SearchMovieActivityTag = "SearchMovieActivity";
    private Toolbar toolbar;
    private Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        initView();
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("搜索");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //点击返回...
        if(item.getItemId() == android.R.id.home){
            Toast.makeText(SearchMovieActivity.this,"返回",Toast.LENGTH_SHORT).show();
            mIntent = new Intent(SearchMovieActivity.this,NavigationDrawerActivity.class);
            startActivity(mIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);
        return true;
    }
}
