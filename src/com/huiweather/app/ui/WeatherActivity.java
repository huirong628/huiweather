package com.huiweather.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends Activity{
	
	private static final String TAG = "WeatherActivity";
	
	private LinearLayout mWeatherInfoLayout;
	
	private TextView mCityName;
	private TextView mPublishTime;
	private TextView mWeatherDesp;
	private TextView mTemp1;
	private TextView mTemp2;
	private TextView mCurrentDate;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	/*
	 * Initialize all UI elements from resources.
	 */
	private void initResourceRefs(){
		
	}
	
	

}
