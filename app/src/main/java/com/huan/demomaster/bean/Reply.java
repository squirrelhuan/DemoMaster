package com.huan.demomaster.bean;

import java.util.Date;

public class Reply extends TopicBase {
	private int floor;// 楼层
	private Topic topic;// 所属的主题

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

}
