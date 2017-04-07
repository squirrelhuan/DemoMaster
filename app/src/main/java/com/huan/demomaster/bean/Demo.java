package com.huan.demomaster.bean;

import java.io.Serializable;
import java.util.Date;

public class Demo implements Serializable{
	private int id;
	private String name;
	private String headImageUrl;
	private String imageUrl;
	private User manager;
	private Plate plate;
	private User author;
	private boolean isOriginal;
	private Date time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getManager() {
		return manager;
	}
	public void setManager(User manager) {
		this.manager = manager;
	}
	public Plate getPlate() {
		return plate;
	}
	public void setPlate(Plate plate) {
		this.plate = plate;
	}
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	public boolean isOriginal() {
		return isOriginal;
	}
	public void setOriginal(boolean isOriginal) {
		this.isOriginal = isOriginal;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
