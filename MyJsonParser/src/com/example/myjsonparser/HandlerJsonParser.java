package com.example.myjsonparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;

public class HandlerJsonParser implements YahooNewsJsonParser {

	private OnJsonParserCompleteListener mListener;

	public static final int PARSING_FINISHED = Integer.MAX_VALUE;
	public static final int PROGRESS_UPDATE = Integer.MIN_VALUE;

	private boolean isStarted = false;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case PARSING_FINISHED:
				if (mListener != null) {

					mListener.onJsonParserCompleted((List<YahooNews>) msg.obj);
				}

				break;

			case PROGRESS_UPDATE:
				if (mListener != null) {
					mListener.onProgressUpdate(msg.arg1);
				}

				break;

			default:

				super.handleMessage(msg);
				break;
			}

		}

	};

	@Override
	public void setListener(OnJsonParserCompleteListener listener) {
		mListener = listener;
	}

	@Override
	public void start(String url) {

		if (!isStarted) {
			isStarted = true;
			parseAsync(url);
		}

	}

	private void parseAsync(final String url) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				parse(url);

			}
		}).start();

	}

	@SuppressLint("NewApi")
	private void parse(String url) {


		URLConnection conn = null;

		try {
			conn = new URL(url).openConnection();
		} catch (MalformedURLException e) {
			conn = null;
			e.printStackTrace();
		} catch (IOException e) {
			conn = null;
			e.printStackTrace();
		}

		if (conn != null) {

			DatabaseHelper helper = new DatabaseHelper(
					((Context) mListener).getApplicationContext());

			SQLiteDatabase db = helper.getWritableDatabase();

		

			try {
				InputStream is = conn.getInputStream();

				JsonReader reader = new JsonReader(new BufferedReader(
						new InputStreamReader(conn.getInputStream())));

				reader.beginObject();

				while (reader.hasNext()) {

					String name = reader.nextName();

					if (name.equals(Contract.KEY_VALUE)) {

						reader.beginObject();

						while (reader.hasNext()) {

							if (reader.nextName().equals(Contract.KEY_ITEMS)) {

								reader.beginArray();

								while (reader.hasNext()) {

									reader.beginObject();

									String title = null;
									String link = null;
									String description = null;

									while (reader.hasNext()) {

										String name1 = reader.nextName();
										if (name1.equals(Contract.KEY_TITLE)) {
											title = reader.nextString();
										} else if (name1
												.equals(Contract.KEY_LINK)) {
											link = reader.nextString();
										} else if (name1
												.equals(Contract.KEY_DESCRIPTION)) {
											description = reader.nextString();
										} else {
											reader.skipValue();
										}

									}

									YahooNews news = new YahooNews(title, link,
											description);

									// ///////////////////

									// save in DB

									saveInDatabase(db, news);

									// //////////////////

									//data.add(news);

									reader.endObject();
								}

								reader.endArray();

							} else {

								reader.skipValue();
							}

						}

						reader.endObject();

					} else {

						reader.skipValue();
					}

				}

				reader.endObject();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			List<YahooNews> data = null;
			data = new ArrayList<YahooNews>();
			
			

			publishProgress(100);

			mHandler.obtainMessage(PARSING_FINISHED, data).sendToTarget();

			db.close();

		}
	}

	private void publishProgress(int progress) {

		mHandler.removeMessages(PROGRESS_UPDATE);
		mHandler.obtainMessage(PROGRESS_UPDATE, progress, -1).sendToTarget();

	}

	private void saveInDatabase(SQLiteDatabase db, YahooNews news) {

		try {

			db.beginTransaction();
			db.insert(DatabaseContract.TABLE_NAME, null, news.convert());
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

	}

}
