package lzl.edu.com.movierecommend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/18.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> list = new ArrayList<Fragment>();
    private List<String> titleList;
    public FragmentAdapter(FragmentManager fm, List<Fragment> list,List<String> titileList) {
        super(fm);
        this.list = list;
        this.titleList = titileList;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
