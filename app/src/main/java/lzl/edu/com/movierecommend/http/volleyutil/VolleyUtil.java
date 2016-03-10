package lzl.edu.com.movierecommend.http.volleyutil;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import lzl.edu.com.movierecommend.util.ToastUtil;

/**
 * Created by admin on 2016/3/8.
 */
public class VolleyUtil {
    private VolleyUtil(){
        //防止被实例化
        throw new AssertionError();
    }

    /**
     * 使用jsonObject发送请求。
     * @param context 上下文对象
     * @param requestUrl 访问路径
     * @param jsonObject 访问数据，jsonObject对象
     * @param listener 访问网络成功的listener对象
     * @param errorListener 访问网络失败的listener对象
     */
    public static void sendJsonObjectRequest(Context context, String requestUrl, JSONObject jsonObject, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        if(NetWorkUtil.isNetWorkAvailable(context)) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestUrl, jsonObject, listener, errorListener);
            VolleySingleton volleySingleton = VolleySingleton.getInstance(context);
            volleySingleton.addToRequestQueue(jsonObjectRequest);
        }else{
            ToastUtil.toast(context,"网络连接失败，请检查网络...");
        }
    }

    /**
     * 使用jsonArray发送请求
     * @param context 上下文对象
     * @param requestUrl 访问路径
     * @param listener 成功listener
     * @param errorListener 失败listener
     */
    public static void sendJsonArrayRequest(Context context, String requestUrl, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener){
        if(NetWorkUtil.isNetWorkAvailable(context)) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(requestUrl, listener, errorListener);
            VolleySingleton volleySingleton = VolleySingleton.getInstance(context);
            volleySingleton.addToRequestQueue(jsonArrayRequest);
        }else{
            ToastUtil.toast(context,"网络连接失败，请检查网络...");
        }
    }
    public static ImageLoader getImageLoader(Context context) {
            return VolleySingleton.getInstance(context).getImageLoader();
    }
    public static ImageLoader.ImageContainer sendImageLoader(Context context,String requestUrl, ImageView view,int defaultImageId,int errorImageId){
        if(NetWorkUtil.isNetWorkAvailable(context)) {
            VolleySingleton volleySingleton = VolleySingleton.getInstance(context);
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(view,defaultImageId,errorImageId);
            return volleySingleton.getImageLoader().get(requestUrl,listener);
        }else{
            ToastUtil.toast(context,"网络连接失败，请检查网络...");
        }
        return null;
    }
}
