package lzl.edu.com.movierecommend.activity.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.adapter.FragmentAdapter;

/**
 * Created by admin on 2015/12/28.
 */
public class MoviePagerFragment extends Fragment {
    private View view;
    private List<String> titleList = new ArrayList<>();
    //装在Fragment的Adapter适配器
    private FragmentAdapter fragmentAdapter;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    //三个Fragment
    private HotMovieFragment hotMovieFragment;  //热播电影 2
    private LatestMovieFragment latestMovieFragment;//最新电影  0
    private YouLoveMovieFragment youLoveMovieFragment;//你喜欢的电影 1
    private ViewPager movieViewPager;
    private PagerSlidingTabStrip tabs;
    private Activity mActivity;
    private Toolbar toolbar;
    private AppCompatActivity mAppCompatActivity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.activity_movie_page, container, false);
        mActivity = getActivity();
        initToolBar();
        createFragment();
        return view;
    }
    /**
     * 创建Fragment的方法
     */
    private void createFragment(){

        movieViewPager = (ViewPager) view.findViewById(R.id.moviePageViewPager);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);

        //创建Fragment
        hotMovieFragment = new HotMovieFragment();
        latestMovieFragment = new LatestMovieFragment();
        youLoveMovieFragment = new YouLoveMovieFragment();
        //添加
        fragmentList.add(latestMovieFragment);
        fragmentList.add(hotMovieFragment);
        fragmentList.add(youLoveMovieFragment);
        titleList.add("上映");
        titleList.add("热播电影");
        titleList.add("猜你喜欢");
        //设置到Adapter
        fragmentAdapter = new FragmentAdapter(getFragmentManager(),fragmentList,titleList);
        movieViewPager.setAdapter(fragmentAdapter);
        tabs.setViewPager(movieViewPager);
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setDuplicateParentStateEnabled(true);
        movieViewPager.setCurrentItem(0);
    }
    private void initToolBar(){
        mAppCompatActivity = (AppCompatActivity) mActivity;
        toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.toolBar);
        toolbar.setTitle("首页");
        toolbar.setBackgroundColor(mActivity.getResources().getColor(R.color.colorPrimary));
        mAppCompatActivity.setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) mAppCompatActivity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                mActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        toolbarClick();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //如果显示到前端时，重新加载Toolbar
            initToolBar();
        }
    }
}
