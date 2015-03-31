package com.huiweather.app.ui;

import com.huiweather.app.R;
import com.huiweather.app.util.HttpCallbackListener;
import com.huiweather.app.util.HttpUtil;
import com.huiweather.app.util.Utility;

import android.app.Activity;
import android.app.DownloadManager.Query;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends Activity implements OnClickListener{
	
	private static final String TAG = "WeatherActivity";
	
	private LinearLayout mWeatherInfoLayout;
	
	private TextView mCityName;
	private TextView mPublishTime;
	private TextView mWeatherDesp;
	private TextView mTemp1;
	private TextView mTemp2;
	private TextView mCurrentDate;
	
	private Button mSwitchCity,mRefreshWeather;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		
		initResourceRefs();
		
		String countyCode = getIntent().getStringExtra("county_code");
		if(!TextUtils.isEmpty(countyCode)){
			mPublishTime.setText("同步中...");
			mWeatherInfoLayout.setVisibility(View.VISIBLE);
			mCityName.setVisibility(View.VISIBLE);
			queryWeatherCode(countyCode);
		}else
			showWeather();
		
		
	}

	private void showWeather() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		mCityName.setText(prefs.getString("city_name", ""));
		mTemp1.setText(prefs.getString("temp1", ""));
		mTemp2.setText(prefs.getString("temp2", ""));
		mWeatherDesp.setText(prefs.getString("weather_desp", ""));
		mPublishTime.setText("今天"+prefs.getString("publish_time", "")+"发布");
		mCurrentDate.setText(prefs.getString("current_date", ""));
		mWeatherInfoLayout.setVisibility(View.VISIBLE);
		mCityName.setVisibility(View.VISIBLE);
		
		
		
	}

	private void queryWeatherCode(String countyCode) {
		String address = "http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
		queryFromServer(address,"countyCode");
		
	}

	private void queryFromServer(String address, final String type) {
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				if("countyCode".equals(type)){
					if(!TextUtils.isEmpty(response)){
						String[] array = response.split("\\|");
						if(array != null && array.length == 2){
							String weatherCode = array[1];
							queryWeatherInfo(weatherCode);
						}
					}
				}else if("weatherCode".equals(type)){
					Utility.handleWeatherResponse(WeatherActivity.this, response);
					runOnUiThread(new Runnable() {
						public void run() {
							showWeather();
						}
					});
				}
				
			}
			
			@Override
			public void onError(Exception e) {
				runOnUiThread(new Runnable() {
					public void run() {
						mPublishTime.setText("同步失败...");
					}
				});
				
			}
		});
		
	}

	protected void queryWeatherInfo(String weatherCode) {
		String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		queryFromServer(address,"weatherCode");
		
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
		mWeatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info);
		mCityName = (TextView) findViewById(R.id.city_name);
		mPublishTime = (TextView) findViewById(R.id.publish_time);
		mWeatherDesp = (TextView) findViewById(R.id.weather_desp);
		mTemp1 = (TextView) findViewById(R.id.temp1);
		mTemp2 = (TextView) findViewById(R.id.temp2);
		mCurrentDate = (TextView) findViewById(R.id.current_date);
		mSwitchCity = (Button) findViewById(R.id.switch_city);
		mRefreshWeather = (Button) findViewById(R.id.refresh_weather);
		mSwitchCity.setOnClickListener(this);
		mRefreshWeather.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.switch_city:
			Intent intent = new Intent(this,ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity", true);
			startActivity(intent);
			finish();
			break;
		case R.id.refresh_weather:
			mPublishTime.setText("同步中...");
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			String weatherCode = prefs.getString("weather_code", "");
			if(!TextUtils.isEmpty(weatherCode)){
				queryWeatherInfo(weatherCode);
			}
			break;
		default :
		    break;
		}
		
	}
	
	

}
