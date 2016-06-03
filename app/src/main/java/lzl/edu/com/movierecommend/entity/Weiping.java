package lzl.edu.com.movierecommend.entity;

import java.io.Serializable;

public class Weiping implements Serializable{
	private String articleID;
	private String filmID;
	private String userID;
	private String publishTime;
	private String content;
	private int forwardNum;
	private int feedBackNum;
	private int rating;
	private String transferArticleID;
	private int goodNum;
	
	public String getArticleID() {
		return articleID;
	}
	public void setArticleID(String articleID) {
		this.articleID = articleID;
	}
	public String getFilmID() {
		return filmID;
	}
	public void setFilmID(String filmID) {
		this.filmID = filmID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getForwardNum() {
		return forwardNum;
	}
	public void setForwardNum(int forwardNum) {
		this.forwardNum = forwardNum;
	}
	public int getFeedBackNum() {
		return feedBackNum;
	}
	public void setFeedBackNum(int feedBackNum) {
		this.feedBackNum = feedBackNum;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getTransferArticleID() {
		return transferArticleID;
	}
	public void setTransferArticleID(String transferArticleID) {
		this.transferArticleID = transferArticleID;
	}
	public int getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}
}
