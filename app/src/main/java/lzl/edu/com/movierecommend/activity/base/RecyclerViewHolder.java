package lzl.edu.com.movierecommend.activity.base;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by admin on 2015/12/27.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder{
    //创建一个容器，该容器是Android中类似于Map的容器
    private SparseArray<View> viewArray ;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        viewArray = new SparseArray<View>();
    }

    /**
     * 获取所有的view。
     * @return
     */
    public SparseArray<View> getViewArray(){
        return this.viewArray;
    }

    /**
     * 根据id获取对应的控件，如果没有 则添加到viewArray中
     * @param itemId
     */
    public <T extends View> T getView(int itemId){
        View mView = viewArray.get(itemId);
        if(mView == null){
            mView = itemView.findViewById(itemId);
            viewArray.put(itemId,mView);
        }
        return (T)mView;
    }

    /**
     * 给textView设值
     * @param itemId
     * @param text
     */
    public RecyclerViewHolder setText(int itemId, String text){
        TextView textView = getView(itemId);
        textView.setText(text);
        return this;
    }
    public RecyclerViewHolder setRateBar(int itemId,float num){
        RatingBar rb = getView(itemId);
        rb.setRating(num);
        return this;
    }
    /**
     *设置图片
     * @param itemId
     * @param drawableId
     * @return
     */
    public RecyclerViewHolder setImageResource(int itemId, int drawableId){
        ImageView imageView = getView(itemId);
        imageView.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     * @param itemId
     * @param bitmap
     * @return
     */
    public RecyclerViewHolder setImageBitmap(int itemId, Bitmap bitmap){
        ImageView imageView = getView(itemId);
        imageView.setImageBitmap(bitmap);
        return this;
    }
}
