package com.altimetrik.trackMgnt.model;

import java.util.ArrayList;

public class InputDetails {
	private int NoOfTotalTracks;
	private int NoOfTotalHours;
	private ArrayList<Talks> talks=new ArrayList<>();
	public int getNoOfTotalTracks() {
		return NoOfTotalTracks;
	}
	public void setNoOfTotalTracks(int noOfTotalTracks) {
		NoOfTotalTracks = noOfTotalTracks;
	}
	public int getNoOfTotalHours() {
		return NoOfTotalHours;
	}
	public void setNoOfTotalHours(int noOfTotalHours) {
		NoOfTotalHours = noOfTotalHours;
	}
	public ArrayList<Talks> getTalks() {
		return talks;
	}
	public void setTalks(ArrayList<Talks> talks) {
		this.talks = talks;
	}
}
