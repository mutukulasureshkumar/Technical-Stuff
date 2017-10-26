package com.altimetrik.trackMgnt.model;


public class Talks implements Comparable<Talks>{
	public String getTalkName() {
		return talkName;
	}
	public void setTalkName(String talkName) {
		this.talkName = talkName;
	}
	public int getTalkTime() {
		return talkTime;
	}
	public void setTalkTime(int talkTime) {
		this.talkTime = talkTime;
	}
	private String talkName;
	private int talkTime;
	@Override
	public int compareTo(Talks talks) {
		int compareage=((Talks)talks).getTalkTime();
        /* For Ascending order*/
        return this.talkTime-compareage;
	}
}

