package lzl.edu.com.movierecommend.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import lzl.edu.com.movierecommend.R;

/**
 * Created by admin on 2016/1/14.
 * 监听EditText中是否有内容，
 * 如果有可以显示右边的图片--->并监听是否点击右边的图标。
 * 没有就不显示
 */
public class LoginEditText extends EditText implements OnFocusChangeListener,TextWatcher {
    private static final String TAG="LoginEditText";
    private Drawable mDrawable;
    public LoginEditText(Context context) {
        super(context);
        init();
    }

    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoginEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        Log.i(TAG,"init.....");
        //从xml文件中获取资源文件
        mDrawable = getCompoundDrawables()[2];
        if(mDrawable == null){
            mDrawable = getResources().getDrawable(R.mipmap.ic_login_clear);
        }
        mDrawable.setBounds(0,0,mDrawable.getIntrinsicWidth(),mDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(event.getX() > (getWidth() - getTotalPaddingRight()) && event.getX() <(getWidth() - getPaddingRight())){
                //判断是否点击中，删除的图标。
                this.setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    public void setClearIconVisible(boolean clearIconVisible) {
        //clearIconVisible为true，则显示图标，否则不显示
        Drawable clearIcon = clearIconVisible ? mDrawable : null;

        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],clearIcon,getCompoundDrawables()[3]);
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            //有内容时，为true
            setClearIconVisible(getText().length() >0);
        }else{
            setClearIconVisible(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        setClearIconVisible(getText().length()>0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
