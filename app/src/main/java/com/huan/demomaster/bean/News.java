package com.huan.demomaster.bean;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable{

	private String id;
	private User author;
	private String title;
	private String imageUrl;
	private String url;
	private Date time;
	private boolean isOriginal;
	
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isOriginal() {
		return isOriginal;
	}
	public void setOriginal(boolean isOriginal) {
		this.isOriginal = isOriginal;
	}
	
}
