package lzl.edu.com.movierecommend.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lzl.edu.com.movierecommend.R;

/**
 * Created by admin on 2015/12/28.
 */
public class SearchFragment extends Fragment {
    private View view;
    private Toolbar toolbar;
    private Activity mActivity;
    private AppCompatActivity mAppCompatActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_search_movie,container,false);
        mActivity = getActivity();
        initToolbar();
        return view;
    }
    private void initToolbar(){
        mAppCompatActivity = (AppCompatActivity) mActivity;
        toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.toolBar);
        toolbar.setTitle("搜索");
        toolbar.setBackgroundColor(mActivity.getResources().getColor(R.color.colorPrimary));
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
            //如果显示到前端时，重新加载Toolbar
            initToolbar();
        }
    }
}
