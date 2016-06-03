package lzl.edu.com.movierecommend.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.base.BaseRecyclerAdapter;
import lzl.edu.com.movierecommend.activity.base.RecyclerViewHolder;
import lzl.edu.com.movierecommend.entity.MUser;
import lzl.edu.com.movierecommend.http.URLAddress;

/**
 * Created by admin on 2016/1/5.
 */
public class FriendsAdapter extends BaseRecyclerAdapter<MUser>{
    public FriendsAdapter(RecyclerView view, List<MUser> datas) {
        super(view, datas, R.layout.item_friend_fregment);
    }
    @Override
    public void convert(RecyclerViewHolder holder, MUser item, int position, boolean isScrolling) {
        //设置微评用户
        if (item.isMyFriend()){  //是否关注
            holder.setText(R.id.attention_btn,"已关注");
        }else {
            holder.setText(R.id.attention_btn,"+关注");
        }
        if(item.getSex()==0){ //女的
            holder.setImageResource(R.id.sex_iv,R.mipmap.ic_female);
        }else{  //男的
            holder.setImageResource(R.id.sex_iv,R.mipmap.ic_male);
        }
        holder.setNetWorkImageUrl(R.id.headImg_Network, URLAddress.getRealUrlImg(item.getHeadImg()),super.mContext);
        holder.setText(R.id.username_tv,item.getName());
    }
}
