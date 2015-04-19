package com.example.myjsonparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class JsonParserTask extends AsyncTask<String, Void, List<YahooNews>>
		implements YahooNewsJsonParser {

	private OnJsonParserCompleteListener mListener;

	@Override
	protected List<YahooNews> doInBackground(String... params) {
		URLConnection conn = null;
		List<YahooNews> data = null;

		try {
			conn = new URL(params[0]).openConnection();
		} catch (MalformedURLException e) {
			conn = null;
			e.printStackTrace();
		} catch (IOException e) {
			conn = null;
			e.printStackTrace();
		}

		if (conn != null) {

			String jsonString = null;

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));

				StringBuilder builder = new StringBuilder();
				String line = null;

				while ((line = in.readLine()) != null) {

					builder.append(line);

				}

				jsonString = builder.toString();

				Log.d("JSON String", jsonString);

				in.close();

			} catch (IOException e) {

				jsonString = null;

				e.printStackTrace();
			}

			if (jsonString != null) {

				data = new ArrayList<YahooNews>();

				try {
					JSONObject rootObject = new JSONObject(jsonString);

					Log.d("JSON root", rootObject.toString());

					JSONObject jsonValue = rootObject
							.getJSONObject(Contract.KEY_VALUE);

					Log.d("JSON value", jsonValue.toString());

					JSONArray jsonItems = jsonValue
							.getJSONArray(Contract.KEY_ITEMS);

					Log.d("JSON array", "" + jsonItems.length());

					for (int i = 0; i < jsonItems.length(); i++) {

						String title = jsonItems.getJSONObject(i).getString(
								Contract.KEY_TITLE);

						Log.d("JSON title", title);

						String link = jsonItems.getJSONObject(i).getString(
								Contract.KEY_LINK);
						Log.d("JSON link", link);

						String description = jsonItems.getJSONObject(i)
								.getString(Contract.KEY_DESCRIPTION);
						Log.d("JSON description", description);

						YahooNews news = new YahooNews(title, link, description);

						// ///////////////////

						// save in DB

						saveInDatabase(news);

						// //////////////////

						data.add(news);

					}

				} catch (JSONException e) {
					data = null;
					Log.d("JSON exception", "!!!!!");
					e.printStackTrace();
				}

			}
		}

		return data;
	}

	private void saveInDatabase(YahooNews news) {

		DatabaseHelper helper = new DatabaseHelper(
				((Context) mListener).getApplicationContext());

		SQLiteDatabase db = helper.getWritableDatabase();

		try {

			db.beginTransaction();
			db.insert(DatabaseContract.TABLE_NAME, null, news.convert());
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

	}

	@Override
	protected void onPostExecute(List<YahooNews> result) {
		if (mListener != null) {
			mListener.onJsonParserCompleted(result);
		}

	}

	@Override
	public void start(String url) {
		this.execute(url);

	}

	@Override
	public void setListener(OnJsonParserCompleteListener listener) {
		mListener = listener;

	}

}
