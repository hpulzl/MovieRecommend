package lzl.edu.com.movierecommend.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lzl.edu.com.movierecommend.R;

/**
 * Created by admin on 2015/12/28.
 */
public class MineCommentsFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine_comments,container,false);
        return view;
    }
}
