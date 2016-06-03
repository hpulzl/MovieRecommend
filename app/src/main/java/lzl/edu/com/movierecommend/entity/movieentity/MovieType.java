package lzl.edu.com.movierecommend.entity.movieentity;

import java.util.List;

/**
 * Created by admin on 2016/3/11.
 * 该类继承Movie类
 * 拥有的私有属性是movieType.
 * movieType中包括多种movieType类型的电影集合。
 */
public class MovieType extends Movie {
    private String movieType;
    private List<Movie> movieList;

    public List<Movie> getMovieTypeList() {
        return movieList;
    }

    public void setMovieTypeList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

}
