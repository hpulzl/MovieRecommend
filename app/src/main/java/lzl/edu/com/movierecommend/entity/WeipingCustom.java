package lzl.edu.com.movierecommend.entity;

import java.io.Serializable;

/**
 * Created by admin on 2016/5/28.
 */
public class WeipingCustom extends Weiping implements Serializable{
    private MUser user;
    private Film film;

    public MUser getUser() {
        return user;
    }

    public void setUser(MUser user) {
        this.user = user;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
