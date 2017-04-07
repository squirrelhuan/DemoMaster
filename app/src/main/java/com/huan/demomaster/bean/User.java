package com.huan.demomaster.bean;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

	private String Id;//登录用户id
	private String UserName;
	private String PassWord;
	private String NickName;
	private String headimageurl;//头像路径
	private String Sex;
	private String Email;
	private List<News> news;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getHeadimageurl() {
		return headimageurl;
	}

	public void setHeadimageurl(String headimageurl) {
		this.headimageurl = headimageurl;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

}
