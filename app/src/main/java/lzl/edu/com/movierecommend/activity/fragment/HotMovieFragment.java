package lzl.edu.com.movierecommend.activity.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.util.MovieLabel;

public class HotMovieFragment extends Fragment {
    private View view;
    private Activity mActivity;
    private GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hot_movie, container, false);
        mActivity = getActivity();
        initLabel();
        return view;
    }
    private void initLabel(){
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
                Toast.makeText(mActivity,"点击率"+tv.getText(),Toast.LENGTH_SHORT).show();
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