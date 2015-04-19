package com.example.rssfeedreader;
/**
 * This class implements a list listener.
 */
import java.util.List;

import com.example.rssfeedreader.data.RssNews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListListener implements OnItemClickListener {
	//RSS List item's reference
	List<RssNews> listItems;
	//Reference to a calling Activity
	Activity anActivity;
	
	/** Set above references in the constructor */
	public ListListener(List<RssNews> aListItems, Activity mActivity) {
		listItems = aListItems;
		anActivity = mActivity;
	}

	//start a browser with url from the rss item
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Create an Intent which is going to display data
		Intent intent = new Intent(Intent.ACTION_VIEW);
		//set data for new Intent
		intent.setData(Uri.parse(listItems.get(position).getLink()));
		//start activity with this intent
		anActivity.startActivity(intent);
	}

}
