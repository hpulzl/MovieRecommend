package lzl.edu.com.movierecommend.http.jsonparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lzl.edu.com.movierecommend.entity.movieentity.Movie;
import lzl.edu.com.movierecommend.entity.movieentity.MovieType;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.util.LogUtil;
import lzl.edu.com.movierecommend.util.MovieLabel;

/**
 * Created by admin on 2016/3/11.
 */
public class JsonParseHotMovie implements JsonParseInterface<List<MovieType>> {
    private final String TAG="JsonParseHotMovie";
    @Override
    public List<MovieType> parseJsonByObject(List<MovieType> result, JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<MovieType> parseJsonByArray(List<MovieType> result, JSONArray jsonArray){
        List<MovieType> list = result;
        LogUtil.i(TAG,"jsonArray:"+jsonArray.toString());
        try {
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jo = jsonArray.getJSONObject(i);
                String type = jo.getString("movieType");
                LogUtil.i("jsonParseHotMovie","电影类型:"+type);
                MovieType movieType = new MovieType();
                movieType.setMovieId(jo.getString("movieId"));
                movieType.setMovieName(jo.getString("movieName"));
                movieType.setMovieType(type);
                movieType.setTotalPerson(jo.getString("clickNum"));
                movieType.setUrlImg(URLAddress.getRealUrlImg(jo.getString("urlImg")));
                //为movie进行分类
                MovieType movieTypeSetList = classifyMovie(type,movieType);
                //添加所有的movieType类。
                list.add(movieTypeSetList);
            }
            LogUtil.i(TAG,"listSize="+list.size());
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private MovieType classifyMovie(String type,MovieType movieType){
        List<Movie> movies = new ArrayList<>();
        MovieType movieTypeSetList = movieType;
        if(type.equals(MovieLabel.LOVE_MOVIE)){
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }else if(type.equals(MovieLabel.STORY_MOVIE)){
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }else if(type.equals(MovieLabel.SCIENCE_MOVIE)){
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }else if(type.equals(MovieLabel.STRANGE_MOVIE)){
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }else if(type.equals(MovieLabel.SWORD_MOVIE)){
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }else if(type.equals(MovieLabel.ACTION_MOVIE)){
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }else if(type.equals(MovieLabel.ART_MOVIE)){
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }else if(type.equals(MovieLabel.FAMILY_MOVIE)){
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }else if(type.equals(MovieLabel.ETHIC_MOVIE)){
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }else if(type.equals(MovieLabel.COMEDY_MOVIE)){
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }else {
            movies.add(movieType);
            movieTypeSetList.setMovieTypeList(movies);
        }
        return movieTypeSetList;
    }
}
