package lzl.edu.com.movierecommend.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.ScrollingMovieActivity;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.adapter.ImagePager;
import lzl.edu.com.movierecommend.adapter.YouLoveMovieAdapter;
import lzl.edu.com.movierecommend.animation.ZoomOutPageTransformer;
import lzl.edu.com.movierecommend.entity.movieentity.Movie;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.http.jsonparse.JsonParseViewPagerMovieImg;
import lzl.edu.com.movierecommend.http.jsonparse.JsonParseYouLoveMovie;
import lzl.edu.com.movierecommend.http.volleyutil.VolleyUtil;
import lzl.edu.com.movierecommend.util.HandlerMsgNum;
import lzl.edu.com.movierecommend.util.LogUtil;
import lzl.edu.com.movierecommend.util.ToastUtil;
import shareprefrence.OperateShareprefrence;

public class YouLoveMovieFragment extends BaseFragment {
    private ViewPager imagePagerHeader;
    private int currentIndex;
    private List<NetworkImageView> imageViewList;
    private final long DELAY_TIME = 3000;
    private List<View> viewList;
    private YouLoveMovieAdapter youLoveMovieAdapter;
    private RecyclerView youLoveRecyclerView;
    private List<Movie> movieList = new ArrayList<>();
    private final static String TAG="YouLoveMovieFragment";

    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
          switch (msg.what){
              case HandlerMsgNum.IMAGE_CHANGE:
                  currentIndex = (currentIndex)%imageViewList.size();
                  imagePagerHeader.setCurrentItem(currentIndex);
                  currentIndex++;
                  handler.sendEmptyMessageDelayed(HandlerMsgNum.IMAGE_CHANGE,DELAY_TIME);
                  break;
              case HandlerMsgNum.REFRESH_ZERO:
                  //1、要获取后台中最新的电影图片和你喜欢的电影信息
                  //获取你喜欢的电影信息
                  movieList.clear();
                  movieList = (List<Movie>) msg.obj;
                 youLoveMovieAdapter.setData(movieList);
                  youLoveMovieAdapter.notifyDataSetChanged();
                  setRefreshState(false);
                  break;
              case HandlerMsgNum.REFRESH_ONE:
                  //已经获取电影的图片和ID
                  List<String> imgList = (List<String>) msg.obj;
                  initImagePager(imgList);
                  break;
              default:break;
          }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.getApplication(this);
       return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    protected int getFragmentId() {
        return R.layout.activity_you_love_movie;
    }

    @Override
    public void initFindViewById() {
        baseRefeshLayout = (SwipeRefreshLayout) view.findViewById(R.id.loveFreshLayout);
        baseLoadData = (TextView) view.findViewById(R.id.reLoadData_tv);
        super.setProgressViewOffset();
        super.setSwipeRefreshLayout();
        baseRefeshLayout.setOnRefreshListener(this);

        youLoveRecyclerView = (RecyclerView) view.findViewById(R.id.loveMovieRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        youLoveRecyclerView.setLayoutManager(linearLayoutManager);
        youLoveMovieAdapter = new YouLoveMovieAdapter(youLoveRecyclerView,movieList);
        View header =  LayoutInflater.from(mContext).inflate(R.layout.viewpager_header,youLoveRecyclerView,false);
        //获取ViewPager的网络图片..
        getNetImages();
        youLoveMovieAdapter.setHeaderView(header);
        youLoveRecyclerView.setAdapter(youLoveMovieAdapter);
        setOnItemClick();
    }

    @Override
    public void initDatas() {
        String userID = OperateShareprefrence.loadShareprefrence(mContext).getUserid();
        if(userID.isEmpty()){
            userID = "402881e6507a17b501507a17b69f0000";
        }
        getYouLoveMovieData(userID);
    }

    /**
     * 获取网络数据
     */
    private void getYouLoveMovieData(String userId) {
        setRefreshState(true);
        String url = URLAddress.getURLPath("YouLoveMovieSer?userId="+userId);
        VolleyUtil.sendJsonArrayRequest(mActivity, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                JsonParseYouLoveMovie jsonParseYouLoveMovie = new JsonParseYouLoveMovie();
                Message m = new Message();
                m.obj = jsonParseYouLoveMovie.parseJsonByArray(new ArrayList<Movie>(),jsonArray);
                m.what = HandlerMsgNum.REFRESH_ZERO;
                handler.sendMessage(m);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.toast(mActivity,"加载更多数据...");
            }
        });
    }

    private void setOnItemClick() {
        youLoveMovieAdapter.setOnItemOnclickListener(new BaseRecyclerAdapter.OnItemOnclickListener() {
            @Override
            public void onItemClick(View view, Object obj, int position) {
                if(movieList.size()>0) {
                    Intent mIntent = new Intent(mActivity, ScrollingMovieActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("movieId", movieList.get(position).getMovieId());
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                }
            }
        });
    }

    /**
     * 获取网络图片...
     */
    private void getNetImages(){
        String url = URLAddress.getURLPath("HotFilmImgSer");
        VolleyUtil.sendJsonArrayRequest(mActivity, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                LogUtil.i(TAG,jsonArray.toString());
                JsonParseViewPagerMovieImg jsonParseViewPagerMovieImg = new JsonParseViewPagerMovieImg();
                Message m = new Message();
                m.obj = jsonParseViewPagerMovieImg.parseJsonByArray(new ArrayList<String>(),jsonArray);
                m.what = HandlerMsgNum.REFRESH_ONE;
                handler.sendMessage(m);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.toast(mActivity,"加载更多数据...");
            }
        });
    }
    /**
     * 初始化imagePager图片
     */
    private void initImagePager(List<String> listImg){
        imagePagerHeader = (ViewPager) view.findViewById(R.id.imagePagerHeader);
        ImagePager imagePager = new ImagePager(getImages(listImg));
        getCircle(view);
        imagePagerHeader.setAdapter(imagePager);
        imagePagerHeader.setPageTransformer(true,new ZoomOutPageTransformer());
        //自动轮播
        startChangeImages();
        //手动轮播
        imagePagerHeaderScroll();
    }
    private void getCircle(View view){  //滚动的圆圈
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
     * 将网络图片转化成NetImageView格式
     * @return
     */
    private List<NetworkImageView> getImages(List<String> listImg){
         imageViewList = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        for(int i=0;i<listImg.size();i++){
            NetworkImageView linearLayout = (NetworkImageView) inflater.inflate(R.layout.item_images,imagePagerHeader,false);
            NetworkImageView image = (NetworkImageView) linearLayout.findViewById(R.id.movieImageViewPagers);
            image.setImageUrl(listImg.get(i),VolleyUtil.getImageLoader(mActivity));
            imageViewList.add(image);
        }
        return imageViewList;
    }
    private void startChangeImages(){
        handler.sendEmptyMessageDelayed(HandlerMsgNum.IMAGE_CHANGE,DELAY_TIME);
    }
}
