package lzl.edu.com.movierecommend.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
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

public class CommentFragment extends Fragment {
    private View view;
    private Activity mActivity;
    private Toolbar toolbar;
    private AppCompatActivity mAppCompatActivity;

    private HotCommentsFragment hotCommentsFragment;
    private MineCommentsFragment mineCommentsFragment;
    private List<Fragment> listFragment = new ArrayList<>();
    private List<String> comTitleList = new ArrayList<>();
    private ViewPager commentsViewPager;
    private PagerSlidingTabStrip tabStrip;
    private int colors[];


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = getActivity();
        colors = new int[]{mActivity.getResources().getColor(R.color.colorRed),
                getResources().getColor(R.color.colorRedLine)
        };
        view = inflater.inflate(R.layout.fragment_comment, container, false);
        initToolbar();
        createFragment();
        return view;
    }
    private void initToolbar(){
        mAppCompatActivity = (AppCompatActivity) mActivity;
        //Activity中的元素
        toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.toolBar);
        toolbar.setTitle("微评");
        toolbar.setBackgroundColor(colors[0]);
        mAppCompatActivity.setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) mAppCompatActivity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                mActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }
    private void createFragment(){
        tabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.commentsTabs);
        commentsViewPager = (ViewPager) view.findViewById(R.id.commentsViewPager);

        //创建Fragment
        hotCommentsFragment = new HotCommentsFragment();
        mineCommentsFragment = new MineCommentsFragment();
        listFragment.add(hotCommentsFragment);
        listFragment.add(mineCommentsFragment);

        comTitleList.add("热门微评");
        comTitleList.add("我的微评");

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getFragmentManager(),listFragment,comTitleList);
        commentsViewPager.setAdapter(fragmentAdapter);
        //commentsViewPager要装到tabStrip中
        tabStrip.setViewPager(commentsViewPager);
        tabStrip.setIndicatorColor(colors[1]);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //如果显示到前端时，重新加载Toolbar
            initToolbar();
        }
    }
}
