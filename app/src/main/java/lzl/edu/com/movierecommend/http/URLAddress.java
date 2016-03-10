package lzl.edu.com.movierecommend.http;

import android.util.Log;

import java.io.File;

/**
 * Created by admin on 2016/2/28.
 */
public class URLAddress {
//   public static final String IPADDRESS="http://192.168.1.101:8888";
  public static final String IPADDRESS="http://10.18.250.113:8888";
   private static final String WEB_PATH="FilmEvaluateSys";
   private static final String TAG="URLAddress";

   public static String getURLPath(String servletName){
      Log.i(TAG,IPADDRESS + File.separator+WEB_PATH+File.separator+servletName);
      return IPADDRESS + File.separator+WEB_PATH+File.separator+servletName;
   }
    public static String getRealUrlImg(String urlImg){
        return URLAddress.getURLPath("upload"+ File.separator+urlImg);
    }

}
