package com.example.util;

import java.util.HashMap;
import java.util.Map;

public class CommonUtils {

	static Map<String,String> store = new HashMap<>();

	public static void updateToken(String token) {
		store.put("token", token);
	}
	
	public static boolean isTokenExist(String token) {
		Object object = store.get("token");
		if(object != null)
			return true;
		return false;
	}
	
	public static String getToken() {
		System.out.println("Toekn from store");
		return store.get("token") != null ? store.get("token").toString():"";
	}
}
