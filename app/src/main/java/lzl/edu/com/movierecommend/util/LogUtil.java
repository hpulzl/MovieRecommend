package lzl.edu.com.movierecommend.util;

import android.util.Log;

/**
 * Created by admin on 2016/3/6.
 */
public class LogUtil{
    public static final int INFO = 1;
    public static final int WARN = 2;
    public static final int LEVEL = 0;
    public static void i(String tag,String info){
        if(LEVEL < INFO){
            Log.i(tag,info);
        }
    }
    public static void w(String tag,String info){
        if(LEVEL < WARN){
            Log.w(tag,info);
        }
    }
}
