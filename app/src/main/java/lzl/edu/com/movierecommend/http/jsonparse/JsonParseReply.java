package lzl.edu.com.movierecommend.http.jsonparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lzl.edu.com.movierecommend.entity.MUser;
import lzl.edu.com.movierecommend.entity.movieentity.WeiReplyCustom;
import lzl.edu.com.movierecommend.http.URLAddress;

/**
 * Created by admin on 2016/3/16.
 */
public class JsonParseReply implements JsonParseInterface<List<WeiReplyCustom>> {
    @Override
    public List<WeiReplyCustom> parseJsonByObject(List<WeiReplyCustom> result, JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<WeiReplyCustom> parseJsonByArray(List<WeiReplyCustom> result, JSONArray jsonArray) {
        List<WeiReplyCustom> customs = result;
        try {
        for (int i=0;i<jsonArray.length();i++){
                JSONObject jo = jsonArray.getJSONObject(i);
               //解析微评回复内容
                WeiReplyCustom weiReplyCustom = new WeiReplyCustom();
                weiReplyCustom.setWreplyID(jo.getString("wreplyID"));
                weiReplyCustom.setArticleID(jo.getString("articleID"));
                weiReplyCustom.setWcontent(jo.getString("wcontent"));
                MUser user = new MUser();
                user.setName(jo.getString("username"));
                user.setHeadImg(URLAddress.getRealUrlImg(jo.getString("headImg")));
                weiReplyCustom.setUser(user);
            customs.add(weiReplyCustom);
            }
            return customs;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
