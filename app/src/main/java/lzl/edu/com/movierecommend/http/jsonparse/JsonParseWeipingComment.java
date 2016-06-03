package lzl.edu.com.movierecommend.http.jsonparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lzl.edu.com.movierecommend.entity.Film;
import lzl.edu.com.movierecommend.entity.MUser;
import lzl.edu.com.movierecommend.entity.WeipingCustom;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.util.MovieLabel;

/**
 * Created by admin on 2016/5/28.
 */
public class JsonParseWeipingComment implements JsonParseInterface<List<WeipingCustom>> {
    @Override
    public List<WeipingCustom> parseJsonByObject(List<WeipingCustom> result, JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<WeipingCustom> parseJsonByArray(List<WeipingCustom> result, JSONArray jsonArray) {
        List<WeipingCustom> weipingCustoms = result;
        for(int i=0;i<jsonArray.length();i++){
            WeipingCustom weipingCustom = new WeipingCustom();
            MUser user = new MUser();
            Film film = new Film();
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                //用户信息
                user.setUserID(jsonObject.getString("userid"));
                user.setName(jsonObject.getString("userName"));
                user.setHeadImg(URLAddress.getRealUrlImg(jsonObject.getString("headImg")));
                weipingCustom.setUser(user);
                //电影信息
                film.setFilmID(jsonObject.getString("movieid"));
                film.setName(jsonObject.getString("name"));
                film.setType(MovieLabel.getMovieType(jsonObject.getInt("type")));
                film.setTimeOn(jsonObject.getString("timeOn"));
                film.setTimeLeng(jsonObject.getString("timeLength"));
                film.setActor(jsonObject.getString("actor"));
                film.setDirector(jsonObject.getString("director"));
                film.setImagepath(URLAddress.getRealUrlImg(jsonObject.getString("imgPath")));
                weipingCustom.setFilm(film);
                //微评信息
                weipingCustom.setArticleID(jsonObject.getString("weipingId"));
                weipingCustom.setContent(jsonObject.getString("content"));
                weipingCustom.setPublishTime(jsonObject.getString("publishTime"));
                weipingCustom.setForwardNum(jsonObject.getInt("forward"));
                weipingCustom.setFeedBackNum(jsonObject.getInt("feedBack"));
                weipingCustom.setGoodNum(jsonObject.getInt("goodNum"));
                weipingCustom.setFilmID(jsonObject.getString("movieid"));
                weipingCustom.setUserID(jsonObject.getString("userid"));
                //添加到list集合中
                weipingCustoms.add(weipingCustom);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return weipingCustoms;
    }
}
