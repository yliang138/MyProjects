package com.example.myjsonparser;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity implements
		OnJsonParserCompleteListener {

	private YahooNewsJsonParser mParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mParser = (YahooNewsJsonParser) getLastNonConfigurationInstance();

		if (mParser != null) {
			mParser.setListener(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_refresh:

			refreshNews();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void refreshNews() {

		mParser = JsonParserFactory.getNewParser();

		mParser.setListener(this);

		mParser.start(Contract.NEWS_JSON_FEED);

	}

	@Override
	public void onJsonParserCompleted(final List<YahooNews> data) {
		// SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
		// data, android.R.layout.simple_list_item_1, from, to)

		if (data != null) {

			setListAdapter(new BaseAdapter() {

				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {

					if (convertView == null) {
						convertView = LayoutInflater.from(MainActivity.this).inflate(
								android.R.layout.simple_list_item_1, parent,
								false);
					}

					((TextView) convertView).setText(data.get(position)
							.toString());

					return convertView;
				}

				@Override
				public long getItemId(int position) {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public Object getItem(int position) {
					return data.get(position);
				}

				@Override
				public int getCount() {
					return data.size();
				}
			});
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return mParser;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mParser != null) {
			mParser.setListener(null);
		}
	}

	@Override
	public void onProgressUpdate(int progress) {
		// TODO Auto-generated method stub
		
	}

	
}
