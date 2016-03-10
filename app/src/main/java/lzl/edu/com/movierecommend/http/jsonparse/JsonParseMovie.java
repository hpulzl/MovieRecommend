package lzl.edu.com.movierecommend.http.jsonparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.entity.movieentity.Movie;
import lzl.edu.com.movierecommend.http.URLAddress;

/**
 * Created by admin on 2016/3/8.
 */
public class JsonParseMovie implements JsonParseInterface<List<Movie>> {

    @Override
    public List<Movie> parseJsonByObject(List<Movie> result, JSONObject jsonObject) {
    return result;
    }

    @Override
    public List<Movie> parseJsonByArray(List<Movie> result, JSONArray jsonArray) {
        ArrayList<Movie> movies = (ArrayList<Movie>) result;
        try {
            for(int i=0;i<jsonArray.length();i++){
            JSONObject jo = jsonArray.getJSONObject(i);
            Movie m = new Movie();
            m.setMovieName(jo.getString("filmName"));
            m.setStartNum(jo.getInt("startNum"));
            m.setUrlImg(URLAddress.getRealUrlImg(jo.getString("imgUrl")));
            m.setMovieId(jo.getString("movieId"));
            movies.add(m);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
