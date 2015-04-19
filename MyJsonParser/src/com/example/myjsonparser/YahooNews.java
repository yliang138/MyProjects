package com.example.myjsonparser;

import android.content.ContentValues;

public class YahooNews {

	private String title;
	private String link;
	private String description;

	public YahooNews(String title, String link, String description) {
		super();
		this.title = title;
		this.link = link;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return title;
	}

	public ContentValues convert() {

		ContentValues values = new ContentValues();

		values.put(DatabaseContract.TITLE_COLUMN_NAME, title);
		values.put(DatabaseContract.LINK_COLUMN_NAME, link);
		values.put(DatabaseContract.DESCRIPTION_COLUMN_NAME, description);

		return values;

	}

}
