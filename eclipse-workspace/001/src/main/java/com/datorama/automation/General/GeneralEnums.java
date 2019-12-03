package com.datorama.automation.General;


public class GeneralEnums{


	public enum RequestType
	{
		Delay,
		Status
	}
	public enum StatusCode
	{
		 status200("200" , 200 ,"200 OK The request has succeeded")
		,status404("404" , 404 ,"404 Not Found")
		,status500("500" , 500 ,"500 Internal Server Error")
		,status503("503" , 503 ,"503 Service Unavailable")
		,status304("304", 304 , "Not Modified");
		
		private String value;
		private int numericValue;
		private String messageType;
		
		StatusCode(String value ,int numericValue , String messageType)
		{
			this.value = value;
			this.numericValue = numericValue;
			this.messageType = messageType;	
		}
		
		public String getValue()
		{
			return value;
		}
		
		public int getNumericValue()
		{
			return numericValue;
		}
		
		public String getMessageType()
		{
			return messageType;
		}

	}

}

