package shareprefrence;

import android.content.Context;
import android.content.SharedPreferences;

import lzl.edu.com.movierecommend.entity.User;

/**
 * Created by wjb on 2015/12/13.
 */
public class OperateShareprefrence {

    /**
     * 存储用户数据
     * @param context
     * @param userid
     * @param userphone
     */
    public static void storeShareprefrence(Context context,String userid,String userphone,String username) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", 0).edit();
        editor.putString("userid", userid);
        editor.putString("userphone", userphone);
        editor.putString("username", username);
        editor.commit();
    }

    /**
     * 获取用户对象
     * @param context
     * @return
     */
    public static User  loadShareprefrence(Context context) {
        User u = new User();
        SharedPreferences pref = context.getSharedPreferences("user", 0);
        u.setUserid(pref.getString("userid",""));
        u.setUserphone(pref.getString("userphone",""));
        u.setNickName(pref.getString("username",""));
        return u;
    }

    public static void deleteShareprefrence(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("user", 0).edit();
        editor.clear();
        editor.commit();
    }

}
