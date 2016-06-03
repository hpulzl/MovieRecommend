package lzl.edu.com.movierecommend.activity.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.adapter.HotMovieAdapter;
import lzl.edu.com.movierecommend.entity.movieentity.MovieType;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.http.jsonparse.JsonParseHotMovie;
import lzl.edu.com.movierecommend.http.volleyutil.VolleyUtil;
import lzl.edu.com.movierecommend.util.HandlerMsgNum;
import lzl.edu.com.movierecommend.util.MovieLabel;
import lzl.edu.com.movierecommend.util.ToastUtil;

public class HotMovieFragment extends BaseFragment {
    private GridView gridView;
    private RecyclerView hotMovieRecycleView;
    private HotMovieAdapter hotMovieAdapter;
    private List<MovieType> hotMovieList = new ArrayList<>();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HandlerMsgNum.REFRESH_ZERO:
                    hotMovieList.clear();
                    hotMovieList = (List<MovieType>) msg.obj;
                    if(hotMovieList==null){
                        throw new RuntimeException("data is null");
                    }
                    hotMovieAdapter.setData(hotMovieList);
                    hotMovieAdapter.notifyDataSetChanged();
                    setRefreshState(false);
                    break;
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
        return R.layout.fragment_hot_movie;
    }

    @Override
    public void initDatas() {
        setRefreshState(true);
        String url = URLAddress.getURLPath("HotFilmSer");
        VolleyUtil.sendJsonArrayRequest(mActivity, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                JsonParseHotMovie jsonParseHotMovie = new JsonParseHotMovie();
                Message m = new Message();
                m.obj = jsonParseHotMovie.parseJsonByArray(new ArrayList<MovieType>(),jsonArray);
                m.what = HandlerMsgNum.REFRESH_ZERO;
                mHandler.sendMessage(m);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.toast(mActivity,"加载更多数据...");
            }
        });
    }

    @Override
    public void initFindViewById() {
        //初始化进度条
        baseRefeshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hotMovieFreshLayout);
        baseLoadData = (TextView) view.findViewById(R.id.reLoadData_tv);
        super.setProgressViewOffset();
        super.setSwipeRefreshLayout();
        baseRefeshLayout.setOnRefreshListener(this);
        hotMovieRecycleView = (RecyclerView) view.findViewById(R.id.hotMovieRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置为线性布局
        hotMovieRecycleView.setLayoutManager(linearLayoutManager);
        hotMovieAdapter = new HotMovieAdapter(hotMovieRecycleView,hotMovieList);
        hotMovieRecycleView.setAdapter(hotMovieAdapter);
        hotMovieAdapter.setHeaderView(initLabel());
    }

    private View initLabel(){
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_header,hotMovieRecycleView,false);
        gridView = (GridView) headerView.findViewById(R.id.gridView);
        GridViewAdapter adapter = new GridViewAdapter(mActivity);
        gridView.setAdapter(adapter);
        gridViewClick();
        return headerView;
    }
    private void gridViewClick(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.labelTextView);
                tv.setEnabled(true);
                tv.setTextColor(Color.WHITE);
                Toast.makeText(mActivity,"点击了"+tv.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
class GridViewAdapter extends BaseAdapter{
    private List<String> list = MovieLabel.getMovieList();
    private Context mContext;
    private LayoutInflater inflater;
    public GridViewAdapter(Context c){
        mContext = c;
        inflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieLabelHolder movieLabelHolder;
        if(convertView ==null){
            convertView = inflater.inflate(R.layout.movie_label,null);
            movieLabelHolder = new MovieLabelHolder();
            movieLabelHolder.tv = (TextView) convertView.findViewById(R.id.labelTextView);

            convertView.setTag(movieLabelHolder);
        }else{
            movieLabelHolder = (MovieLabelHolder) convertView.getTag();
        }
        movieLabelHolder.tv.setEnabled(false);
        movieLabelHolder.tv.setText(list.get(position));
        return convertView;
    }
    class MovieLabelHolder{
        TextView tv;
    }
}