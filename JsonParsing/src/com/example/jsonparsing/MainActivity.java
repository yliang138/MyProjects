package com.example.jsonparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	private ProgressDialog pDialog;
	
	// URL to get contacs JSON
	private static String url = "http://api.androidhive.info/contacts/";
	
	//JSON Node names
	    private static final String TAG_CONTACTS = "contacts";
	    private static final String TAG_ID = "id";
	    private static final String TAG_NAME = "name";
	    private static final String TAG_EMAIL = "email";
	    private static final String TAG_ADDRESS = "address";
	    private static final String TAG_GENDER = "gender";
	    private static final String TAG_PHONE = "phone";
	    private static final String TAG_PHONE_MOBILE = "mobile";
	    private static final String TAG_PHONE_HOME = "home";
	    private static final String TAG_PHONE_OFFICE = "office";

	    //contacts JSONArray
	    JSONArray contacts = null;
	    //Hashmap for ListView
	    ArrayList<HashMap<String, String>> contactList;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		contactList = new ArrayList<HashMap<String,String>>();
		//ListView listView = getListView(R.id.list);
		ListView listView = (ListView)findViewById(R.id.list);
		
		//ListView on item click listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				//getting values from selected ListItem
				String name = ((TextView)view.findViewById(R.id.txtName)).getText().toString();
				String email = ((TextView)view.findViewById(R.id.txtEmail)).getText().toString();
				String phone = ((TextView)view.findViewById(R.id.txtPhone)).getText().toString();
				
				//starting single contact activity
				Intent i = new Intent(getApplicationContext(), ContactActivity.class);
				i.putExtra(TAG_NAME, name);
				i.putExtra(TAG_EMAIL, email);
				i.putExtra(TAG_PHONE, phone);
				startActivity(i);
			}
			
		});
		//calling AsyncTask to get JSON
		new GetContacts().execute();
	}
	private class GetContacts extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//showing progress dialog
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("PLEASE WAIT...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// CREATING service handler class instance
			ServiceHandler sh = new ServiceHandler();
			
			//making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
			Log.d("Response: ", ">> " + jsonStr);
			
			if(jsonStr !=null){
				try{
					JSONObject jsonObj = new JSONObject(jsonStr);
					//getting JSON Array node
					contacts = jsonObj.getJSONArray(TAG_CONTACTS);
					//looping through All Contacts
					for(int i=0; i<contacts.length(); i++){
						JSONObject c = contacts.getJSONObject(i);
						String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String email = c.getString(TAG_EMAIL);
                        String address = c.getString(TAG_ADDRESS);
                        String gender = c.getString(TAG_GENDER);
 
                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject(TAG_PHONE);
                        String mobile = phone.getString(TAG_PHONE_MOBILE);
                        String home = phone.getString(TAG_PHONE_HOME);
                        String office = phone.getString(TAG_PHONE_OFFICE);
 
                        // tmp hashmap for single contact
                        HashMap<String, String> contact = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        contact.put(TAG_ID, id);
                        contact.put(TAG_NAME, name);
                        contact.put(TAG_EMAIL, email);
                        contact.put(TAG_PHONE_MOBILE, mobile);
 
                        // adding contact to contact list
                        contactList.add(contact);
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}else{
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			 // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
                            TAG_PHONE_MOBILE }, new int[] {R.id.txtName,
                            R.id.txtEmail, R.id.txtPhone });
 
            setListAdapter(adapter);
		}
	}
	
}
