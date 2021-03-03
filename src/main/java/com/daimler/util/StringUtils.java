package com.daimler.util;

public class StringUtils {
	public static boolean isNullOrEmpty(String str)
	{
		if(str == null)
			return true;
		
		if(str.trim().isEmpty())
			return true;
		
		return false;
	}

    public static boolean isNotEmpty(String str)
    {
        return !isNullOrEmpty(str);
    }
	
	public static String convert(Object obj) {
		if(obj == null)
			return null;
		
		return obj.toString();

	}
}
