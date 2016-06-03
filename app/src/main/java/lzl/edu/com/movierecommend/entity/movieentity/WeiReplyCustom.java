package lzl.edu.com.movierecommend.entity.movieentity;

import lzl.edu.com.movierecommend.entity.MUser;
import lzl.edu.com.movierecommend.entity.WeiReply;

public class WeiReplyCustom extends WeiReply{
	private MUser user;

	public MUser getUser() {
		return user;
	}

	public void setUser(MUser user) {
		this.user = user;
	}
}
