package lzl.edu.com.movierecommend.http.jsonparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lzl.edu.com.movierecommend.entity.movieentity.Movie;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.util.LogUtil;

/**
 * Created by admin on 2016/3/9.
 */
public class JsonParseMovieInfo implements JsonParseInterface<Movie> {
    @Override
    public Movie parseJsonByObject(Movie result, JSONObject jsonObject) {
            Movie m = result;
        try {
            m.setMovieName(jsonObject.getString("movieName"));
            m.setDirectorName(jsonObject.getString("director"));
            m.setRoleName(jsonObject.getString("actor"));
            m.setDescription(jsonObject.getString("description"));
            m.setUrlImg(URLAddress.getRealUrlImg(jsonObject.getString("imgUrl")));
            m.setStartNum(jsonObject.getInt("startNum"));
            LogUtil.i("nihao",m.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return m;
    }

    @Override
    public Movie parseJsonByArray(Movie result, JSONArray jsonArray) {
        return result;
    }
}
