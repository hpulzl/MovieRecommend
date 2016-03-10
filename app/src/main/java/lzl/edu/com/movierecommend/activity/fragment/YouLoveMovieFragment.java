package lzl.edu.com.movierecommend.activity.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.adapter.ImagePager;
import lzl.edu.com.movierecommend.adapter.YouLoveMovieAdapter;
import lzl.edu.com.movierecommend.animation.ZoomOutPageTransformer;
import lzl.edu.com.movierecommend.entity.movieentity.Movie;

public class YouLoveMovieFragment extends Fragment {
    private Activity mActivity;
    private RecyclerView youLoveRecyclerView;
    private ViewPager imagePagerHeader;
    private int currentIndex;
    private List<ImageView> imageViewList;
    private static final int IMAGE_CHANGE = 0;
    private final long DELAY_TIME = 3000;
    private List<View> viewList;
    private YouLoveMovieAdapter youLoveMovieAdapter;

    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
          switch (msg.what){
              case IMAGE_CHANGE:
                  currentIndex = (currentIndex)%imageViewList.size();
                  imagePagerHeader.setCurrentItem(currentIndex);
                  currentIndex++;
                  handler.sendEmptyMessageDelayed(IMAGE_CHANGE,DELAY_TIME);
                  break;
              default:break;
          }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_you_love_movie, container, false);
        mActivity = getActivity();
        initDataView(view);
        initImagePager(view);
        return view;
    }

    /**
     * 初始化RecyclerView的数据
     * @param view
     */
    private void initDataView(View view){
        youLoveRecyclerView = (RecyclerView) view.findViewById(R.id.youLoveRecyclerView);
        youLoveRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        youLoveMovieAdapter = new YouLoveMovieAdapter(youLoveRecyclerView,getMovies());
        youLoveRecyclerView.setAdapter(youLoveMovieAdapter);
        RecyclerViewHeader header = RecyclerViewHeader.fromXml(mActivity,R.layout.viewpager_header);
        header.attachTo(youLoveRecyclerView);
    }

    /**
     * 获取你喜欢的电影的数据
     * @return
     */
    private List<Movie> getMovies(){
        List<Movie> movieList = new ArrayList<>();
        Movie m = new Movie();
        m.setMovieName("寻龙诀");
        m.setUrlImage(R.mipmap.image4);
//        m.setCollection(false);
        m.setDirectorName("我是谁");
        m.setRoleName("小天王");
//        m.setCollectionPersonNum(1000);
        for(int i=0;i<10;i++) {
            movieList.add(m);
        }
        return movieList;
    }
    /**
     * 初始化imagePager图片
     * @param view
     */
    private void initImagePager(View view){
        imagePagerHeader = (ViewPager) view.findViewById(R.id.imagePagerHeader);
        ImagePager imagePager = new ImagePager(getImages());
        getCircle(view);
        imagePagerHeader.setAdapter(imagePager);
        imagePagerHeader.setPageTransformer(true,new ZoomOutPageTransformer());
        //自动轮播
        startChangeImages();
        //手动轮播
        imagePagerHeaderScroll();
    }
    private void getCircle(View view){
        viewList = new ArrayList<>();
        viewList.add(view.findViewById(R.id.circle1));
        viewList.add(view.findViewById(R.id.circle2));
        viewList.add(view.findViewById(R.id.circle3));
    }
    /**
     * 为viewPagerImage设置滑动监听。
     */
    private void imagePagerHeaderScroll(){
        imagePagerHeader.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                if(imageViewList.size()>1){  //有1个以上图片
                    if(position > imageViewList.size()){
                        position = 0;
                        imagePagerHeader.setCurrentItem(position,false);
                    }else if(position < 0){
                        position = imageViewList.size();
                        imagePagerHeader.setCurrentItem(position,false);
                    }
                }
                for(int i=0;i<viewList.size();i++)
                    viewList.get(i).setEnabled(true);
                viewList.get(position).setEnabled(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    /**
     * 获取图片资源
     * @return
     */
    private List<ImageView> getImages(){
         imageViewList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        int imageId[] ={R.mipmap.image1,R.mipmap.image2,R.mipmap.image3};
        for(int i=0;i<imageId.length;i++){
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.item_images,null);
            ImageView image = (ImageView) linearLayout.findViewById(R.id.movieImageViewPagers);
            Log.i("viewImage",image.toString());
            image.setImageResource(imageId[i]);
            imageViewList.add(image);
        }
        return imageViewList;
    }
    private void startChangeImages(){
        handler.sendEmptyMessageDelayed(IMAGE_CHANGE,DELAY_TIME);
    }
}
