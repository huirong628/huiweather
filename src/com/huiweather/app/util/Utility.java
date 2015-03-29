package com.huiweather.app.util;

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

}
