package lzl.edu.com.movierecommend.entity;

import java.util.List;
import java.util.Map;

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
    //导演
    private String directorName;
    //主演
    private String roleName;
    //收藏
    private boolean isCollection;
    //收藏统计
    private Integer collectionPersonNum;
    //获取电影类别集合
    private List<Integer> moviesImage;
    //某一类电影的集合
    private Map<String,Movie> mapMovie;
    //获取电影的评论
    private List<Comments> commentsList;

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public String getDirectorName() {
        return directorName;
    }

    public Integer getCollectionPersonNum() {
        return collectionPersonNum;
    }

    public void setCollectionPersonNum(Integer collectionPersonNum) {
        this.collectionPersonNum = collectionPersonNum;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public Map<String, Movie> getMapMovie() {
        return mapMovie;
    }

    public void setMapMovie(Map<String, Movie> mapMovie) {
        this.mapMovie = mapMovie;
    }

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
