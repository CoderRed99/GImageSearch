package com.example.gimagesearch;

import java.util.List;

import android.content.Context;
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
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		//Put one image url into one ImageView
		ImageResult imageInfo = this.getItem(position);
		SmartImageView ivImage;
		if (convertView == null) 
		{  //if we are not passed in a View, then inflate to create it.
			LayoutInflater inf = LayoutInflater.from(getContext());
			ivImage = (SmartImageView) inf.inflate(R.layout.item_image_result, parent, false);
		} else {
			// Clear out the existing image
			ivImage = (SmartImageView) convertView;
			ivImage.setImageResource(android.R.color.transparent);
		}
		// set the image url and return the image
		ivImage.setImageUrl(imageInfo.getThumbUrl());
		return ivImage;
	}
}
