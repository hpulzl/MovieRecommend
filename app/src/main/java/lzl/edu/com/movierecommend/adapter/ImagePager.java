package lzl.edu.com.movierecommend.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by admin on 2016/1/4.
 */
public class ImagePager extends PagerAdapter {

    private List<NetworkImageView> imageViewList;
    public ImagePager(List<NetworkImageView> imageList){
        this.imageViewList = imageList;
    }
    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        NetworkImageView imageView = imageViewList.get(position);
        ViewParent vp = imageView.getParent();
        if(vp!=null){
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView(imageViewList.get(position));
    }
}
