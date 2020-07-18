package com.zhyaoqi.platformautoplug.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SpUtils {

private static Context context;
	
	public static String PREFERENCE_NAME = "AndroidCommon";
	
	private static SharedPreferences setting;
	
	 /**
	  * 在程序初始化入口初始化该方法
	  * @param scontext
	  */
	public static void initSpUtil(Context scontext){
		if(context == null){
			synchronized(SpUtils.class){
				if(context == null){
					context = scontext;
					setting = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
				}
			}
		}
	}
	
	private SpUtils(){}
	
	public static void putString(String key, String value){
		checkInit(key);
		SharedPreferences.Editor editor = setting.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getString(String key, String defStr){
		checkInit(key);
		return setting.getString(key, defStr);
	}
	
	public static void putInt(String key, int value){
		checkInit(key);
		SharedPreferences.Editor editor = setting.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static int getInt(String key, int defValue){
		checkInit(key);
		return setting.getInt(key, defValue);
	}
	
	public static void putFloat(String key, float value){
		checkInit(key);
		SharedPreferences.Editor editor = setting.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	
	public static float getFloat(String key, float defValue){
		checkInit(key);
		return setting.getFloat(key, defValue);
	}
	
	public static void putBoolean(String key, boolean value){
		checkInit(key);
		SharedPreferences.Editor editor = setting.edit();
		editor.putBoolean(key, value); 
		editor.commit();
	}
	
	public static boolean getBoolean(String key, boolean defValue){
		checkInit(key);
		return setting.getBoolean(key, defValue);
	}
	
	public static void putLong(String key, long value){
		checkInit(key);
		SharedPreferences.Editor editor = setting.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public static long getLong(String key, long defValue){
		checkInit(key);
		return setting.getLong(key, defValue);
	}
	

	
	private static void checkInit(String key){
		if(context == null){
			throw new RuntimeException("SpUtils未初始化");
		}
		if(TextUtils.isEmpty(key)){
			throw new RuntimeException("key不能为空");
		}
	}

	/**
	 * 检查对应的key是否存在
	 */
	public static boolean contains(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, context.MODE_PRIVATE);
		return sp.contains(key);
	}
}
