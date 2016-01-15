package lzl.edu.com.movierecommend.util;

import android.text.TextUtils;

/**
 * Created by admin on 2016/1/14.
 */
public class CheckValidate {
    public static final String USER_EMPTY="手机号不能为空";
    public static final String FAULT="手机号或密码错误";
    public static final String USER_UNFORM="手机号格式不对";
    public static final String PASS_EMPTY="密码不能为空";
    public static final String PASS_UNFORM="密码少于6位";
    public static final String NICK_UNFORM="用户名不符合要求";
    public static final String NICK_EMPTY="用户名不能为空";
    public static final String CODE_EMPTY="验证码不能为空";

    public String checkPhone(String phoneNum){
        if(TextUtils.isEmpty(phoneNum)){
            return USER_EMPTY;
        }else if(phoneNum.length()<11){
            return USER_UNFORM;
        }
        return null;
    }
    public String checkPass(String passNum) {
        if(TextUtils.isEmpty(passNum)){ //密码为空
            return CheckValidate.PASS_EMPTY;
        }else if(passNum.length()<6){
            return PASS_UNFORM;
        }
        return null;
    }
    public String checkNickName(String nickName){
        int length = nickName.length();
        if(TextUtils.isEmpty(nickName)){ //密码为空
            return NICK_EMPTY;
        }else if(length<1||length>5){
            return NICK_UNFORM;
        }
        return null;
    }
    public String checkCode(String code){
        if(TextUtils.isEmpty(code)){
            return CODE_EMPTY;
        }
        return null;
    }
}
