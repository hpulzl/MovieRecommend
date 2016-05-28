package lzl.edu.com.movierecommend.entity;

import java.util.Date;

public class Film {
	private String filmID;
	private String name;
	private int hot;
	private String actor;
	private String director;
	private String location;
	private String timeLeng;
	private String timeOn;
	private String description;
	private String language;
	private int type;
	private String imagepath;
	
	public String getFilmID() {
		return filmID;
	}
	public void setFilmID(String filmID) {
		this.filmID = filmID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTimeLeng() {
		return timeLeng;
	}
	public void setTimeLeng(String timeLeng) {
		this.timeLeng = timeLeng;
	}
	public String getTimeOn() {
		return timeOn;
	}
	public void setTimeOn(String timeOn) {
		this.timeOn = timeOn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	
}
