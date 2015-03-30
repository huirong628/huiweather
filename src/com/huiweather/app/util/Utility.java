package com.huiweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.huiweather.app.model.City;
import com.huiweather.app.model.County;
import com.huiweather.app.model.HuiWeatherDB;
import com.huiweather.app.model.Province;

/**
 * 处理和解析从服务器获取的数据
 * @author Huirong
 *
 */
public class Utility {
	private static final String TAG = "Utility";
	
	public synchronized static boolean handleProvinceResponse(HuiWeatherDB huiWeatherDB,String response){
		Log.d(TAG, "handleProvinceResponse(),response = "+response);
		// 数据格式：代号|城市，代号|城市
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");//先用逗号分隔
			if(allProvinces != null && allProvinces.length >0){
				for(String p :allProvinces){
					Log.i(TAG,"p = "+ p);
					String[] array = p.split("\\|");//再用单竖线分隔
					Province province = new Province();
					province.setPrivinceCode(array[0]);
					province.setProvinceName(array[1]);
					huiWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}

	public static boolean handleCityResponse(HuiWeatherDB huiWeatherDB,
			String response, int provinceId) {
		Log.d(TAG,"handleCityResponse(),response = "+response);
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");//先用逗号分隔
			if(allCities != null && allCities.length >0){
				for(String c :allCities){
					Log.i(TAG,"c = "+ c);
					String[] array = c.split("\\|");//再用单竖线分隔
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					huiWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}

	public static boolean handleCountyResponse(HuiWeatherDB huiWeatherDB,
			String response, int cityId) {
		Log.d(TAG,"handleCityResponse(),response = "+response);
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = response.split(",");//先用逗号分隔
			if(allCounties != null && allCounties.length >0){
				for(String c :allCounties){
					Log.i(TAG,"c = "+ c);
					String[] array = c.split("\\|");//再用单竖线分隔
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					huiWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Creates a new JSONObject with name/value mappings from the JSON string.
	 * json a JSON-encoded string containing an object.
	 */
	public static void handleWeatherResponse(Context context,String response){
		Log.i(TAG,"responese = "+response);
		try {
			JSONObject jsonObject = new JSONObject(response);
			
			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
			
			String cityName = weatherInfo.getString("city");
			String weatherCode = weatherInfo.getString("cityid");
			String temp1 = weatherInfo.getString("temp1");
			String temp2 = weatherInfo.getString("temp2");
			String weatherDesp = weatherInfo.getString("weather");
			String publishTime = weatherInfo.getString("time");
			saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Interface used for modifying values in a SharedPreferences object. 
	 * All changes you make in an editor are batched, 
	 * and not copied back to the original SharedPreferences until you call commit or apply
	 */

	private static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp,
			String publishTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
		SharedPreferences.Editor editor =  PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}

}
