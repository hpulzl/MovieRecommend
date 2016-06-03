package lzl.edu.com.movierecommend.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2015/10/29.
 */
public class ToastUtil extends Toast {
    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public ToastUtil(Context context) {
        super(context);
    }
    public static void toast(Context context,String info){
        Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
    }
    public static void analysisException(Context cxt,String message){
        toast(cxt,message);
    }
}
