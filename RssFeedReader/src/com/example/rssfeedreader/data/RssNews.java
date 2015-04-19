package com.example.rssfeedreader.data;
/**
 * create a class to represents data gathered from the RSS feed page.
 */
public class RssNews {
	//define variables that represent each news
	private String title;
	private String link;
	
	//using Source > generate getter & setter to generate the methods
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return title;
	}
	

}
