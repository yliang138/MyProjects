package com.example.rssfeedreader;

import com.example.rssfeedreader.data.RssNews;
import com.example.rssfeedreader.util.RssReader;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try{
		//create RSS reader
			RssReader rssReader = new RssReader("http://download.itcuties.com/tmp-xml/test-rss.xml");
			//Get a ListView from main View
			ListView items = (ListView)findViewById(R.id.listView1);
		//create a list Adapter
			ArrayAdapter adapter = new ArrayAdapter<RssNews>(getApplicationContext(), 
					android.R.layout.simple_list_item_1,
					rssReader.getNews());
		//Set List Adapter for the ListView
			items.setAdapter(adapter);
		//SET list view item click listener
			items.setOnItemClickListener(new ListListener(rssReader.getNews(), this));
		}catch(Exception e){
			Log.e("rssReader", e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
