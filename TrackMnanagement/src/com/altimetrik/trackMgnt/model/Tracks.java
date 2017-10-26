package com.altimetrik.trackMgnt.model;

import java.util.HashMap;

public class Tracks{
	private HashMap<String, Integer> mrngSessionMap;
	private HashMap<String, Integer> aftnSessionMap;
	public HashMap<String, Integer> getMrngSessionMap() {
		return mrngSessionMap;
	}
	public void setMrngSessionMap(HashMap<String, Integer> mrngSessionMap) {
		this.mrngSessionMap = mrngSessionMap;
	}
	public HashMap<String, Integer> getAftnSessionMap() {
		return aftnSessionMap;
	}
	public void setAftnSessionMap(HashMap<String, Integer> aftnSessionMap) {
		this.aftnSessionMap = aftnSessionMap;
	}
}
