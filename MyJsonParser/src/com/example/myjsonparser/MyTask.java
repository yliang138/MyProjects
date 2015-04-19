package com.example.myjsonparser;

public class MyTask implements Runnable, YahooNewsJsonParser {
	
	private OnJsonParserCompleteListener mListener;
	private String mUrl;

	@Override
	public void run() {
		
		
	}

	@Override
	public void start(String url) {
		
		mUrl = url;
		new Thread(this).start();
		
	}

	@Override
	public void setListener(OnJsonParserCompleteListener listener) {

		mListener = listener;
	}

}
