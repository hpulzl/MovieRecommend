package lzl.edu.com.movierecommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.fragment.HotMovieFragment;
import lzl.edu.com.movierecommend.activity.fragment.LatestMovieFragment;
import lzl.edu.com.movierecommend.activity.fragment.YouLoveMovieFragment;
import lzl.edu.com.movierecommend.adapter.FragmentAdapter;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,ViewPager.OnPageChangeListener,View.OnClickListener{
    //自定义的viewPager
    private ViewPager movieViewPager;
    //装在Fragment的Adapter适配器
    private FragmentAdapter fragmentAdapter;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    //三个Fragment
    private HotMovieFragment hotMovieFragment;  //热播电影 2
    private LatestMovieFragment latestMovieFragment;//最新电影  0
    private YouLoveMovieFragment youLoveMovieFragment;//你喜欢的电影 1

    private TextView comingSoonTextView,hotMovieTextView,youLoveTextView;

    private Toolbar toolbar;
    private Intent mIntent;

    private ImageView toolLineImageView;
    private int currentIndex;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nativation_drawer);
        initView();
        initToolBar();
        createFragment();
        initTabLineWidth();
    }
    private void initView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //初始化参数
        movieViewPager = (ViewPager) findViewById(R.id.moviePageViewPager);
        toolLineImageView = (ImageView) findViewById(R.id.toolLineImageView);
        comingSoonTextView = (TextView) findViewById(R.id.comingSoonTextView);
        hotMovieTextView = (TextView) findViewById(R.id.hotMovieTextView);
        youLoveTextView = (TextView) findViewById(R.id.youLoveTextView);

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
        //设置到Adapter
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        movieViewPager.setAdapter(fragmentAdapter);
        movieViewPager.setCurrentItem(0);

        movieViewPager.setOnPageChangeListener(this);
        comingSoonTextView.setOnClickListener(this);
        hotMovieTextView.setOnClickListener(this);
        youLoveTextView.setOnClickListener(this);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) toolLineImageView
                .getLayoutParams();

        /**
         * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
         * 设置mTabLineIv的左边距 滑动场景：
         * 记3个页面,
         * 从左到右分别为0,1,2
         * 0->1; 1->2; 2->1; 1->0
         */

        if (currentIndex == 0 && position == 0)// 0->1
        {
            lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currentIndex
                    * (screenWidth / 3));

        } else if (currentIndex == 1 && position == 0) // 1->0
        {
            lp.leftMargin = (int) (-(1 - positionOffset)
                    * (screenWidth * 1.0 / 3) + currentIndex
                    * (screenWidth / 3));

        } else if (currentIndex == 1 && position == 1) // 1->2
        {
            lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currentIndex
                    * (screenWidth / 3));
        } else if (currentIndex == 2 && position == 1) // 2->1
        {
            lp.leftMargin = (int) (-(1 - positionOffset)
                    * (screenWidth * 1.0 / 3) + currentIndex
                    * (screenWidth / 3));
        }
        toolLineImageView.setLayoutParams(lp);
    }

    @Override
    public void onPageSelected(int position) {
        currentIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    /**
     * 将Tab导航线的宽度设置为屏幕的1/3.
     */
    private void initTabLineWidth(){
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) toolLineImageView
                .getLayoutParams();
        lp.width = screenWidth / 3;
        toolLineImageView.setLayoutParams(lp);
    }
    /**
     * 设置点击哪一个Fragment
     * @param item
     */
    private void showFragment(int item){
        movieViewPager.setCurrentItem(item);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comingSoonTextView:
                Toast.makeText(NavigationDrawerActivity.this,"即将上映",Toast.LENGTH_LONG).show();
                showFragment(0);
                break;
            case R.id.hotMovieTextView:
                showFragment(1);
                break;
            case R.id.youLoveTextView:
                showFragment(2);
                break;
            default:
                break;
        }
    }
}
