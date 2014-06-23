package com.example.gimagesearch;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity 
{
	EditText etQuery;
	Button btnSearch;
	GridView gvResults;
	private FilterSettings filter;
	final static int GET_RESULT_TEXT = 0;
	int totalThumbUrls = 0;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		filter = new FilterSettings();

		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener( new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long rowId) 
			{	
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);

				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});

		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your AdapterView
				customLoadMoreDataFromApi(); 
			}
		});
	}

	public void customLoadMoreDataFromApi() {
		// TODO merge this one with the other query
		String query = etQuery.getText().toString();
		AsyncHttpClient client = new AsyncHttpClient();
		// request looks like
		// https://ajax.googleapis.com/ajax/services/search/images?q=Android&v=1.0
		String url = "https://ajax.googleapis.com/ajax/services/search/images?";
		String size = "rsz=8";
		// You paginate by changing the start see 24:00
		String offset = "&start=";     
		String ver = "&v=1.0";
		String q = "&q=" + Uri.encode(query);  // drops space, illegal chars, etc
		String url_with_args= url + size + offset + totalThumbUrls + ver + q;

		client.get(url_with_args, jHandler);
	}

	JsonHttpResponseHandler jHandler = new JsonHttpResponseHandler () 
	{

		@Override
		public void onSuccess(JSONObject response) 
		{
			JSONArray imageJsonResults = null;

			try 
			{
				imageJsonResults = response.getJSONObject("responseData")
						.getJSONArray("results");

				JSONObject jo = response.getJSONObject("responseData");
				String formatJo = jo.toString(4);
				Log.d("imagesearch", "response object ="+formatJo);

				ArrayList<ImageResult> alir = ImageResult.fromJSONArray(imageJsonResults);
				totalThumbUrls += alir.size();

				imageAdapter.addAll(alir);
				imageAdapter.notifyDataSetChanged();
				Log.d("DEBUG", imageResults.toString());
			} catch (JSONException e) 
			{
				e.printStackTrace();
			}
		}

		@Override
        public void onFailure(Throwable e, JSONObject response) 
        {
			Toast.makeText(SearchActivity.this, "onFailure reached ", Toast.LENGTH_SHORT).show();
			Log.d("DEBUG", "onFailure was just called!");
			Log.d("DEBUG", imageResults.toString());
			Log.d("DEBUG", response.toString());
        }
	};

	public void onImageSearch(View v)
	{ // TODO This looks like a hot mess, clean it up
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
		//Toast.makeText(this, "asked for page, startOffset=" + totalThumbUrls, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=0&v=1.0&q=android
		Log.d("LOG_TAG", "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" 
				+ "start=" + 0 + "&v=1.0&q=" + Uri.encode(query));
		client.get(
				"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" 
						// You paginate by changing the start see 24:00
						+ "start=" + 0 + "&v=1.0&q=" + Uri.encode(query), 
				new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONObject response)
			{
				Log.d("DEBUG", "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" 
						+ "start=" + 0 + "&v=1.0&q=");
				JSONArray imageJsonResults = null;
				try 
				{
					imageJsonResults = response.getJSONObject(
							"responseData").getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult
							.fromJSONArray(imageJsonResults));
					Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) 
				{
					e.printStackTrace();
				}
			}

			@Override
	        public void onFailure(Throwable e, JSONObject response) 
	        {
				Toast.makeText(SearchActivity.this, "onFailure reached ", Toast.LENGTH_SHORT).show();
				Log.d("DEBUG", "onFailure was just called!");
				Log.d("DEBUG", imageResults.toString());
				Log.d("DEBUG", response.toString());
	        }

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_action_bar_menu, menu);
		Log.d("DEBUG", "onCreateOptionsMenu was just called!");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		Log.d("DEBUG", "menu item selected was just called!");
		Toast.makeText(this, "menu item selected", Toast.LENGTH_SHORT).show();
		

		/*Log.d("DEBUG", "REQUEST_NAME AND REQUEST_CODE");
		Log.d("DEBUG", REQUEST_NAME + String.valueOf(REQUEST_CODE));
		Toast.makeText(this, REQUEST_NAME + String.valueOf(REQUEST_CODE), Toast.LENGTH_SHORT).show();*/
		// FilterSettings filter = (FilterSettings) getIntent().getSerializableExtra("filters");
		// FilterSettings filter = new FilterSettings();
        
		Intent i = new Intent(this, FilterSettingsActivity.class);
		// Bundle fBundle = new Bundle(); 
		// fBundle.putSerializable("filters", filter);
		i.putExtra("filters", filter);     // set up Extras bundle to send with intent
		Log.d("DEBUG", "onFilterSettings-Menu--> intent");
		Log.d("DEBUG", i.toString());
		
		startActivityForResult(i, 1);
		return true;
	}

	// Once the activity returns a result, concatenate the url with the FilterSettings for the filtered search
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1) 
		{
			if (resultCode == Activity.RESULT_OK) 
			{
				Log.d("DEBUG", "good request and result codes");
				
				Bundle b = data.getExtras();
				if (b != null)
				{// Retrieve the filter reference from the intent Extra
					Toast.makeText(this, "Retrieve Intent data", Toast.LENGTH_SHORT).show();
					Log.d("DEBUG", "Retrieve Intent data");
					String filterText = data.getStringExtra("text");
					Log.d("DEBUG", filterText);
					Toast.makeText(this, filterText, Toast.LENGTH_LONG).show();
					
					FilterSettings newFilter = (FilterSettings) b.getSerializable("filters");
					Log.d("DEBUG", "filter after get");
					// Log.d("DEBUG", filter.toString());
					
					Log.d("DEBUG", "get filter data");
					String filterSize = newFilter.getSize().toString();
					String filterColor = newFilter.getColor().toString();
					String filterType = newFilter.getType().toString();
					String filterSite = newFilter.getSite().toString();

					Log.d("DEBUG", "filter object");
					Log.d("DEBUG", newFilter.toString());
				} else if (resultCode == 0)
					{
					Toast.makeText(this, "RESULT CANCELLED", Toast.LENGTH_LONG).show();
					}
				}
				
				// Concatenate the url and perform the filtered search
				// TextView tvResult = (TextView)findViewById(R.id.txtDisplayResult);
				// tvResult.setText(data.getStringExtra("result"));
				// TODO save data in either this activity or the SearchActivity?
				// Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();


				// Log.d("DEBUG", imageResults.toString());
				// Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
			}
		}
	

	public void setupViews()
	{
		etQuery = (EditText) findViewById(R.id.etQuery);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);
		Log.d("DEBUG", "setupViews was just called!");
	}
	
	/*
	public void onFilterSettings(View v) 
	{
		Log.d("DEBUG", "filterSettings was just called!");
		Toast.makeText(this, "filterSettings", Toast.LENGTH_SHORT).show();

		Log.d("DEBUG", "onFilterSettings");
		Toast.makeText(this, "filter settings selected", Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent(this, FilterSettingsActivity.class);

		Log.d("DEBUG", "REQUEST_NAME AND REQUEST_CODE");
		Log.d("DEBUG", REQUEST_NAME + String.valueOf(REQUEST_CODE));
		Toast.makeText(this, REQUEST_NAME + String.valueOf(REQUEST_CODE), Toast.LENGTH_SHORT).show();
		
		i.putExtra("filters", filter);   // set up Extra to send with intent
		Log.d("DEBUG", "onFilterSettings-Menu--> intent");
		Log.d("DEBUG", i.toString());
		
		startActivityForResult(i, 0);
	}*/
}