package com.altimetrik.trackMgnt.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.altimetrik.trackMgnt.model.Talks;
import com.altimetrik.trackMgnt.model.Tracks;

public class Util {

	public static void removeTalksInList(List<Talks> talks, HashMap<String, Integer> currentTrackMap) {
		for (String taskName : currentTrackMap.keySet()) {
			for (int i = 0; i < talks.size(); i++) {
				if (talks.get(i).getTalkName().equals(taskName))
					talks.remove(i);
			}
		}
	}

	/*
	 * @ Print Talk times
	 * 
	 * @
	 */
	public static void print(ArrayList<Tracks> trackList) {
		int trackCount = 1;
		for (Tracks track : trackList) {
			System.out.println("Track : " + trackCount);
			if (track.getMrngSessionMap() != null) {
				int startTime = 9;
				int minsCount = 0;
				boolean check = true;
				for (String talkName : track.getMrngSessionMap().keySet()) {
					if (check) {
						System.out.println("09:00AM " + talkName);
						minsCount = (int) track.getMrngSessionMap().get(talkName);
						check = false;
					} else {
						int talkMins = (int) track.getMrngSessionMap().get(talkName);
						if (minsCount > 59) {
							startTime += 1;
							minsCount = minsCount - 60;
						}
						String time = formatTime(startTime, minsCount);
						minsCount += talkMins;
						System.out.println(time + "AM " + talkName);
					}
				}
			}
			System.out.println("12:00PM Lunch");
			if (track.getAftnSessionMap() != null) {
				int startTime = 1;
				int minsCount = 0;
				boolean check = true;
				for (String talkName : track.getAftnSessionMap().keySet()) {
					if (check) {
						System.out.println("01:00PM " + talkName);
						minsCount = (int) track.getAftnSessionMap().get(talkName);
						check = false;
					} else {
						int talkMins = (int) track.getAftnSessionMap().get(talkName);
						if (minsCount > 59) {
							startTime += 1;
							minsCount = minsCount - 60;
						}
						String time = formatTime(startTime, minsCount);
						minsCount += talkMins;
						System.out.println(time + "PM " + talkName);
					}
				}
			}
			System.out.println("05:00PM Networking Event");
			trackCount++;
		}
	}

	/*
	 * @ GET Hours, Minutes Format
	 * 
	 * @
	 */
	private static String formatTime(int i, int minutes) {
		int hours = i + (minutes / 60);
		int mins = minutes % 60;
		StringBuffer result = new StringBuffer();
		if (String.valueOf(hours).length() == 1)
			result.append("0" + String.valueOf(hours) + ":");
		else
			result.append(String.valueOf(hours) + ":");

		if (String.valueOf(mins).length() == 1)
			result.append("0" + String.valueOf(mins));
		else
			result.append(String.valueOf(mins));
		return result.toString();
	}

}
