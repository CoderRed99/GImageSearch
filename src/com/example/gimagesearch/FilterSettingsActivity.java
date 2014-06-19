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
	Spinner sizeSpinner;
	Spinner colorSpinner;
	Spinner typeSpinner;
	EditText etSite;
	Button btnSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_settings);
		Log.d("DEBUG", "Ive reached the Filter Settings Activity!");
		Toast.makeText(this, "Ive reached the Filter Settings Activity", Toast.LENGTH_SHORT).show();
		sizeSpinner = (Spinner) findViewById(R.id.spSize);
		colorSpinner = (Spinner) findViewById(R.id.spColor);
		typeSpinner = (Spinner) findViewById(R.id.spType);
		etSite = (EditText) findViewById(R.id.etSite);
		btnSave = (Button) findViewById(R.id.btnSave);
	}
	
	public void onSendResult(View v) 
	{
		String size = sizeSpinner.getSelectedItem().toString();
		String color = colorSpinner.getSelectedItem().toString();
		String type = typeSpinner.getSelectedItem().toString();
		String site = etSite.getText().toString();
		
		FilterSettings filter = new FilterSettings(size, color, type, site);
		String concatText = (size + ":" + color + ":" + type + ":" + site);
		Log.d("DEBUG", concatText);
		Toast.makeText(this, concatText, Toast.LENGTH_LONG).show();
		
		/*	
			public void setSpinnerToValue(Spinner spinner, String value) {
			    int index = 0;
			    SpinnerAdapter adapter = spinner.getAdapter();
			    for (int i = 0; i < adapter.getCount(); i++) {
			        if (adapter.getItem(i).equals(value)) {
			            index = i;
			        }
			    }
			    spinner.setSelection(index);
			}*/
		// String result = ((EditText) findViewById(R.id.txtRandomResultText)).getText().toString();
		// String result = (concatText);
		
		// TODO Set this up correctly and combine IMAGE SEARCH FUNCTIONS
		Intent i = new Intent();
		i.putExtra("result", filter);
		setResult(RESULT_OK, i);
		finish();
	}
}
