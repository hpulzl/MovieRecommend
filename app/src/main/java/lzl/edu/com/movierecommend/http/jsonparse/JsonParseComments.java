package lzl.edu.com.movierecommend.http.jsonparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lzl.edu.com.movierecommend.entity.Comments;
import lzl.edu.com.movierecommend.entity.User;
import lzl.edu.com.movierecommend.http.URLAddress;

/**
 * Created by admin on 2016/3/10.
 */
public class JsonParseComments implements JsonParseInterface<List<Comments>> {

    @Override
    public List<Comments> parseJsonByObject(List<Comments> result, JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<Comments> parseJsonByArray(List<Comments> result, JSONArray jsonArray) {
        List<Comments> commentses = result;
        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject jo = (JSONObject) jsonArray.get(i);
                Comments c = new Comments();
                User u = new User();
                c.setContents(jo.getString("rContent"));
                c.setTime(jo.getString("rTime"));
                u.setNickName(jo.getString("userName"));
                u.setHeadImg(URLAddress.getRealUrlImg(jo.getString("headImg")));
                u.setProvince(jo.getString("province"));
                c.setUser(u);
                commentses.add(c);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return commentses;
    }
}
