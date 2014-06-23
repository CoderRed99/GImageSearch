package com.example.gimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FilterSettingsActivity extends Activity 
{
	FilterSettings filter;
	Spinner spSize;
	Spinner spColor;
	Spinner spType;
	EditText etSite;
	String size;
	String color;
	String type;
	String site;
	Button btnSave;
	String requestName;
	int requestCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.d("DEBUG", "Filter Settings Activity");
		
        Bundle b = getIntent().getExtras();
        setContentView(R.layout.activity_filter_settings);
		
        if (b != null) 
        {
        	filter = (FilterSettings) getIntent().getExtras()
        		.getSerializable("filters");
        	Log.d("DEBUG", "Retrieve filter from Intent");
    		Toast.makeText(this, filter.toString(), Toast.LENGTH_SHORT).show();
    		Log.d("DEBUG", filter.toString());
    		
    		size = filter.getSize();
    		color = filter.getColor();
    		type = filter.getType();
    		site = filter.getSite();
    		
    		Log.d("DEBUG", "Got filters from filter object");
    		String concatText = (size + ":" + color + ":" + type + ":" + site);
    		Toast.makeText(this, concatText, Toast.LENGTH_LONG).show();
    		Log.d("DEBUG", concatText);
    		Log.d("DEBUG", filter.toString());
    		setupValues();
        }
	}
	
	public void onSendResult(View v) 
	{// Obtain the data from the user entered selections
		Log.d("DEBUG", "onSendResult");
		
		size = spSize.getSelectedItem().toString();
		filter.setSize(spSize.getSelectedItem().toString());
		color = spColor.getSelectedItem().toString();
		filter.setColor(spColor.getSelectedItem().toString());
		type = spType.getSelectedItem().toString();
		filter.setType(spType.getSelectedItem().toString());
		site = etSite.getText().toString();
		filter.setSite(etSite.getText().toString());
		
		String concatText = (size + ":" + color + ":" + type + ":" + site);
		Toast.makeText(this, concatText, Toast.LENGTH_LONG).show();
		Log.d("DEBUG", concatText);
		Log.d("DEBUG", filter.toString());
		// Create the filter object containing the user selected filters
		// FilterSettings filter = new FilterSettings(size, color, type, site);

		// Get the filter object data and display it in the log
		Log.d("DEBUG", "filter object in Settings Activity");
		size = filter.getSize().toString();
		color = filter.getColor().toString();
		type = filter.getType().toString();
		site = filter.getSite().toString();
		String filterText = (size + ":" + color + ":" + type + ":" + site);
		Log.d("DEBUG", filterText);
		
		// Return the filter object reference to the Search Activity
		Intent i = new Intent();
		Bundle fBundle = new Bundle();
		fBundle.putSerializable("filters", filter);
		i.putExtras(fBundle);     // set up Extras bundle to send with intent
		Log.d("DEBUG", "onFilterSettings-Menu--> intent");
		
		
		// i.putExtra("filters", filter);
		i.putExtra("text", concatText);
		
		setResult(RESULT_OK, i);
		finish();
		
		

		// TextView mTextView = new TextView(this); 
		//   Person mPerson = (Person)getIntent().getSerializableExtra(ObjectPassDemo.SER_KEY); 
		//   mTextView.setText("You name is: " + mPerson.getName() + "/n"+ 
		//    "You age is: " + mPerson.getAge()); 
		//   setContentView(mTextView);
		// Log.d("DEBUG", "REQUEST_NAME AND REQUEST_CODE");
		// Toast.makeText(this, name + String.valueOf(code), Toast.LENGTH_SHORT).show();
	}
	
	public void setupValues()
	{// TODO set spinners to saved values from filter object
		Log.d("DEBUG", "setupValues");
		
		etSite = (EditText) findViewById(R.id.etSite);
		if (site != null && !site.isEmpty())
		{
			etSite.setText(site, TextView.BufferType.EDITABLE);
		}
		
		spSize = (Spinner) findViewById(R.id.spSize);
		if (size != null && !size.isEmpty())
		{
			spSize.setSelection(getIndex(spSize, size));
		}
		
		spColor = (Spinner) findViewById(R.id.spColor);
		if (color != null && !color.isEmpty())
		{
			spColor.setSelection(getIndex(spColor, color));
		}
		
		spType = (Spinner) findViewById(R.id.spType);
		if (type != null && !type.isEmpty())
		{
			spType.setSelection(getIndex(spType, type));
		}
		
		btnSave = (Button) findViewById(R.id.btnSave);
		
	}

	 // get the index of the value stored in filter object
	 private int getIndex(Spinner spinner, String myString)
	 {
		 Log.d("DEBUG", "getIndex");
	  int index = 0;

	  for (int i=0;i<spinner.getCount();i++)
	  {
	   if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString))
	   {
	    index = i;
	    i=spinner.getCount();//will stop the loop, kind of break, by making condition false
	   }
	  }
	  return index;
	 }
}
