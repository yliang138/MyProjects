package com.example.myjsonparser;

public abstract class Contract {
	
	public static final String BASE_URL = true?"http://pipes.yahooapis.com":"http://dev.pipes.yahooapis.com";
		
	public static final String NEWS_JSON_FEED = BASE_URL + "/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json" ;
	
	public static final String KEY_VALUE = "value";

	public static final String KEY_ITEMS = "items";

	public static final String KEY_TITLE = "title";
	public static final String KEY_LINK = "link";
	
	public static final String KEY_DESCRIPTION = "description";
	
	

}
