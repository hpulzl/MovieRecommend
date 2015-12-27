package lzl.edu.com.movierecommend.entity;

/**
 * Created by admin on 2015/12/26.
 */
public class Movie {
    //电影图片
    private Integer urlImage;
    private String movieName;
    private Integer startNum;

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
}
