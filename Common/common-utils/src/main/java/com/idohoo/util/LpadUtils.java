package com.idohoo.util;

/**
 * 对应LPAD函数
 * @author yangc
 *
 */
public class LpadUtils {
	public static String lpad(String str,int num,String pad){  
		String n_str=str;  
		if(str==null)  
			n_str= " ";  
		for(int i=str.length();i <num;i++){  
			n_str=pad+n_str;  
		}  
		return n_str;  
	} 
	
	public static void main(String[] args){
		System.out.println(LpadUtils.lpad("2364", 8, String.valueOf(0)));
	}
}
