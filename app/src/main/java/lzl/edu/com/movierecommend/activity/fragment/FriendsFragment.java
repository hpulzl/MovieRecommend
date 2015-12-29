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

public class FriendsFragment extends Fragment {
    private View view;
    private Activity mActivity;
    private AppCompatActivity mAppCompatActivity;
    private Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends, container, false);
        mActivity = getActivity();
        initToolbar();
        return view;
    }
    private void initToolbar(){
        mAppCompatActivity = (AppCompatActivity) mActivity;
        //Activity中的元素
        toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.toolBar);
        toolbar.setTitle("好友列表");
        toolbar.setBackgroundColor(mActivity.getResources().getColor(R.color.colorLightGreen));
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
            this.initToolbar();
        }
    }
}
