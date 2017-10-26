package com.altimetrik.trackMgnt.Business;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.altimetrik.trackMgnt.model.InputDetails;
import com.altimetrik.trackMgnt.model.Talks;
import com.altimetrik.trackMgnt.model.Tracks;
import com.altimetrik.trackMgnt.util.Util;

public class ManageTalks {
	private static final int MRNG_SESSION_MINUITS = 180; // 9AM to 12PM -->
															// 3Hours --> 180
															// minutes.
	private static final int AFTRN_SESSION_MINUITS = 240; // 1PM to 5PM -->
															// 4Hours --> 240
															// minutes.
	private InputDetails inputDetails = null;

	public ManageTalks(String file){
		inputDetails=new InputDetails();
		ArrayList<Talks> talksSortedList=getTalks(file);
		if(talksSortedList !=null && !talksSortedList.isEmpty()){
			inputDetails.setTalks(talksSortedList);
			getNoOfTracks();
		}
	}
	
	/*@getTalks method reads the input file
	 * and arrange the talks in a natural 
	 * sorted list
	 */
	@SuppressWarnings("resource")
	public ArrayList<Talks> getTalks(String talksFile) {
		ArrayList<Talks> talks = null;
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		try {
			talks = new ArrayList<Talks>();
			fstream = new FileInputStream(talksFile);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String lastWord = null;
			String talkName = null;
			int talkTime = 0;
			while ((strLine = br.readLine()) != null) {
				if (strLine.endsWith("min")) {
					lastWord = strLine.substring(strLine.lastIndexOf(" ") + 1);
					talkName = strLine;
					try {
						talkTime = Integer.parseInt(lastWord.substring(0, (lastWord.length() - 3)));
						if (talkTime <=0){
							System.err.println("Error!!\nTalk Name:: " + strLine + ".\nMessage:: Talk time should be more then 0 mins");
							return null;
						}
					} catch (Exception e) {
						System.err.println("Error!!\nTalk Name:: " + strLine + ".\nMessage:: Talk should contain time");
						return null;
					}
					if (talkTime > AFTRN_SESSION_MINUITS) {
						System.err.println("Error!!\nTalk Name:: " + talkName
								+ ".\nMessage:: Talk time should not be more than 240 Hrs");
						return null;
					}
				} else if (strLine.endsWith("lightning")) {
					talkName = strLine;
					talkTime = 5;
				} else {
					System.err.println("Error!!\nTalk Name:: " + strLine + ".\nMessage:: Talk should contain time");
					return null;
				}
				Talks talk = new Talks();
				talk.setTalkName(talkName);
				talk.setTalkTime(talkTime);
				talks.add(talk);
				inputDetails.setNoOfTotalHours(inputDetails.getNoOfTotalHours()+talkTime);  
			}
		} catch (Exception e) {
			System.err.println("Error!!\nInternal Server Error.\nMessage::" + e.getMessage());
			return null;

		} finally {
			try {
				if (in != null)
					in.close();
				if (fstream != null)
					fstream.close();
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (talks.size() == 0) {
			System.err.println("Error!!\nMessage:: Invalid File.");

			System.exit(0);
		}
		Collections.sort(talks);
		return talks;
	}

	/*
	 * @ getNoOfTracks method calculates the no. of tracks based on total talks
	 * time and day time.
	 * @
	 */
	public void getNoOfTracks() {
		int NoOfTracks = 0;
		try {
			if (inputDetails.getNoOfTotalHours() > (MRNG_SESSION_MINUITS + AFTRN_SESSION_MINUITS)) {
				if (inputDetails.getNoOfTotalHours() % (MRNG_SESSION_MINUITS + AFTRN_SESSION_MINUITS) > 0)
					NoOfTracks = inputDetails.getNoOfTotalHours() / (MRNG_SESSION_MINUITS + AFTRN_SESSION_MINUITS) + 1;
				else
					NoOfTracks = inputDetails.getNoOfTotalHours() / (MRNG_SESSION_MINUITS + AFTRN_SESSION_MINUITS);
			} else {
				NoOfTracks = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		inputDetails.setNoOfTotalTracks(NoOfTracks);
	}

	/*@
	 *scheduleTalks method calculates
	 *the no of tracks details and prints it.
	 *@
	 */
	public void scheduleTalks() {
		ArrayList<Tracks> tracks = new ArrayList<>();
		try {
			ArrayList<Talks> talks=inputDetails.getTalks();
			if(!talks.isEmpty() && talks != null){
				for (int i = 0; i < inputDetails.getNoOfTotalTracks(); i++) {
					Tracks track = new Tracks();
					track.setMrngSessionMap(loop(talks, MRNG_SESSION_MINUITS));
					track.setAftnSessionMap(loop(talks, AFTRN_SESSION_MINUITS));
					tracks.add(track);
				}
			}
		} catch (Exception e) {
			System.err.println("Error!!\nInternal Server Error.\nMessage::" + e.getMessage());
		}
		if(tracks.size()>0)
			Util.print(tracks);
	}

	/*
	 * @loop method reads the natural oder sorted list and
	 * calculate the talk time and position.
	 * @
	 */
	private static HashMap<String, Integer> loop(List<Talks> talks, int sessionType) {
		boolean check = false;
		int sessionMrngTime = 0;
		HashMap<String, Integer> currentTrackMap = null;
		if (!talks.isEmpty() && talks != null) {
			for (int i = 0; i < talks.size(); i++) {
				currentTrackMap = new HashMap<String, Integer>();
				sessionMrngTime = 0;
				sessionMrngTime = talks.get(i).getTalkTime();
				if (sessionMrngTime == sessionType) {
					currentTrackMap.put(talks.get(i).getTalkName(), talks.get(i).getTalkTime());
					check = true;
					Util.removeTalksInList(talks, currentTrackMap);
					break;
				}
				currentTrackMap.put(talks.get(i).getTalkName(), talks.get(i).getTalkTime());
				int jIndex = i + 1;
				for (int j = jIndex; j < talks.size(); j++) {
					sessionMrngTime += talks.get(j).getTalkTime();
					if (sessionMrngTime == sessionType) {
						currentTrackMap.put(talks.get(j).getTalkName(), talks.get(j).getTalkTime());
						check = true;
						Util.removeTalksInList(talks, currentTrackMap);
						break;
					}
					if (sessionMrngTime < sessionType) {
						currentTrackMap.put(talks.get(j).getTalkName(), talks.get(j).getTalkTime());
						for (int k = j + 1; k < talks.size(); k++) {
							if (sessionMrngTime + talks.get(k).getTalkTime() < sessionType) {
								sessionMrngTime += talks.get(k).getTalkTime();
								currentTrackMap.put(talks.get(k).getTalkName(), talks.get(k).getTalkTime());
							} else if (sessionMrngTime + talks.get(k).getTalkTime() == sessionType) {
								currentTrackMap.put(talks.get(k).getTalkName(), talks.get(k).getTalkTime());
								check = true;
								Util.removeTalksInList(talks, currentTrackMap);
								break;
							} else if (sessionMrngTime + talks.get(k).getTalkTime() > sessionType) {
								if ((currentTrackMap.size() + 1) == talks.size()) {
									check = true;
									Util.removeTalksInList(talks, currentTrackMap);
									break;
								}
								sessionMrngTime = talks.get(i).getTalkTime();
								currentTrackMap = new HashMap<String, Integer>();
								currentTrackMap.put(talks.get(i).getTalkName(), talks.get(i).getTalkTime());
								break;
							}
						}
					}
					if (check || (currentTrackMap.size() == talks.size())) {
						check = true;
						break;
					}
				}
				if (check || currentTrackMap.size() == talks.size()) {
					check = true;
					break;
				}
			}
		}
		return currentTrackMap;
	}

}
