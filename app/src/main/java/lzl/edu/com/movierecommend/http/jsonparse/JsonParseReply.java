package lzl.edu.com.movierecommend.http.jsonparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lzl.edu.com.movierecommend.entity.movieentity.Movie;
import lzl.edu.com.movierecommend.http.URLAddress;

/**
 * Created by admin on 2016/3/16.
 */
public class JsonParseReply implements JsonParseInterface<List<Movie>> {
    @Override
    public List<Movie> parseJsonByObject(List<Movie> result, JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<Movie> parseJsonByArray(List<Movie> result, JSONArray jsonArray) {
        List<Movie> movies = result;
        try {
        for (int i=0;i<jsonArray.length();i++){
                JSONObject jo = jsonArray.getJSONObject(i);
                Movie m = new Movie();
                m.setMovieId(jo.getString("movieId"));
                m.setUrlImg(URLAddress.getRealUrlImg(jo.getString("imgUrl")));
                m.setMovieName(jo.getString("movieName"));
                m.setDirectorName(jo.getString("director"));
                m.setRoleName(jo.getString("actor"));
                m.setTotalPerson(jo.getString("clickNum"));
                movies.add(m);
            }
            return movies;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
