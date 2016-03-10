package lzl.edu.com.movierecommend.http.jsonparse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by admin on 2016/3/8.
 */
public interface JsonParseInterface<T> {
    T parseJsonByObject(T result, JSONObject jsonObject);
    T parseJsonByArray(T result, JSONArray jsonArray);
}
