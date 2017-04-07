package com.huan.demomaster.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Topic extends TopicBase implements Serializable{

	private Section section;
	private String title;
    private List<Reply> replys;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Reply> getReplys() {
		return replys;
	}
	public void setReplys(List<Reply> replys) {
		this.replys = replys;
	}
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
    
}
