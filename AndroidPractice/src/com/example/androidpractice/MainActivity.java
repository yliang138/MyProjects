package com.example.androidpractice;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	//define the key for intent's extra using a public constant, using the app's package name as a prefix as a unique name
	public final static String EXTRA_MESSAGE = "com.example.androidpractice.MESSAGE";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//for minSdkversion is 11 or higher
		getActionBar().setHomeButtonEnabled(true);

	}

	/** called when the user clicks the Send button     */
	public void addBook(View view){
		Intent intent = new Intent(this, MyListActivity.class);
		EditText editText = (EditText)findViewById(R.id.editText1);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	//respond to Action Buttons
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_search:
			openSearch();
			return true;
			
		case R.id.action_settings:
			openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
	}

	private void openSettings() {
		// TODO Auto-generated method stub
		
	}

	private void openSearch() {
		// TODO Auto-generated method stub
		
	}
}
