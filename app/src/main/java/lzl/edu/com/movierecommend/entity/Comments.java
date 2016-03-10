package lzl.edu.com.movierecommend.entity;

/**
 * Created by admin on 2016/1/9.
 * 电影的评论表。
 * 这个评论是由用户发布的评论。
 * 通过该评论可以找到用户信息。
 *
 */
public class Comments {
    //发布时间
    private String time;
    //内容
    private String contents;
    //发布者
    private User user;   //用户写评论，一对多

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "time='" + time + '\'' +
                ", contents='" + contents + '\'' +
//                ", user=" + this.getUser().getNickName() +"..."+this.getUser().getHeadImg()+"..."+this.getUser().getProvince()
                +'}';
    }
}
