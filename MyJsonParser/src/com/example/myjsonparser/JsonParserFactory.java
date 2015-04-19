package com.example.myjsonparser;

import android.content.Intent;
import android.os.Build;

public class JsonParserFactory {
	
	private JsonParserFactory (){
		
	}

	public static YahooNewsJsonParser getNewParser(){
		
		YahooNewsJsonParser result;
		
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1){
			
			result = new JsonParserTask();
			
		}else{
			
			result = new HandlerJsonParser();
			
		}
		
		return result;
		
	}
	
}
