package com.huiweather.app.service;

import com.huiweather.app.util.HttpCallbackListener;
import com.huiweather.app.util.HttpUtil;
import com.huiweather.app.util.Utility;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

public class AutoUpdateService extends Service {

	/*
	 * static :强调只有一份 final :说明是常量 TAG：恒定初始值，编译期常量
	 */
	private static final String TAG = "AutoUpdateService";

	private Looper mServiceLooper;

	private ServiceHandler mServiceHandler;

	// private final BroadcastReceiver mReceiver = new HeadsetEventReceiver();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");
		HandlerThread thread = new HandlerThread(TAG, 1);
		thread.start();
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
		// IntentFilter intentFilter = new
		// IntentFilter(Intent.ACTION_HEADSET_PLUG);
		// getApplicationContext().registerReceiver(mReceiver, intentFilter);
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 * 
	 * Message:线程间传递的消息，可以在内部携带少量的信息，用于在不同线程间传递数据。
	 * Message msg = handler.obtainMessage();
	 * obtainmessage()是从消息池中拿来一个msg 不需要另开辟空间;
	 * Message msg = new Message();
	 * new需要重新申请，效率低，
	 * obtianmessage可以循环利用；
	 * use Handler.obtainMessage(),instead of msg = new Message();  
	 * because if there is already an Message object,that not be used by   
	 * any one ,the system will hand use that object,so you don't have to   
	 * create an object and allocate memory.  
	 * it is also another example of object recycling and reusing in android.  
	 * 
	 */
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand()");
		Message msg = mServiceHandler.obtainMessage();
		msg.arg1 = startId;
		msg.obj = intent;
		mServiceHandler.sendMessage(msg);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestory()");
	}
	
	/*
	 * Handler:用于发送和处理消息的
	 * 
	 */

	private class ServiceHandler extends Handler {

		public ServiceHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Intent intent = (Intent) msg.obj;
			if(intent == null){
				Log.d(TAG,"intent is null,so return");
				return ;
			}
			handleUpdateWeather();
		}
		
		

	}

	private void handleUpdateWeather() {
		Log.d(TAG,"handleUpdateWeather");
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("weather_code", "");
		String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				Utility.handleWeatherResponse(AutoUpdateService.this, response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

}
