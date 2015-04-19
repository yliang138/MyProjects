package com.example.androidpractice;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

@SuppressLint("NewApi")
public class MyListActivity extends ListActivity {

	private ArrayAdapter<String> mAdapter;
	private String book1 = "The Prolific Oven";
	private String book2 = "Introduction to Visual Basic";
	private String book3 = "Java 7";
	private String book4 = "Android Development";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		//Get the message from the intent
		Intent intent = getIntent();
		String newBook = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		/** display the message to the text view
		TextView tv1 = (TextView)findViewById(R.id.textView1);
		//TextView tv1 = new TextView(this);
		tv1.setTextSize(40);
		tv1.setText(message);
		setContentView(tv1);  **/
		
		List<String> data = new ArrayList<String>();
		data.add(book1);
		data.add(book2);
		data.add(book3);
		data.add(book4);
		data.add(newBook);
		
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
		setListAdapter(mAdapter);
		
		/* Show the Up button in the action bar. make sure we're running on Honeycomb or higher to use ActionBar APIs*/
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			getActionBar().setHomeButtonEnabled(true);
		}
		
		
	}
	//Click on each item to see the details
	protected void onListItemClick(ListView listView, View view, int position, long id){
		super.onListItemClick(listView, view, position, id);
		
		String string = (String)getListAdapter().getItem(position);
		Toast.makeText(this, "Title: " + string, Toast.LENGTH_LONG).show();
		
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
