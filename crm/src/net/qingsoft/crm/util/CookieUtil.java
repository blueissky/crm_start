package net.qingsoft.crm.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {

	private CookieUtil() {
	}
	/**
	 * 获取cookie的value 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request,String name){
		String str="";
		Cookie[] cookies=request.getCookies();
		for(int i=0;cookies!=null&&i<cookies.length;i++){
			if(cookies[i].getName().equals(name)){
				str=cookies[i].getValue();
				return str;
			}
		}
		return null;
	}
}
