package lzl.edu.com.movierecommend.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by admin on 2016/1/11.
 * 继承NestedScrollView，
 * 重写onInterceptTouchEvent方法
 */
public class MyNestedScrollView extends NestedScrollView {
    private static final String TAG ="MyNestedScrollView";
    private int mTouchState = 0;
    //水平，左右滑动
    private static final int TOUCH_STATE_HORIZONTAL_SCROLLING = 1;
    //垂直，上下滑动
    private static final int TOUCH_STATE_VERTICAL_SCROLLING = 2;
    //抬起屏幕
    private static final int TOUCH_STATE_STATE_RESET = 0;
    private float mLastX = 0;
    private float mLastY = 0;
    public MyNestedScrollView(Context context) {
        super(context);
    }


    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,"监听事件ACTION_DOWN");
                mLastX = ev.getX();
                mLastY = ev.getY();
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG,"监听事件ACTION_MOVE");

                mTouchState = getScrollDirection(x,y,mLastX,mLastY);
                if(mTouchState == TOUCH_STATE_VERTICAL_SCROLLING){
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG,"监听事件ACTION_UP");
                mTouchState = TOUCH_STATE_STATE_RESET;
                intercept = false;
                break;
        }
        return intercept;
    }

    private int getScrollDirection(float reX,float reY,float lastX,float lastY){

        Log.i(TAG,"原x:"+reX+"reY "+reY+" lastX "+lastX+ " lastY "+lastY);

        float verticalNum = lastY - reY;
        float horizontalNum = lastX - reX;
        //垂直距离大于水平距离，判断为上下滑动
        if(Math.abs(verticalNum) > Math.abs(horizontalNum)){
            return TOUCH_STATE_VERTICAL_SCROLLING;
        }
        if(Math.abs(verticalNum) < Math.abs(horizontalNum)){
            return TOUCH_STATE_HORIZONTAL_SCROLLING;
        }
        //否则的话就不滑动
        return TOUCH_STATE_STATE_RESET;
    }
}
