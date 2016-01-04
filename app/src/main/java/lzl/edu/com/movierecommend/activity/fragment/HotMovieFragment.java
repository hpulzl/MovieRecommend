package lzl.edu.com.movierecommend.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.adapter.HotMovieAdapter;
import lzl.edu.com.movierecommend.entity.Movie;
import lzl.edu.com.movierecommend.util.MovieLabel;

public class HotMovieFragment extends Fragment {
    private Activity mActivity;
    private GridView gridView;
    private RecyclerView hotMovieRecycleView;
    private HotMovieAdapter hotMovieAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = getActivity();
       View view = inflater.inflate(R.layout.fragment_hot_movie, container, false);
        initMovieData(view);
        initLabel(view);
        return view;
    }

    private void initMovieData(View view){
        hotMovieRecycleView = (RecyclerView) view.findViewById(R.id.hotMovieRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置为线性布局
        hotMovieRecycleView.setLayoutManager(linearLayoutManager);
        hotMovieAdapter = new HotMovieAdapter(hotMovieRecycleView,getDatas());
        hotMovieRecycleView.setAdapter(hotMovieAdapter);

       RecyclerViewHeader header = RecyclerViewHeader.fromXml(mActivity,R.layout.recyclerview_header);
        header.attachTo(hotMovieRecycleView);
    }
    private void initLabel(View view){
        gridView = (GridView) view.findViewById(R.id.gridView);
        GridViewAdapter adapter = new GridViewAdapter(mActivity);
        gridView.setAdapter(adapter);
        gridViewClick();
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
    private List<Movie> getDatas(){
      List<Movie>  hotMovieList = new ArrayList<>();
        List<Integer> seenList= new ArrayList<>();
        seenList.add(1000);
        seenList.add(1345);
        seenList.add(52123);
        List<Integer> urlList = new ArrayList<>();
        urlList.add(R.mipmap.image1);
        urlList.add(R.mipmap.image2);
        urlList.add(R.mipmap.image3);
        for(int i=0;i<5;i++){
            Movie m = new Movie();
            m.setMovieClassify("经典片"+i);
            m.setSeenPerson(seenList);
            m.setMoviesImage(urlList);
            hotMovieList.add(m);
        }
        return hotMovieList;
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