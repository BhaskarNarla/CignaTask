package com.tcs.cigna.pojo;

import java.util.List;

/**
 * Created by ADMIN on 1/12/2016.
 */
public class Season {
	String Title ="Game of Thrones";
	int Season = 1;
	List<Episodes> Episodes;
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public int getSeason() {
		return Season;
	}
	public void setSeason(int season) {
		Season = season;
	}
	public List<Episodes> getEpisodes() {
		return Episodes;
	}
	public void setEpisodes(List<Episodes> Episodes) {
		this.Episodes = Episodes;
	} 
	
	
}
