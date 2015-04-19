package com.example.myjsonparser;

public interface YahooNewsJsonParser {
	
	public void start(String url);
	
	public void setListener(OnJsonParserCompleteListener listener);
	

}
