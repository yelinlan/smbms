package com.yll.util;

public class StringUtils {
	public static boolean isNullOrEmpty(String s) {
		return s == null || s.trim().equals("") ;
	}
}