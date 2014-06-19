package com.example.gimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;


import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		Log.d("DEBUG ", "ImageDisplayActivity--> onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);
		ivImage.setImageUrl(result.getFullUrl());

/*		Log.d("DEBUG ", "ImageDisplayActivity");
		Log.d("ivImage ", ivImage.toString());
		Log.d("DEBUG ", "ImageDisplayActivity--> url from intent");
		Log.d("DEBUG ", getIntent().getStringExtra("url"));*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}
}
