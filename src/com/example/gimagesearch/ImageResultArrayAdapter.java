package com.example.gimagesearch;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> 
{
	public ImageResultArrayAdapter(Context context, List<ImageResult> images) 
	{
		super(context, R.layout.item_image_result, images);
	}
	// Convert data source to image view
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// Grab the Image and access to the SmartImageView
		ImageResult imageInfo = this.getItem(position);
		SmartImageView ivImage;
		if (convertView == null) 
		{  //if we are not passed in a View, then inflate to create it.
			LayoutInflater inflator = LayoutInflater.from(getContext());
			ivImage = (SmartImageView) inflator.inflate(R.layout.item_image_result, parent, false);
			Log.d("DEBUG ", "convertView == null, inflate new view from item image and creating the sub item");
			Log.d("ivImage ", ivImage.toString());
		} else {
			// Clear out the existing image
			ivImage = (SmartImageView) convertView;
			Log.d("DEBUG ", "Clear out the existing image");
			Log.d("ivImage ", ivImage.toString());
			ivImage.setImageResource(android.R.color.transparent);
			Log.d("DEBUG ", "create the new image view");
			Log.d("ivImage ", ivImage.toString());
		}
		// set the image url and return the image view
		ivImage.setImageUrl(imageInfo.getThumbUrl());
		Log.d("DEBUG ", "setImageUrl");
		Log.d("ivImage ", ivImage.toString());
		return ivImage;
	}
}
