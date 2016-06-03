package lzl.edu.com.movierecommend.http.jsonparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lzl.edu.com.movierecommend.http.URLAddress;

/**
 * Created by admin on 2016/3/17.
 */
public class JsonParseViewPagerMovieImg implements JsonParseInterface<List<String>> {
    @Override
    public List<String> parseJsonByObject(List<String> result, JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<String> parseJsonByArray(List<String> result, JSONArray jsonArray) {
        List<String> list = result;
        try {
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jo = jsonArray.getJSONObject(i);
                String movieId = URLAddress.getViewPagerUrl(jo.getString("movieId"));
                list.add(movieId);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
