package lzl.edu.com.movierecommend.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.fragment.HotMovieFragment;
import lzl.edu.com.movierecommend.activity.fragment.LatestMovieFragment;
import lzl.edu.com.movierecommend.activity.fragment.YouLoveMovieFragment;
import lzl.edu.com.movierecommend.adapter.FragmentAdapter;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    //自定义的viewPager
    private ViewPager movieViewPager;
    //装在Fragment的Adapter适配器
    private FragmentAdapter fragmentAdapter;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    //三个Fragment
    private HotMovieFragment hotMovieFragment;  //热播电影 2
    private LatestMovieFragment latestMovieFragment;//最新电影  0
    private YouLoveMovieFragment youLoveMovieFragment;//你喜欢的电影 1


    private Toolbar toolbar;
    private Intent mIntent;
    private List<String> titleList = new ArrayList<>();

    private PagerSlidingTabStrip tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nativation_drawer);
        initView();
        initToolBar();
        createFragment();
    }
    private void initView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //初始化参数
        movieViewPager = (ViewPager) findViewById(R.id.moviePageViewPager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        navigationView.setNavigationItemSelectedListener(this);
    }
    private void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ab_search:
                        Toast.makeText(NavigationDrawerActivity.this,"搜索",Toast.LENGTH_LONG).show();
                        mIntent = new Intent(NavigationDrawerActivity.this,SearchMovieActivity.class);
                        startActivity(mIntent);
                        break;
                    case R.id.action_login:
                        Toast.makeText(NavigationDrawerActivity.this,"分享",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_logout:
                        Toast.makeText(NavigationDrawerActivity.this,"设置",Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 创建Fragment的方法
      */
    private void createFragment(){
        //创建Fragment
        hotMovieFragment = new HotMovieFragment();
        latestMovieFragment = new LatestMovieFragment();
        youLoveMovieFragment = new YouLoveMovieFragment();
        //添加
        fragmentList.add(latestMovieFragment);
        fragmentList.add(youLoveMovieFragment);
        fragmentList.add(hotMovieFragment);
        titleList.add("上映");
        titleList.add("热播电影");
        titleList.add("猜你喜欢");
        //设置到Adapter
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragmentList,titleList);
        movieViewPager.setAdapter(fragmentAdapter);
        tabs.setViewPager(movieViewPager);
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setDuplicateParentStateEnabled(true);
        movieViewPager.setCurrentItem(0);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            mIntent = new Intent(this,SearchMovieActivity.class);
            startActivity(mIntent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
