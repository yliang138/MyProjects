package com.example.androidpractice;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class MyPreferenceActivity extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}
	@Override
	protected void onPause() {
		
		super.onPause();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		//grab a preference value
		Boolean crashy = prefs.getBoolean("crashrandomly", true);
		String run = prefs.getString("runpref", "");
		
		System.err.println("crashy:" +crashy);
		System.err.println("run:" + run);
		
	}
}
