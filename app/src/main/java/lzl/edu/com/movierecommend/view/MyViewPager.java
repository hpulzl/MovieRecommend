package lzl.edu.com.movierecommend.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import lzl.edu.com.movierecommend.R;

/**
 * Created by admin on 2015/12/26.
 */
public class MyViewPager extends ViewPager {
    private int currentIndex;
    private LayoutInflater inflater;
    private Context mContext;
    public MyViewPager(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        mContext = context;
        initView();
        setOnPageChangeListener(new PageChangeListener());
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        setOnPageChangeListener(new PageChangeListener());
    }
    private void initView(){
        View view = inflate(mContext, R.layout.activity_movie_page,null);

    }
}

class PageChangeListener implements ViewPager.OnPageChangeListener{

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}