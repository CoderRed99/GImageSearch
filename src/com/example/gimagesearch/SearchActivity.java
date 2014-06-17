package com.example.gimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity 
{
	EditText etQuery;
	Button btnSearch;
	GridView gvResults;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().indlate(R.menu.search, menu);
		return true;
	}*/
	
	public void setupViews()
	{
		etQuery = (EditText) findViewById(R.id.etQuery);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);
	}
	
	public void onImageSearch(View v)
	{
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?rsz=8&start=0&v=1.0&q=android
	
		client.get(
				"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" 
	// You paginate by changing the start 24:00
				+ "start=" + 0 + "&v=1.0&q=" + Uri.encode(query), 
				new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONObject response)
			{
				JSONArray imageJsonResults = null;
				try 
				{
					imageJsonResults = response.getJSONObject(
							"responseData").getJSONArray("results");
					imageResults.clear();
					imageResults.addAll(ImageResult
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
	        }
			
		});
	}
}
