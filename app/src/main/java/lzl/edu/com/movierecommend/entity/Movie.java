package lzl.edu.com.movierecommend.entity;

import java.util.List;

/**
 * Created by admin on 2015/12/26.
 */
public class Movie {
    //类别
    private String movieClassify;
    //看过
    private List<Integer> seenPerson;
    //电影图片
    private Integer urlImage;
    //电影名称
    private String movieName;
    //评论
    private Integer startNum;
    //获取电影类别集合
    private List<Integer> moviesImage;

    public Integer getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(Integer urlImage) {
        this.urlImage = urlImage;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public String getMovieClassify() {
        return movieClassify;
    }

    public void setMovieClassify(String movieClassify) {
        this.movieClassify = movieClassify;
    }


    public List<Integer> getSeenPerson() {
        return seenPerson;
    }

    public void setSeenPerson(List<Integer> seenPerson) {
        this.seenPerson = seenPerson;
    }

    public List<Integer> getMoviesImage() {
        return moviesImage;
    }

    public void setMoviesImage(List<Integer> moviesImage) {
        this.moviesImage = moviesImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return !(movieName != null ? !movieName.equals(movie.movieName) : movie.movieName != null);

    }

    @Override
    public int hashCode() {
        return movieName != null ? movieName.hashCode() : 0;
    }
}
