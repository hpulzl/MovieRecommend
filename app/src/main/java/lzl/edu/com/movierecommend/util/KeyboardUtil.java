package lzl.edu.com.movierecommend.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by admin on 2016/1/14.
 */
public class KeyboardUtil {
    private Activity mActivity;
    public KeyboardUtil(Activity activity){
    this.mActivity = activity;
    }
    public void hideKeyboard() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
