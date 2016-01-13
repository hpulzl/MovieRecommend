package lzl.edu.com.movierecommend.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by admin on 2016/1/8.
 * 该类用于判断网络是否连接。
 * 当前网络是否有用。
 */
public class NetWorkUtil {
    public static boolean isNetWorkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager!=null){
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if(info!=null && info.isConnected()){
                    if(info.getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
        }
        return false;
    }
}
