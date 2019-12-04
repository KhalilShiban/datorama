package com.datorama.automation.General.Basics;

import com.datorama.automation.General.AppEnums.GeneralEnums.RequestType;
import com.datorama.automation.General.conts.AppConsts;

public class UrlHelper {

	private RequestType requestType;
	private String requested;
	public static String url;
	
	public String urlBuilder(RequestType requestType, String requested ) {
		
		this.requestType = requestType;
		this.requested = requested;
		return buildUrl();

	}
	
	private String buildUrl()
	{
		url = (AppConsts.POSTMAN_ECHO_HOST + "/" + requestType.name() + "/" + requested);
		
		return url;
	}
}
