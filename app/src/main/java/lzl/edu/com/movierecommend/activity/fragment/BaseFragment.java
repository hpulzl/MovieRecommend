package lzl.edu.com.movierecommend.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 2016/3/17.
 * 该类作为Fragment的基类，主要实现下拉刷新的功能。
 * 所有继承该fragment的类，都公用同一个xml界面布局
 */
public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    public Activity mActivity;
    public Context mContext;
    public View view;
    public SwipeRefreshLayout baseRefeshLayout;
    public TextView baseLoadData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getFragmentId(), container, false);
        initFindViewById();
        initDatas();
        return view;
    }

    public void getApplication(Fragment f){
            mActivity = f.getActivity();
            mContext = f.getContext();
    }
    /**
     * 设置SwipeRefreshLayout的显示属性
     */
    public void setSwipeRefreshLayout(){
        baseRefeshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }
    public void setRefreshState(boolean state){
        baseRefeshLayout.setRefreshing(state);
    }
    public void setProgressViewOffset(){
        //这个是解决第一次进入首页时，没有产生下拉刷新的动画
        baseRefeshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                        getResources().getDisplayMetrics()));
    }
    @Override
    public void onRefresh() {
        setRefreshState(true);
        initDatas();
    }
    //布局id
    protected abstract int getFragmentId();
    //初始化数据
    public abstract void initDatas();
    public abstract void initFindViewById();
    public void reLoadData(){
        baseLoadData.setVisibility(View.VISIBLE);
        baseLoadData.setText("点击重新加载数据");
        baseLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatas();
            }
        });
    }
}
