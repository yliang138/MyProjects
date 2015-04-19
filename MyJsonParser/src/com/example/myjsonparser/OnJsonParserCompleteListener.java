package com.example.myjsonparser;

import java.util.List;

public interface OnJsonParserCompleteListener {
	
	public void onJsonParserCompleted(List<YahooNews> data);
	
	public void onProgressUpdate(int progress);

}
