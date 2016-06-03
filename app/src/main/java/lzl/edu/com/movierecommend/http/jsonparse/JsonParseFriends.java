package lzl.edu.com.movierecommend.http.jsonparse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lzl.edu.com.movierecommend.entity.MUser;

/**
 * Created by admin on 2016/3/16.
 */
public class JsonParseFriends implements JsonParseInterface<List<MUser>> {
    @Override
    public List<MUser> parseJsonByObject(List<MUser> result, JSONObject jsonObject) {
        return null;
    }

    @Override
    public List<MUser> parseJsonByArray(List<MUser> result, JSONArray jsonArray) {
        List<MUser> list = result;
        for(int i=0;i<jsonArray.length();i++){
            try {
                MUser user = new MUser();
                JSONObject jo = jsonArray.getJSONObject(i);
                user.setHeadImg(jo.getString("headImg"));
                user.setName(jo.getString("name"));
                user.setUserID(jo.getString("userID"));
                user.setMyFriend(jo.getBoolean("myFriend"));
                user.setSex(jo.getInt("sex"));
                list.add(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            return list;
    }
}
