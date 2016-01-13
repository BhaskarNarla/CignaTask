package com.tcs.cigna.pojo;

/**
 * Created by ADMIN on 1/12/2016.
 */
public class Episodes {
	String Title= "Winter Is Coming";
	String Released= "2011-04-17";
	int Episode=1;
	double imdbRating = 8.1;
	String imdbID="tt1480055";
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getReleased() {
		return Released;
	}
	public void setReleased(String released) {
		Released = released;
	}
	public int getEpisode() {
		return Episode;
	}
	public void setEpisode(int episode) {
		Episode = episode;
	}
	public double getImdbRating() {
		return imdbRating;
	}
	public void setImdbRating(double imdbRating) {
		this.imdbRating = imdbRating;
	}
	public String getImdbID() {
		return imdbID;
	}
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	
	
}
