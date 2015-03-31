package com.huiweather.app.model;

import java.util.ArrayList;
import java.util.List;

import com.huiweather.app.db.HuiWeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HuiWeatherDB {
	public static final String DB_NAME = "hui_weather";
	private static final int VERSION = 1;
	private static HuiWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	
	private HuiWeatherDB(Context context){
		HuiWeatherOpenHelper dbHelper = new HuiWeatherOpenHelper(context,DB_NAME,null,VERSION );
		db = dbHelper.getWritableDatabase();
	}
	
	/*
	 * ��ʵ����
	 * ��֤ȫ�ַ�Χ��ֻ����һ��ʵ��
	 */
	public synchronized static HuiWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new HuiWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getPrivinceCode());
			db.insert("Province", null, values);
		}
	}
	
	public void saveCity(City city){
		if(city != null){
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	
	public void saveCounty(County county){
		if(county != null){
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	
	public List<Province> loadProvince(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				province.setId(id);
				String provinceName = cursor.getString(cursor.getColumnIndex("province_name"));
				province.setProvinceName(provinceName);
				String provinceCode = cursor.getString(cursor.getColumnIndex("province_code"));
				province.setPrivinceCode(provinceCode);
				list.add(province);
			}while(cursor.moveToNext());
		}
		if(cursor!=null){
			cursor.close();
		}
		return list;
	}

	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ? ", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		if(cursor!= null)
			cursor.close();
		return list;
	}
	
	public List<County> loadCounties(int cityId) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id = ? ", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
		}
		if(cursor!= null)
			cursor.close();
		return list;
	}
	
}
