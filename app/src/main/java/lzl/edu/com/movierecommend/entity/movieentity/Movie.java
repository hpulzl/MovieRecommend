package lzl.edu.com.movierecommend.entity.movieentity;

import java.util.List;

import lzl.edu.com.movierecommend.entity.Comments;

/**
 * Created by admin on 2015/12/26.
 * 该类是电影基类
 * 拥有所有电影类的共性特征
 * 主要包括
 * 电影Id|电影名称|导演名|评分|主演|图片路径|评论类|统计评分人数|简介
 */
public class Movie {
    private String movieId;
    //计算评分
    private Integer startNum;
    //电影图片
    @Deprecated
    private int urlImage;
    private String urlImg;
    //电影名称
    private String movieName;
    //导演
    private String directorName;
    //主演
    private String roleName;
    //统计评分人数
    private String totalPerson;
    //简介
    private String description;

    public String getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(String totalPerson) {
        this.totalPerson = totalPerson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
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

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
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

    @Override
    public String toString() {
        return "Movie{" +
                "movieId='" + movieId + '\'' +
                ", startNum=" + startNum +
                ", urlImage=" + urlImage +
                ", urlImg='" + urlImg + '\'' +
                ", movieName='" + movieName + '\'' +
                ", directorName='" + directorName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", totalPerson='" + totalPerson + '\'' +
                ", description='" + description + '\'' +
                ", commentsList=" + commentsList +
                '}';
    }
}
