package com.huiweather.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoUpdateReceiver extends BroadcastReceiver{

	private static final String TAG = "AutoUpdateReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"onReceive()");
		intent.setClass(context, AutoUpdateService.class);
		context.startService(intent);
	}
	

}
