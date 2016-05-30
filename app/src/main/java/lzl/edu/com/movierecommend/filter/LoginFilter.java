package lzl.edu.com.movierecommend.filter;

import android.content.Context;

import lzl.edu.com.movierecommend.entity.User;
import shareprefrence.OperateShareprefrence;

/**
 * Created by admin on 2016/5/27.
 * 用户登录过滤器，所有需要登录权限的地方
 * 都要进行登录校验
 */
public class LoginFilter {
    /**
     * 用户登录校验
     * @param cxt
     * @return
     */
    public static boolean isLogin(Context cxt){
        User u = OperateShareprefrence.loadShareprefrence(cxt);
        if(u ==null|| u.getUserid().isEmpty()||u.getNickName().isEmpty()||u.getUserphone().isEmpty()){ //用户为空，说明没有进行登录
            return false;
        }
        return true;
    }
}
