package com.huan.demomaster.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Section implements Serializable{

	private String id;				//���id
	private String name;		//������
	private User author;		//����id
	private String logo;		//���logoͼƬ·��
	private List<Topic> topicList=new ArrayList<Topic>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public List<Topic> getTopicList() {
		return topicList;
	}
	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}
	
}
