package org.mw.bi.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Volodymyr Diskovskyy
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

public class NoNull {
	
	public static final Logger logger =
		Logger.getLogger(NoNull.class.getName());
	
	/*
	 * this method returns abbreviate 
	 * substring with current length
	 *   
	 */
	public static String subString(String string, int length, String ende) {
		logger.debug("<>");
		string=StringUtils.defaultString(string);
		if(length<=0)
			return "";
		else if (string.length()>length){
			return string.substring(0,length)+StringUtils.defaultString(ende);
		}
		else
			return string;
	}

	
	public static int noNullTrimLength(String string) {
		logger.debug("<>");
		return (string==null)? 0 : string.trim().length();
	}		
	
	public static int noNullLength(String string) {
		logger.debug("<>");
		return StringUtils.defaultString(string).length();
	}		
	
	public static int noNullLength(byte[] array) {
		logger.debug("<>");
		return (array==null)? 0 : array.length;
	}		
	public static int noNullLength(Object[] array) {
		logger.debug("<>");
		return (array==null)? 0 : array.length;
	}	
	public static int noNullLength(int[] array) {
		return (array==null)? 0 : array.length;
	}	
	public static int noNullLength(long[] array) {
		return (array==null)? 0 : array.length;
	}	
	public static int noNullLength(double[] array) {
		return (array==null)? 0 : array.length;
	}	
	public static int noNullLength(float[] array) {
		return (array==null)? 0 : array.length;
	}

	public static int noNullSize(List<?> list) {
		return (list==null)? 0 : list.size();
	}

	public static int noNullSize(Map<?,?> map) {
		return (map==null)? 0 : map.size();
	}

	public static int noNullSize(Set<?> set) {
		return (set==null)? 0 : set.size();
	}


	public static <E> E defaultValue(E object, E defaultObject) {
		return object!=null ? object : defaultObject;
	}
	
	public static String objectToString(Object object){
		
		String returnString=null;
		if(object!=null){
			if(object instanceof String){
			returnString=(String) object;
			} else{
				returnString=object.toString();
			}
		}
		return returnString;
	}
}
