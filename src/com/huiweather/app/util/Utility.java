package com.huiweather.app.util;

import android.text.TextUtils;
import android.util.Log;

import com.huiweather.app.model.City;
import com.huiweather.app.model.County;
import com.huiweather.app.model.HuiWeatherDB;
import com.huiweather.app.model.Province;

/**
 * ����ͽ����ӷ�������ȡ������
 * @author Huirong
 *
 */
public class Utility {
	private static final String TAG = "Utility";
	
	public synchronized static boolean handleProvinceResponse(HuiWeatherDB huiWeatherDB,String response){
		Log.d(TAG, "handleProvinceResponse(),response = "+response);
		// ���ݸ�ʽ������|���У�����|����
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");//���ö��ŷָ�
			if(allProvinces != null && allProvinces.length >0){
				for(String p :allProvinces){
					Log.i(TAG,"p = "+ p);
					String[] array = p.split("\\|");//���õ����߷ָ�
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
			String[] allCities = response.split(",");//���ö��ŷָ�
			if(allCities != null && allCities.length >0){
				for(String c :allCities){
					Log.i(TAG,"c = "+ c);
					String[] array = c.split("\\|");//���õ����߷ָ�
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
			String[] allCounties = response.split(",");//���ö��ŷָ�
			if(allCounties != null && allCounties.length >0){
				for(String c :allCounties){
					Log.i(TAG,"c = "+ c);
					String[] array = c.split("\\|");//���õ����߷ָ�
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
